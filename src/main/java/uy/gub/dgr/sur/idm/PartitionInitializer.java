package uy.gub.dgr.sur.idm;

import org.picketlink.event.PartitionManagerCreateEvent;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.model.basic.Realm;

import javax.enterprise.event.Observes;

/**
 * Created by rafa on 8/9/2016.
 */
public class PartitionInitializer {

    public void initPartition(@Observes PartitionManagerCreateEvent event) throws Exception {
        PartitionManager partitionManager = event.getPartitionManager();

        if (partitionManager.getPartition(Realm.class, Realm.DEFAULT_REALM) == null) {
            partitionManager.add(new Realm(Realm.DEFAULT_REALM));
        }
    }
}