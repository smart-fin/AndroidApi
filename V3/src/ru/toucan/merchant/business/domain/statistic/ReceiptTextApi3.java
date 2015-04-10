package ru.toucan.merchant.business.domain.statistic;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import ru.toucan.merchant.business.domain.ParcelableObject;
import ru.toucan.merchant.business.domain.RubDeserializer;
import ru.toucan.merchant.business.domain.RubSerializer;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ReceiptTextApi3 extends ParcelableObject {

    //    Торговая точка (торговое название мерчанта)
    @JsonProperty("Outlet")
    public String Outlet;

    //    Сумма платежа
    @JsonProperty("Amount")
    @JsonDeserialize(using = RubDeserializer.class)
    @JsonSerialize(using = RubSerializer.class)
    public Integer Amount;

    //    Комиссия
    @JsonProperty("Fee")
    @JsonDeserialize(using = RubDeserializer.class)
    @JsonSerialize(using = RubSerializer.class)
    public Integer Fee;

    //    Маска номера карты
    @JsonProperty("MaskedCardNumber")
    public String MaskedCardNumber;

    //    Название EMV-приложения (если платеж был по карте с чипом)
    @JsonProperty("EmvApp")
    public String EmvApp;

    //    Идентификатор EMV-приложения (если платеж был по карте с чипом)
    @JsonProperty("EmvAid")
    public String EmvAid;

    //    Идентификатор платежа
    @JsonProperty("OperationNum")
    public Integer OperationNum;

    //    Поддержка "Облачной кассы"
    @JsonProperty("RRN")
    public String RRN;

    //    Код авторизации
    @JsonProperty("AuthCode")
    public String AuthCode;

    //    Идентификатор марчанта в банке
    @JsonProperty("MID")
    public String MID;

    //    Идентификатор терминала
    @JsonProperty("TerminalId")
    public Integer TerminalId;

    //    Дата и время платежа в формате "yyyy-MM-dd HH:mm:ss"
    @JsonProperty("DateTime")
    public String DateTime;

    //        Назначение платежа
    @JsonProperty("Description")
    public String Description;

    //        Данные организации (ЗАО "Смартфин")
    @JsonProperty("Organization")
    public String Organization;

    //    Данные предприятия (мерчанта)
    @JsonProperty("Enterprise")
    public String Enterprise;

    @Override
    public Class getClassType() {
        return ReceiptTextApi3.class;
    }
}
