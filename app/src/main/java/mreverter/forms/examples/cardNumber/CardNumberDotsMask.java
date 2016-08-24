package mreverter.forms.examples.cardNumber;

import mreverter.forms.model.PaymentMethod;

/**
 * Created by mreverter on 8/7/16.
 */
public class CardNumberDotsMask extends CardNumberMask {

    public CardNumberDotsMask(PaymentMethod paymentMethod) {
        super(paymentMethod);
        setUnmaskedText("");
    }

    public CardNumberDotsMask() {
        super();
        setUnmaskedText("");
    }

    @Override
    public String getMaskedText() {

        String cardNumber = getRealText();
        int missingNumbers = getCardNumberLength() - cardNumber.length();

        if(missingNumbers != 0) {
            for (int i = 0; i < missingNumbers; i++) {
                cardNumber += "•";
            }
        }
        return getMaskedText(cardNumber);
    }
}
