package header.request;

import java.util.ArrayList;
import java.util.List;

import header.HeaderField;

public class AcceptLanguageField extends HeaderField {

	private List<String> language;
	private List<String> country;
	
	public AcceptLanguageField(String name) {
		super(name);
		this.language = new ArrayList<String>();
		this.country = new ArrayList<String>();
	}
	
	public void add(String language, String country) {
		this.language.add(language);
		this.country.add(country);
	}
	
	public void remove(int index) {
		this.language.remove(index);
		this.country.remove(index);
	}
	
	public String getLanguage(int index) {
		return this.language.get(index);
	}
	
	public String getCountry(int index) {
		return this.country.get(index);
	}

	@Override
	public String convertToString() {
		String strClass = this.getName() + ":";
		int size = this.country.size();
		
		if(size > 0) {
			strClass += this.getLanguage(0) + "-" + this.getCountry(0);
					
			for(int i = 1 ; i < size ; i++) {
				strClass += "," + this.getLanguage(i) + "-" + this.getCountry(i);
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
			
			String[] strTypes = strValues[i].split("-");
			this.add(strTypes[0], strTypes[1]);
		}
	}
	
	public static void main(String args[]) {
		AcceptLanguageField alf = new AcceptLanguageField("Accept-Language");
		alf.add("pt", "BR");
		alf.add("en", "US");
		
		System.out.println(alf.convertToString());
	}
		
	
}
