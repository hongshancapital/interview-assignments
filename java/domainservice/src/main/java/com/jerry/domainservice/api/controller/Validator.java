package com.jerry.domainservice.api.controller;

public interface Validator<T> {
	public void validate(T body);
}
