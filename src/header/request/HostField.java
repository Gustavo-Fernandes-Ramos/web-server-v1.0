package header.request;

import header.HeaderField;

public class HostField extends HeaderField {
	private String hostName;
	private int port = 8080;

	public HostField(String name, String hostName, int port) {
		super(name);
		this.hostName = hostName;
		this.port = port;
	}
	
	public String getHostName() {
		return hostName;
	}
	
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public String convertToString() {
		return this.getName() + ":" + this.getHostName() + ":" + Integer.toString(this.port);
	}
	
	@Override
	public void convertToClass(String strClass) {
		String[] strAttributes = strClass.split(":");
		
		this.setName(strAttributes[0]);
		this.setHostName(strAttributes[1]);
		this.setPort(Integer.parseInt(strAttributes[2]));
	}
}
