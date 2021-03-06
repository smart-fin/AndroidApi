package ru.toucan.example;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.toucan.merchant.business.domain.print.Receipt4Print;
import ru.toucan.merchant.business.domain_external.Payment;
import ru.toucan.merchant.common.PaymentType;

public class GeneralActivity extends Activity {

    private static final int PAYMENT_RESULT_CODE = 0x101010;
    private static final int GET_RECEIPT_RESULT_CODE = 0x202020;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.btnPayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText amountView = (EditText) findViewById(R.id.amountView);
                if (amountView.getText().length() == 0) {
                    Toast.makeText(GeneralActivity.this, getString(R.string.enter_amount), Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    Double.parseDouble(amountView.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(GeneralActivity.this, getString(R.string.enter_correct_amount), Toast.LENGTH_LONG).show();
                    return;
                }
                EditText descView = (EditText) findViewById(R.id.descView);
                if (descView.getText().length() == 0) {
                    Toast.makeText(GeneralActivity.this, getString(R.string.enter_desc), Toast.LENGTH_LONG).show();
                    return;

                }
                makePayment(Double.parseDouble(amountView.getText().toString()), descView.getText().toString());
            }
        });
        findViewById(R.id.btnPaymentCash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText amountView = (EditText) findViewById(R.id.amountView);
                if (amountView.getText().length() == 0) {
                    Toast.makeText(GeneralActivity.this, getString(R.string.enter_amount), Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    Double.parseDouble(amountView.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(GeneralActivity.this, getString(R.string.enter_correct_amount), Toast.LENGTH_LONG).show();
                    return;
                }
                EditText descView = (EditText) findViewById(R.id.descView);
                if (descView.getText().length() == 0) {
                    Toast.makeText(GeneralActivity.this, getString(R.string.enter_desc), Toast.LENGTH_LONG).show();
                    return;

                }
                makePaymentCash(Double.parseDouble(amountView.getText().toString()), descView.getText().toString());
            }
        });

        findViewById(R.id.btnHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHistory();
            }
        });

        findViewById(R.id.btnGetReceipt4Print).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReceipt4Print();
            }
        });

        setTitle(getString(R.string.app_name) + " " + getVersionName(getApplicationContext()));

        updateView();
    }

    /**
     * Запрос на проведение платежа картой
     * <p/>
     * Если приложение не было активированно, откроется окно активации приложения
     * После успешной активации нужно будет настроить код доступа и затем проводить платеж
     * <p/>
     * Если приложение было ранее активированно, будет запрошен пин-код
     */
    private void makePayment(double amount, String desc) {

        Intent intent = new Intent(Actions.PAYMENT_ACTION);
        // packageName - имя пакета для возвращения результата проведения платежа
        intent.putExtra(Extras.packageName, getPackageName());
        String pin = ((EditText)findViewById(R.id.pinView)).getText().toString();
        if (pin.length() > 0) {
            intent.putExtra(Extras.pin, pin);
        }
        // сумма платежа
        intent.putExtra(Extras.amount, amount);
        // назначение платежа
        intent.putExtra(Extras.desc, desc);
        // тип платежа
        intent.putExtra(Extras.paymentType, PaymentType.card);
        // isLocalFiscalization = true  - фискализация в этом приложении
        //                      = false - фискализация в mPos
        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkboxView);
        intent.putExtra(Extras.isLocalFiscalization, checkBox.isChecked());
        try {
            startActivityForResult(intent, PAYMENT_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(GeneralActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Запрос на проведение платежа наличными
     * <p/>
     * Если приложение не было активированно, откроется окно активации приложения
     * После успешной активации нужно будет настроить код доступа и затем проводить платеж
     * <p/>
     * Если приложение было ранее активированно, будет запрошен пин-код
     */
    private void makePaymentCash(double amount, String desc) {

        Intent intent = new Intent(Actions.PAYMENT_ACTION);
        // packageName - имя пакета для возвращения результата проведения платежа
        intent.putExtra(Extras.packageName, getPackageName());
        String pin = ((EditText)findViewById(R.id.pinView)).getText().toString();
        if (pin!=null && pin.length() > 0) {
            intent.putExtra(Extras.pin, pin);
        }
        // amount - сумма платежа
        intent.putExtra(Extras.amount, amount);
        // desc - назначение платежа
        intent.putExtra(Extras.desc, desc);
        // тип платежа
        intent.putExtra(Extras.paymentType, PaymentType.cash);
        // isLocalFiscalization = true  - фискализация в этом приложении
        //                      = false - фискализация в mPos
        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkboxView);
        intent.putExtra(Extras.isLocalFiscalization, checkBox.isChecked());
        try {
            startActivityForResult(intent, PAYMENT_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(GeneralActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Запрос на просмотр истории платежей
     * <p/>
     * Если приложение не было активированно, откроется окно активации приложения
     * После успешной активации нужно будет настроить код доступа и затем проводить платеж
     * <p/>
     * Если приложение было ранее активированно, будет запрошен пин-код
     */
    private void showHistory() {

        Intent intent = new Intent(Actions.HISTORY_ACTION);
        // packageName - имя пакета для возвращения результата проведения платежа
        intent.putExtra(Extras.packageName, getPackageName());
        String pin = ((EditText)findViewById(R.id.pinView)).getText().toString();
        if (pin!=null && pin.length() > 0) {
            intent.putExtra(Extras.pin, pin);
        }
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(GeneralActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Запрос на получение фискальных данных для платежа
     */
    private void getReceipt4Print() {
        Intent intent = new Intent(Actions.GET_RECEIPT_ACTION);
        // packageName - имя пакета для возвращения результата проведения платежа
        intent.putExtra(Extras.packageName, getPackageName());
        // код-доступа
        String pin = ((EditText)findViewById(R.id.pinView)).getText().toString();
        if (pin!=null && pin.length() > 0) {
            intent.putExtra(Extras.pin, pin);
        }
        // тип платежа
        intent.putExtra(Extras.paymentType, getIntent().getSerializableExtra(Extras.paymentType));
        // Id платежа
        intent.putExtra(Extras.paymentId, getIntent().getStringExtra(Extras.paymentId));
        try {
            startActivityForResult(intent, GET_RECEIPT_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(GeneralActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return " - ";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("2can", "got result " + resultCode);
        switch (requestCode) {
            case PAYMENT_RESULT_CODE:
                String msg = null;
                if (data == null) {
                    msg = "Ошибка. Code: " + resultCode + " [" + /*APIResult.getResult(*/resultCode/*).toString()*/ + "]";
                } else {
                    if (resultCode == 0) {
                        msg = getString(R.string.payment_finished);
                        Payment payment = data.getParcelableExtra(Extras.payment);
                        if (payment != null) {
                            getIntent().putExtra(Extras.paymentId, payment.paymentId);
                            getIntent().putExtra(Extras.paymentType, payment.type);
                            msg += "\n\n" + payment.toString();

                            updateView();
                        } else {
                            msg += "\n\n" + "payment is null";
                        }
                    } else {
                        msg = getString(R.string.operation_not_finished)+"\n\nCode: " + resultCode + " [" + /*APIResult.getResult(*/resultCode/*).toString() */+ "]";
                    }
                }
                setResult(msg);
                break;
            case GET_RECEIPT_RESULT_CODE:
                if (data == null) {
                    msg = getString(R.string.operation_not_finished) + "\n\nCode: " + resultCode + " [" + /*APIResult.getResult(*/resultCode/*).toString()*/ + "]";
                } else {
                    if (resultCode == 0) {
                        msg = getString(R.string.got_fiscal_data);
                        Payment payment = data.getParcelableExtra(Extras.payment);
                        if (payment != null) {
                            Receipt4Print receipt4Print = payment.receipt4Print;
                            msg += "\n\n" + (receipt4Print != null ? receipt4Print.toString() : "receipt4print is null");
                        } else {
                            msg += "\n\n" + "payment is null";
                        }
                    } else {
                        msg = getString(R.string.operation_not_finished) + "\n\nCode: " + resultCode + " [" + /*APIResult.getResult(*/resultCode/*).toString() */+ "]";
                    }
                }
                setResult(msg);
                break;
            default:
                setResult("requestCode (" + requestCode + ")");
        }
    }

    private void setResult(String text) {
        ((TextView)findViewById(R.id.resultView)).setText(text);
    }

    private void updateView() {
        findViewById(R.id.btnGetReceipt4Print).setEnabled(
                getIntent().hasExtra(Extras.paymentId)
        );
    }
}
