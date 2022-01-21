package indi.zixiu.shortdomainname.service;

import indi.zixiu.shortdomainname.dto.DomainNameDto;

/**
 * {@link DomainNameService#addDomainName(String)} 方法的返回值类型
 */
public class AddDomainNameResult extends Result<DomainNameDto> {
    public static final int CODE_INVALID_DOMAIN_NAME = 1;  // 非法域名
    public static final int CODE_EXCEED_DOMAIN_NAME_REPOSITORY_CAPACITY = 2;  // 超出域名数据库能容纳的域名数量

    public AddDomainNameResult(int code, String message, DomainNameDto data) {
        super(code, message, data);
    }
}
