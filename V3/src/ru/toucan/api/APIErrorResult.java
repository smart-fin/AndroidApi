package ru.toucan.api;

import android.os.Parcel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;

import ru.toucan.merchant.business.domain.ParcelableObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class APIErrorResult extends ParcelableObject {

    // Код ошибки (дублирует resultCode)
    @JsonProperty("ErrorCode")
    public Integer ErrorCode;

    // Текст ошибки
    @JsonProperty("ErrorText")
    public String ErrorText;

    // Код ошибки сервера
    @JsonProperty("ErrorCodeExt")
    public Integer ErrorCodeExt;

    @Override
    public String toString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (IOException e) {
            return "PaymentDetailResponseApi3 == null 0_O";
        }
    }

    public APIErrorResult() {
    }

    public APIErrorResult(Parcel source) {
        super(source);
    }

    @Override
    public Class getClassType() {
        return APIErrorResult.class;
    }

    public static final Creator CREATOR = new Creator() {
        public APIErrorResult createFromParcel(Parcel source) {
            return new APIErrorResult(source);
        }

        public APIErrorResult[] newArray(int size) {
            return new APIErrorResult[size];
        }
    };
}
