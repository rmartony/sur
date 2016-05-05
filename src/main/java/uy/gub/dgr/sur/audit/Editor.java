package uy.gub.dgr.sur.audit;

/**
 * User: rmartony
 * Date: 16/01/14
 * Time: 06:51 PM
 */


import org.picketlink.Identity;
import org.picketlink.idm.model.basic.User;
import uy.gub.dgr.sur.entity.BaseEntity;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class is typically been called in a JSF backing bean before reading
 * a database entity which is being edited. This information is stored
 * in a ThreadLocal and will be accessed from inside the entities to
 * create historisation data.
 *
 * @author <a href="mailto:struberg@yahoo.de">Mark Struberg</a>
 */
@Named
@SessionScoped
public class Editor implements Serializable {
    private EditInfo edit;

    @Inject
    private Identity identity;

    /**
     * @param he
     * @return the entity key which consists of the ClassName + the id
     */
    private static Object getEntityKey(BaseEntity he) {
        if (he.getId() != null) {
            return he.getClass().getName() + "/" + he.getId();
        }
        return he;
    }

    /**
     * By calling this method, the given entities will be under
     * suspicion of being edited if (and only if) the user has
     * proper permission to change the entity
     * <p>
     * This method should only be called if the current user has
     * sufficient rights to change the given entities at all.
     *
     * @param user           the User who performs the editing
     * @param editedEntities the entities whose changes should get tracked
     */
    public void markEditable(User user, Class<? extends BaseEntity>... editedEntities) {
        if (edit == null) {
            edit = new EditInfo();

            if (user != null) {
                edit.user = user;
            } else {
                if (identity != null && identity.getAccount() != null) edit.user = (User) identity.getAccount();
            }
            /* else {
                // use Apache MyFaces CODI BeanManagerProvider to get CDI beans in unmanaged classes
                edit.user = BeanManagerProvider.getInstance(UserSettings.class);
            }*/

            edit.edited = new HashSet<Class<? extends BaseEntity>>();
            edit.unmarked = new HashSet<Class<? extends BaseEntity>>();
            edit.oldData = new HashMap<Object, Map<String, Object>>();
        }

        for (Class<? extends BaseEntity> historisedEntity : editedEntities) {
            edit.edited.add(historisedEntity);
        }
    }

    public Set<Class<? extends BaseEntity>> getEdited() {
        if (edit != null) {
            return edit.edited;
        }

        return null;
    }

    /**
     * Call this method to disable the collection of the oldValues for the current thread.
     * Any previously edited entities will still contain the oldValues and will therefore
     * get tracked.
     *
     * @param uneditedEntities
     */
    public void unmarkEditable(Class<? extends BaseEntity>... uneditedEntities) {
        if (edit != null) {
            for (Class<? extends BaseEntity> unmarkedEntity : uneditedEntities) {
                edit.unmarked.add(unmarkedEntity);
            }
        }
    }


    /**
     * This has to be called at the end of each request to ensure
     * that the ThreadLocal editor information get's cleared properly.
     */
/*
    public static void cleanupThread() {
        EditInfo edit = edits.get();
        if (edit != null) {
            edits.set(null);
            edits.remove();
        }
    }
*/

    /**
     * Determine whether an entity is unmarked again.
     * very useful after storing and before forwarding.
     */
    public boolean isUnmarked(BaseEntity he) {
        if (edit == null) {
            return false;
        }

        if (edit.unmarked != null && !edit.unmarked.isEmpty()) {
            return edit.unmarked.contains(he.getClass());
        }

        return false;
    }

    /**
     * @param he the entity which should get checked if it's currently being edited
     * @return <code>true</code> if the given entity is currently being edited
     * and thus should get editor.
     */
    public boolean isEdited(BaseEntity he) {
        if (edit == null) {
            return false;
        }

        if (edit.edited != null && !edit.edited.isEmpty()) {
            return edit.edited.contains(he.getClass());
        }

        return false;
    }

    public void setOldData(BaseEntity he, Map<String, Object> oldData) {
        if (edit == null) {
            throw new RuntimeException("Entity not in edit mode! " + he.getClass().getName() + " id=" + he.getId());
        }

        Object key = getEntityKey(he);

        edit.oldData.put(key, oldData);
    }

    public Map<String, Object> getOldData(BaseEntity he) {
        if (edit == null) {
            throw new RuntimeException("Entity not in edit mode! " + he.getClass().getName() + " id=" + he.getId());
        }

        Object key = getEntityKey(he);

        Map<String, Object> oldData = edit.oldData.get(key);

        if (oldData == null) {
            // fallback to object
            oldData = edit.oldData.get(he);
        }

        return oldData;
    }

    public User getUser() {
        return edit.user;
    }

    /**
     * Maintain a constant transaction timestamp which will be
     * used for all changes in a transaction.
     */
/*
    public Date getTransactionTst() {
        if (edit == null) {
            return null;
        }

        Date tst = edit.transactionTst;
        if (tst == null) {
            tst = new Date();
            edit.transactionTst = tst;
        }

        return tst;
    }
*/


    /**
     * internal data container.
     */
    private class EditInfo {
        public User user;
        //public Locale locale;
        public Set<Class<? extends BaseEntity>> edited;
        public Set<Class<? extends BaseEntity>> unmarked;
        public Map<Object, Map<String, Object>> oldData;
        //public Date transactionTst;
    }

}
