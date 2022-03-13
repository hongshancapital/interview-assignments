package springboot.bench.service;

public interface DomainServiceIntf {

    /**
     * 输入长链，保存并返回生成的短链; 
     * 长链已经存在短链，抛出异常: ERR_LONG_DOMAIN_EXISTS; 
     * 本地长链<->短链键值队存储到最大数量时，抛出异常: ERR_DOMAIN_POOL_FULL
     * 
     * @param longDomain
     * @return
     * @throws DomainPoolException
     */
    String saveLongDomain(String longDomain) throws Exception;
    
    /**
     * 输入短链，输出长链; 不存在时，返回空
     * @param shortDomain 短链
     * @return
     */
    String getOriginalDomain(String shortDomain);
    
}
