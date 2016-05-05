package uy.gub.dgr.sur.audit;

import java.lang.annotation.*;

/**
 * User: rmartony
 * Date: 16/01/14
 * Time: 06:47 PM
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TrackChanges {
}
