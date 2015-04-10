package ru.toucan.api;

import android.os.Parcel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;

import ru.toucan.merchant.business.domain.ParcelableObject;
import ru.toucan.merchant.business.domain.statistic.PaymentDetailApi3;


/**
 * Created with IntelliJ IDEA.
 * User: Nastya
 * Date: 20.07.12
 * Time: 23:37
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class APIViewPaymentResult extends ParcelableObject {

    // Идентификатор платежа
    @JsonProperty("Payment")
    public Integer Payment;

    // Полная информация о платеже (необязательное поле)
    @JsonProperty("PaymentInfo")
    public PaymentDetailApi3 PaymentInfo;

    @Override
    public String toString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (IOException e) {
            return "PaymentDetailResponseApi3 == null 0_O";
        }
    }

    public APIViewPaymentResult() {
    }

    public APIViewPaymentResult(Parcel source) {
        super(source);
    }

    @Override
    public Class getClassType() {
        return APIViewPaymentResult.class;
    }

    public static final Creator CREATOR = new Creator() {
        public APIViewPaymentResult createFromParcel(Parcel source) {
            return new APIViewPaymentResult(source);
        }

        public APIViewPaymentResult[] newArray(int size) {
            return new APIViewPaymentResult[size];
        }
    };
}
