package ru.toucan.merchant.business.domain;

import android.util.Base64;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class UrlDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonparser,
                            DeserializationContext deserializationcontext) throws IOException {

        String data = jsonparser.getText();

        String url = null;
        try {
            byte[] decoded = Base64.decode(data, Base64.DEFAULT);
            if (decoded != null && decoded.length > 0) {
                url = new String(decoded, "UTF-8");
            }
        } catch (Exception e) {
        }
        return url;
    }
}
