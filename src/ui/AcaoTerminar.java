package ui;

import java.security.InvalidParameterException;

import menu.MenuAction;
import server.Server;

public class AcaoTerminar implements MenuAction {
	@Override
	public String execute(Object ...params) {
		if (params == null) {
			throw new InvalidParameterException("Faltou o Servidor");
		}
		Object obj = params[0];
		if (!(obj instanceof Server)) {
			throw new InvalidParameterException("Parametro Invalido: " + obj);				
		}
		Server srv = (Server)obj;
		srv.finish();
		return "terminou";
	}
}