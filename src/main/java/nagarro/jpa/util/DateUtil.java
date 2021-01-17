package nagarro.jpa.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author alperkopuz
 * DateUtil class used for Date operations
 */
public class DateUtil {

    public static final String DD_MM_YYYY = "dd.MM.yyyy";

    public static Date convertDateStringtoDate(String dateField) throws ParseException {
        DateFormat formatter = new SimpleDateFormat(DD_MM_YYYY);
        return formatter.parse(dateField);
    }

}
