package com.sequoiacap.data;

public interface AsyncCallback<T>
{
	public void set(T value);
	public void throwe(Throwable e);
}
