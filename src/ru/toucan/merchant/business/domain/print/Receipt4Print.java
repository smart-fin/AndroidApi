package ru.toucan.merchant.business.domain.print;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;

import ru.toucan.example.utils.Logger;
import ru.toucan.merchant.business.domain.DateDeserializer;
import ru.toucan.merchant.business.domain.DateSerializer;
import ru.toucan.merchant.business.domain.ParcelableObject;

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
    public Integer cashRegisterId;

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

    @JsonProperty("INN")
    public String inn;

    @JsonProperty("Num")
    public String num;

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
        String encodeMessage = null;
        if (errorMessage != null)
            try {
                encodeMessage = URLEncoder.encode(errorMessage, "UTF-8");
            } catch (Exception e) {
                Logger.log(e);
            }
        return "Receipt4Print{" +
                "items=" + items + ", " +
                "authCode=" + authCode + ", " +
                "cashRegisterId=" + cashRegisterId + ", " +
                "company=" + company + ", " +
                "companyAddress=" + companyAddress + ", " +
                "phone=" + phone + ", " +
                "dateTime=" + dateTime + ", " +
                "id=" + id + ", " +
                "inn=" + inn + ", " +
                "num=" + num + ", " +
                "offlineNum=" + offlineNum + ", " +
                "deviceOfflineNum=" + deviceOfflineNum + ", " +
                "lastFourDigits=" + lastFourDigits + ", " +
                "deposit=" + deposit + ", " +
                "vatSum=" + vatSum + ", " +
                "vatRate=" + vatRate + ", " +
                "taxationType=" + taxationType + ", " +
                "OFDPrintText=" + OFDPrintText + ", " +
                "errorMessage=" + encodeMessage +
                '}';
    }
}