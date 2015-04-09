package ru.toucan.merchant.business.domain.print;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.net.URLEncoder;

import ru.toucan.example.utils.Logger;
import ru.toucan.merchant.business.domain.ParcelableObject;

/**
 * Created by Nastya on 24.07.2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Item extends ParcelableObject {

    public Item() {
    }

    @JsonProperty("Description")
    public String desc;

    @JsonProperty("Amount")
    public double amount;

    public Item(Parcel source) {
        super(source);
    }

    public Class getClassType() {
        return this.getClass();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public String toString() {
        String encodeDesc = null;
        if (desc != null)
            try {
                encodeDesc = URLEncoder.encode(desc, "UTF-8");
            } catch (Exception e) {
                Logger.log(e.getLocalizedMessage());
            }
        return "Item{" +
                "amount=" + amount + ", " +
                "desc=" + encodeDesc +
                '}';
    }
}
