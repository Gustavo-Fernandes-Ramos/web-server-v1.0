package request;

import java.util.ArrayList;
import java.util.HashMap;

import header.HeaderField;

@SuppressWarnings("unused")
public class Request {
	private String method; // GET
	private String URL; // someDir/page.html
	private String version; // HTTP/1.0
	
	private HashMap<String, HeaderField> headerFields;
	
	private String body;
	
	public Request(String method, String uRL, String version) {
		super();
		this.method = method;
		this.URL = uRL;
		this.version = version;
		this.headerFields = new HashMap<String, HeaderField>();
	}
	
	public Request(String method, String uRL, String version, HashMap<String, HeaderField> headerFields, String body) {
		super();
		this.method = method;
		this.URL = uRL;
		this.version = version;
		this.headerFields = headerFields;
		this.body = body;
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
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
