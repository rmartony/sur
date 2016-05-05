package uy.gub.dgr.sur.model;


import org.apache.commons.collections4.CollectionUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import uy.gub.dgr.sur.entity.NodoLte;
import uy.gub.dgr.sur.service.DataAccessService;

import javax.inject.Inject;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Custom Lazy Sitio DataModel which extends PrimeFaces LazyDataModel.
 * For more information please visit http://www.primefaces.org/showcase-labs/ui/datatableLazy.jsf
 */

public class LazyNodoLteDataModel extends LazyDataModel<NodoLte> implements Serializable {

    @Inject
    private Logger log;
    // Data Source for binding data to the DataTable
    private List<NodoLte> datasource;
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
    public LazyNodoLteDataModel(DataAccessService crudService) {
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
    public List<NodoLte> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        CriteriaBuilder criteriaBuilder = crudService.getCriteriaBuilder();
        CriteriaQuery<NodoLte> criteriaQuery = criteriaBuilder.createQuery(NodoLte.class);
        Metamodel metamodel = crudService.getMetamodel();
        EntityType<NodoLte> entityType = metamodel.entity(NodoLte.class);
        Root<NodoLte> from = criteriaQuery.from(entityType);
        List<Order> orderList = new ArrayList<Order>();

        // count query
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        Root<NodoLte> fromCount = cqCount.from(entityType);
        cqCount.select(criteriaBuilder.count(from));

        //Sorting
        String[] sortFields = null;
        if (sortField != null) {
            sortFields = sortField.split("\\.", 2);
            // it is not a join
            if (sortFields.length < 2) {
                Path path = from.get(sortField);
                orderList.add(sortOrder == SortOrder.ASCENDING
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
                        orderList.add(sortOrder == SortOrder.ASCENDING
                                ? criteriaBuilder.asc(join.get(attribute))
                                : criteriaBuilder.desc(join.get(attribute)));
                    }
                }
            }

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

        if (CollectionUtils.isNotEmpty(orderList)) {
            criteriaQuery.orderBy(orderList);
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get("sigla")));
        }

        datasource = crudService.findWithTypedQuery(criteriaQuery, first, pageSize);


        setRowCount(crudService.count(cqCount));
        this.first = first;

/*
        datasource = crudService.findWithNamedQuery(Sitio.ALL, first, first + pageSize);
        // if sort field is not null then we sort the field according to sortfield and sortOrder parameter
        if (sortField != null) {
            Collections.sort(datasource, new LazySorter(sortField, sortOrder));
        }
        setRowCount(crudService.countTotalRecord(Sitio.TOTAL));
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
     * Gets the sitio object's primary key
     *
     * @param sitio
     * @return Object
     */
    @Override
    public Object getRowKey(NodoLte sitio) {
        return sitio.getId().toString();
    }

    /**
     * Returns the sitio object at the specified position in datasource.
     *
     * @return
     */
    @Override
    public NodoLte getRowData() {
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
    public NodoLte getRowData(String rowKey) {
        if (datasource == null)
            return null;
        for (NodoLte nodoLte : datasource) {
            if (nodoLte.getId().toString().equals(rowKey))
                return nodoLte;
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
        this.datasource = (List<NodoLte>) list;
    }
}
                    
