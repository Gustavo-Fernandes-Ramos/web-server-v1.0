package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import date.DateFormat;
import file.SimpleFile;

public class TarefaST {
	ServerSocket tcpServerSocket;
	Socket clientConnection;

	private static final String newLine = "\r\n";
	private static final String newLineTwice = "\r\n\r\n";
	
	TarefaST(ServerSocket tcpServerSocket, Socket clientConnection) {
		this.tcpServerSocket = tcpServerSocket;
		this.clientConnection = clientConnection;
	}
	
	public void executar() {
		executarTarefa();
	}
	
	public void executarTarefa() {
		try {
			
			DataInputStream din=new DataInputStream(clientConnection.getInputStream());  
			DataOutputStream dout=new DataOutputStream(clientConnection.getOutputStream()); 

			String str="";
			
			while(!str.equals("stop")){ 
				
				String userAgent = null, host = null;
				List<String> accept = null, acceptLanguage = null;
				
				str=din.readUTF(); 
				
				if(str == null) {
					dout.writeUTF("HTTP/1.0 400 Bad Request" + newLine + newLine);
					dout.flush();
					continue;
				}
				
				if(str.equals("stop")) {
					dout.writeUTF("conexao tcp com o servidor foi finalizada!" + newLine + newLine);
					dout.flush();
					din.close();  
					clientConnection.close(); 
					return;
				}
				
				String[] segments =  str.split(newLineTwice);
				String header = segments[0];
				//String body = segments[1];
				
				String[] lines = header.split(newLine);
				
				String requestLine[] = lines[0].split(" ");
				
				String method = requestLine[0];
				
				if(!requestLine[1].startsWith("http://servidor/")) {
					dout.writeUTF("HTTP/1.0 400 Bad Request" + newLine + newLine);  
					dout.flush();
					continue;
				}
				
				String path = requestLine[1].substring(requestLine[1].lastIndexOf('/')+1, requestLine[1].length());

				String version = requestLine[2];
				
				if (!method.equals("GET") || !(version.equals("HTTP/1.0") || version.equals("HTTP/1.1"))) {
					dout.writeUTF("HTTP/1.0 400 Bad Request" + newLine + newLine);
					dout.flush();
					continue;
				}
				
				for(int j = 1 ; j < lines.length ; j++) {
					String headerLine = lines[j];
					int index = headerLine.indexOf(':');
					if(index == -1 || index+1 == headerLine.length()) {
						dout.writeUTF("HTTP/1.0 400 Bad Request" + newLine + newLine);
						dout.flush();
					}
					
					String headerName = headerLine.substring(0, index);
					String headerValue = headerLine.substring(index+1, headerLine.length());
					
					String[] acceptValues, acceptLanguageValues;
					
					switch(headerName) {
					case "User-Agent":
						if(userAgent != null) {
							dout.writeUTF("HTTP/1.0 400 Bad Request" + newLine + newLine); 
							dout.flush();
							continue;
						}
						userAgent = headerValue;
						break;
						
					case "Host":
						if(host != null) {
							dout.writeUTF("HTTP/1.0 400 Bad Request" + newLine + newLine);
							dout.flush();
							continue;
						}
						host = headerValue;
						break;
						
					case "Accept":
						if(accept != null) {
							dout.writeUTF("HTTP/1.0 400 Bad Request" + newLine + newLine);
							dout.flush();
							continue;
						}
						accept = new ArrayList<String>();
						acceptValues = headerValue.split(",");
						
						for(int i = 0 ; i < acceptValues.length ; i++) {
							if(acceptValues[i].equals("text/html")) accept.add(acceptValues[i]);
						}
						
						break;
						
					case "Accept-Language":
						if(acceptLanguage != null) {
							dout.writeUTF("HTTP/1.0 400 Bad Request" + newLine + newLine);
							dout.flush();
							continue;
						}
						acceptLanguage = new ArrayList<String>();
						acceptLanguageValues = headerValue.split(",");
						
						for(int i = 0 ; i < acceptLanguageValues.length ; i++) {
							if(acceptLanguageValues[i].equals("pt-BR") || acceptLanguageValues[i].equals("en-US")) {
								acceptLanguage.add(acceptLanguageValues[i]);
							}
						}
						
						break;
					
					default:
						dout.writeUTF("HTTP/1.0 400 Bad Request" + newLine + newLine);
						dout.flush();
						continue;
					}//fim do switch
					
				}//fim do for
				String response;
				
				if(path.startsWith("calculadora?")) {
					System.out.println(path);
					QueryStringHandler handler = new QueryStringHandler(path.split("\\?")[1]);
					if(handler.isValid()) {
						response = "<!DOCTYPE html><html><body><h2>" + handler.getResult() + "<h2></body></html>";
						dout.writeUTF(
								version + " " + "200 OK" + newLine + 
								"Date:" + DateFormat.formatDate(new Date()) + newLine + 
								"Server:" + "Web-Server/v1.0" + newLine + 
								"Content-length:" + response.length() + newLine + newLine + 
								response
								);
						
					}else {
						dout.writeUTF("HTTP/1.0 400 Bad Request" + newLine + newLine);
						dout.flush();
						continue;
					}
					
				}else {
					SimpleFile f = new SimpleFile("recursos\\html\\" + path);
					
					if(!f.exists()) {
						dout.writeUTF("HTTP/1.0 404 Not Found" + newLine + newLine);
						dout.flush();
						continue;
					}
						
					response = f.readFile();
					dout.writeUTF(
							version + " " + "200 OK" + newLine + 
							"Date:" + DateFormat.formatDate(new Date()) + newLine + 
							"Server:" + "Web-Server/v1.0" + newLine + 
							"Last-Modified:" + DateFormat.formatDate(new Date(f.lastModified())) + newLine + 
							"Content-Type:" + accept.get(0) + newLine + 
							"Content-length:" + f.length() + newLine + newLine + 
							response
							);
					
					dout.flush();
				}
			}  //fim do while
			
			din.close();  
			clientConnection.close(); 
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
