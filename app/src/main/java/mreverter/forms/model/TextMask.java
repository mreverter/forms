package mreverter.forms.model;

/**
 * Created by mreverter on 8/7/16.
 */
public abstract class TextMask {

    private String realText;

    public void setUnmaskedText(String text) {
        realText = text;
    }

    public String getRealText() {
        return realText;
    }

    public abstract String getMaskedText();
}
