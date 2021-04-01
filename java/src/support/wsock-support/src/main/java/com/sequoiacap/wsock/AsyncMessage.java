package com.sequoiacap.wsock;

public class AsyncMessage<T> {
	private String type;
	private T body;

	public AsyncMessage(String type, T body)
	{
		this.type = type;
		this.body = body;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}
}
