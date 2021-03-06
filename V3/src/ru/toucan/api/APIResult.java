package ru.toucan.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Nastya
 * Date: 18.07.12
 * Time: 21:25
 * To change this template use File | Settings | File Templates.
 */
public class APIResult {

    private static Map<Integer, APIResult> byApiCode = new HashMap<Integer, APIResult>();
    private static Map<Integer, APIResult> byServerCode = new HashMap<Integer, APIResult>();


    // Код ошибки
    public final int code;
    // Краткое описание
    public String text;
    // Серверный код
    public int serverCode;

    public APIResult(int code, String text) {
        this(code, text, -1);
    }
    public APIResult(int code, String text, int serverCode) {
        this.code = code;
        this.text = text;
        this.serverCode = serverCode;

        // старые значения не перезаписываем!
        if (!byApiCode.containsKey(code)) {
            byApiCode.put(code, this);
        }
        if (serverCode > 0 && !byServerCode.containsKey(serverCode)) {
            byServerCode.put(serverCode, this);
        }

    }
    public APIResult(APIResult another) {
        this.code = another.code;
        this.text = another.text;
        this.serverCode = another.serverCode;
    }

    public static final APIResult back = new APIResult(-1, "back");

    public static final APIResult success = new APIResult(0, "Успешно");

    public static final APIResult success_put_to_queue = new APIResult(1, "Успешно, поставлено в очередь");
    public static final APIResult success_activation = new APIResult(2, "Успешно");

    public static final APIResult connection_timeout = new APIResult(3, "Сервер не отвечает");

    public static final APIResult network_is_not_available = new APIResult(4, "Сеть недоступна");
    public static final APIResult network_is_available = new APIResult(5, "network_is_available");

    public static final APIResult system_service_error = new APIResult(6, "system_service_error");

    public static final APIResult enter_amount = new APIResult(7, "Необходимо указать сумму");
    public static final APIResult enter_desc = new APIResult(8, "Необходимо указать назначение платежа");
    public static final APIResult enter_package_name = new APIResult(9, "Необходимо указать PackageName");
    public static final APIResult enter_email = new APIResult(10, "E-mail для отправки терминального чека указан неверно");
    public static final APIResult enter_phone_number = new APIResult(12, "Номер телефона для отправки терминального чека указан неверно");

    public static final APIResult success_config_reader = new APIResult(11, "success_config_reader");
    public static final APIResult plug_card_by_chip = new APIResult(13, "Вставьте карту чипом в ридер", 301041);
    public static final APIResult try_payment_again = new APIResult(14, "Попробуйте провести платёж заново");

    public static final APIResult payment_not_confirmed = new APIResult(15, "Платеж не подтвержден");
    public static final APIResult payment_canceled = new APIResult(16, "Платеж отменен");

    public static final APIResult need_reactivation = new APIResult(17, "Некорректный статус устройства, выполните повторную активацию", 114);
    public static final APIResult payment_limited = new APIResult(18, "Превышен лимит платежа");
    public static final APIResult delay_signature_is_disabled = new APIResult(19, "Мерчанту не позволена отложенная догрузка подписи");

    public static final APIResult server_error = new APIResult(20, "Ошибка сервера");
    public static final APIResult server_parser_error = new APIResult(21, "Ошибка сервера");
    public static final APIResult need_activation = new APIResult(22, "Выполните активацию");
    public static final APIResult payment_month_limited = new APIResult(23, "Превышен месячный лимит");

    public static final APIResult payment_repeated_see_in_history = new APIResult(31, "Повтор платежа, проверьте историю", 300203);

    public static final APIResult select_vat = new APIResult(50, "У платежа не указан НДС");
    public static final APIResult cash_payment_not_available = new APIResult(60, "В приложении mPos отключен прием наличных платежей");

    public static final APIResult pin_verified = new APIResult(70, "pin_verified");
    public static final APIResult pin_wrong = new APIResult(71, "Неверный код доступа");
    // для отладки
    public static final APIResult unknown_error = new APIResult(100, "Ошибка");
    public static final APIResult cancel_activation = new APIResult(101, "cancel_activation");
    public static final APIResult cancel_operation = new APIResult(200, "Отмена операции");

    public static final APIResult waiting_timeout = new APIResult(400, "waiting_timeout");

    public static final APIResult reader_not_init = new APIResult(1001, "Ридер не инициализирован", 500010);
    public static final APIResult reader_not_identify = new APIResult(1002, "Неизвестный ридер");
    public static final APIResult transaction_aborted_by_card = new APIResult(1003, "Операция отклонена картой", 300202);
    public static final APIResult card_blocked = new APIResult(1004, "Карта заблокирована");
    public static final APIResult dont_use_card = new APIResult(1005, "Не принимайте эту карту к оплате!"); // wtf?
    public static final APIResult payment_cannot_processing = new APIResult(1006, "Платеж не может быть обработан", 300200);
    public static final APIResult payment_cannot_processing2 = new APIResult(300201, "Операция отклонена. Воспользуйтесь другой картой или свяжитесь с банком, выпустившим карту.", 300201);
    public static final APIResult wrong_payment_status = new APIResult(1007, "Неверный статус платежа", 300102);
    public static final APIResult error_config_reader = new APIResult(1010, "Ошибка регистрации ридера. Обратитесь в службу поддержки.");
    public static final APIResult error_reader_testing = new APIResult(1011, "Ошибка проверки ридера.");
    public static final APIResult error_not_enough_parameters = new APIResult(2000, "Не переданы необходимые параметры");

    public static final APIResult cannot_make_refund = new APIResult(5910, "Невозможно выполнить возврат", 300208);
    public static final APIResult operation_refund = new APIResult(5057, "Операция отклонена. Воспользуйтесь другой картой или свяжитесь с банком, выпустившим карту.", 5057);
    public static final APIResult operation_refund_2 = new APIResult(5065, "Операция отклонена. Воспользуйтесь другой картой или свяжитесь с банком, выпустившим карту.", 5065);
    public static final APIResult operation_refund_3 = new APIResult(5097, "Операция отклонена. Воспользуйтесь другой картой или свяжитесь с банком, выпустившим карту.", 5097);
    public static final APIResult invalid_pin = new APIResult(5055, "Операция отклонена. Воспользуйтесь другой картой или свяжитесь с банком, выпустившим карту.", 5055);
    public static final APIResult settings_in_bank_not_correct = new APIResult(5012, "Операция отклонена. \n" +
            "Воспользуйтесь другой картой или свяжитесь со службой поддержки.", 5012);
    public static final APIResult operation_refund_4 = new APIResult(5096, "Операция отклонена. \n" +
            "Воспользуйтесь другой картой или свяжитесь со службой поддержки.", 5096);
    public static final APIResult operation_refund_region_limit = new APIResult(5036, "Операция отклонена. \n" +
            "Воспользуйтесь другой картой или свяжитесь со службой поддержки.", 5036);
    public static final APIResult operation_refund_region_limit_2 = new APIResult(5062, "Операция отклонена. \n" +
            "Воспользуйтесь другой картой или свяжитесь со службой поддержки.", 5062);
    public static final APIResult operation_refund_5 = new APIResult(5001, "Операция отклонена. \n" +
            "Воспользуйтесь другой картой или свяжитесь со службой поддержки.", 5001);
    public static final APIResult operation_refund_6 = new APIResult(5002, "Операция отклонена. \n" +
            "Воспользуйтесь другой картой или свяжитесь со службой поддержки.", 5002);
    public static final APIResult not_enough_money = new APIResult(5051, "Недостаточно средств. Воспользуйтесь другой картой или укажите меньшую сумму.", 5051);
    public static final APIResult card_of_foreign_banks_not_accepted = new APIResult(2111, "Карты иностранных банков не принимаются. Воспользуйтесь другой картой." +
            "Для приема карт иностранных Банков обратитесь в службу поддержки.", 2111);
    public static final APIResult country_of_banks_undefined = new APIResult(2110, "Страна банка, выпустившего карту не определена. Воспользуйтесь другой картой.", 2110);
    public static final APIResult exceeded_limit_frequency_of_successful_payments = new APIResult(2014, "Превышен лимит частоты успешно проведенных платежей по одной карте. " +
            "Для увеличения лимита свяжитесь со службой поддержки.", 2014);
    public static final APIResult reader_blocked = new APIResult(5101, "Ридер заблокирован. \n" +
            "Обратитесь в службу поддержки.", 5101);
    public static final APIResult exceeded_limit_of_sum_payment = new APIResult(2001, "Превышен лимит суммы платежа.  \n" +
            "Укажите меньшую сумму. Для увеличения лимита свяжитесь со службой поддержки.", 2001);

    public static final APIResult merchant_payment_locked = new APIResult(400503, "Прием платежей заблокирован", 400503);
    public static final APIResult sign_timeout_payment_canceled = new APIResult(0, "Время ожидания подписи истекло. Платеж отменен.", 0);

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", text='" + text + '\'' +
                ", serverCode=" + serverCode +
                '}';
    }

    public static APIResult getResult(Integer code) {
        if (byApiCode.containsKey(code)) {
            return byApiCode.get(code);
        } else {
            return unknown_error;
        }
    }
    public static APIResult getResultByServerCode(Integer serverCode, String serverDescription) {
        APIResult out;
        if (byServerCode.containsKey(serverCode)) {
            out = new APIResult(byServerCode.get(serverCode));
        } else {
            out = new APIResult(server_error);
            out.text = serverDescription;
        }

        // сохраняем дескрипшен и код, пришедшие с сервера
        out.serverCode = serverCode;

        return out;
    }

    @Override
    public boolean equals(Object that) {
        if ( this == that ) return true;
        if ( !(that instanceof APIResult) ) return false;
        return ((APIResult)that).code == this.code;
    }

    public APIErrorResult getAER() {
        APIErrorResult r = new APIErrorResult();
        r.ErrorCode = this.code;
        r.ErrorText = this.text;
        r.ErrorCodeExt = this.serverCode;
        return r;
    }
}
