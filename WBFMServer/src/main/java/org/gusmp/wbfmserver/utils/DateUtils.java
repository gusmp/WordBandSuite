package org.gusmp.wbfmserver.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static String parseDate(Date date) {
		return sdf.format(date);
	}

}
