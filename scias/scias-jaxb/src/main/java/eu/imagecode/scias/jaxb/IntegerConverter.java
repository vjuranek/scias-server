package eu.imagecode.scias.jaxb;

import javax.xml.bind.DatatypeConverter;

/**
 * Converter between {@link String} and {@link Integer} to replace default JAXb conversion, which converts integer
 * numbers to {@link BigInteger}.
 * 
 * @author vjuranek
 *
 */
public class IntegerConverter {

    public static Integer parseInteger(String s) {
        return DatatypeConverter.parseInt(s);
    }

    public static String printInteger(Integer i) {
        return DatatypeConverter.printInt(i);
    }

}
