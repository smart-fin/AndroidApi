package ru.toucan.merchant.business.domain;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class RubSerializer extends JsonSerializer<Integer> {

    public static String serialize(Integer value) {
        return fo(value);
    }

    @Override
    public void serialize(Integer value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeString(fo(value));
    }

    private static String fo (Integer value) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');

        DecimalFormat decimalFormatBig = new DecimalFormat("###0", decimalFormatSymbols);
        decimalFormatBig.setGroupingUsed(false);
        DecimalFormat decimalFormatSmall = new DecimalFormat("00", decimalFormatSymbols);

        return decimalFormatBig.format(value / 100) + "." + decimalFormatSmall.format(value % 100);
    }

}
