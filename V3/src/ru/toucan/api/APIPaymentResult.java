package ru.toucan.api;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;

import ru.toucan.merchant.business.domain.ParcelableObject;
import ru.toucan.merchant.business.domain.print.Receipt4Print;
import ru.toucan.merchant.business.domain.statistic.PaymentDetailApi3;


/**
 * Created with IntelliJ IDEA.
 * User: Nastya
 * Date: 20.07.12
 * Time: 23:37
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class APIPaymentResult extends ParcelableObject {

    // Идентификатор платежа
    @JsonProperty("Payment")
    public Integer Payment;

    // Код авторизации
    @JsonProperty("AuthorizationCode")
    public String AuthorizationCode;

    // Безопасный номер карты(6 первых, 4 последних цифры, остальное * )
    @JsonProperty("CardNumber")
    public String CardNumber;

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

    public APIPaymentResult() {
    }

    public APIPaymentResult(Parcel source) {
        super(source);
    }

    @Override
    public Class getClassType() {
        return APIPaymentResult.class;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public APIPaymentResult createFromParcel(Parcel source) {
            return new APIPaymentResult(source);
        }

        public APIPaymentResult[] newArray(int size) {
            return new APIPaymentResult[size];
        }
    };
}
