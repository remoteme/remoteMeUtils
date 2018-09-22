package org.remoteme.utils.general;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class DateTimeUtils {
	public static final DateTimeFormatter ddmmyyyy_ = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	public static final DateTimeFormatter ddmmyyyyHHMM_ = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	public static final DateTimeFormatter ddmmyyyyHHMMss_ = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
	public static final DateTimeFormatter ddmmyyyyHHMMRaw_ = DateTimeFormatter.ofPattern("ddMMyyyyHHmm");

	static Map<Pattern, Function<String, LocalDateTime>> patterns;

	static {
		patterns = new HashMap<>();
		patterns.put(Pattern.compile("\\d{2}.\\d{2}.\\d{4}"), date -> LocalDate.parse(date, ddmmyyyy_.withZone(ZoneOffset.UTC)).atTime(0, 0));
		patterns.put(Pattern.compile("\\d{2}.\\d{2}.\\d{4}\\s\\d{2}:\\d{2}"), date -> LocalDateTime.parse(date, ddmmyyyyHHMM_.withZone(ZoneOffset.UTC)));
		patterns.put(Pattern.compile("\\d{2}.\\d{2}.\\d{4}\\s\\d{2}:\\d{2}:\\d{2}"), date -> LocalDateTime.parse(date, ddmmyyyyHHMMss_.withZone(ZoneOffset.UTC)));
		patterns.put(Pattern.compile("\\d{12}"), date -> LocalDateTime.parse(date, ddmmyyyyHHMMRaw_.withZone(ZoneOffset.UTC)));


	}

	public static LocalDateTime getFromMillis(long millis) {
		return getFromMillis(millis,ZoneOffset.UTC);
	}
	public static LocalDateTime getFromMillis(long millis,ZoneId zone) {
		return Instant.ofEpochMilli(millis).atZone(zone).toLocalDateTime();
	}
	public static LocalDateTime parseddmmyyyy(String ddmmyyyy) {

		return LocalDate.parse(ddmmyyyy, ddmmyyyy_.withZone(ZoneOffset.UTC)).atTime(0, 0);
	}

	public static LocalDateTime parseddmmyyyyhhmmss(String ddmmyyyyhhmmss) {

		return LocalDateTime.parse(ddmmyyyyhhmmss, ddmmyyyyHHMMss_.withZone(ZoneOffset.UTC));
	}


	public static String print(long date, String format, ZoneId zone) {
		return DateTimeFormatter.ofPattern(format).format(DateTimeUtils.getFromMillis(date,zone));

	}

	public static String printddmmyyyyhhmm(LocalDateTime date) {
		return date.format(ddmmyyyyHHMM_);
	}

	public static LocalDateTime parseddmmyyyyHHmmRaw(String formattedTime) {
		return LocalDateTime.parse(formattedTime, ddmmyyyyHHMMRaw_.withZone(ZoneOffset.UTC));
	}

	public static LocalDateTime roundToMinutes(LocalDateTime time) {
		return time.plusSeconds(30).truncatedTo(ChronoUnit.MINUTES);
	}


	public static LocalDateTime roundToSeconds(LocalDateTime dt, int seconds) {
		return dt.withNano(0).plusSeconds((65 - dt.getSecond()) % seconds);
	}

	public static LocalDateTime roundToMinutes(LocalDateTime dt, int minutes) {
		return dt.withSecond(0).withNano(0).plusMinutes((65 - dt.getMinute()) % minutes);
	}

	public static LocalDateTime roundToHours(LocalDateTime dt, int hours) {
		return dt.withSecond(0).withNano(0).withMinute(0).plusHours((65 - dt.getHour()) % hours);
	}

	public static LocalDateTime floorToDay(LocalDateTime dt) {
		return dt.withSecond(0).withNano(0).withMinute(0).withHour(0);
	}

	public static LocalDateTime now() {
		return LocalDateTime.now();
	}

	public static LocalDateTime now(ZoneId zone) {
		return LocalDateTime.now(zone);

	}

	public static long getMillisUTC(LocalDateTime date) {
		return getMillis(date,ZoneOffset.UTC);
	}
	public static long getMillis(LocalDateTime date,ZoneOffset zone) {
		return date.toInstant(zone).toEpochMilli();
	}
	public static LocalDateTime getTime(String dt) {
		for (Map.Entry<Pattern, Function<String, LocalDateTime>> p : patterns.entrySet()) {
			if (p.getKey().matcher(dt).matches()) {

				return p.getValue().apply(dt);
			}
		}
		throw new RuntimeException("date format not recongized");

	}

	public static LocalDateTime min(LocalDateTime date1, LocalDateTime date2) {
		return (date1.isBefore(date2)) ? date1 : date2;

	}

	public static LocalDateTime getFromDate(Date expired) {
		return expired.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
	}

	public static Date convert(LocalDateTime date) {
		if (date == null) {
			return null;
		} else {
			return new Date(getMillisUTC(date));
		}
	}

	public static int getDiffrent(LocalDateTime date1, LocalDateTime date2) {
		return (int)((getMillisUTC(date1)- getMillisUTC(date2))/1000);
	}

	public static boolean older(LocalDateTime lastPing, int seconds) {
		return DateTimeUtils.getDiffrent(DateTimeUtils.now(),lastPing)>seconds;
	}

	public static Long nowMillis() {
		return getMillisUTC(DateTimeUtils.now());
	}

	public static int compareNullSafe(Date d1, Date d2) {
		if (d1==null && d2==null){
			return 0;
		}else if ((d1!=null) && (d2!=null)){
			return d1.compareTo(d2);
		}else if (d1==null){
			return 1;
		}else{
			return -1;
		}
	}

	public static int compareNullSafe(LocalDateTime d1, LocalDateTime d2) {
		if (d1==null && d2==null){
			return 0;
		}else if ((d1!=null) && (d2!=null)){
			return d1.compareTo(d2);
		}else if (d1==null){
			return 1;
		}else{
			return -1;
		}
	}



}
