package ru.toucan.merchant.business.domain_external;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import ru.toucan.merchant.business.domain.ParcelableObject;
import ru.toucan.merchant.business.domain.print.Receipt4Print;

/**
 * Created by Nastya on 12.11.14.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Payment extends ParcelableObject {

    @JsonProperty("PaymentId")
    public String paymentId;

    @JsonProperty("Type")
    public int type;

    @JsonProperty("Receipt4Print")
    public Receipt4Print receipt4Print;

    public Payment() {
    }

    public Payment(String paymentId, int type, Receipt4Print receipt4Print) {
        this.paymentId = paymentId;
        this.type = type;
        this.receipt4Print = receipt4Print;
    }

    public Payment(Parcel source) {
        super(source);
    }

    public Class getClassType() {
        return this.getClass();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Payment createFromParcel(Parcel source) {
            return new Payment(source);
        }

        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId + ", " +
                "type=" + type + ", " +
                "receipt4Print=" + receipt4Print +
                '}';
    }
}
