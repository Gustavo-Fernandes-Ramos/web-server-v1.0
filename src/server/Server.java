package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

@SuppressWarnings("unused")
public class Server implements IServer {

	private int port = 8080;
	private boolean paused = true;
	private boolean forever = false;
	private boolean isMultiThread;
	
	private Thread threadWorker = null;
	private Object monitor = new Object();
	
	private ServerSocket socket = null;
	
	private static final String newLine = "\r\n";
	
	public Server(boolean isMultiThread) {
		this.isMultiThread = isMultiThread;
	}
	
	public Server(int port, boolean isMultiThread) {
		this.port = port;
		this.isMultiThread = isMultiThread;
	}
	
	@Override
	public void start() { }

	@Override
	public void pause() { 
		this.paused = true;
	}

	@Override
	public void toContinue() { }

	@Override
	public void stop() {
		this.forever = false;
		threadWorker = null;
	}
	
	@Override
	public void finish() {
		closeSocket();
		forever = false;
	}
	
	public void execute() {
		this.paused = false;
		this.forever = true;
		Socket clientConnection;
		
		System.out.println("Servidor Web iniciado na porta " + port);
		
		while (forever) {
			System.out.println("aguardando request");
			this.setSocket();
			
			if(this.socket != null) {
				clientConnection = this.connect();
				
				if(clientConnection != null) {
					
					if (this.isMultiThread) {
						TarefaMT tarefaMT = new TarefaMT(this.socket, clientConnection);
						tarefaMT.executar();
					} else {
						TarefaST tarefaST = new TarefaST(this.socket, clientConnection);
						tarefaST.executar();
					}
				}
			}
			
		}
		System.out.println("parou");
	}
	
	public void setSocket() {
		try {
			if (this.socket == null || this.socket.isClosed()) {
				this.socket = new ServerSocket(port);
				this.socket.setSoTimeout(3*1000);
			}
			
		} catch (SocketException e) {
			System.out.println("Ocorreu um SocketException!");
		} catch (IOException e) {
			System.out.println("Ocorreu uma IOException!");
		}
	}
	
	public Socket connect() {
		Socket connect = null;
		
		try {
			connect = this.socket.accept();
		} catch(SocketTimeoutException e) {
			System.out.println("Cansou de esperar...\"reiniciando\"");
		}catch (IOException e) {
			System.out.println("Ocorreu uma IOException!");
		}
		return connect;
	}
	
	public void closeSocket() {
		try {
			if (this.socket != null) {
				this.socket.close();
				this.socket = null;
			}
		} catch (IOException e) { 
			System.out.println("Incapaz de finalizar o socket");
		}
	}

	

}
