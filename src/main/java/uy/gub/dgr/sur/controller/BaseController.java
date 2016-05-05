package uy.gub.dgr.sur.controller;

import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;

import java.io.Serializable;

/**
 * User: rafa
 * Date: 19/03/14
 * Time: 06:42 PM
 */
public class BaseController implements Serializable {
    @Getter
    @Setter
    private ControllerMode mode = ControllerMode.VIEW;

    @Getter
    private String backOutcome;

    @Getter
    @Setter
    private String lastBackOutcome;

    public void setBackOutcome(String outcome) {
        lastBackOutcome = backOutcome;
        backOutcome = outcome;
    }

    public String doView() {
        setMode(ControllerMode.VIEW);
        return "view";
    }

    public String doCancel() {
        if (getBackOutcome() != null) {
            String viewId = Faces.getContext().getViewRoot().getViewId();

            if (getBackOutcome().equals(viewId)) {
                if (getLastBackOutcome() != null) {
                    return getLastBackOutcome() + "?faces-redirect=true";
                } else {
                    return getBackOutcome();
                }
            } else {
                return getBackOutcome() + "?faces-redirect=true";
            }
        }
        return "cancel";
    }

    protected String resolveNavegation() {
        String outcome;
        if (getBackOutcome() != null) {
            outcome = getBackOutcome();
        } else {
            outcome = "success";
        }
        return outcome;
    }

    public boolean isReadOnly() {
        return ControllerMode.VIEW.equals(getMode());
    }
}
