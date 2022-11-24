package server;

public interface IServer {
	void start();
	void pause();
	void toContinue();
	void execute();
	void stop();
	void finish();
}
