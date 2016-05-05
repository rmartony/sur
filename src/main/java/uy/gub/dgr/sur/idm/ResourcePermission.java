package uy.gub.dgr.sur.idm;

/**
 * User: rmartony
 * Date: 16/12/13
 * Time: 08:51 PM
 */

import org.picketlink.idm.jpa.annotations.OwnerReference;
import org.picketlink.idm.jpa.annotations.PermissionOperation;
import org.picketlink.idm.jpa.annotations.PermissionResourceClass;
import org.picketlink.idm.jpa.annotations.PermissionResourceIdentifier;
import org.picketlink.idm.jpa.annotations.entity.PermissionManaged;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * This entity stores resource permissions, as configured by the @PermissionManaged annotation.  As
 * the annotation does not specify a value for its resourceClasses member, it may be used to store
 * permissions for resources of all types.
 *
 * @author Shane Bryzak
 */
@Entity
@PermissionManaged
public class ResourcePermission implements Serializable {

    private static final long serialVersionUID = -7409821749592191950L;

    @Id
    @GeneratedValue
    private Long id;

    @OwnerReference
    private String assignee;

    @PermissionResourceClass
    private String resourceClass;

    @PermissionResourceIdentifier
    private String resourceIdentifier;

    @PermissionOperation
    private String operation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getResourceClass() {
        return resourceClass;
    }

    public void setResourceClass(String resourceClass) {
        this.resourceClass = resourceClass;
    }

    public String getResourceIdentifier() {
        return resourceIdentifier;
    }

    public void setResourceIdentifier(String resourceIdentifier) {
        this.resourceIdentifier = resourceIdentifier;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
