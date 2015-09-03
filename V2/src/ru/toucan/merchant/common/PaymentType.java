package ru.toucan.merchant.common;

/**
 * Created with IntelliJ IDEA.
 * User: Nastya
 * Date: 19.03.13
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
public enum PaymentType {

    /**
     * Безналичный платеж
     */
    card(1),

    /**
     * Наличный платеж
     */
    cash(2);

    private int id;

    PaymentType(int id) {
        this.id = id;
    }

    public void value(int id) {
        this.id = id;
    }

    public int value() {
        return id;
    }

    public static PaymentType valueOf(int value) {
        PaymentType[] paymentTypes = PaymentType.values();
        for (PaymentType paymentType : paymentTypes) {
            if (paymentType.value() == value) return paymentType;
        }

        return null;
    }

}
