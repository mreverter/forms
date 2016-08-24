package mreverter.forms.model;

/**
 * Created by mreverter on 8/7/16.
 */
public interface Input {
    void setErrorHandler(InputErrorHandler inputErrorHandler);
    void validate() throws InputException;
    void showError(InputException e);
}
