package ami.web.converters;

import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;

public class DateTimeToStringConverter  implements Converter<DateTime,String> {
   
	 @Override
	 public String convert(DateTime localTime) {
		 if(localTime==null){
			 return null;
		 }
        return localTime.toString();
	 }

}
