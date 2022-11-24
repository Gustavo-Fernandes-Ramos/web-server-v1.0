package header.response;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import header.HeaderField;

public class LastModifiedField extends HeaderField{
	
	private Date date;
	
	public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
	
	private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

	public LastModifiedField(String name, long date) {
		super(name);
		this.date = new Date(date);
	}
	
	public LastModifiedField(String name, Date date) {
		super(name);
		this.date = date;
	}
	
	public String formatDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_RFC1123, Locale.US);
        formatter.setTimeZone(GMT);
        
        return formatter.format(this.date);
    }
	
	public static void main(String args[]) {
		LastModifiedField lmf = new LastModifiedField("Last-Modified", 1323333334);
		System.out.println(lmf.formatDate());
	}

	@Override
	public String convertToString() {
		return this.getName() + ":" + this.formatDate();
	}

	@Override
	public void convertToClass(String strClass) {
		// TODO Auto-generated method stub
		
	}

}
