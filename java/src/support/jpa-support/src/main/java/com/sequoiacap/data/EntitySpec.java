package com.sequoiacap.data;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;

public class EntitySpec<T>
	implements Specification<T>
{
	protected T entity, orEntity;

	protected CriteriaQuery<?> query;
	
	public EntitySpec(T entity)
	{
		this.entity = entity;
	}
	
	public EntitySpec(T entity, T orEntity)
	{
		this.entity = entity;
		this.orEntity = orEntity;
	}

	public Predicate toPredicate(
		Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb)
	{
		List<Predicate> predicates = new ArrayList<Predicate>();
	
		this.query = query;
		
		if (entity != null)
		{
			predicates = addPredicate(predicates, root, entity, cb);
		}

		if (orEntity != null)
		{
			List<Predicate> orPredicates = 
				addPredicate(new ArrayList<Predicate>(), root, orEntity, cb);

			if (orPredicates.size() != 0)
				predicates.add(orTogether(orPredicates, cb));
		}

		return andTogether(predicates, cb);
	}

	protected List<Predicate> addPredicate(
		List<Predicate> predicates, Path<?> root, Object obj, CriteriaBuilder cb)
	{
		PropertyDescriptor[] fields =
			BeanUtils.getPropertyDescriptors(obj.getClass());
		for(PropertyDescriptor field: fields)
		{
			try
			{
				Method method = field.getReadMethod();

				String name = field.getName();
				Class<?> clazz = field.getPropertyType();

				Object value = method.invoke(obj);
				if (value != null && !Class.class.isAssignableFrom(clazz))
				{
					Path<?> path = root.get(name);

					if (String.class.isAssignableFrom(clazz))
					{
						if (!StringUtils.isBlank(value.toString()))
						{
							predicates.add(
								cb.like((Path<String>) path, value.toString()));
						}
					} else
					if (clazz.getAnnotation(Entity.class) != null)
					{
						addPredicate(predicates, root.get(name), value, cb);
					} else
					{
						predicates.add(cb.equal(path, value));
					}
				}
			} catch(Exception e)
			{
				//e.printStackTrace();
			}
		}
		
		return predicates;
	}

	protected Predicate
		andTogether(List<Predicate> predicates, CriteriaBuilder cb)
	{
		return cb.and(predicates.toArray(new Predicate[0]));
	}
	
	protected Predicate
		orTogether(List<Predicate> predicates, CriteriaBuilder cb)
	{
		return cb.or(predicates.toArray(new Predicate[0]));
	}
}
