package uy.gub.dgr.sur.model;


import org.apache.commons.lang3.StringUtils;
import org.picketlink.idm.model.AttributedType;
import org.picketlink.idm.model.basic.User;
import org.picketlink.idm.query.AttributeParameter;
import org.picketlink.idm.query.IdentityQuery;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import uy.gub.dgr.sur.service.UsuarioService;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Custom Lazy Sitio DataModel which extends PrimeFaces LazyDataModel.
 * For more information please visit http://www.primefaces.org/showcase-labs/ui/datatableLazy.jsf
 */

public class LazyUsuarioDataModel extends LazyDataModel<User> implements Serializable {

    @Inject
    private Logger log;
    // Data Source for binding data to the DataTable
    private List<User> datasource;
    // Selected Page size in the DataTable
    private int pageSize;
    // Current row index number
    private int rowIndex;
    // Total row number
    private int rowCount;

    private int first;
    // Data Access Service for create read update delete operations
    private UsuarioService crudService;

    public LazyUsuarioDataModel() {
    }

    /**
     * @param crudService
     */
    public LazyUsuarioDataModel(UsuarioService crudService) {
        this.crudService = crudService;
    }

    /**
     * Lazy loading Sitio list with sorting ability
     *
     * @param first
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @param filters
     * @return List<Sitio>
     */
    @Override
    public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        IdentityQuery<User> query = crudService.createIdentityQuery();
        if (StringUtils.isNotEmpty(sortField)) {
            query.setSortParameters(AttributedType.QUERY_ATTRIBUTE.byName(sortField));
            query.setSortAscending(sortOrder.equals(SortOrder.ASCENDING));
        } else {
            query.setSortParameters(AttributedType.QUERY_ATTRIBUTE.byName("loginName"));
            query.setSortAscending(sortOrder.equals(SortOrder.ASCENDING));
        }

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            String[] filterField = filter.getKey().split("\\.", 2);
            for (String s : filterField) {
                query.setParameter(new AttributeParameter(s), filter.getValue());
            }
        }
        query.setLimit(pageSize);
        query.setOffset(first);
        datasource = crudService.findUser(query);

        setRowCount(crudService.count(query));
        this.first = first;

        return datasource;
    }

    /**
     * Checks if the row is available
     *
     * @return boolean
     */
    @Override
    public boolean isRowAvailable() {
        if (datasource == null)
            return false;
        int index = rowIndex % pageSize;
        return index >= 0 && index < datasource.size();
    }

    /**
     * Gets the user object's primary key
     *
     * @param user
     * @return Object
     */
    @Override
    public Object getRowKey(User user) {
        return user.getId().toString();
    }

    /**
     * Returns the sitio object at the specified position in datasource.
     *
     * @return
     */
    @Override
    public User getRowData() {
        if (datasource == null)
            return null;
        int index = rowIndex % pageSize;
        if (index > datasource.size()) {
            return null;
        }
        return datasource.get(index);
    }

    /**
     * Returns the sitio object that has the row key.
     *
     * @param rowKey
     * @return
     */
    @Override
    public User getRowData(String rowKey) {
        if (datasource == null)
            return null;
        for (User User : datasource) {
            if (User.getId().equals(rowKey))
                return User;
        }
        return null;
    }
    
    
    /*
     * ===== Getters and Setters of LazySitioDataModel fields
     */

    /**
     * Returns page size
     *
     * @return int
     */
    @Override
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     */
    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Returns current row index
     *
     * @return int
     */
    @Override
    public int getRowIndex() {
        return this.rowIndex;
    }

    /**
     * Sets row index
     *
     * @param rowIndex
     */
    @Override
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    /**
     * Returns row count
     *
     * @return int
     */
    @Override
    public int getRowCount() {
        return this.rowCount;
    }

    /**
     * Sets row count
     *
     * @param rowCount
     */
    @Override
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    /**
     * Returns wrapped data
     *
     * @return
     */
    @Override
    public Object getWrappedData() {
        return datasource;
    }

    /**
     * Sets wrapped data
     *
     * @param list
     */
    @Override
    public void setWrappedData(Object list) {
        this.datasource = (List<User>) list;
    }
}
                    
