package header.response;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import header.HeaderField;

public class DateField extends HeaderField{
	private Date date;
	
	public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
	
	private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
	
	public DateField(String name, long date) {
		super(name);
		this.date = new Date(date);
	}

	public DateField(String name, Date date) {
		super(name);
		this.date = date;
	}
	
	public String formatDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_RFC1123, Locale.US);
        formatter.setTimeZone(GMT);
        
        return formatter.format(this.date);
    }

	@Override
	public String convertToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void convertToClass(String strClass) {
		// TODO Auto-generated method stub
		
	}
}
