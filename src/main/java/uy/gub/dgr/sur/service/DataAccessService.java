package uy.gub.dgr.sur.service;

/**
 * User: rmartony
 * Date: 18/12/13
 * Time: 11:24 AM
 */

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the generic Data Access Service
 * All CRUD (create, read, update, delete) basic data access operations for any
 * persistent object are performed in this class.
 *
 * @author rmartony
 */

public abstract class DataAccessService<T> implements Serializable {

    @PersistenceContext
    private EntityManager em;
    private Class<T> type;

    public DataAccessService() {
    }

    /**
     * Default constructor
     *
     * @param type entity class
     */
    public DataAccessService(Class<T> type) {
        this.type = type;
    }

    /**
     * Stores an instance of the entity class in the database
     *
     * @param T Object
     * @return
     */
    public T create(T t) {
        this.em.persist(t);
        this.em.flush();
        this.em.refresh(t);
        return t;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * Retrieves an entity instance that was previously persisted to the database
     *
     * @param T  Object
     * @param id
     * @return
     */
    public T find(Object id) {
        return this.em.find(this.type, id);
    }

    /**
     * Removes the record that is associated with the entity instance
     *
     * @param type
     * @param id
     */
    public void delete(Object id) {
        Object ref = this.em.getReference(this.type, id);
        this.em.remove(ref);
    }

    /**
     * Removes the number of entries from a table
     *
     * @param <T>
     * @param items
     * @return
     */
    public boolean deleteItems(T[] items) {
        for (T item : items) {
            em.remove(em.merge(item));
        }
        return true;
    }

    /**
     * Updates the entity instance
     *
     * @param <T>
     * @param t
     * @return the object that is updated
     */
    public T update(T item) {
        return (T) this.em.merge(item);

    }

    public void flush() {
        em.flush();
    }

    public Object findSingleResult(String sqlQuery, Object[] params) {
        Query query = em.createQuery(sqlQuery);
        setParameters(query, params);
        return query.getSingleResult();
    }

    public Object findSingleResultNamedQuery(String namedQueryName, Map params) {
        Query query = em.createNamedQuery(namedQueryName);
        setParameters(query, params);

        Object result = null;
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return result;
    }

    private void setParameters(Query query, Object[] params) {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
        }
    }

    private void setParameters(Query query, Map params) {
        if (params != null && !params.isEmpty()) {
            Set<Map.Entry<String, Object>> rawParameters = params.entrySet();
            for (Map.Entry<String, Object> entry : rawParameters) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Returns the number of records that meet the criteria
     *
     * @param namedQueryName
     * @return List
     */
    public List findWithNamedQuery(String namedQueryName) {
        return this.em.createNamedQuery(namedQueryName).getResultList();
    }

    /**
     * Returns the number of records that meet the criteria
     *
     * @param namedQueryName
     * @param parameters
     * @return List
     */
    public List findWithNamedQuery(String namedQueryName, Map parameters) {
        return findWithNamedQuery(namedQueryName, parameters, 0);
    }

    /**
     * Returns the number of records with result limit
     *
     * @param queryName
     * @param resultLimit
     * @return List
     */
    public List findWithNamedQuery(String queryName, int resultLimit) {
        return this.em.createNamedQuery(queryName).
                setMaxResults(resultLimit).
                getResultList();
    }

    /**
     * Returns the number of records that meet the criteria
     *
     * @param <T>
     * @param sql
     * @param type
     * @return List
     */
    public List<T> findByNativeQuery(String sql) {
        return this.em.createNativeQuery(sql, type).getResultList();
    }

    /**
     * Returns the number of total records
     *
     * @param namedQueryName
     * @return int
     */
    public int countTotalRecord(String namedQueryName) {
        Query query = em.createNamedQuery(namedQueryName);
        Number result = (Number) query.getSingleResult();
        return result.intValue();
    }

    /**
     * Returns the number of records that meet the criteria with parameter map and
     * result limit
     *
     * @param namedQueryName
     * @param parameters
     * @param resultLimit
     * @return List
     */
    public List findWithNamedQuery(String namedQueryName, Map parameters, int resultLimit) {
        Query query = this.em.createNamedQuery(namedQueryName);
        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }
        setParameters(query, parameters);
        return query.getResultList();
    }

    /**
     * Returns the number of records that will be used with lazy loading / pagination
     *
     * @param namedQueryName
     * @param start
     * @param end
     * @return List
     */
    public List findWithNamedQuery(String namedQueryName, int start, int end) {
        Query query = this.em.createNamedQuery(namedQueryName);
        query.setMaxResults(end - start);
        query.setFirstResult(start);
        return query.getResultList();
    }

    public List<T> findWithTypedQuery(CriteriaQuery<T> criteriaQuery, int first, int pageSize) {
        TypedQuery<T> typedQuery = em.createQuery(criteriaQuery).setFirstResult(first).setMaxResults(pageSize);
        return typedQuery.getResultList();
    }

    public int count(CriteriaQuery cq) {
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }


    public CriteriaBuilder getCriteriaBuilder() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        return criteriaBuilder;
    }

    public Metamodel getMetamodel() {
        return em.getMetamodel();
    }

    public void sessionClear() {
        em.clear();
    }

}