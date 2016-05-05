package uy.gub.dgr.sur.audit;

/**
 * User: rafa
 * Date: 04/03/14
 * Time: 04:20 PM
 */

import org.apache.deltaspike.core.api.provider.BeanManagerProvider;
import org.hibernate.envers.RevisionListener;
import uy.gub.dgr.sur.controller.LoginController;
import uy.gub.dgr.sur.entity.Auditoria;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

public class AppRevisionListener implements RevisionListener {
    public void newRevision(Object revisionEntity) {
        Auditoria revEntity = (Auditoria) revisionEntity;

        LoginController loginController = getLoginControllerInstance();
        try {
            revEntity.setUserName(loginController.getLoginName());
        } catch (Exception e) {

        }
    }

    public LoginController getLoginControllerInstance() {
        BeanManager bm = BeanManagerProvider.getInstance().getBeanManager();
        Bean<?> bean = bm.resolve(bm.getBeans(LoginController.class));
        CreationalContext cc = bm.createCreationalContext(bean);
        return (LoginController) bm.getReference(bean, LoginController.class, cc);
    }
}