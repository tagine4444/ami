package ami.web.converters;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.core.convert.converter.Converter;

public class DateTimeToStringConverter  implements Converter<DateTime,String> {
   
	
	DateTimeFormatter formatter = ISODateTimeFormat.dateTimeNoMillis();
		        
	 @Override
	 public String convert(DateTime localTime) {
		 if(localTime==null){
			 return null;
		 }
		 
		String result = formatter.print(localTime.withZone(DateTimeZone.forID("America/Los_Angeles")));
        return result;
        
//        return localTime.toString();
	 }

}
