package indi.zixiu.shortdomainname.repository;

/**
 * 网域名数据库插入域名记录时发生键重复
 */
public class DuplicateKeyException extends RuntimeException {
    public DuplicateKeyException(String message) {
        super(message);
    }
}
