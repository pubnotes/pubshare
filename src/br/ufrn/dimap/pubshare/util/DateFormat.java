package br.ufrn.dimap.pubshare.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormat {
	
	private static final SimpleDateFormat iso8601Format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss"); 
	
	 
	public static String iso8601Format( Date format ){		
		return iso8601Format.format(format);
	}
	
	public static Date iso8601Format( String format ){		 
		try {
			return iso8601Format.parse(format);
		} catch (ParseException e) {
			return null;
		}
	}


}
