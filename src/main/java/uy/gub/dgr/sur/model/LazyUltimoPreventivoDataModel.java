package uy.gub.dgr.sur.model;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import uy.gub.dgr.sur.entity.UltimoPreventivo;
import uy.gub.dgr.sur.entity.Zona;
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

public class LazyUltimoPreventivoDataModel extends LazyDataModel<UltimoPreventivo> implements Serializable {

    @Inject
    private Logger log;
    // Data Source for binding data to the DataTable
    private List<UltimoPreventivo> datasource;
    // Selected Page size in the DataTable
    private int pageSize;
    // Current row index number
    private int rowIndex;
    // Total row number
    private int rowCount;

    private int first;
    // Data Access Service for create read update delete operations
    private DataAccessService crudService;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private List<Zona> zonasTecnico;

    // filter by selected zona
    @Getter
    @Setter
    private Zona selectedZona;

    /**
     * @param crudService
     */
    public LazyUltimoPreventivoDataModel(DataAccessService crudService) {
        this.crudService = crudService;
    }

    public LazyUltimoPreventivoDataModel(DataAccessService crudService, String username, List<Zona> zonasTecnico) {
        this.crudService = crudService;
        this.username = username;
        this.zonasTecnico = zonasTecnico;
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
    public List<UltimoPreventivo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        CriteriaBuilder criteriaBuilder = crudService.getCriteriaBuilder();
        CriteriaQuery<UltimoPreventivo> criteriaQuery = criteriaBuilder.createQuery(UltimoPreventivo.class);
        Metamodel metamodel = crudService.getMetamodel();
        EntityType<UltimoPreventivo> entityType = metamodel.entity(UltimoPreventivo.class);
        Root<UltimoPreventivo> from = criteriaQuery.from(entityType);
        List<Order> orderList = new ArrayList<Order>();

        // count query
        CriteriaQuery<Long> cqCount = criteriaBuilder.createQuery(Long.class);
        Root<UltimoPreventivo> fromCount = cqCount.from(entityType);
        cqCount.select(criteriaBuilder.count(from));

        //Sorting
        String[] sortFields = null;
        if (sortField != null) {
            sortFields = sortField.split("\\.");
            // it is not a join
            if (sortFields.length < 2) {
                Path path = from.get(sortField);
                orderList.add(sortOrder == SortOrder.ASCENDING
                        ? criteriaBuilder.asc(path)
                        : criteriaBuilder.desc(path));
            } else {
                if ("nodo".equals(sortFields[0]) && sortFields.length == 2) {
                    Join join = from.join("nodo");
                    fromCount.join("nodo");
                    Path path = join.get(sortFields[1]);
                    orderList.add(sortOrder == SortOrder.ASCENDING
                            ? criteriaBuilder.asc(path)
                            : criteriaBuilder.desc(path));
                } else if ("preventivo".equals(sortFields[0])) {
                    Join join = from.join("preventivo");
                    fromCount.join("preventivo");
                    Path path = join.get(sortFields[1]);
                    orderList.add(sortOrder == SortOrder.ASCENDING
                            ? criteriaBuilder.asc(path)
                            : criteriaBuilder.desc(path));
                } else if ("nodo".equals(sortFields[0]) && sortField.length() > 2) {
                    Join join = from.join("nodo").join("sitio").join("zona");
                    fromCount.join("nodo").join("sitio").join("zona");
                    Path path = join.get(sortFields[3]);
                    orderList.add(sortOrder == SortOrder.ASCENDING
                            ? criteriaBuilder.asc(path)
                            : criteriaBuilder.desc(path));
                }

            }
        }

        List<Predicate> predicates = new ArrayList<Predicate>();

        if (username != null) {
            Join join = from.join("preventivo");
            Path path = join.get("tecnico");
            fromCount.join("preventivo");
            predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(path), username.toLowerCase()));
        }

        if (selectedZona != null) {
            Join join = from.join("nodo").join("sitio").join("zona");
            fromCount.join("nodo").join("sitio").join("zona");
            Path path = join.get("id");
            predicates.add(path.in(selectedZona.getId()));
        } else {
            if (CollectionUtils.isNotEmpty(zonasTecnico)) {
                Join join = from.join("nodo").join("sitio").join("zona");
                fromCount.join("nodo").join("sitio").join("zona");
                Path path = join.get("id");
                List<Integer> idZonaList = new ArrayList<>(zonasTecnico.size());
                for (Zona zona : zonasTecnico) {
                    idZonaList.add(zona.getId());
                }

                predicates.add(path.in(idZonaList));
            }
        }

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            String[] filterField = filter.getKey().split("\\.", 3);
            if (filterField.length < 2) {
                Path path = from.get(filterField[0]);
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(path), ((String) filter.getValue()).toLowerCase() + "%"));
            } else if (filterField.length == 2) {
                // this is a simple join
                final String entity = filterField[0];
                Join join = from.join(entity);
                Join joinCount = fromCount.join(entity);
                final String attribute = filterField[1];

                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(join.get(attribute)), ((String) filter.getValue()).toLowerCase() + "%"));
            } else {
                final String attribute = filterField[0] + filterField[1] + filterField[2];
                String[] attr = filterField[2].split("\\.", 2);
                if (attribute.equalsIgnoreCase("nodositiozona.nombre")) {
                    Join join = from.join("nodo").join("sitio").join("zona");
                    Join joinCount = fromCount.join("nodo").join("sitio").join("zona");
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(join.get(attr[1])), ((String) filter.getValue()).toLowerCase() + "%"));
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
        }/* else {
            criteriaQuery.orderBy(criteriaBuilder.desc(from.get("fecha")));
        }*/

        if (username != null && CollectionUtils.isEmpty(zonasTecnico)) {
            datasource = new ArrayList<>();
            setRowCount(0);
        } else {
            datasource = crudService.findWithTypedQuery(criteriaQuery, first, pageSize);
            setRowCount(crudService.count(cqCount));
        }
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
     * Gets the data object's primary key
     *
     * @param data
     * @return Object
     */
    @Override
    public Object getRowKey(UltimoPreventivo data) {
        return data.getId().toString();
    }

    /**
     * Returns the sitio object at the specified position in datasource.
     *
     * @return
     */
    @Override
    public UltimoPreventivo getRowData() {
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
    public UltimoPreventivo getRowData(String rowKey) {
        if (datasource == null)
            return null;
        for (UltimoPreventivo ultimoPreventivo : datasource) {
            if (ultimoPreventivo.getId().toString().equals(rowKey))
                return ultimoPreventivo;
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
        this.datasource = (List<UltimoPreventivo>) list;
    }
}
                    
