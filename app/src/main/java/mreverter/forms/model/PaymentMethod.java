package mreverter.forms.model;

/**
 * Created by mreverter on 8/7/16.
 */
public class PaymentMethod {

    private String id;
    private int cardNumberLength;
    private int securityCodeLength;

    public int getCardNumberLength() {
        return cardNumberLength;
    }

    public void setCardNumberLength(int cardNumberLength) {
        this.cardNumberLength = cardNumberLength;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSecurityCodeLength() {
        return securityCodeLength;
    }

    public void setSecurityCodeLength(int securityCodeLength) {
        this.securityCodeLength = securityCodeLength;
    }
}
