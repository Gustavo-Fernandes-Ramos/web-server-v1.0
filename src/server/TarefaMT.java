package server;

import java.net.ServerSocket;
import java.net.Socket;

public class TarefaMT extends TarefaST implements Runnable {
	Thread threadWorker;
	
	public TarefaMT(ServerSocket serverSocket, Socket clientRequest) {
		super(serverSocket, clientRequest);
	}

	@Override
	public void run() {
		super.executarTarefa();
	}
	
	//@Override
	public void executar() {
		threadWorker = new Thread(this);
		threadWorker.start();
	}
}
