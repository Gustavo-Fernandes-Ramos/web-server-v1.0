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
			
			String requestLine[] = request.split("\n");
			
			String method = requestLine[0];
			String path = requestLine[1];
			String version = requestLine[2];
			
			if (!method.equals("GET") || !(version.equals("HTTP/1.0") || version.equals("HTTP/1.1"))) {
				pout.print("HTTP/1.0 400 Bad Request" + newLine + newLine);
				return;
			}
			
			int index;
			while(request != null && request.length() > 0) {
				
				request = in.readLine();
				index = request.indexOf(':');
				
				String headerName = request.substring(0, index);
				String headerValue = request.substring(index+1, request.length());
				
				String userAgent = null;
				String host = null;
				List<String> accept = null;
				List<String> acceptLanguage = null;
				
				String[] acceptValues, acceptLanguageValues;
				
				switch(headerName) {
				case "User-Agent":
					if(userAgent != null) return;
					userAgent = headerValue;
					break;
					
				case "Host":
					if(host != null) return;
					host = headerValue;
					break;
					
				case "Accept":
					if(accept != null) return;
					accept = new ArrayList<String>();
					acceptValues = headerValue.split(",");
					
					for(int i = 0 ; i < acceptValues.length ; i++) {
						if(acceptValues[i].equals("text/html")) {
							accept.add(acceptValues[i]);
						}	
					}
					
					break;
					
				case "Accept-Language":
					if(acceptLanguage != null) return;
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
				}
				
			}
			SimpleFile f = new SimpleFile(path);
			f.createFile();
			String response = f.readFile();
			
			//--------------------------------------------------------------------------------------
			
			Date date = new Date();
			pout.print(
					"HTTP/1.0 200 OK" + newLine + 
					"Content-Type: text/plain" + newLine + 
					"Date: "+ date + newLine + 
					"Content-length: " + response.length() + newLine + newLine + 
					response
					);
			
			pout.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	 
}
