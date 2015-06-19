# API для взаимодействия с Мобильным терминалом 2can

API предназначено для интеграции вашего мобильного приложения и Мобильного терминала 2can

## Основной сценарий использования

1. Ваше мобильное приложение передает Мобильному терминалу 2can назначение и сумму платежа
2. Мобильный терминал 2can проводит операцию с платежной картой
3. Мобильный терминал 2can возвращает результат операции и передает управление обратно вашему приложению

## Что здесь?

Здесь находится исходный код приложения, демонстрирующий возможности API.

Если на вашем телефоне установлен и активирован
Мобильный терминал 2can, то демо-приложение вызовет его для проведения операции. 

## Версии Мобильного терминала 2can, поддерживающие данное API

- 2can Касса https://play.google.com/store/apps/details?id=ru.toucan.ecommerce

## Контакты и ресурсы

- Сайт проекта: http://www.2can.ru
- Адрес службы поддержки: android-support@smart-fin.ru

## Описание API
##### Пример формирования и отправки запроса
```java
Intent intent = new Intent("ACTION");
intent.putExtra("Param1", Param1);
intent.putExtra("Param2", Param2);
...
intent.putExtra("ParamN", ParamN);
startActivityForResult(intent, ACTION_RESULT_CODE);
```
ACTION_RESULT_CODE - requestCode, см. описание методов [startActivityForResult]( https://developer.android.com/reference/android/app/Activity.html#startActivityForResult(android.content.Intent,%20int) ) и [onActivityResult]( https://developer.android.com/reference/android/app/Activity.html#onActivityResult(int,%20int,%20android.content.Intent) )

##### Получение ответа

Для получения ответа необходимо переопределить метод 
```java
void onActivityResult(int requestCode, int resultCode, Intent data)
```
resultCode
```java
public static final int RESULT_CANCELED    = 0;
public static final int RESULT_OK          = -1;
```

Ответ может быть 2 видов, в зависимости от значения Response (FULL (если параметр в запрос не включен) | SHORT).
###### 1. Если Response == FULL, тогда результат возвращается в ключе result, в виде сложного объекта реализующего интерфейс Parcelable.
```java
if (intent.hasExtra("result")) {
     ParcelableObject result = intent.getParcelableExtra(Extras.result);

     if (result instanceof ru.toucan.api.APIPaymentResult) {
          // получили результат ru.toucan.api.APIPaymentResult instanceof ParcelableObject
     }
}
```

```java
public abstract class ParcelableObject implements Parcelable {
    public ParcelableObject(Parcel source) {
        try {
            String sourceJson = source.readString();
        
            Object readValue = getJsonObject(); // json object
        
            //инициализация всех полей данного объекта
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     @Override
    public void writeToParcel(Parcel dest, int flags) {
        try {
            String destJson = getJsonObject();
            
            dest.writeString(destJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    ...
}
```
```java
public class APIPaymentResult extends ParcelableObject {

    // Идентификатор платежа
    public Integer Payment;

    // Код авторизации
    public String AuthorizationCode;

    // Полная информация о платеже (необязательное поле)
    public PaymentDetailApi3 PaymentInfo;
}
```
См. подробное описание объектов [ru.toucan.api] (http://github.com/smart-fin/AndroidApi/tree/master/V3/src/ru/toucan/api/)

###### 2. Если Response == SHORT, тогда результат возвращается в виде словаря простых типов:
byte –> Число
char –> Строка
double –> Число
float –> Число
int –> Число
long –> Число
short –> Число
boolean –> Число
String –> Строка
Uri –> Строка
Bitmap –> Картинка

```java
Intent resultIntent = new Intent();
resultIntent.putExtra("param1", "Значение 1");
resultIntent.putExtra("param2", "Значение 2");
...
resultIntent.putExtra("paramN_1", 99);
resultIntent.putExtra("paramN", 100);
setResult(RESULT_OK, resultIntent);
```

### 1. ru.toucan.OPEN
Проверяет наличие подключенного ридера к мобильному терминалу.

##### Параметры запроса
###### Основные параметры:       параметры полученные в GET_PARAMETERS
###### Дополнительные парамерты: нет

### 2. ru.toucan.CLOSE
После окончания работы с мобильным терминалом существует возможность закрытия его с целью уменьшения потребления ресурсов устройства.

##### Параметры запроса
###### Основные параметры:       параметры полученные в GET_PARAMETERS
###### Дополнительные парамерты: нет

### 3. ru.toucan.PAYMENT
Оплата картой.

##### Параметры запроса
###### Основные параметры:       параметры полученные в GET_PARAMETERS
###### Дополнительные парамерты: описание в таблице
Имя параметра|Тип|Описание|Обязательный или нет
-------------|---|--------|-------------------
Amount | int | сумма платежа в МДЕ (в копейках) - целочисленный формат | обязательный
Description | String | назначение платежа | обязательный
FullDescription | String | подробное назначение платежа, используется для печати подробной информации об услуге на кассовом чеке (поддержка ФР Штрих-м) | не обязательный
ValueAddedTaxRate | String | НДС (в промилях) | не обязательный
ReceiptNumber | Номер чека | не обязательный
GetPayInfo | int | переключатель показывающий надо ли возвращать полную информацию о платеже (1-да, 2-нет)| не обязательный
FiscalizationFlag | int | Необходимость фискализации (0 - фискализация нужна(значение по-умолчанию), 1-фискализация не нужна) | не обязательный

### 4. ru.toucan.CASH_PAYMENT
Оплата наличными.

##### Параметры запроса
###### Основные параметры:       параметры полученные в GET_PARAMETERS
###### Дополнительные парамерты: описание в таблице
Имя параметра|Тип|Описание|Обязательный или нет
-------------|---|--------|-------------------
Amount | int | сумма платежа в МДЕ (в копейках) - целочисленный формат | обязательный
Description | String | назначение платежа | обязательный
FullDescription | String | подробное назначение платежа, используется для печати подробной информации об услуге на кассовом чеке (поддержка ФР Штрих-м) | не обязательный
ValueAddedTaxRate | String | НДС (в промилях) | не обязательный
ReceiptNumber | Номер чека | не обязательный
GetPayInfo | int | переключатель показывающий надо ли возвращать полную информацию о платеже (1-да, 2-нет)| не обязательный
FiscalizationFlag | int | Необходимость фискализации (0 - фискализация нужна(значение по-умолчанию), 1-фискализация не нужна) | не обязательный

### 5. ru.toucan.VIEW_PAYMENT
Просмотр детализации платежа.

##### Параметры запроса
###### Основные параметры:       параметры полученные в GET_PARAMETERS
###### Дополнительные парамерты: описание в таблице
Имя параметра|Тип|Описание|Обязательный или нет
-------------|---|--------|-------------------
Payment | int | ID платежа | обязательный

### 6. ru.toucan.CANCEL_PAYMENT
Отмена платежа по платежной карте.

##### Параметры запроса
###### Основные параметры:       параметры полученные в GET_PARAMETERS
###### Дополнительные парамерты: описание в таблице
Имя параметра|Тип|Описание|Обязательный или нет
-------------|---|--------|-------------------
Payment | int | ID платежа | обязательный
Amount | int | сумма платежа в МДЕ (в копейках) - целочисленный формат | обязательный
RRNCode | String | код RRN операции | обязательный
GetPayInfo | int | переключатель показывающий надо ли возвращать полную информацию о платеже (1-да, 2-нет)| не обязательный
FiscalizationFlag | int | необходимость фискализации (0 - фискализация нужна(значение по-умолчанию), 1-фискализация не нужна) | не обязательный

### 7. ru.toucan.RETURN_PAYMENT
Возврат платежа по платежной карте.

##### Параметры запроса
###### Основные параметры:       параметры полученные в GET_PARAMETERS
###### Дополнительные парамерты: описание в таблице
Имя параметра|Тип|Описание|Обязательный или нет
-------------|---|--------|-------------------
Payment | int | ID платежа | обязательный
Amount | int | сумма возврата в МДЕ (в копейках) - целочисленный формат | обязательный
Reason | String | Причина возврата | обязательный
SecureCardNumber | String | Безопасный номер карты | обязательный
RRNCode | String | RRN | обязательный
ReceiptNumber | Номер чека | не обязательный
GetPayInfo | int | переключатель показывающий надо ли возвращать полную информацию о платеже (1-да, 2-нет)| не обязательный
FiscalizationFlag | int | Необходимость фискализации (0 - фискализация нужна(значение по-умолчанию), 1-фискализация не нужна) | не обязательный

### 8. ru.toucan.SETTLEMENT
Получение итогов дня по картам. Операция сверки итогов, как правило, выполняется при закрытии кассовой смены. В случае, если используется «Касса.Мобильная», самостоятельно реализующая печать чека – выполняет печать чека. Иначе же, если используется «Мобильный терминал» и печать чека осуществляется на отдельно подключенном к системе фискальном регистраторе, метод возвращает текст слип чека для печати.

##### Параметры запроса
###### Основные параметры:       параметры полученные в GET_PARAMETERS
###### Дополнительные парамерты: описание в таблице
Имя параметра|Тип|Описание|Обязательный или нет
-------------|---|--------|-------------------
DateTimeFrom | String | Дата и время начала периода, формат yyyy-MM-ddHH:mm:ss (по умолчанию текущая дата) | не обязательный
DateTimeTill | String | Дата и время окончания периода, формат yyyy-MM-ddHH:mm:ss (по умолчанию текущая дата) | не обязательный

### 9. ru.toucan.GET_LAST_ERROR
Получение ошибки, если она возникла во время выполения последней операции.

##### Параметры запроса
###### Основные параметры:       нет
###### Дополнительные парамерты: нет

### 10. ru.toucan.GET_VERSION
Получение версии мобильного терминала 2can.

##### Параметры запроса
###### Основные параметры:       нет
###### Дополнительные парамерты: нет

### 11. ru.toucan.GET_PARAMETERS
Получение списка настраиваемых параметров. Данный метод предоставляет возможность стороннему мобильному приложению получить список обязательных входных параметров для всех предоставляемых методов, кроме GET_VERSION. Возвращает XML-строку в ключе TableParameters, описывающую данные параметры.

##### Параметры запроса
###### Основные параметры:       нет
###### Дополнительные парамерты: нет

Пример xml-файла

```xml
<Settings>
    <Page Caption="Параметры">
        <Group Caption="Параметры подключения">
            <Parameter Name="SecureCode" Caption="Код доступа" TypeValue="String" DefaultValue="1111" Required="false"/>
            <Parameter Name="PackageName" Caption="Название пакета" TypeValue="String" DefaultValue="ru.toucan.api.ems.demo" Required="true"/>
            <Parameter Name="Response" Caption="Вид ответа(SHORT|FULL)" TypeValue="String" DefaultValue="SHORT" Required="false"/>
        </Group>
    </Page>
</Settings>
```
Name         - имя параметра
Caption      - описание
TypeValue    - тип
DefaultValue - значение по умолчанию
Required     - обязательный