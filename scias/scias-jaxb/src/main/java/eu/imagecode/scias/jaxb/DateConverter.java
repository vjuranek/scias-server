package eu.imagecode.scias.jaxb;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Converter between {@link String} and {@link Date} to replace default JAXb conversion, which converts date time to
 * {@link XMLGregorianCalendar}.
 * 
 * 
 * @author vjuranek
 *
 */
public class DateConverter {

    public static Date parseDate(String s) {
        return DatatypeConverter.parseDate(s).getTime();
    }

    public static String printDate(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return DatatypeConverter.printDate(cal);
    }

}
