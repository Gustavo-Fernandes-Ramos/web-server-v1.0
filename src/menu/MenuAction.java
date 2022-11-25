package menu;

public interface MenuAction {
	default String execute(Object ...params) { return "None";}
}
