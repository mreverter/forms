package mreverter.forms.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mreverter.forms.R;
import mreverter.forms.examples.cardNumber.CardNumberDotsMask;
import mreverter.forms.examples.cardNumber.CardNumberInput;
import mreverter.forms.examples.cardNumber.CardNumberMask;
import mreverter.forms.examples.securityCode.SecurityCodeDotsMask;
import mreverter.forms.examples.securityCode.SecurityCodeInput;
import mreverter.forms.fragments.CardRepresentationFragment;
import mreverter.forms.model.Input;
import mreverter.forms.model.InputErrorHandler;
import mreverter.forms.model.InputException;
import mreverter.forms.model.PaymentMethod;

public class SimpleFormActivity extends AppCompatActivity {

    private PaymentMethod mSelectedPaymentMethod;

    private CardNumberDotsMask mCardNumberMask;
    private CardNumberMask mCardNumberEntryMask;
    private CardNumberInput mCardNumberInput;

    private SecurityCodeInput mSecurityCodeInput;
    private SecurityCodeDotsMask mSecurityCodeMask;

    private List<Input> mInputs;

    private EditText mCardNumberViewEditText;
    private EditText mSecurityCodeEditText;
    private CardRepresentationFragment mCardRepresentationFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_form);

        initializeControls();
        initializeMasks();
        createInputs();
        setDataListeners();
        initializeCardRepresentationFragment();
    }

    private void initializeCardRepresentationFragment() {
        mCardRepresentationFragment = CardRepresentationFragment.newInstance(mCardNumberMask, mSecurityCodeMask);
        getSupportFragmentManager().beginTransaction().replace(R.id.cardRepresentation,mCardRepresentationFragment).commit();
    }

    private void initializeControls() {
        mCardNumberViewEditText = (EditText) findViewById(R.id.cardNumberEditText);
        mSecurityCodeEditText = (EditText) findViewById(R.id.securityCodeEditText);
        mSecurityCodeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    mCardRepresentationFragment.onSecurityCodeFocused();
                } else {
                    mCardRepresentationFragment.onSecurityCodeUnfocused();
                }
            }
        });
    }

    private void initializeMasks() {
        mCardNumberEntryMask = new CardNumberMask();
        mCardNumberMask = new CardNumberDotsMask();

        mSecurityCodeMask = new SecurityCodeDotsMask();
    }

    private void createInputs() {
        mInputs = new ArrayList<>();

        createCardNumberInput();

        createSecurityCodeInput();
    }

    private void createCardNumberInput() {
        mCardNumberInput = new CardNumberInput();
        mCardNumberInput.setErrorMessages("Ingresa tu número de tarjeta");
        mCardNumberInput.setErrorHandler(new InputErrorHandler() {
            @Override
            public void handle(InputException exception) {
                mCardNumberViewEditText.setError(exception.getInputErrorMessage());
            }
        });
        mInputs.add(mCardNumberInput);
    }

    private void createSecurityCodeInput() {
        mSecurityCodeInput = new SecurityCodeInput();
        mSecurityCodeInput.setErrorMessages("Ingresa tu código de seguridad");
        mSecurityCodeInput.setErrorHandler(new InputErrorHandler() {
            @Override
            public void handle(InputException exception) {
                mSecurityCodeEditText.setError(exception.getInputErrorMessage());
            }
        });
        mInputs.add(mSecurityCodeInput);
    }

    private void setDataListeners() {
        setCardNumberDataListener();
        setSecurityCodeDataListener();
    }

    private void setCardNumberDataListener() {
        mCardNumberViewEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mSelectedPaymentMethod = guessPaymentMethod(s.toString());
                updateMasksForPaymentMethod(mSelectedPaymentMethod);
                String cardNumberWithoutSpaces = s.toString().replace(" ", "");

                boolean cardNumberInputChanged = cardNumberInputChanged(cardNumberWithoutSpaces);

                if(cardNumberInputChanged && !mCardNumberInput.maxLengthReached(mSelectedPaymentMethod)){
                    updateCardDataEntryText(cardNumberWithoutSpaces);
                    updateCardDataRepresentation(cardNumberWithoutSpaces);
                }
            }
        });
    }

    private void setSecurityCodeDataListener() {
        mSecurityCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!securityCodeMaxLengthReached(s.toString(), mSelectedPaymentMethod)) {
                    mSecurityCodeInput.onSecurityCodeDataChanged(s.toString());
                    mCardRepresentationFragment.onSecurityCodeDataChanged(s.toString());
                }
            }
        });
    }

    private void updateCardDataRepresentation(String cardNumber) {
        mCardRepresentationFragment.onCardDataChanged(cardNumber);
    }

    private void updateCardDataEntryText(String cardNumber) {
        mCardNumberInput.onCardDataChanged(cardNumber);
        mCardNumberEntryMask.setUnmaskedText(cardNumber);
        mCardNumberViewEditText.setText(mCardNumberEntryMask.getMaskedText());
        mCardNumberViewEditText.setSelection(mCardNumberViewEditText.length());
    }

    private void updateMasksForPaymentMethod(PaymentMethod paymentMethod) {
        mCardNumberEntryMask.setPaymentMethod(paymentMethod);
        mCardRepresentationFragment.getMask().setPaymentMethod(paymentMethod);
    }

    private PaymentMethod guessPaymentMethod(String cardNumberInput) {

        PaymentMethod paymentMethod = null;

        if(!cardNumberInput.isEmpty()) {
            if(cardNumberInput.charAt(0) == '3') {
                paymentMethod = new PaymentMethod();
                paymentMethod.setId("amex");
                paymentMethod.setCardNumberLength(14);
                paymentMethod.setSecurityCodeLength(4);
            }
            else {
                paymentMethod = new PaymentMethod();
                paymentMethod.setId("visa");
                paymentMethod.setCardNumberLength(16);
                paymentMethod.setSecurityCodeLength(3);
            }
        }
        return paymentMethod;
    }

    private boolean cardNumberMaxLengthReached(String cardNumber, PaymentMethod paymentMethod) {
        boolean reached = false;
        if(paymentMethod != null && cardNumber.length() > paymentMethod.getCardNumberLength()) {
            reached = true;
        }
        return reached;
    }

    private boolean securityCodeMaxLengthReached(String securityCode, PaymentMethod paymentMethod) {
        boolean reached = false;
        if(paymentMethod != null && securityCode.length() >= paymentMethod.getSecurityCodeLength()) {
            reached = true;
        }
        return reached;
    }

    private boolean cardNumberInputChanged(String cardNumber) {
        return !cardNumber.equals(mCardNumberInput.getCardNumber());
    }

    public void submitForm(View view) {

        boolean validSubmit = true;
        for(Input input : mInputs) {
            try {
                input.validate();
            } catch (InputException e) {
                validSubmit = false;
                input.showError(e);
            }
        }
        if(validSubmit) {
            Toast.makeText(this, "¡Todo bien!", Toast.LENGTH_LONG).show();
        }
    }
}
