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
import ru.toucan.merchant.business.domain.statistic.ReturnDetailApi3;


/**
 * Created with IntelliJ IDEA.
 * User: Nastya
 * Date: 20.07.12
 * Time: 23:37
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class APIReturnPaymentResult extends ParcelableObject {

    // Идентификатор возврата
    @JsonProperty("Id")
    public Integer Id;

    // Идентификатор возврата
    @JsonProperty("CardNumber")
    public String CardNumber;

    // Полная информация о платеже (необязательное поле)
    @JsonProperty("PaymentInfo")
    public ReturnDetailApi3 PaymentInfo;

    @Override
    public String toString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (IOException e) {
            return "PaymentDetailResponseApi3 == null 0_O";
        }
    }

    public APIReturnPaymentResult() {
    }

    public APIReturnPaymentResult(Parcel source) {
        super(source);
    }

    @Override
    public Class getClassType() {
        return APIReturnPaymentResult.class;
    }

    public static final Creator CREATOR = new Creator() {
        public APIReturnPaymentResult createFromParcel(Parcel source) {
            return new APIReturnPaymentResult(source);
        }

        public APIReturnPaymentResult[] newArray(int size) {
            return new APIReturnPaymentResult[size];
        }
    };
}
