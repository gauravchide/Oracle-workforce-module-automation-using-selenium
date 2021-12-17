package source_package;

import java.util.Date;

public class DateSetter {
	public static String getTimeStamp(){
		Date date = new Date();
		return date.toString();
	}
}
