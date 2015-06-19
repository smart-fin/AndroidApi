package ru.toucan.merchant.business.domain;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static ru.toucan.merchant.common.logger.Logger.log;
/**
 * Created by IntelliJ IDEA.
 * User: Nastya
 * Date: 30.09.11
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
public class DateDeserializer extends JsonDeserializer<Date> {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Date deserialize(JsonParser jsonparser,
                            DeserializationContext deserializationcontext) throws IOException {

        String date = jsonparser.getText();
        try {
            format.setTimeZone( TimeZone.getTimeZone("GMT") );
            return format.parse(date);
        } catch (ParseException e) {
            log(e);
            throw new RuntimeException(e);
        }
    }
}
