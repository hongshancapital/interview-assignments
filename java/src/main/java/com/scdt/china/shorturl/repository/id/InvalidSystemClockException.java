package com.scdt.china.shorturl.repository.id;

/**
 * 无效系统时钟异常：时钟回拨时发生<>
 * 解决时钟回拨问题需要持久化时钟，确保系统时钟回拨时，逻辑时钟不回拨
 */
class InvalidSystemClockException extends RuntimeException {

    public InvalidSystemClockException(String message) {
        super(message, null, false, false);
    }

}
