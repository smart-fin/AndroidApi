# API для взаимодействия с Мобильным терминалом 2can

API предназначено для интеграции вашего мобильного приложения и Мобильного терминала 2can.

## Основной сценарий использования

1. Ваше мобильное приложение передает Мобильному терминалу 2can назначение и сумму платежа
2. Мобильный терминал 2can проводит операцию с платежной картой
3. Мобильный терминал 2can возвращает результат операции и передает управление обратно вашему приложению

## Что здесь?

Здесь находится исходный код приложения, демонстрирующий возможности API.

Если на вашем телефоне установлен и активирован
Мобильный терминал 2can, то демо-приложение вызовет его для проведения операции. 

## Версии Мобильного терминала 2can, поддерживающие данное API

- 2can mPos https://play.google.com/store/apps/details?id=ru.toucan.merchant 
- 2can Касса https://play.google.com/store/apps/details?id=ru.toucan.ecommerce

## Контакты и ресурсы

- Сайт проекта: http://www.2can.ru
- Адрес службы поддержки: android-support@smart-fin.ru

## Описание API

### 1. Отправка запроса на проведение платежа

#### Формирование и отправка запроса
```java
Intent intent = new Intent("ru.toucan.CALL_FOR_PAYMENT");
intent.putExtra("packageName", packageName);
intent.putExtra("Pin", Pin);
intent.putExtra("amount", amount);
intent.putExtra("desc", desc);
intent.putExtra("type", 1);
intent.putExtra("isLocalFiscalization", false);
startActivityForResult(intent, PAYMENT_RESULT_CODE);
```

Имя параметра|Тип|Описание|Обязательный или нет
-------------|---|--------|-------------------
packageName | String | имя пакета вашего приложения | обязательный
amount | int | сумма платежа в МДЕ (в копейках) - целочисленный формат | обязательный
desc | String | назначение платежа | обязательный
type | int | тип платежа (возможные значения: 1 - card, 2 - cash), при отсутствии в параметрах запроса будет запрошен у пользователя | не обязательный
isLocalFiscalization | Boolean | локальная фискализация (true), фискализация в mPos (false) | не обязательный
Pin | String | код доступа к Мобильному терминалу, при отсутствии в параметрах запроса будет запрошен у пользователя | не обязательный

PAYMENT_RESULT_CODE - requestCode, см. описание методов [startActivityForResult]( https://developer.android.com/reference/android/app/Activity.html#startActivityForResult(android.content.Intent,%20int) ) и [onActivityResult]( https://developer.android.com/reference/android/app/Activity.html#onActivityResult(int,%20int,%20android.content.Intent) )

#### Получение ответа

Для получения ответа необходимо переопределить метод 
```java
void onActivityResult(int requestCode, int resultCode, Intent data)
```

При успешном выполнении запроса data должен содержать объект:
```java
ru.toucan.merchant.business.domain_external.Payment payment = data.getParcelableExtra(Extras.payment);
```
Описание ru.toucan.merchant.business.domain_external.Payment:

Имя параметра|Тип|Описание
-------------|---|--------
paymentId | String | ID платежа
type | int | тип платежа (возможные значения: 1 - cardPayment, 2 - cashPayment, 3 - refundCard(voidCard), 4 - refundCash(voidCash)
receipt4print | ru.toucan.merchant.business.domain.print.Receipt4Print (см. описание ниже) | фискальные данные (если в запросе был прописан параметр isLocalFiscalization == true и мерчант зарегистрирован в ОФД)

Описание ru.toucan.merchant.business.domain.print.Receipt4Print:

Имя параметра|Тип|Описание
-------------|---|--------
cashRegisterId | Integer | ID облачной кассы мерчанта
company | String | Название компании мерчанта
companyAddress | String | Адрес компании мерчанта
phone | String | Телефон компании мерчанта
dateTime | String | Дата чека в формате yyyy-MM-dd HH:mm:ss
id | Integer | ID записи о чеке в нашей БД
INN | String | ИНН компании мерчанта
num | String | Номер чека облачной кассы
offlineNum | String | Оффлайн-номер чека облачной кассы, сгенерированный на сервере если облачная касса была не доступна, или сгенерированный мобильным приложением если наш API был недоступен
taxationType | Integer | Тип налогообложения (возможные значения: 1 - УСН, 2 - ОСН, 3 - ЕНВД)
vatRate | Decimal | Ставка НДС всего чека, %
vatSum | Decimal | Сумма НДС всего чека
items | ArrayList<ru.toucan.merchant.business.domain.print.Items> | Коллекция товаров или услуг в чеке (см. описане ru.toucan.merchant.business.domain.print.Items ниже)
deposit | Decimal | Внесено денег (наличный платеж)
authCode | String | Код авторизации платежа (платеж по карте)
cardType | Integer | Тип карты (платеж по карте)
lastFourDigits | String | Последние 4 цифры карты (платеж по карте)
reason | String | Причина возврата (чек возврата)
OFDPrintText | String | Фискальная часть чека
errorMessage | String | Ошибка формирования чека

Пример текста в OFDPrintText:

"------------------------------
Кассовый чек
ОФД - ФГУП ГНИВЦ ФНС России
Фискальный признак № $
{ticket_number}
Код ККТ ФНС: $
{fns_kkt_id}
Для проверки чека зайдите на
сайт: consumer.ofd-gnivc.ru
------------------------------"

Описание ru.toucan.merchant.business.domain.print.Items:

Имя параметра|Тип|Описание
-------------|---|--------
amount | Decimal | Стоимость единицы продукции или услуги чека
description | String | Название единицы продукции или услуги чека


resultCode — результат выполнения операции

Код | Описание
----|---------
0 | Успешно (если data != null)
1 | Подпись успешно поставлена в очередь на отправку
2 | Активация прошла успешно
3 | Сервер недоступен
4 | Интернет недоступен
6 | Системная ошибка сервиса
7 | Укажите сумму
8 | Укажите назначение
9 | Укажите packageName
11 | Инициализация ридера прошла успешно
13 | Ошибка. Вставьте карту чипом
14 | Ошибка проведения платежа. Попробуйте провести платеж заново
15 | Платеж не подтвержден картой
16 | Платеж отменен
17 | Некорректный ID устройства, выполните повторную активацию
18 | Превышен лимит платежа
20 | Ошибка сервера
21 | Ошибка сервера
50 | У платежа не указан НДС
60 | В приложении mPos отключен прием наличных платежей
71 | Код-доступа неверный
101 | Отмена активации
200 | Отмена операции
400 | Превышено время ожидания. Сессия закрыта
1001 | Ридер не инициализирован
1002 | Неизвестный ридер
1003  | Операция отклонена картой
1004 | Карта заблокирована
1005 | Не принимайте эту карту к оплате
1006 | Платеж не может быть обработан
1010 | Ошибка регистрации ридера. Обратитесь в службу поддержки

### 2. Отправка запроса на просмотр истории платежей

#### Формирование и отправка запроса
```java
Intent intent = new Intent("ru.toucan.CALL_FOR_HISTORY");
intent.putExtra("packageName", packageName);
intent.putExtra("Pin", Pin);
startActivity(intent);
```

Имя параметра|Тип|Описание|Обязательный или нет
-------------|---|--------|-------------------
packageName | String | имя пакета вашего приложения | обязательный
Pin | String | код доступа к Мобильному терминалу, при отсутствии в параметрах запроса будет запрошен у пользователя | не обязательный

### 3. Отправка запроса на получение фискальных данных

#### Формирование и отправка запроса
```java
Intent intent = new Intent("ru.toucan.CALL_GET_RECEIPT");
intent.putExtra("packageName", packageName);
intent.putExtra("Pin", Pin);
intent.putExtra("type", type);
intent.putExtra("paymentId", paymentId);
startActivityForResult(intent, GET_RECEIPT_RESULT_CODE);
```

Имя параметра|Тип|Описание|Обязательный или нет
-------------|---|--------|-------------------
packageName | String | имя пакета вашего приложения | обязательный
paymentId | String | ID платежа | обязательный
type | int | тип платежа (возможные значения: 1 - cardPayment, 2 - cashPayment, 3 - refundCard(voidCard), 4 - refundCash(voidCash) | обязательный
Pin | String | код доступа к Мобильному терминалу, при отсутствии в параметрах запроса будет запрошен у пользователя | не обязательный

#### Получение ответа

См. пункт 1.