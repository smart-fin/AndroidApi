package ru.toucan.merchant.business.domain.print;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import ru.toucan.merchant.business.domain.DateDeserializer;
import ru.toucan.merchant.business.domain.DateSerializer;
import ru.toucan.merchant.business.domain.ParcelableObject;
import ru.toucan.merchant.business.domain.UrlDeserializer;
import ru.toucan.merchant.business.domain.UrlSerializer;

/**
 * Created by Nastya on 24.07.2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Receipt4Print extends ParcelableObject {

    public Receipt4Print() {
    }

    @JsonProperty("Items")
    public ArrayList<Item> items;

    @JsonProperty("AuthCode")
    public String authCode;

    @JsonProperty("CashRegisterId")
    public int cashRegisterId;

    @JsonProperty("TId")
    public Integer tId;

    @JsonProperty("Company")
    public String company;

    @JsonProperty("CompanyAddress")
    public String companyAddress;

    @JsonProperty("Phone")
    public String phone;

    @JsonProperty("DateTimeU")
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    public Date dateTime;

    @JsonProperty("Id")
    public Integer id;

    @JsonProperty("Ptk")
    public Integer ptk;

    @JsonProperty("INN")
    public String inn;

    @JsonProperty("Num")
    public String num;

    @JsonProperty("PaymentNum")
    public String payNum;

    @JsonProperty("OfflineNum")
    public String offlineNum;

    @JsonProperty("DeviceOfflineNum")
    public String deviceOfflineNum;

    @JsonProperty("CardType")
    public Integer cardType;

    @JsonProperty("LastFourDigits")
    public String lastFourDigits;

    @JsonProperty("Deposit")
    public Double deposit;

    @JsonProperty("VatSum")
    public Double vatSum;

    @JsonProperty("VatRate")
    public Integer vatRate;

    @JsonProperty("TaxationType")
    public Integer taxationType;

    @JsonProperty("OFDPrintText")
    public String OFDPrintText;

    @JsonProperty("UrlQrCode")
    @JsonDeserialize(using = UrlDeserializer.class)
    @JsonSerialize(using = UrlSerializer.class)
    public String URLQRcode;

    @JsonProperty
    public String errorMessage;

    public String[] getOFDPrintText() {
        return OFDPrintText != null ? OFDPrintText.split("\n") : new String[]{};
    }

    public Receipt4Print(Parcel source) {
        super(source);
    }

    public Class getClassType() {
        return this.getClass();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Receipt4Print createFromParcel(Parcel source) {
            return new Receipt4Print(source);
        }

        public Receipt4Print[] newArray(int size) {
            return new Receipt4Print[size];
        }
    };

    @Override
    public String toString() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (IOException e) {
            return "Receipt4Print == null 0_O";
        }
    }

}
