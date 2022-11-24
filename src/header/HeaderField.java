package header;

public abstract class HeaderField {
	
	private String name;
	
	public HeaderField(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public abstract String convertToString();
	public abstract void convertToClass(String strClass);
}
