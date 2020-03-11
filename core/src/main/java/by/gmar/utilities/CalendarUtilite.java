package by.gmar.utilities;

import by.gmar.model.Schedule;
import by.gmar.model.Schedule;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 *
 * @author s.kosik
 */
public class CalendarUtilite {
    private Calendar calendar;
    private List<String> zoneList;
    
    public String getTimeZoneByLocale(final String languageTag){
        final Locale locale = Locale.forLanguageTag(languageTag);
        final Calendar cal = Calendar.getInstance(locale);
        final TimeZone timeZone = cal.getTimeZone();
        return timeZone.getID();
    }
    
    @Deprecated
    public String parseOffset(final Float timeZone){
        if(null == timeZone)
            return null;
        
        final LocalDateTime dt = LocalDateTime.now();
        final ZoneOffset userTimeZone = ZoneOffset.of(timeZone.toString());
        final List<String> zoneList = getZoneList();
        
        for (String s : zoneList) {
            ZoneId zone = ZoneId.of(s);
            ZonedDateTime zdt = dt.atZone(zone);
            ZoneOffset offset = zdt.getOffset();
            if(offset.equals(userTimeZone)){
                return zone.toString();
            }
        }
        return null;
        
    }

    public List<String> getZoneList() {
        if(null == zoneList){
            List<String> zoneList = 
                    new ArrayList<String>(ZoneId.getAvailableZoneIds());
        }
        return zoneList;
    }
    
    public static Long setToLastDayMinute(Date date){
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        return cal.getTimeInMillis();
    }
    
    public static Long getDateWithoutHoursANDMinutesAccuracy(Date date){
        Calendar cal = Calendar.getInstance(); // locale-specific
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time = cal.getTimeInMillis();
//        Instant instant = date.toInstant();
//        instant = instant.truncatedTo(ChronoUnit.DAYS);
//        date = Date.from(instant);        
        
        return time;
    }
    
    public Date dateMinusDays(Date date, int days){
        Instant inst = date.toInstant().minus(days, ChronoUnit.DAYS);
        return Date.from(inst);
    }
    
    public Date datePlusDays(Date date, int days){
        Instant inst = date.toInstant().plus(days, ChronoUnit.DAYS);
        return Date.from(inst);
    }
    
    public boolean isTimeToSchedule(final Schedule schedule){
        Instant nextInvocationTime = new Date(schedule.getLastUpdateTime()).toInstant().
                plus(schedule.getPeriodInMinutes(), ChronoUnit.MINUTES);
        LocalDateTime localNextInvocationTime = LocalDateTime.ofInstant(nextInvocationTime, ZoneId.systemDefault());
        boolean flag = LocalDateTime.now().isAfter(localNextInvocationTime);
        
        LocalDateTime localDate = 
                LocalDateTime.ofInstant(new Date(0L).toInstant(), ZoneId.systemDefault());
        Date.from(localDate.toInstant(ZoneOffset.of("-05:00")));
        
        return flag;
    }
    
    public Long getYearsBetween(final Date start, final Date end){
        final LocalDate startLocalDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        final LocalDate endLocalDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        final Long numberOfYears = ChronoUnit.YEARS.between(startLocalDate, endLocalDate);

        return numberOfYears;
    }
    
    public Date currentDateMinusMinutes(int hours) {
        LocalDateTime pastHour = LocalDateTime.now().minus(hours, ChronoUnit.MINUTES);
        Instant pastInstant = pastHour.atZone(ZoneId.systemDefault()).toInstant();
        final Date hourAgo = Date.from(pastInstant);
        return hourAgo;
    }

    public Date currentDateMinusDays(int days) {
        LocalDateTime pastHour = LocalDateTime.now().minus(days, ChronoUnit.DAYS);
        Instant pastInstant = pastHour.atZone(ZoneId.systemDefault()).toInstant();
        final Date hourAgo = Date.from(pastInstant);
        return hourAgo;
    }
    
    public Instant currentYearMinus(int interval){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, -interval);
        final Instant ageInterval = now.toInstant();
        return ageInterval;
    }
    
    @Deprecated
    public Date getLastDate(int dayQuantity) {
        getCalendar().setTimeInMillis(new Date().getTime());
        getCalendar().add(Calendar.DAY_OF_MONTH, -dayQuantity);
        Date lastDay = new Date(getCalendar().getTimeInMillis());
        return lastDay;
    }
    
    @Deprecated
    public Date getFollowDate(int dayQuantity) {
        getCalendar().setTimeInMillis(new Date().getTime());
        getCalendar().add(Calendar.DAY_OF_MONTH, +dayQuantity);
        Date lastDay = new Date(getCalendar().getTimeInMillis());
        return lastDay;
    }
    
    @Deprecated
    public int calculateMinutesTimeDiff(final Date startDate, final Date endDate) throws IllegalArgumentException {
        getCalendar().setTimeInMillis(endDate.getTime());
        long diff = endDate.getTime() - startDate.getTime();
        diff = diff / 1000 / 60;
        if (diff < Integer.MIN_VALUE || diff > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(diff + " cannot be cast to int without changing its value.");
        }

        return (int) diff;
    }

    private Calendar getCalendar() {
        return null == calendar ? calendar = Calendar.getInstance() : calendar;
    }

}
