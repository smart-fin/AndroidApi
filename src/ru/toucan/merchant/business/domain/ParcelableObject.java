package ru.toucan.merchant.business.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Field;

import ru.toucan.example.utils.Logger;
import ru.toucan.merchant.business.domain.JsonMapper;
import ru.toucan.example.utils.Logger;

/**
 * Created by Nastya on 04.12.2014 0:20 for 2can.android
 */
public abstract class ParcelableObject implements Parcelable {

    public ParcelableObject(Parcel source) {
        String sourceJson = source.readString();
        try {
            Object readValue = JsonMapper.getInstance().mapper.readValue(sourceJson, getClassType());
            for (Field field : getClassType().getDeclaredFields()) {
                if (field.getAnnotations().length == 0) continue;

                Object fieldValue = field.get(readValue);
                field.set(this, fieldValue);
            }
        } catch (Exception e) {
            Logger.log(e);
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
        try {
            String destJson = JsonMapper.getInstance().mapper.writeValueAsString(this);
            dest.writeString(destJson);
        } catch (Exception e) {
            Logger.log(e);
        }
    }

    public abstract Class getClassType();
}
