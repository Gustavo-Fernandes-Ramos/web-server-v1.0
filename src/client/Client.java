package client;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
	
	private static final String newLine = "\r\n";
	
	public static void main(String[] args) {  
		try{      
			Socket s = new Socket("localhost",8080);
			
			//BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			OutputStream out = new BufferedOutputStream(s.getOutputStream());
			PrintStream pout = new PrintStream(out);
			/*recursos/pt/BR/html/cachorro.html HTTP/1.0"*/
			String url = "recursos\\pt\\BR\\html\\cachorro.html";
			pout.print(
					"GET" + " " + url + " " + "HTTP/1.0" + newLine + 
					"User-Agent:" + "Mozilla/4.0" + newLine +
					"Accept:" + "text/html" + newLine + 
					"Host:" + "localhost:8080" + newLine + 
					"Accept-Language:" + "en-US,pt-BR" + newLine + newLine + 
					"aqui esta o conteudo (body)"
					);
			
			pout.close();
			s.close(); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}