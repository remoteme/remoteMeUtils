package org.remoteme.utils.general;

import org.remoteme.utils.messages.v1.enums.variables.VariableHistoryRound;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public class InstantUtils {
	public static final DateTimeFormatter ddmmyyyy_ = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	public static final DateTimeFormatter ddmmyyyyHHMM_ = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	public static final DateTimeFormatter ddmmyyyyHHMM_WithDOW = DateTimeFormatter.ofPattern("dd.MM.yyyy (E) HH:mm");
	public static final DateTimeFormatter ddmmyyyyHHMMss_ = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
	public static final DateTimeFormatter ddmmyyyyHHMMRaw_ = DateTimeFormatter.ofPattern("ddMMyyyyHHmm");

	static Map<Pattern, BiFunction<String,ZoneId, Instant>> patterns;

	static {
		patterns = new HashMap<>();
		patterns.put(Pattern.compile("\\d{2}.\\d{2}.\\d{4}"), (date,zone) -> parse(date, ddmmyyyy_,zone));
		patterns.put(Pattern.compile("\\d{2}.\\d{2}.\\d{4}\\s\\d{2}:\\d{2}"),  (date,zone) -> parse(date, ddmmyyyyHHMM_,zone));
		patterns.put(Pattern.compile("\\d{2}.\\d{2}.\\d{4}\\s\\d{2}:\\d{2}:\\d{2}"),  (date,zone) -> parse(date, ddmmyyyyHHMMss_,zone));
		patterns.put(Pattern.compile("\\d{12}"),  (date,zone) -> parse(date, ddmmyyyyHHMMRaw_,zone));


	}

	public static Instant parse(String date,DateTimeFormatter formatter,ZoneId zoneId){
		return formatter.withZone(zoneId).parse(date,Instant::from);
	}

	public static Instant getFromMillis(Long millis) {
		if ((millis==null)||(millis==-1)){
			return null;
		}else{
			return Instant.ofEpochMilli(millis);
		}
	}

	public static Instant getFromMillis(Long millis,int daysToAdd) {
		Instant fromMillis = getFromMillis(millis);
		if (fromMillis!=null){
			fromMillis=plusDays(fromMillis, daysToAdd);
		}
		return fromMillis;
	}

	public static LocalDateTime getFromMillis(Long millis,ZoneId zone) {
		if (millis==null || millis==-1){
			return null;
		}else{
			return Instant.ofEpochMilli(millis).atZone(zone).toLocalDateTime();
		}
	}
	public static Instant parseddmmyyyy(String ddmmyyyy,ZoneId zoneId) {
		return ddmmyyyy_.withZone(zoneId).parse(ddmmyyyy,Instant::from);
	}

	public static Instant parseddmmyyyyhhmm(String ddmmyyyyhhmm, ZoneId zoneId) {
		return ddmmyyyyHHMM_.withZone(zoneId).parse(ddmmyyyyhhmm,Instant::from);

	}




	public static Instant parseddmmyyyyhhmmss(String ddmmyyyyhhmmss) {
		return ddmmyyyyHHMMss_.withZone(ZoneOffset.UTC).parse(ddmmyyyyhhmmss,Instant::from);
	}


	public static String print(long date, String format, ZoneId zone) {
		return DateTimeFormatter.ofPattern(format).format(InstantUtils.getFromMillis(date,zone));
	}


	public static String printddmmyyyyhhmm(Instant date, ZoneId zoneId) {
		return ddmmyyyyHHMM_.withZone(zoneId).format(date);

	}

	public static String printddmmyyyyhhmmDOW(Instant date, ZoneId zone) {
		return  ddmmyyyyHHMM_WithDOW.withZone(zone).format(date);
	}
	public static LocalDateTime parseddmmyyyyHHmmRaw(String formattedTime) {
		return LocalDateTime.parse(formattedTime, ddmmyyyyHHMMRaw_.withZone(ZoneOffset.UTC));
	}

	public static Instant roundToMinutes(Instant time) {
		return time.plusSeconds(30).truncatedTo(ChronoUnit.MINUTES);
	}


	public static Instant roundToSeconds(Instant dt, int seconds) {
		dt= dt.plusMillis(seconds*500);
		return dt.truncatedTo(ChronoUnit.SECONDS).minusSeconds((getSeconds(dt)) % seconds);
	}

	private static int getSeconds(Instant dt) {
		return LocalDateTime.ofInstant(dt, ZoneOffset.UTC).getSecond();
	}

	private static int getMinute(Instant dt) {
		return LocalDateTime.ofInstant(dt, ZoneOffset.UTC).getMinute();
	}


	public static Instant roundToMinutes(Instant dt, int minutes) {
		dt= dt.plus(minutes*30,ChronoUnit.SECONDS);
		return dt.truncatedTo(ChronoUnit.MINUTES).minus((getMinute(dt)) % minutes,ChronoUnit.MINUTES);
	}

	public static Instant roundToHours(Instant dt ) {
		return  dt.plus(30,ChronoUnit.MINUTES).truncatedTo(ChronoUnit.HOURS);
	}

	public static Instant floorToDay(Instant dt, ZoneOffset zone) {
		LocalDateTime l = LocalDateTime.ofInstant(dt, zone);
		return LocalDateTime.of(l.getYear(),l.getMonth(),l.getDayOfMonth(),0,0).toInstant(zone);
	}

	public static Instant floorToDay(Instant dt, ZoneId zone) {
		LocalDateTime l = LocalDateTime.ofInstant(dt, zone);
		return LocalDateTime.of(l.getYear(),l.getMonth(),l.getDayOfMonth(),0,0).toInstant(zone.getRules().getOffset(dt));
	}


	public static Instant now() {
		return Instant.now();
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

	public static Instant getTime(String dt,ZoneId zone) {
		for (Map.Entry<Pattern, BiFunction<String,ZoneId, Instant>> p : patterns.entrySet()) {
			if (p.getKey().matcher(dt).matches()) {

				return p.getValue().apply(dt, zone);
			}
		}
		throw new RuntimeException("date format not recongized");

	}

	public static Instant min(Instant date1, Instant date2) {
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
	public static int getDiffrent(Instant date1, Instant date2) {
		return (int)((date1.toEpochMilli()- date2.toEpochMilli())/1000);
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


	public static boolean inPast(Instant first) {
		return first.isBefore(InstantUtils.now());
	}

	public static boolean inPast(Instant first,int minutes) {
		return first.plus(minutes,ChronoUnit.MINUTES).isBefore(InstantUtils.now());
	}



	public static Instant round(VariableHistoryRound settings, Instant dateTime) {
			switch (settings){
				case _1S	:return InstantUtils.roundToSeconds(dateTime,1);
				case _2S	:return InstantUtils.roundToSeconds(dateTime,2);
				case _5S	:return InstantUtils.roundToSeconds(dateTime,5);
				case _10S	:return InstantUtils.roundToSeconds(dateTime,10);
				case _15S	:return InstantUtils.roundToSeconds(dateTime,15);
				case _20S	:return InstantUtils.roundToSeconds(dateTime,20);
				case _30S	:return InstantUtils.roundToSeconds(dateTime,30);
				case _1M	:return InstantUtils.roundToMinutes(dateTime,1);
				case _5M	:return InstantUtils.roundToMinutes(dateTime,5);
				default:	return dateTime;
			}



	}

	public static boolean equals(Instant date1, Instant date2) {
		return date1.equals(date2);
	}

	public static Optional<Instant> getFromMillisOpt(long date) {
		return Optional.ofNullable(getFromMillis(date));
	}

	public static Optional<Instant> getFromMillisOpt(long date, int daysToAdd ) {


		return Optional.ofNullable( getFromMillis(date,daysToAdd));
	}

	public static Instant plusHours(Instant date, int i) {
		return date.plus(i,ChronoUnit.HOURS);
	}

	public static Instant plusYears(Instant date, int i) {
		return LocalDateTime.ofInstant(date, ZoneOffset.UTC).plusYears(i).toInstant( ZoneOffset.UTC);
	}

	public static Instant plusDays(Instant date, int i) {
		return date.plus(i,ChronoUnit.DAYS);
	}

	public static boolean older(Instant lastPing, int seconds) {
		return getDiffrent(Instant.now(),lastPing)>seconds;
	}


}
