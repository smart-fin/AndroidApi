package ru.toucan.api;

import android.os.Parcel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;

import ru.toucan.merchant.business.domain.ParcelableObject;


/**
 * Created with IntelliJ IDEA.
 * User: Nastya
 * Date: 20.07.12
 * Time: 23:37
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class APICancelPaymentResult extends ParcelableObject {

    // Текст квитанции
    @JsonProperty("Slip")
    public String Slip;

    // Безопасный номер карты(6 первых, 4 последних цифры, остальное * )
    @JsonProperty("CardNumber")
    public String CardNumber;

    @Override
    public String toString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (IOException e) {
            return "APIGetParametersResult == null 0_O";
        }
    }

    public APICancelPaymentResult() { }

    public APICancelPaymentResult(Parcel source) {
        super(source);
    }

    @Override
    public Class getClassType() {
        return APICancelPaymentResult.class;
    }

    public static final Creator CREATOR = new Creator() {
        public APICancelPaymentResult createFromParcel(Parcel source) {
            return new APICancelPaymentResult(source);
        }

        public APICancelPaymentResult[] newArray(int size) {
            return new APICancelPaymentResult[size];
        }
    };
}
