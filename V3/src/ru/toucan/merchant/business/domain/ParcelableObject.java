package ru.toucan.merchant.business.domain;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.map.ObjectMapper;

import java.lang.reflect.Field;

/**
 * Created by Nastya on 04.12.2014 0:20 for 2can.android
 */
public abstract class ParcelableObject implements Parcelable {

    public ParcelableObject(Parcel source) {
        String sourceJson = source.readString();
        try {
            ObjectMapper ow = new ObjectMapper();
            Object readValue =  ow.readValue(sourceJson, getClassType());
            for (Field field : getClassType().getDeclaredFields()) {
                if (field.getAnnotations().length == 0) continue;

                Object fieldValue = field.get(readValue);
                field.set(this, fieldValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ParcelableObject() {
    }

    @Override
    public int describeContents() {
        return super.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ObjectMapper ow = new ObjectMapper();
        try {
            dest.writeString(ow.writeValueAsString(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract Class getClassType();
}
