package ru.toucan.api;

import android.os.Parcel;

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
public class APISettlementResult extends ParcelableObject {

    // Текст итогового отчета
    @JsonProperty("Slip")
    public String Slip;

    @Override
    public String toString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (IOException e) {
            return "PaymentDetailResponseApi3 == null 0_O";
        }
    }

    public APISettlementResult() {
    }

    public APISettlementResult(Parcel source) {
        super(source);
    }

    @Override
    public Class getClassType() {
        return APISettlementResult.class;
    }

    public static final Creator CREATOR = new Creator() {
        public APISettlementResult createFromParcel(Parcel source) {
            return new APISettlementResult(source);
        }

        public APISettlementResult[] newArray(int size) {
            return new APISettlementResult[size];
        }
    };
}
