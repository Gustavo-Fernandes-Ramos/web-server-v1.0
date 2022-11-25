package server;

import java.net.ServerSocket;
import java.net.Socket;

public class TarefaST {
	ServerSocket tcpServerSocket;
	Socket clientRequest;

	TarefaST(ServerSocket tcpServerSocket, Socket clientRequest) {
		this.tcpServerSocket = tcpServerSocket;
		this.clientRequest = clientRequest;
	}
	
	public void executar() {
		executarTarefa();
	}
	public void executarTarefa() {
		System.out.println("chegou mensagem");
	}
}
