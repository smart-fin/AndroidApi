package ru.toucan.merchant.business.domain.print;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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

    @JsonProperty("Count")
    public int count = 1;

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
}
