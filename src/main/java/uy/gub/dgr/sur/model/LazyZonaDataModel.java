package uy.gub.dgr.sur.model;


import org.apache.commons.collections4.CollectionUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import uy.gub.dgr.sur.entity.Zona;
import uy.gub.dgr.sur.service.DataAccessService;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Custom Lazy Zona DataModel which extends PrimeFaces LazyDataModel.
 * For more information please visit http://www.primefaces.org/showcase-labs/ui/datatableLazy.jsf
 */

public class LazyZonaDataModel extends LazyDataModel<Zona> implements Serializable {

    // Data Source for binding data to the DataTable
    private List<Zona> datasource;
    // Selected Page size in the DataTable
    private int pageSize;
    // Current row index number
    private int rowIndex;
    // Total row number
    private int rowCount;
    // Data Access Service for create read update delete operations
    private DataAccessService crudService;

    /**
     * @param crudService
     */
    public LazyZonaDataModel(DataAccessService crudService) {
        this.crudService = crudService;
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
    public List<Zona> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        CriteriaBuilder criteriaBuilder = crudService.getCriteriaBuilder();
        CriteriaQuery<Zona> criteriaQuery = criteriaBuilder.createQuery(Zona.class);
        Metamodel metamodel = crudService.getMetamodel();
        EntityType<Zona> entityType = metamodel.entity(Zona.class);
        Root<Zona> from = criteriaQuery.from(entityType);
        List<Order> orders = new ArrayList<Order>();

        // count
        CriteriaQuery cqCount = criteriaBuilder.createQuery();

        //Sorting
        if (sortField != null) {
            Path path = from.get(sortField);
            orders.add(sortOrder == SortOrder.ASCENDING
                    ? criteriaBuilder.asc(path)
                    : criteriaBuilder.desc(path));
        }

        List<Predicate> predicates = new ArrayList<Predicate>();

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            String[] filterField = filter.getKey().split("\\.", 2);
            Path path = from.get(filterField[0]);
            predicates.add(criteriaBuilder.like(path, "%" + filter.getValue() + "%"));

/*
            predicates.add(filter.getValue().matches("[0-9]+")
                    ? criteriaBuilder.equal(path, Long.valueOf(filter.getValue()))
                    : criteriaBuilder.like(path, "%" + filter.getValue() + "%"));
*/
        }

        if (CollectionUtils.isNotEmpty(predicates)) {
            final Predicate[] predicates1 = predicates.toArray(new Predicate[0]);
            criteriaQuery.where(predicates1);
            cqCount.where(predicates1);
        }

        if (CollectionUtils.isNotEmpty(orders)) {
            criteriaQuery.orderBy(orders);
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get("nombre")));
        }

        datasource = crudService.findWithTypedQuery(criteriaQuery, first, pageSize);


        cqCount.from(entityType);
        cqCount.select(criteriaBuilder.count(from));

        setRowCount(crudService.count(cqCount));

/*
        datasource = crudService.findWithNamedQuery(Zona.ALL, first, first + pageSize);
        // if sort field is not null then we sort the field according to sortfield and sortOrder parameter
        if (sortField != null) {
            Collections.sort(datasource, new LazySorter(sortField, sortOrder));
        }
        setRowCount(crudService.countTotalRecord(Zona.TOTAL));
        */
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
     * Gets the Zona object's primary key
     *
     * @param Zona
     * @return Object
     */
    @Override
    public Object getRowKey(Zona zona) {
        return zona.getId().toString();
    }

    /**
     * Returns the Zona object at the specified position in datasource.
     *
     * @return
     */
    @Override
    public Zona getRowData() {
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
    public Zona getRowData(String rowKey) {
        if (datasource == null)
            return null;
        for (Zona zona : datasource) {
            if (zona.getId().toString().equals(rowKey))
                return zona;
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
        this.datasource = (List<Zona>) list;
    }
}
                    
