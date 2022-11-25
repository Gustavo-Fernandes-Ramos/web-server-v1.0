package menu;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Menu {
	private List<String> names;
	private List<MenuAction> actions;
	private String msg;

	public Menu() {
		names = new ArrayList<>();
		actions = new ArrayList<>();
		msg = "Escolha uma opcao...";
	}
	
	public Menu(String msg) {
		names = new ArrayList<>();
		actions = new ArrayList<>();
		this.msg = msg;
	}

	public void addItem(String name, MenuAction action) {
		check1(name);
		check(action);
		names.add(name);
		actions.add(action);
	}

	public void removeItem(String name) {
		check2(name);
		int pos = names.indexOf(name);
		names.remove(pos);
		actions.remove(pos);
	}
	
	public String getMessage() {
		return msg;
	}
	
	public void setMessage(String str) {
		if (str == null || str.isEmpty()) {
			throw new InvalidParameterException("Invalid Message: " + str);
		}		
		msg = str;
	}
	
	public String getItemName(int i) {
		if (i < 0 || i >= names.size()) {
			throw new InvalidParameterException("Invalid name pos: " + i);			
		}
		return names.get(i);
	}
	
	public MenuAction getAction(String name) {
		check2(name);
		int pos = names.indexOf(name);
		return actions.get(pos);
	}
	
	public MenuAction getAction(int i) {
		if (i < 0 || i >= actions.size()) {
			throw new InvalidParameterException("Invalid action pos: " + i);			
		}
		return actions.get(i);
	}
	
	public String[] getNames() {
		if (names.size() == 0) {
			return new String[0];
		}
		return names.toArray(new String[names.size()]);
	}
	
	public MenuAction[] getActions() {
		if (actions.size() == 0) {
			return new MenuAction[0];
		}
		return actions.toArray(new MenuAction[actions.size()]);
	}

	public void display() {
		println(getMessage());
		for (int i = 0; i < names.size(); i++) {
			println("\t" + i + ": " + names.get(i));
		}
	}
	
	
	private void println(String str) {
		System.out.println(str);
	}
	
	private void check2(String str) {
		if (str == null || str.isEmpty()) {
			throw new InvalidParameterException("Name error: " + str);
		}		
		if (names.indexOf(str) < 0) {
			throw new InvalidParameterException("Invalid name");						
		}
	}
	
	private void check1(String str) {
		if (str == null || str.isEmpty()) {
			throw new InvalidParameterException("Name error: " + str);
		}		
		if (names.indexOf(str) >= 0) {
			throw new InvalidParameterException("Duplicate item");						
		}
	}

	private void check(MenuAction action) {
		if (action == null) {
			throw new InvalidParameterException("Invalid action:" + action);			
		}
	}

}
