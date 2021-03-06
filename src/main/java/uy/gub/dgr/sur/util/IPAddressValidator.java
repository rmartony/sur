package uy.gub.dgr.sur.util;

import org.apache.commons.lang3.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: rmartony
 * Date: 20/02/14
 * Time: 12:23 PM
 */
@FacesValidator("ipValidator")
public class IPAddressValidator implements Validator {
    public final static String REGEXP_IP_VALIDATE = "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";

    private Pattern pattern;
    private Matcher matcher;

    public IPAddressValidator() {
        pattern = Pattern.compile(REGEXP_IP_VALIDATE);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        if (!validate((String) value)) {
            throw new ValidatorException(new FacesMessage("Dirección IP no válida"));
        }
    }

    /**
     * Validate ip address with regular expression
     *
     * @param ip ip address for validation
     * @return true valid ip address, false invalid ip address
     */
    public boolean validate(final String ip) {
        if (StringUtils.isEmpty(ip)) {
            return true;
        }
        matcher = pattern.matcher(ip);
        return matcher.matches();
    }
}