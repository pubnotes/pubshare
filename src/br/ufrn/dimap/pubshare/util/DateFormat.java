/**
 *    This file is part of PubShare.
 *
 *    PubShare is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    PubShare is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with PubShare.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.ufrn.dimap.pubshare.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
	
	private static final SimpleDateFormat iso8601Format = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss"); 
	
	 
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
