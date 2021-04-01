package com.sequoiacap.utils.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Generic Manager that talks to GenericDao to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) managers
 * for your domain objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public interface GenericManager<T, PK> {

    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
    public ArrayList<T> getAll();

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    public T get(PK id);

    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id the identifier (primary key) of the object to get
     * @return - true if it exists, false if it doesn't
     */
    public boolean exists(PK id);

    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return the updated object
     */
    public T save(T object);

    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
     */
    public void remove(PK id);

    public List<T> findByExample(T entity);
    public Page<T> findByExample(T entity, Pageable page);
    public List<T> findByExample(T entity, T orEntity);
    public Page<T> findByExample(T entity, T orEntity, Pageable page);
}
