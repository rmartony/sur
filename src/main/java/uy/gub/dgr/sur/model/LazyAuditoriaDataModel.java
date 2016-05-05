package uy.gub.dgr.sur.model;


import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import uy.gub.dgr.sur.entity.BaseEntity;
import uy.gub.dgr.sur.service.AuditoriaService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Custom Lazy Zona DataModel which extends PrimeFaces LazyDataModel.
 * For more information please visit http://www.primefaces.org/showcase-labs/ui/datatableLazy.jsf
 */

public class LazyAuditoriaDataModel extends LazyDataModel<Object> implements Serializable {

    private Class clazz;
    // Data Source for binding data to the DataTable
    private List<Object> datasource;
    // Selected Page size in the DataTable
    private int pageSize;
    // Current row index number
    private int rowIndex;
    // Total row number
    private int rowCount;

    private int first;
    // Data Access Service for create read update delete operations
    private AuditoriaService crudService;

    /**
     * @param crudService
     */
    public LazyAuditoriaDataModel(AuditoriaService crudService, Class clazz) {
        this.crudService = crudService;
        this.clazz = clazz;
    }

    /**
     * Lazy loading Zona list with sorting ability
     *
     * @param first
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @param filters
     * @return List<Zona>
     */
    @Override
    public List<Object> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        AuditReader reader = AuditReaderFactory.get(crudService.getEntityManager());
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(clazz, true, true);
        datasource = query.setFirstResult(first).setMaxResults(pageSize).getResultList();

        //setRowCount();
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
     * Gets the Rnc object's primary key
     *
     * @param object
     * @return Object
     */
    @Override
    public Object getRowKey(Object object) {
        return ((BaseEntity) object).getId().toString();
    }

    /**
     * Returns the Zona object at the specified position in datasource.
     *
     * @return
     */
    @Override
    public Object getRowData() {
        if (datasource == null)
            return null;
        int index = rowIndex % pageSize;
        if (index > datasource.size()) {
            return null;
        }
        return datasource.get(index);
    }

    /**
     * Returns the Zona object that has the row key.
     *
     * @param rowKey
     * @return
     */
    @Override
    public Object getRowData(String rowKey) {
        if (datasource == null)
            return null;
        for (Object object : datasource) {
            if (((BaseEntity) object).getId().toString().equals(rowKey))
                return object;
        }
        return null;
    }
    
    
    /*
     * ===== Getters and Setters of LazyZonaDataModel fields
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
        this.datasource = (List<Object>) list;
    }
}
                    
