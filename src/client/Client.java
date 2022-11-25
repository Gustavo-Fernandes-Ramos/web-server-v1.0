package client;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
	
	private static final String newLine = "\r\n";
	
	public static void main(String[] args) {  
		try{      
			Socket s=new Socket("localhost",8080);  
			OutputStream out = new BufferedOutputStream(s.getOutputStream());
			PrintStream pout = new PrintStream(out);
			
			pout.print(
					"GET dir/page.html HTTP/1.0" + newLine + 
					"Content-Type: text/plain" + newLine + 
					"Date: " + "Fri, 16 Jan 1970 07:35:33 GMT" + newLine + 
					"Content-length: " + "300" + newLine + newLine + 
					"aqui esta o conteudo (body)"
					);
			pout.close();
			s.close();  
		}catch(Exception e){
			System.out.println(e);  
		}
	}
}