package br.com.baladasp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

	public static String formatarData(Calendar date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
		return sdf.format(date);
	}
	
	public static Date formatarData(String string) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
		
		try {
			Date date = df.parse(string);
			return date;
		} catch (ParseException ex) {
			ex.printStackTrace(System.err);
			return null;
		}
	}

	
	public static String formatarData(Date createdAt) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
		return sdf.format(createdAt);
	}
}
