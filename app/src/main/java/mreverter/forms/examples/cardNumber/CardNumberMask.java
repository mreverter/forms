package mreverter.forms.examples.cardNumber;

import mreverter.forms.model.PaymentMethod;
import mreverter.forms.model.TextMask;

/**
 * Created by mreverter on 8/7/16.
 */
public class CardNumberMask extends TextMask {

    protected PaymentMethod paymentMethod;
    protected final int DEFAULT_CARD_LENGTH = 16;

    public CardNumberMask(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CardNumberMask() {
        this.paymentMethod = null;
    }

    @Override
    public void setUnmaskedText(String text) {
        if(paymentMethod == null || text.length() <= paymentMethod.getCardNumberLength()) {
            super.setUnmaskedText(text);
        }
    }

    @Override
    public String getMaskedText() {
        String cardNumber = getRealText();
        return getMaskedText(cardNumber);
    }

    protected String getMaskedText(String cardNumber) {
        String maskedText = "";
        int cardNumberLength = getCardNumberLength();

        if(paymentMethod != null && paymentMethod.getId().equals("amex")) {
            for (int i = 0; i < cardNumber.length(); i++) {
                maskedText += cardNumber.charAt(i);
                if (i != 0 && i != cardNumberLength-1 && ((i + 1) == 4) || ((i + 1) == 11)) {
                    maskedText += " ";
                }
            }
        }
        else {
            for (int i = 0; i < cardNumber.length(); i++) {
                maskedText += cardNumber.charAt(i);
                if (i != 0 && i != cardNumberLength-1 && (i + 1) % 4 == 0) {
                    maskedText += " ";
                }
            }
        }
        return maskedText;
    }

    protected int getCardNumberLength() {
        return paymentMethod == null ? DEFAULT_CARD_LENGTH : paymentMethod.getCardNumberLength();
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
