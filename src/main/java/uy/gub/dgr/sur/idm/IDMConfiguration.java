package uy.gub.dgr.sur.idm;

/**
 * User: rmartony
 * Date: 16/12/13
 * Time: 07:12 PM
 */

import org.picketlink.IdentityConfigurationEvent;
import org.picketlink.idm.config.IdentityConfiguration;
import org.picketlink.idm.config.IdentityConfigurationBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

/**
 * This bean produces the configuration for PicketLink IDM
 *
 * @author Shane Bryzak
 */
@ApplicationScoped
public class IDMConfiguration {

/*
    @Inject
    private EEJPAContextInitializer contextInitializer;
*/

    private IdentityConfiguration identityConfig = null;

    @Produces
    IdentityConfiguration createConfig() {
        if (identityConfig == null) {
            initConfig(null);
        }
        return identityConfig;
    }

    public void configureIdentityManagement(@Observes IdentityConfigurationEvent event) {
        initConfig(event.getConfig());
    }

    /**
     * This method uses the IdentityConfigurationBuilder to create an IdentityConfiguration, which
     * defines how PicketLink stores identity-related data.  In this particular example, a
     * JPAIdentityStore is configured to allow the identity data to be stored in a relational database
     * using JPA.AttributedTypeEntity.class,
     */
    private void initConfig(IdentityConfigurationBuilder builder) {
        if (builder == null) {
            builder = new IdentityConfigurationBuilder();
        }

        builder
                .named("default")
                .stores()
                .jpa()
/*
                .mappedEntity(
                        AccountTypeEntity.class,
                        RoleTypeEntity.class,
                        GroupTypeEntity.class,
                        IdentityTypeEntity.class,
                        RelationshipTypeEntity.class,
                        RelationshipIdentityTypeEntity.class,
                        PartitionTypeEntity.class,
                        PasswordCredentialTypeEntity.class,
                        AttributeTypeEntity.class)

                .supportGlobalRelationship(Relationship.class)
                .addContextInitializer(this.contextInitializer)
*/
                        // Specify that this identity store configuration supports all features
                .supportAllFeatures();

        identityConfig = builder.build();
    }
}
