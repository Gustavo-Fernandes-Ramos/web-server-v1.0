package client;

import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
	
	public static void main(String[] args) {  
		try{      
			Socket s=new Socket("localhost",8080);  
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
			dout.writeUTF("Hello Server");  
			dout.flush();  
			dout.close();  
			s.close();  
		}catch(Exception e){
			System.out.println(e);  
		}
	}
}