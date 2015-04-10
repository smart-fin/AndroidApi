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
public class ReturnDetailApi3 extends ParcelableObject {

    //    Идентификатор платежа
    @JsonProperty("PaymentId")
    public Integer PaymentId;

    //    Дата и время платежа в UTC
    @JsonProperty("DateTimeU")
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    public Date dateTime;

    //    Сумма возврата
    @JsonProperty("Amount")
    @JsonDeserialize(using = RubDeserializer.class)
    @JsonSerialize(using = RubSerializer.class)
    public Integer Amount;

    //    Сумма платежа
    @JsonProperty("PaymentAmount")
    @JsonDeserialize(using = RubDeserializer.class)
    @JsonSerialize(using = RubSerializer.class)
    public Integer PaymentAmount;

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

    //    Причина возврата
    @JsonProperty("RefundReason")
    public String RefundReason;

    //    Мобильный телефон или e­mailадрес покупателя
    @JsonProperty("CustomerAddress")
    public String CustomerAddress;

    //    Безопасный номер карты(6 первых, 4 последних цифры, остальное * )
    @JsonProperty("MaskedCardNumber")
    public String CardNumber;

    //    Retrieval Reference Number уникальный 16­ти значный идентификатор транзакции
    @JsonProperty("RRN")
    public String RRNCode;

    //    Код авторизации
    @JsonProperty("AuthCode")
    public String AuthorizationCode;

    //    Строка для печати чекa (формируется приложением)
    @JsonProperty("Slip")
    public String Slip;

    // technical staff

    public Class getClassType() {
        return this.getClass();
    }
}
