package client;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	
	private static final String newLine = "\r\n";
	private static final String newLineTwice = "\r\n\r\n";
	
	public static void main(String[] args) { 
		try{  
			String host = "localhost";
			int port = 8080;
			Socket s = new Socket(host, port);  
			
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
			DataInputStream din=new DataInputStream(s.getInputStream());  
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
			  
			String answer, url = "", body = "mensagem do body"; 
			
			while(!url.equals("stop")){ 
				System.out.print("digite uma url valida: ");
				url=br.readLine(); 
				
				/*enviando requisicao*/
				
				String request =
						"GET" + " " + url + " " + "HTTP/1.0" + newLine + 
						"User-Agent:" + "Mozilla/4.0" + newLine +
						"Accept:" + "text/html" + newLine + 
						"Host:" + host + ":" + port + newLine + 
						"Accept-Language:" + "en-US,pt-BR" + newLine + newLine + 
						body
						;
				dout.writeUTF(request); 
				dout.flush();
				
				/*recebendo resposta*/
				
				answer=din.readUTF();  
				System.out.println(answer);
				
				if(!url.equals("stop")) {
					
					if(Desktop.isDesktopSupported()){
						File file = new File("page.html");
						
						file.createNewFile();
						
						FileWriter writer = new FileWriter(file.getAbsolutePath());
						writer.write(answer.split(newLineTwice)[1]);
						writer.close();
						
					    Desktop desktop = Desktop.getDesktop();
					    desktop.open(file);
				    }
				}
			}    
			dout.close();  
			s.close();  
			
		}catch(IOException e){
			System.out.println("Ocorreu uma IOException do lado cliente!");
		}catch(Exception e){
			System.out.println("Ocorreu uma Exception do lado cliente!");
		}
	}
}