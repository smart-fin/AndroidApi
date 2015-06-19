package ru.toucan.merchant.business.domain;

import android.content.Context;

import org.codehaus.jackson.annotate.JsonValue;

import ru.toucan.api.R;


/**
 * Created by Nastya on 27.02.2015 14:43 for 2can.android
 * <p/>
 * «фискализация платежа»
 */
public enum FiscalizationType {

    // Платеж не фискализирован(значение по-умолчанию)
    NUN(0),
    // Платеж фискализирован в ОФД
    OFD(1),
    // Платеж фискализирован в ФР АТОЛ
    ATOL(2),
    // Платеж фискализирован в ФР Штрих-М
    SHTRIH(3);

    private final int id;

    FiscalizationType(int id) {
        this.id = id;
    }

    @JsonValue
    public int value() {
        return id;
    }


    public String toString(Context context) {
        switch (this) {
            case ATOL:
                return context.getString(R.string.atol);
            case SHTRIH:
                return context.getString(R.string.shtrih);
            case OFD:
                return context.getString(R.string.ofd);
            default:
                return context.getString(R.string.nun);
        }
    }

    public static FiscalizationType fromString(String type) {
        FiscalizationType[] fiscalizationTypes = FiscalizationType.values();
        for (FiscalizationType fiscalizationType : fiscalizationTypes) {
            if (fiscalizationType.toString().toLowerCase().equals(type.toLowerCase())) {
                return fiscalizationType;
            }
        }
        return null;
    }
}
