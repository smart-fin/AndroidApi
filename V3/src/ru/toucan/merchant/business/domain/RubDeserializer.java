package ru.toucan.merchant.business.domain;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class RubDeserializer extends JsonDeserializer<Integer> {

    @Override
    public Integer deserialize(JsonParser jsonparser,
                            DeserializationContext deserializationcontext) throws IOException {

        String data = jsonparser.getText();
        String[] parts = data.split("[.,]");
        Integer sum = Integer.valueOf(parts[0]) * 100;
        if (parts.length > 1) {
            sum += Integer.valueOf((parts[1] + "00").substring(0,2));
        }

        return sum;
    }
}
