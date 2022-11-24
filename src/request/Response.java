package request;

import java.util.HashMap;

import header.HeaderField;

@SuppressWarnings("unused")
public class Response {
	private String version; // HTTP/1.0
	private String status; // 200
	private String statusMsg; // ok
	
	private HashMap<String, HeaderField> headerFields;
	
	private String body;
	
	public Response(String version, String status, String statusMsg, HashMap<String, HeaderField> headerFields,
			String body) {
		super();
		this.version = version;
		this.status = status;
		this.statusMsg = statusMsg;
		this.headerFields = headerFields;
		this.body = body;
	}

	public void add(HeaderField headerField) {
		this.headerFields.put(headerField.getName(), headerField);
	}
	
	public void remove(String name) {
		this.headerFields.remove(name);
	}
	
	public HeaderField get(String name) {
		return this.headerFields.getOrDefault(name, null);
	}
	
	public boolean exists(HeaderField headerField) {
		return this.headerFields.containsKey(headerField.getName());
	}

}

