package ami.web.converters;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.core.convert.converter.Converter;

public class StringToDateTimeConverter implements Converter<String, DateTime> {
    @Override
    public DateTime convert(String s) {
//    	System.out.println("========================> " +s );
    	
//    	DateTimeFormatter parser    = ISODateTimeFormat.dateTimeParser();
//    	DateTime parseDateTime = parser.parseDateTime(s);
//    	return parseDateTime;

       return DateTime.parse(s);
    }

}
