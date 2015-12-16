package help;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ichti
 */
public class DateHelp {
    
    private static DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    
    public static String getDateStringFromDate(Date date) {
        return sdfISO.format(date);
    }
    
    public static Date getDateFromDateString(String dateString) throws ParseException {
        return sdfISO.parse(dateString);
    }
}
