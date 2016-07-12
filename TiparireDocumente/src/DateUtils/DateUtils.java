package DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	public static int diffHours(Date dateStart, Date dateStop) {
		long diff = dateStop.getTime() - dateStart.getTime();

		long diffHours = diff / (60 * 60 * 1000) % 24;

		
		
		return Math.round(diffHours);

	}

}
