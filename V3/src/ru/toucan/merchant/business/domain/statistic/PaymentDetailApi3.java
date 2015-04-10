package ru.toucan.merchant.business.domain.statistic;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

import ru.toucan.merchant.business.domain.DateDeserializer;
import ru.toucan.merchant.business.domain.DateSerializer;
import ru.toucan.merchant.business.domain.ParcelableObject;
import ru.toucan.merchant.business.domain.RubDeserializer;
import ru.toucan.merchant.business.domain.RubSerializer;

/**
 * Created with IntelliJ IDEA.
 * User: Nastya
 * Date: 20.07.12
 * Time: 21:29
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PaymentDetailApi3 extends ParcelableObject {

    //    Идентификатор платежа
    @JsonProperty("Id")
    public Integer Payment;

    //    Код авторизации
    @JsonProperty("AuthCode")
    public String AuthorizationCode;

    //    Дата и время платежа в UTC
    @JsonProperty("DateTimeU")
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    public Date dateTime;

    //    Статус платежа
    @JsonProperty("Status")
    public String Status;

    //    Причина, если статус платежа «Отклонен»
    @JsonProperty("Reason")
    public String Reason;

    //    Сумма платежа
    @JsonProperty("Amount")
//    public Integer Amount;
    @JsonDeserialize(using = RubDeserializer.class)
    @JsonSerialize(using = RubSerializer.class)
    public Integer Amount;

    //    Сумма комиссии
    @JsonProperty("Fee")
    @JsonDeserialize(using = RubDeserializer.class)
    @JsonSerialize(using = RubSerializer.class)
    public Integer Fee;

    //    Сумма возмещения
    @JsonProperty("Payout")
    @JsonDeserialize(using = RubDeserializer.class)
    @JsonSerialize(using = RubSerializer.class)
    public Integer Payout;

    //    Назначение платежа
    @JsonProperty("Description")
    public String Description;

/*
10 Actions.Void Bool Нет Платеж можно отменить
11 Actions.Refund Bool Нет Платеж можно вернуть
12 Actions.Sign Bool Нет Платеж можно подписать
13 Actions.Receipt Bool Нет Можно отправить чек
*/

    //    Мобильный телефон или e­mailадрес покупателя
    @JsonProperty("CustomerAddress")
    public String CustomerAddress;

    //    Retrieval Reference Number уникальный 16­ти значный идентификатор транзакции
    @JsonProperty("RRN")
    public String RRNCode;

    //    Номер договора(номер устройства в ПЦ, присылается банком)
    @JsonProperty("Merchant")
    public Integer Merchant;

    //    Идентификатор мобильного оборудования
    @JsonProperty("IMEI")
    public String IMEI;

    //    Параметры активированного устройства (Активация устройства, исходящий параметр)
    @JsonProperty("DeviceID")
    public Integer DeviceID;

    //    Геолокационные данные, широта
    @JsonProperty("Lat")
    public String Lat;

    //    Геолокационные данные, долгота
    @JsonProperty("Lng")
    public String Lng;

    //    Тип ридера
//    0 – Магнитный квадратный 1 – Магнитный треугольный 2 – Дуальный квадратный Значение по умолчанию – 0
    @JsonProperty("ReaderType")
    public Integer ReaderType;

    //    Безопасный номер карты(6 первых, 4 последних цифры, остальное * )
    @JsonProperty("MaskedCardNumber")
    public String CardNumber;

    //    Строка для печати чекa (формируется приложением)
    @JsonProperty("Slip")
    public String Slip;

    // Информация для печати чека (для внутреннего использования, наружу всегда null)
    @JsonProperty("ReceiptText")
    public ReceiptTextApi3 ReceiptText;

    // негде взять:
    // ErrorText
    // ContractNumber
    // InsuranceProduct
    // PolicyDate
    // PolicyDateString
    // PolicyDateU

    // technical staff

    public Class getClassType() {
        return this.getClass();
    }
}
