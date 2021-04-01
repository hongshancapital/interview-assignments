package com.sequoiacap.utils.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sequoiacap.data.EntitySpec;

public abstract class AbstractManagerImpl<T, PK extends Serializable>
    implements GenericManager<T, PK>, InitializingBean
{
    private CrudRepository<T, PK> repository;

    protected AbstractManagerImpl()
    { }

    public ArrayList<T> getAll()
    {
        ArrayList<T> bulletins = new ArrayList<T>();
        
        Iterable<T> it = repository.findAll();
        for(T bulletin: it)
            bulletins.add(bulletin);
        
        return bulletins;
    }

    public T get(PK id) 
    {
    	if (id == null)
    		return null;
    	
        return repository.findById(id).get();
    }

    public boolean exists(PK id)
    {
    	if (id == null)
    		return false;
    	
        return repository.existsById(id);
    }

    @Transactional
    public T save(T object)
    {
        repository.save(object);
        
        return object;
    }

    @Transactional
    public void remove(PK id)
    {
    	try {
    		if (id != null)    	
    			repository.deleteById(id);
    	} catch (Exception e) {
    		
    		e.printStackTrace();
    	}
    }

    public List<T> findByExample(T entity)
    {
    	if (repository instanceof JpaSpecificationExecutor)
    	{
    		JpaSpecificationExecutor<T> executor =
    			(JpaSpecificationExecutor<T>) repository;

    		return executor.findAll(new EntitySpec<T>(entity));
    	}

    	return new ArrayList<T>();
    }

    public Page<T> findByExample(T entity, Pageable page)
    {
    	if (repository instanceof JpaSpecificationExecutor)
    	{
    		JpaSpecificationExecutor<T> executor =
    			(JpaSpecificationExecutor<T>) repository;

    		return(Page<T>) executor.findAll(new EntitySpec<T>(entity), page);
    	}

    	return new PageImpl<T>(new ArrayList<T>());
    }
    
    public List<T> findByExample(T entity, T orEntity)
    {
    	if (repository instanceof JpaSpecificationExecutor)
    	{
    		JpaSpecificationExecutor<T> executor =
    			(JpaSpecificationExecutor<T>) repository;

    		return(List<T>) executor.findAll(
    			new EntitySpec<T>(entity, orEntity));
    	}

    	return new ArrayList<T>();
    }
    
    public Page<T> findByExample(T entity, T orEntity, Pageable page)
    {
    	if (repository instanceof JpaSpecificationExecutor)
    	{
    		JpaSpecificationExecutor<T> executor =
    			(JpaSpecificationExecutor<T>) repository;

    		return(Page<T>) executor.findAll(
    			new EntitySpec<T>(entity, orEntity), page);
    	}

    	return new PageImpl<T>(new ArrayList<T>());
    }
    
    public void setRepository(CrudRepository<T, PK> repository)
    {
        this.repository = repository;
    }
}
