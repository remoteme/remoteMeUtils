package org.remoteme.utils.general;

import org.remoteme.utils.messages.v1.enums.AddMessageSettings;
import org.remoteme.utils.messages.v1.enums.variables.VariableHistoryRound;

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
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

public class DateTimeUtils {
	public static final DateTimeFormatter ddmmyyyy_ = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	public static final DateTimeFormatter ddmmyyyyHHMM_ = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	public static final DateTimeFormatter ddmmyyyyHHMM_WithDOW = DateTimeFormatter.ofPattern("dd.MM.yyyy (E) HH:mm");
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

	public static LocalDateTime getFromMillis(Long millis) {
		return getFromMillis(millis,ZoneOffset.UTC);
	}
	public static LocalDateTime getFromMillis(Long millis,ZoneId zone) {
		if (millis==null || millis==-1){
			return null;
		}else{
			return Instant.ofEpochMilli(millis).atZone(zone).toLocalDateTime();
		}
	}
	public static LocalDateTime parseddmmyyyy(String ddmmyyyy,ZoneId zoneId) {
		return LocalDate.parse(ddmmyyyy, ddmmyyyy_.withZone(zoneId)).atTime(0, 0);
	}

	public static LocalDateTime parseddmmyyyyhhmm(String ddmmyyyyhhmm, ZoneId zoneId) {
		return LocalDateTime.parse(ddmmyyyyhhmm, ddmmyyyyHHMM_.withZone(zoneId));
	}

	public static LocalDateTime parseddmmyyyy(String ddmmyyyy) {
		return parseddmmyyyy(ddmmyyyy,ZoneOffset.UTC);
	}


	public static LocalDateTime parseddmmyyyyhhmmss(String ddmmyyyyhhmmss) {

		return LocalDateTime.parse(ddmmyyyyhhmmss, ddmmyyyyHHMMss_.withZone(ZoneOffset.UTC));
	}


	public static String print(long date, String format, ZoneId zone) {
		return DateTimeFormatter.ofPattern(format).format(DateTimeUtils.getFromMillis(date,zone));
	}

	public static String printddmmyyyyhhmm(LocalDateTime date) {
		return printddmmyyyyhhmm(date,ZoneOffset.UTC);
	}
	public static String printddmmyyyyhhmm(LocalDateTime date, ZoneId zoneId) {
		return date.format(ddmmyyyyHHMM_.withZone(zoneId));
	}

	public static String printddmmyyyyhhmmDOW(LocalDateTime date) {
		return  date.format(ddmmyyyyHHMM_WithDOW);
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
	public static long getMillis(LocalDateTime date,ZoneId zone) {

		return date.toInstant(zone.getRules().getOffset(date)).toEpochMilli();
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
		if (date == null)  {
			return null;
		} else {
			return new Date(getMillisUTC(date));
		}
	}
	public static Date convert(LocalDateTime date, ZoneId zone) {
		if (date == null) {
			return null;
		} else {
			return new Date(getMillis(date,zone));
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


	public static boolean inPast(LocalDateTime first) {
		return first.isBefore(DateTimeUtils.now());
	}

	public static boolean inPast(LocalDateTime first,int minutes) {
		return first.plusMinutes(minutes).isBefore(DateTimeUtils.now());
	}


	public static long getTime(AddMessageSettings settings, long timeTemp){

		if (timeTemp<=0){
			timeTemp = System.currentTimeMillis();
		}


		if (settings != AddMessageSettings.NO_ROUND) {
			LocalDateTime local = DateTimeUtils.getFromMillis(timeTemp);

			switch (settings){
				case _1S	:local=DateTimeUtils.roundToSeconds(local,1);break;
				case _2S	:local=DateTimeUtils.roundToSeconds(local,2);break;
				case _5S	:local=DateTimeUtils.roundToSeconds(local,5);break;
				case _10S	:local=DateTimeUtils.roundToSeconds(local,10);break;
				case _15S	:local=DateTimeUtils.roundToSeconds(local,15);break;
				case _20S	:local=DateTimeUtils.roundToSeconds(local,20);break;
				case _30S	:local=DateTimeUtils.roundToSeconds(local,30);break;
				case _1M	:local=DateTimeUtils.roundToMinutes(local,1);break;
				case _5M	:local=DateTimeUtils.roundToMinutes(local,5);break;
				case _10M	:local=DateTimeUtils.roundToMinutes(local,10);break;
				case _15M	:local=DateTimeUtils.roundToMinutes(local,15);break;
				case _30M	:local=DateTimeUtils.roundToMinutes(local,30);break;
				case _1G	:local=DateTimeUtils.roundToHours(local,1);break;
				case _2G	:local=DateTimeUtils.roundToHours(local,2);break;
				case _3G	:local=DateTimeUtils.roundToHours(local,3);break;
				case _4G	:local=DateTimeUtils.roundToHours(local,4);break;
				case _5G	:local=DateTimeUtils.roundToHours(local,5);break;
				case _6G	:local=DateTimeUtils.roundToHours(local,6);break;
			}

			timeTemp=DateTimeUtils.getMillisUTC(local);
		}
		return timeTemp;
	}


	public static LocalDateTime round(VariableHistoryRound settings, LocalDateTime dateTime) {
			switch (settings){
				case _1S	:return DateTimeUtils.roundToSeconds(dateTime,1);
				case _2S	:return DateTimeUtils.roundToSeconds(dateTime,2);
				case _5S	:return DateTimeUtils.roundToSeconds(dateTime,5);
				case _10S	:return DateTimeUtils.roundToSeconds(dateTime,10);
				case _15S	:return DateTimeUtils.roundToSeconds(dateTime,15);
				case _20S	:return DateTimeUtils.roundToSeconds(dateTime,20);
				case _30S	:return DateTimeUtils.roundToSeconds(dateTime,30);
				case _1M	:return DateTimeUtils.roundToMinutes(dateTime,1);
				case _5M	:return DateTimeUtils.roundToMinutes(dateTime,5);
				default:	return dateTime;
			}



	}

	public static boolean equals(LocalDateTime date1, LocalDateTime date2) {
		return getMillis(date1,ZoneOffset.UTC )==getMillis(date2,ZoneOffset.UTC );
	}

	public static Optional<LocalDateTime> getFromMillisOpt(long date) {
		return Optional.ofNullable(getFromMillis(date));
	}
}
