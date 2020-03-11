package by.gmar.utilities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

/**
 *
 * @author s.kosik
 */
public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {
    private String DATE_FORMAT_PATTERN = "dd-MM-yyyy";

    public CustomJsonDateDeserializer(){}
    public CustomJsonDateDeserializer(final String pattern){
        DATE_FORMAT_PATTERN = pattern;
    }
    
    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException {
        final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        final String date = jsonparser.getText();
        if (StringUtils.hasText(date)) {
            try {
                return format.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }

    }

}
