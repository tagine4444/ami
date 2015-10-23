package ami.converters;

import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;

public class StringToDateTimeConverter implements Converter<String, DateTime> {
    @Override
    public DateTime convert(String s) {
//    	System.out.println("========================> " +s );
        return DateTime.parse(s);
    }

}
