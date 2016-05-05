package uy.gub.dgr.sur.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * User: rmartony
 * Date: 09/01/14
 * Time: 07:48 PM
 */
@FacesConverter(value = "genericMapConverter")
public class GenericMapEntityConverter implements Converter {
    private static Map<Object, String> entities = new WeakHashMap<>();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        synchronized (entities) {
            if (!entities.containsKey(entity)) {
                String uuid = UUID.randomUUID().toString();
                entities.put(entity, uuid);
                return uuid;
            } else {
                return entities.get(entity);
            }
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
        for (Entry<Object, String> entry : entities.entrySet()) {
            if (entry.getValue().equals(uuid)) {
                return entry.getKey();
            }
        }
        return null;
    }

}

