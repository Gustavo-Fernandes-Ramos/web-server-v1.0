package header.response;

import header.HeaderField;

public class ServerField extends HeaderField {
	
	private String serverName;

	public ServerField(String name, String serverName) {
		super(name);
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@Override
	public String convertToString() {
		return this.getName() + ":" + this.getServerName();
	}

	@Override
	public void convertToClass(String strClass) {
		String[] strAttributes = strClass.split(":");
		
		this.setName(strAttributes[0]);
		this.setServerName(strAttributes[1]);
	}
	
}
