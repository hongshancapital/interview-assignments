package cn.sequoiacap.shorturl.controller;

enum ResponseStatus {
    SUCCESS(2000, null),
    PARAM_ERROR(4000, "request param is invalid"),
    NOT_FOUND(4004, "not found"),
    INTERNAL_ERROR(5000, "server internal error");

    private final Integer code;
    private final String message;

    ResponseStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    Integer getCode() {
        return code;
    }

    String getMessage() {
        return message;
    }
}
