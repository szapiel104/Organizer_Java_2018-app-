package services.date;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import enums.Organizer;

public class DateParser {
	public static com.mindfusion.common.DateTime ParseDate(String date) {
		try {
			System.out.println(MessageFormat.format("[{0}][{1}][{2}]" + "Parser for date from database: {3}",
					new Date().toGMTString(), "DateParser", "Information", date));

			DateTimeFormatter parser = DateTimeFormat.forPattern("MM/dd/yy, HH:mm:ss").withZoneUTC();
			org.joda.time.DateTime time = parser.parseDateTime(date);
			System.out.println(time);

			// DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yy
			// hh:mm:ss").withLocale( new Locale());
			// org.joda.time.DateTime dateTime = dtf.parseDateTime(date);

			com.mindfusion.common.DateTime mindfusionDate = new com.mindfusion.common.DateTime(time.getYear(),
					time.getMonthOfYear(), time.getDayOfMonth(), time.getHourOfDay(),
					time.getMinuteOfHour(), time.getSecondOfMinute());

			return mindfusionDate;

		} catch (Exception ex) {
			System.out.println(MessageFormat.format("[{0}][{1}][{2}][{3}][{4}]" + ex.getMessage(),
					new Date().toGMTString(), "DateParser", "Exception", ex.getStackTrace()[0].getLineNumber(),
					Thread.currentThread().getStackTrace()));
		}

		return null;
	}

}
