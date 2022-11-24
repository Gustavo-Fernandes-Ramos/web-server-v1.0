package header.request;

import java.util.ArrayList;
import java.util.List;

import header.HeaderField;

public class AcceptField extends HeaderField {
	
	private List<String> type;
	private List<String> subtype;
	
	public AcceptField(String name) {
		super(name);
		this.type = new ArrayList<String>();
		this.subtype = new ArrayList<String>();
	}
	
	public void add(String type, String subtype) {
		this.type.add(type);
		this.subtype.add(subtype);
	}
	
	public void remove(int index) {
		this.type.remove(index);
		this.subtype.remove(index);
	}
	
	public String getType(int index) {
		return this.type.get(index);
	}
	
	public String getSubtype(int index) {
		return this.subtype.get(index);
	}

	@Override
	public String convertToString() {
		String strClass = this.getName() + ":";
		int size = this.type.size();
		
		if(size > 0) {
			strClass += this.getType(0) + "/" + this.getSubtype(0);
					
			for(int i = 1 ; i < size ; i++) {
				strClass += "," + this.getType(i) + "/" + this.getSubtype(i);
			}
		}
		return strClass;
	}

	@Override
	public void convertToClass(String strClass) {
		
		String[] strAttributes = strClass.split(":");
		this.setName(strAttributes[0]);
		
		String[] strValues = strAttributes[1].split(",");
		
		for(int i = 0 ; i < strValues.length ; i++) {
			
			String[] strTypes = strValues[i].split("/");
			this.add(strTypes[0], strTypes[1]);
		}
		
	}

}
