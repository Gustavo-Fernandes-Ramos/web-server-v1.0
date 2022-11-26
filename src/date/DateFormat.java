package date;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormat{
	
	public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";
	private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
	
	public static String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_RFC1123, Locale.US);
        formatter.setTimeZone(GMT);
        
        return formatter.format(date);
    }
}