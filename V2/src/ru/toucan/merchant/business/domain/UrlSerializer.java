package ru.toucan.merchant.business.domain;

import android.util.Base64;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class UrlSerializer extends JsonSerializer<String> {

    public static String serialize(String value) {
        return fo(value);
    }

    @Override
    public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeString(fo(value));
    }

    private static String fo (String value) {
        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

}
