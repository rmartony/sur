package uy.gub.dgr.sur.model;


import org.apache.commons.collections4.CollectionUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import uy.gub.dgr.sur.entity.TipoBTS;
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

public class LazyTipoBTSDataModel extends LazyDataModel<TipoBTS> implements Serializable {

    // Data Source for binding data to the DataTable
    private List<TipoBTS> datasource;
    // Selected Page size in the DataTable
    private int pageSize;
    // Current row index number
    private int rowIndex;
    // Total row number
    private int rowCount;

    private int first;
    // Data Access Service for create read update delete operations
    private DataAccessService crudService;

    /**
     * @param crudService
     */
    public LazyTipoBTSDataModel(DataAccessService crudService) {
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
    public List<TipoBTS> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        CriteriaBuilder criteriaBuilder = crudService.getCriteriaBuilder();
        CriteriaQuery<TipoBTS> criteriaQuery = criteriaBuilder.createQuery(TipoBTS.class);
        Metamodel metamodel = crudService.getMetamodel();
        EntityType<TipoBTS> entityType = metamodel.entity(TipoBTS.class);
        Root<TipoBTS> from = criteriaQuery.from(entityType);
        List<Order> orders = new ArrayList<Order>();

        // count query
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        Root<TipoBTS> fromCount = cqCount.from(entityType);
        cqCount.select(criteriaBuilder.count(from));

        //Sorting
        String[] sortFields = null;
        if (sortField != null) {
            sortFields = sortField.split("\\.", 2);
            // it is not a join
            if (sortFields.length < 2) {
                Path path = from.get(sortField);
                orders.add(sortOrder == SortOrder.ASCENDING
                        ? criteriaBuilder.asc(path)
                        : criteriaBuilder.desc(path));
            }
        }

        List<Predicate> predicates = new ArrayList<Predicate>();

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            String[] filterField = filter.getKey().split("\\.", 2);
            if (filterField.length < 2) {
                Path path = from.get(filterField[0]);
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(path), ((String) filter.getValue()).toLowerCase() + "%"));
            } else {
                // this is a join
                final String entity = filterField[0];
                Join join = from.join(entity);
                Join joinCount = fromCount.join(entity);
                final String attribute = filterField[1];

                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(join.get(attribute)), ((String) filter.getValue()).toLowerCase() + "%"));
                if (sortFields != null && sortFields.length >= 2) {
                    if (sortFields[0].equalsIgnoreCase(entity)) {
                        orders.add(sortOrder == SortOrder.ASCENDING
                                ? criteriaBuilder.asc(join.get(attribute))
                                : criteriaBuilder.desc(join.get(attribute)));
                    }
                }
            }

        }

        if (CollectionUtils.isNotEmpty(predicates)) {
            final Predicate[] predicates1 = predicates.toArray(new Predicate[0]);
            criteriaQuery.where(predicates1);
            cqCount.where(predicates1);
        }

        if (CollectionUtils.isNotEmpty(orders)) {
            criteriaQuery.orderBy(orders);
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get("modelo")));
        }

        datasource = crudService.findWithTypedQuery(criteriaQuery, first, pageSize);

        setRowCount(crudService.count(cqCount));
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
     * Gets the Registro object's primary key
     *
     * @param tipoBTS
     * @return Object
     */
    @Override
    public Object getRowKey(TipoBTS tipoBTS) {
        return tipoBTS.getId().toString();
    }

    /**
     * Returns the Zona object at the specified position in datasource.
     *
     * @return
     */
    @Override
    public TipoBTS getRowData() {
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
    public TipoBTS getRowData(String rowKey) {
        if (datasource == null)
            return null;
        for (TipoBTS tipoBTS : datasource) {
            if (tipoBTS.getId().toString().equals(rowKey))
                return tipoBTS;
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
        this.datasource = (List<TipoBTS>) list;
    }
}
                    
