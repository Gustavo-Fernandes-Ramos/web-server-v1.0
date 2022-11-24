package header.request;

import header.HeaderField;

public class UserAgentField extends HeaderField {
	
	private String agentName;

	public UserAgentField(String name, String agentName) {
		super(name);
		this.agentName = agentName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	@Override
	public String convertToString() {
		return this.getName() + ":" + this.getAgentName();
	}

	@Override
	public void convertToClass(String strClass) {
		String[] strAttributes = strClass.split(":");
		
		this.setName(strAttributes[0]);
		this.setAgentName(strAttributes[1]);
	}
	
}
