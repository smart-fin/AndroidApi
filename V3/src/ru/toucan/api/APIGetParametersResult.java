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
public class APIGetParametersResult extends ParcelableObject {

    // Список параметров в формате XML
    @JsonProperty("TableParameters")
    public String TableParameters;

    @Override
    public String toString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (IOException e) {
            return "APIGetParametersResult == null 0_O";
        }
    }

    public APIGetParametersResult() {
    }

    public APIGetParametersResult(Parcel source) {
        super(source);
    }

    @Override
    public Class getClassType() {
        return APIGetParametersResult.class;
    }

    public static final Creator CREATOR = new Creator() {
        public APIGetParametersResult createFromParcel(Parcel source) {
            return new APIGetParametersResult(source);
        }

        public APIGetParametersResult[] newArray(int size) {
            return new APIGetParametersResult[size];
        }
    };
}
