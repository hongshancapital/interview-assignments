package com.jinchunhai.shorturl.common;

public class Response {

	public static final String SUCCESS = "200";

	public static final String NOTFOUND = "404";

	public static final String NOTLOGINED = "401";

	public static final String NOTAUTHORISED = "403";

	public static final String ERROR = "500";

	private String status = SUCCESS;

	private Object body = "";

	private String message = "";

	public static Response newInstance() {
		Response response = new Response();
		return response;
	}

	public Response() {
		super();
	}

	public Response(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public Response(String status, Object body) {
		super();
		this.status = status;
		this.body = body;
	}

	public String getStatus() {
		return status;
	}

	public Response setStatus(String status) {
		this.status = status;
		return this;
	}

	public Object getBody() {
		return body;
	}

	public Response setBody(Object body) {
		this.body = body == null ? "" : body;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Response setMessage(String message) {
		this.message = message;
		return this;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", body=" + body + ", message=" + message + "]";
	}
	
}
