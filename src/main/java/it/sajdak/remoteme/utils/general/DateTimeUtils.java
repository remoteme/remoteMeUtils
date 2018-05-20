package it.sajdak.remoteme.utils.general;

import javax.swing.text.DateFormatter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
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
		return Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDateTime();
	}

	public static LocalDateTime parseddmmyyyy(String ddmmyyyy) {

		return LocalDate.parse(ddmmyyyy, ddmmyyyy_.withZone(ZoneOffset.UTC)).atTime(0, 0);
	}

	public static LocalDateTime parseddmmyyyyhhmmss(String ddmmyyyyhhmmss) {

		return LocalDateTime.parse(ddmmyyyyhhmmss, ddmmyyyyHHMMss_.withZone(ZoneOffset.UTC));
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

	public static LocalDateTime now() {
		return LocalDateTime.now();
	}

	public static long getMillis(LocalDateTime date) {
		return date.toInstant(ZoneOffset.UTC).toEpochMilli();
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
			return new Date(getMillis(date));
		}
	}

	public static int getDiffrent(LocalDateTime date1, LocalDateTime date2) {
		return (int)((getMillis(date1)- getMillis(date2))/1000);
	}
}
