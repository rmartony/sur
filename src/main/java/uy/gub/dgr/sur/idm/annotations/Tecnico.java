package uy.gub.dgr.sur.idm.annotations;


import org.apache.deltaspike.security.api.authorization.SecurityBindingType;

import java.lang.annotation.*;

/**
 * User: rmartony
 * Date: 17/12/13
 * Time: 12:20 PM
 */

/**
 * A security binding type that can be used to restrict access to the beans and methods
 *
 * @author Shane Bryzak
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@SecurityBindingType
public @interface Tecnico {

}
