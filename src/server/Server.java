package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Server implements IServer, Runnable{

	private int port = 8080;
	private boolean paused;
	private boolean stopped = true;
	private boolean isMultiThread;
	
	private Thread threadWorker = null;
	private Object monitor = new Object();
	
	private ServerSocket socket = null;
	
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
	public void toContinue() {
		if (this.stopped) {
			System.out.println("SERVIDOR FAKE ESTA PARADO.");
			return;
		}
		if (threadWorker == null) {
			return;
		}
		synchronized(monitor) {
			monitor.notify();
		}
	}

	@Override
	public void stop() {
		this.stopped = true;
		threadWorker = null;
	}
	
	@Override
	public void finish() {
		closeSocket();
		System.out.println("TERMINOU SERVIDOR FAKE.");
		this.stopped = true;
	}
	
	@Override
	public void execute() {
		if (this.paused) { // caso tente iniciar, mas somente esta pausado entao continua
			toContinue();
		}
		if (!this.stopped) { // nao esta pausado e nao esta parado, entao faz nada, pois ja esta executando
			return;
		}
		if (threadWorker == null) {
			threadWorker = new Thread(this);
		}
		threadWorker.start();
		
	}
	
	@Override
	public void run() {
		_execute();
	}
	
	public void _execute() {
		System.out.println("INICIOU SERVIDOR FAKE");
		this.stopped = false;
		Socket clientConnection;
		
		while (true) {
			try {
				if(this.stopped) {
					break;
				}
				if(this.paused) {
					System.out.println("PAUSOU SERVIDOR FAKE");
					synchronized(monitor) {
						monitor.wait();
					}
					System.out.println("CONTINUOU SERVIDOR FAKE");
					this.paused = false;
				}
				Thread.sleep(1*1000);
				
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
				
				System.out.println("SERVIDOR FAKE EXECUTANDO");
				
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
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
			
		} catch(IOException e) {
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



