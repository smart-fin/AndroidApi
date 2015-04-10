package ru.toucan.merchant.business.domain;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: Nastya
 * Date: 22.08.12
 * Time: 0:06
 * To change this template use File | Settings | File Templates.
 */
public class DateSerializer extends JsonSerializer<Date> {
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String serialize(Date date) {
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.format(date);
    }

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        String dateString = formatter.format(value);
        jgen.writeString(dateString);
    }
}
