package mreverter.forms.examples.cardNumber;

import mreverter.forms.model.Input;
import mreverter.forms.model.InputErrorHandler;
import mreverter.forms.model.InputException;
import mreverter.forms.model.PaymentMethod;

import static android.text.TextUtils.isEmpty;

/**
 * Created by mreverter on 8/7/16.
 */
public class CardNumberInput implements Input, CardNumberDataListener {

    private String cardNumber;
    private String emptyErrorMessage;
    private InputErrorHandler mInputErrorHandler;

    public CardNumberInput() {
        cardNumber = "";
    }

    @Override
    public void setErrorHandler(InputErrorHandler inputErrorHandler) {
        mInputErrorHandler = inputErrorHandler;
    }

    @Override
    public void validate() throws InputException {
        if(isEmpty(cardNumber)) {
            throw new InputException(emptyErrorMessage);
        }
    }

    public void setErrorMessages(String emptyErrorMessage) {
        this.emptyErrorMessage = emptyErrorMessage;
    }

    @Override
    public void onCardDataChanged(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void showError(InputException e) {
        mInputErrorHandler.handle(e);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public boolean maxLengthReached(PaymentMethod paymentMethod) {
        if(paymentMethod != null) {
            return cardNumber.length() >= paymentMethod.getCardNumberLength();
        }
        else {
            return false;
        }
    }
}
