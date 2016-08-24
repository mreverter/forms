package mreverter.forms.model;

/**
 * Created by mreverter on 8/7/16.
 */
public class InputException extends Exception {
    private final String inputErrorMessage;

    public InputException(String inputError) {
        this.inputErrorMessage = inputError;
    }

    public String getInputErrorMessage() {
        return inputErrorMessage;
    }
}
