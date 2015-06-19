package ru.toucan.api.ems.demo.utils;

/**
 * Created by Nastya on 17.04.2015.
 */
public enum  RequestCode {

    GET_LAST_ERROR,
    GET_VERSION,
    GET_PARAMETERS,
    OPEN,
    CARD_PAYMENT,
    CASH_PAYMENT,
    VIEW_PAYMENT,
    CANCEL_PAYMENT,
    RETURN_PAYMENT,
    SETTLEMENT,
    CLOSE;

    public static final String[] names;

    static {
        RequestCode[] vals = RequestCode.values();

        names = new String[vals.length];
        for (int i=0; i<vals.length; i++) {
            names[i] = vals[i].toString();
        }
    }
}
