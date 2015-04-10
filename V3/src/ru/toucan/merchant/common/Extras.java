package ru.toucan.merchant.common;

/**
 * Created with IntelliJ IDEA.
 * User: Nastya
 * Date: 02.04.13
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public class Extras {
    /*
     *  Команды iMagPay reader
     */
	
    public static String confirmRefund = "confirmRefund";

    // Тип платежа в операциях
    public static String type = "type";
    public static String paymentType = "paymentType";
    public static String reportType = "reportType";
    public static String paymentId = "paymentId";
    public static String time = "time";
    public static String sum = "sum";
    public static String firstSum = "firstSum";
    public static String desc = "desc";
    public static String descfull = "descfull";
    public static String track = "track";
    public static String lastDigits = "lastDigits";
    public static String desc_id = "desc_id";

    public static String demo = "demo";
    public static String authCode = "AuthCode";
    public static String cardType = "cardType";
    public static String notMatch = "notMatch";
    public static String goHome = "goHome";
    public static String hideKeyBoard = "hideKeyBoard";
    public static String date = "date";
    public static String delete = "delete";
    public static String url = "url";
    public static String title = "title";
    public static String dateTime = "dateTime";
    public static String company = "company";
    public static String tId = "tId";

    // cash register
    public static String deposit = "deposit";
    public static String change = "change";
    public static String pan = "pan";
    public static String offlineNumber = "offlineNumber";
    public static String logo = "logo";
    public static String receipt = "receipt";
	
    // запрос на запуск ридера
    public static final String start = "start";
    // ридер запущен
    public static final String started = "started";
    // ридер готов к читать карту
    public static final String read = "read";
    // запрос на остановку чтения
    public static final String stop = "stop";
    // ридер остановлен
    public static final String stopped = "stopped";
    // ридер отключен
    public static final String disconnected = "disconnected";
    // ошибка чтения карты (км-ридер)
    public static final String km_error_read = "km_error_read";
    // ридер не поддерживается
    public static final String not_support = "not_support";
    // ошибка инициализации ридера
    public static final String init_error = "init_error";
    // карта не определена - при чтении чиповой карты
    public static final String card_not_found = "card_not_found";
    // транзакция отклонена
    public static final String aborted = "aborted";
    // ошибка чтения карты
    public static final String errorRead = "errorRead";
    // инициализация ридера завершена
    public static final String init_finish = "init_finish";

    public static final String response = "response";
    public static final String responseAppList = "responseAppList";
    public static final String responseSelectApp = "responseSelectApp";
    // выбор приложения
    public static final String selectApp = "selectApp";
    public static final String command = "command";
    // передача Emv данных в ридер для авторизации платежа
    public static final String authEmvResponse = "authEmvResponse";
    // подтверждение Emv авторизации платежа
    public static final String confirm = "confirm";
    // ошибка подтверждения Emv авторизации платежа
    public static final String errorConfirm = "errorConfirm";
    // уровень батареи ридера
    public static final String battery_level = "battery_level";
    // передача Emv данных в ридер после возврата
    public static final String refundEmvResponse = "refundEmvResponse";
    // ошибка при записи Emv данных в ридер после возврата
    public static final String errorConfirmRefund = "errorConfirmRefund";

    // локальная фискализация для внешнего приложения (моб. апи)
    // клиент сам печатает чек
    public static String isLocalFiscalization = "isLocalFiscalization";
    // объект платеж для внешнего приложения (моб. апи)
    public static String payment = "payment";

    public static final String phone = "phone";
    public static final String email = "email";
    public static final String startReceipt = "startReceipt";
    public static final String numOrder = "numOrder";

    public static final String categoryId = "categoryId";
    public static final String categoryName = "categoryName";

    public static final String insuredName = "insuredName";
    public static final String insuranceProduct = "insuranceProduct";
    public static final String insuranceProductSelected = "insuranceProductSelected";
    public static final String contractNumber = "contractNumber";
    public static final String insurancePremium = "insurancePremium";
    public static final String installmentFrequency = "installmentFrequency";
    public static final String installmentFrequencySelected = "installmentFrequencySelected";
    public static final String insuranceQrCode = "insuranceQrCode";
    public static final String insuredPassportScan = "insuredPassportScan";
    public static final String contractTime = "contractTime";
    public static final String flags = "flags";

    public static final String name = "name";
    public static final String tableNumber = "tableNumber";


    public static final String invoiceId = "invoiceId";
    public static final String invoiceAmount = "invoiceAmount";
    public static final String shopName = "shopName";
    public static final String shopUrl = "shopUrl";
    public static final String mId = "mId";
    public static final String isChip = "isChip";

    public static final String fullPan = "fullPan";
    public static final String expDate = "expDate";
    public static final String key = "key";
    public static final String policyStartDate = "policyStartDate";
    public static final String policyEndDate = "policyEndDate";
    public static final String init_reader = "init_reader";
    public static final String emsrAesKeyData = "emsrAesKeyData";
    public static final String resetConfigMessage = "resetConfigMessage";
    public static final String config_reader = "config_reader";
    public static final String config_chunks = "config_chunks";
    public static final String isStopped = "isStopped";

    public static final String invoiceItems = "invoiceItems";

    public static final String transactionId = "transactionId";
    public static final String close = "close";
    public static final String price = "price";

    public static final String aesMsKeyVersion = "aesMsKeyVersion";
    public static final String serialNumber = "serialNumber";
    public static final String aesKeyVersion = "aesKeyVersion";
    public static final String rsaKeyVersion = "rsaKeyVersion";
    public static final String transactionNumber = "transactionNumber";
    public static final String state = "state";

    public static final String typeFace = "typeFace";
    public static final String canBack = "canBack";
    public static final String amount = "amount";
    public static final String needInstallUpdate = "needInstallUpdate";
    public static final String pin = "Pin";
    public static final String pin2 = "Pin2";
    public static final String packageName = "packageName";
    public static final String receipt4print = "receipt4print";
    public static final String receiptType = "receiptType";
    public static final String vat = "vat";

    // Refund
    public static final String refundDesc = "refundDesc";
    public static final String refundSum = "refundSum";
    public static final String shiftNumber = "shiftNumber";
    
    // API 3.0 params
    public static final String paramPackageName = "PackageName";
    public static final String paramPayment = "Payment";
    public static final String paramSecureCode = "SecureCode";
    public static final String paramRRNCode = "RRNCode";
    public static final String paramAmount = "Amount";
    public static final String paramDescription = "Description";
    public static final String paramFullDescription = "FullDescription";
    public static final String paramValueAddedTaxRate = "ValueAddedTaxRate";
    public static final String paramFiscalizationFlag = "FiscalizationFlag";
    public static final String paramReason = "Reason";
    public static final String paramReceiptNumber = "ReceiptNumber";
    public static final String paramSecureCardNumber = "SecureCardNumber";
    public static final String paramAuthorizationCode = "AuthorizationCode";
    public static final String paramGetPayInfo = "GetPayInfo";
    public static final String offlineSignatureFlag = "offlineSignatureFlag";
    public static final String errorResult = "errorResult";
    public static final String paramDateTimeFrom = "DateTimeFrom";
    public static final String paramDateTimeTill = "DateTimeTill";

    public static final String result = "result";
    public static final String requestCode = "requestCode";
    public static final String resultCode = "resultCode";

    // returning values
    public static final String paramErrorText = "ErrorText";
    public static final String paramSlip = "Slip";
    public static final String CardNumber = "CardNumber";

    public static final String def = "def";
    public static final String value = "value";
    public static final String fiscalization = "fiscalization";
    public static String updateAvailable = "updateAvailable";
}
