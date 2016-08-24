package mreverter.forms.examples.securityCode;

import mreverter.forms.model.PaymentMethod;
import mreverter.forms.model.TextMask;

/**
 * Created by mreverter on 10/7/16.
 */
public class SecurityCodeDotsMask extends TextMask {

    private static final int DEFAULT_CODE_LENGTH = 4;

    private PaymentMethod paymentMethod;

    public SecurityCodeDotsMask() {
        this.paymentMethod = null;
        this.setUnmaskedText("");
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String getMaskedText() {
        String securityCode = getRealText();
        int missingNumbers = getSecurityCodeLength() - securityCode.length();

        if(missingNumbers != 0) {
            for (int i = 0; i < missingNumbers; i++) {
                securityCode += "•";
            }
        }
        return securityCode;
    }

    private int getSecurityCodeLength() {
        return paymentMethod == null ? DEFAULT_CODE_LENGTH : paymentMethod.getSecurityCodeLength();
    }
}
