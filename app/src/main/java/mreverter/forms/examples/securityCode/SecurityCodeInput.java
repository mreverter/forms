package mreverter.forms.examples.securityCode;

import mreverter.forms.model.Input;
import mreverter.forms.model.InputErrorHandler;
import mreverter.forms.model.InputException;

import static android.text.TextUtils.isEmpty;

/**
 * Created by mreverter on 10/7/16.
 */
public class SecurityCodeInput implements Input, SecurityCodeDataListener {

    private String emptyErrorMessage;
    private String securityCode;
    private InputErrorHandler errorHandler;

    @Override
    public void setErrorHandler(InputErrorHandler inputErrorHandler) {
        this.errorHandler = inputErrorHandler;
    }

    @Override
    public void validate() throws InputException {
        if(isEmpty(securityCode)) {
            throw new InputException(emptyErrorMessage);
        }
    }

    public void setErrorMessages(String emptyErrorMessage) {
        this.emptyErrorMessage = emptyErrorMessage;
    }

    @Override
    public void showError(InputException e) {
        errorHandler.handle(e);
    }

    @Override
    public void onSecurityCodeDataChanged(String securityCode) {
        this.securityCode = securityCode;
    }
}
