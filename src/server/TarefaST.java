package server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
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

	TarefaST(ServerSocket tcpServerSocket, Socket clientConnection) {
		this.tcpServerSocket = tcpServerSocket;
		this.clientConnection = clientConnection;
	}
	
	public void executar() {
		executarTarefa();
	}
	
	@SuppressWarnings("unused")
	public void executarTarefa() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
			OutputStream out = new BufferedOutputStream(clientConnection.getOutputStream());
			PrintStream pout = new PrintStream(out);
			
			String request = in.readLine();
			
			if (request == null) {
				pout.print("HTTP/1.0 400 Bad Request" + newLine + newLine);
				return;
			}
			
			String requestLine[] = request.split(" ");
			
			String method = requestLine[0];
			String path = requestLine[1];
			String version = requestLine[2];
			
			if (!method.equals("GET") || !(version.equals("HTTP/1.0") || version.equals("HTTP/1.1"))) {
				pout.print("HTTP/1.0 400 Bad Request" + newLine + newLine);
				return;
			}
			
			String headerName, headerValue;
			
			String userAgent = null, host = null;
			List<String> accept = null, acceptLanguage = null;
			
			int index;
			request = in.readLine();
			
			while(request != null && request.length() > 0) {
				
				if((index = request.indexOf(':')) == -1) pout.print("HTTP/1.0 400 Bad Request" + newLine + newLine);
				
				headerName = request.substring(0, index);
				headerValue = request.substring(index+1, request.length());
				
				String[] acceptValues, acceptLanguageValues;
				
				switch(headerName) {
				case "User-Agent":
					if(userAgent != null) {
						pout.print("HTTP/1.0 400 Bad Request" + newLine + newLine);
						return;
					}
					userAgent = headerValue;
					break;
					
				case "Host":
					if(host != null) {
						pout.print("HTTP/1.0 400 Bad Request" + newLine + newLine);
						return;
					}
					host = headerValue;
					break;
					
				case "Accept":
					if(accept != null) {
						pout.print("HTTP/1.0 400 Bad Request" + newLine + newLine);
						return;
					}
					accept = new ArrayList<String>();
					acceptValues = headerValue.split(",");
					
					for(int i = 0 ; i < acceptValues.length ; i++) {
						if(acceptValues[i].equals("text/html")) {
							accept.add(acceptValues[i]);
						}	
					}
					
					break;
					
				case "Accept-Language":
					if(acceptLanguage != null) {
						pout.print("HTTP/1.0 400 Bad Request" + newLine + newLine);
						return;
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
					pout.print("HTTP/1.0 400 Bad Request" + newLine + newLine);
					return;
				}//fim do switch
				
				request = in.readLine();
			} //fim do while
			
			
			
			
			SimpleFile f = new SimpleFile(path);
			
			if(!f.exists()) {
				pout.print("HTTP/1.0 404 Not Found" + newLine + newLine);
				return;
			}
			
			long lastModified = f.lastModified();
			String response = f.readFile();

			/*pout.print*/System.out.println(
					version + " " + "200 OK" + newLine + 
					"Date:" + DateFormat.formatDate(new Date()) + newLine + 
					"Server:" + "Web-Server/v1.0" + newLine + 
					"Last-Modified:" + DateFormat.formatDate(new Date(lastModified)) + newLine + 
					"Content-Type:" + accept + newLine + 
					"Content-length:" + f.length() + newLine + newLine + 
					response
					);
			
			pout.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	 
}
