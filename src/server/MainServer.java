package server;

import ui.TextUI;

public class MainServer {
	public static void main(String[] args) {
		IServer server = new Server(8080, true);
		TextUI ui = new TextUI(server);
		ui.display();
	}
}
