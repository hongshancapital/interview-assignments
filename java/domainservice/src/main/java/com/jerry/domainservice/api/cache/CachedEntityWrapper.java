package com.jerry.domainservice.api.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class CachedEntityWrapper<V> {
	@Setter @Getter
	private V entity;
	@Setter @Getter
	private Long lastUpdateTime;
	
	
	@Override
	public int hashCode() {
		return entity.hashCode();
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CachedEntityWrapper) {
			return entity.equals(((CachedEntityWrapper)obj).getEntity());
		}
		return false;
	}
}
