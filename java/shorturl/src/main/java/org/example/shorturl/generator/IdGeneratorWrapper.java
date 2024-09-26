package org.example.shorturl.generator;

import org.example.shorturl.generator.impl.MyIdGenerator;
import org.example.shorturl.properties.IdGeneratorProperty;

import javax.annotation.PostConstruct;

/**
 * id生成器包装器
 *
 * @author bai
 * @date 2021/6/19 1:27
 */
public class IdGeneratorWrapper {
    /**
     * Id生成器接口对象。
     */
    private IdGenerator idGenerator;
    
    private final IdGeneratorProperty property;
    
    
    public IdGeneratorWrapper(IdGeneratorProperty idGeneratorProperties) {
        this.property = idGeneratorProperties;
    }
    
    @PostConstruct
    public void init() {
        idGenerator = new MyIdGenerator(property.getSnowflakeWorkNode());
    }
    
    /**
     * 获取基于Snowflake算法的数值型Id。
     * 由于底层实现为synchronized方法,因此计算过程串行化,且线程安全。
     *
     * @return 计算后的全局唯一Id。
     */
    public long nextLongId() {
        return idGenerator.nextLongId();
    }
}
