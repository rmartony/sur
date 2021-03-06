package uy.gub.dgr.sur.util;

import org.omnifaces.converter.SelectItemsConverter;
import uy.gub.dgr.sur.entity.BaseEntity;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 * User: rmartony
 * Date: 26/12/13
 * Time: 11:03 AM
 */
@FacesConverter("entitySelectItemsConverter")
public class EntitySelectItemsConverter extends SelectItemsConverter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Integer id = (value instanceof BaseEntity) ? ((BaseEntity) value).getId() : null;
        return (id != null) ? String.valueOf(id) : null;
    }

}