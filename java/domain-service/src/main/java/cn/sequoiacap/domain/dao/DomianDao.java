package cn.sequoiacap.domain.dao;

import cn.sequoiacap.domain.entity.DomainEntity;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 短域名和长域名对应关系存储
 * <br>
 * 这里目前没有考虑使用数据库，可以根据修改修改本类中的实现即可
 *
 * @author liuhao
 * @date 2021/12/10
 */
@Repository
public class DomianDao {

    /**
     * 这个是存在内存中，实际情况可能是redis或是数据库，由于这个的demo，在极端情况下会有内存溢出问题，
     * 刚开始有考虑过内存溢出的问题，在DomainEntity中还加入过过期字段，本来想写的，启动一个线程，或是用spring中的定时任务进行处理
     * 在规定的时间内将过期的进行清除。由于这种情况在实际生产环境中不存在，因此就没有写了
     */
    private Map<String, DomainEntity> domainStore = new ConcurrentHashMap<>();


    /**
     * 根据短域名返回对应的域名实体信息
     * @param uuid 短域名信息唯一标识
     * @return
     */
    public DomainEntity getDomainEntityByUuid(String uuid){
        return domainStore.get(uuid);
    }


    /**
     * 保存一个domainentity
     *
     * @param domainEntity
     */
    public void saveDomainEntity(DomainEntity domainEntity) {
        domainStore.put(domainEntity.getUuid(), domainEntity);
    }
}
