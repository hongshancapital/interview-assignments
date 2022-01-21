package indi.zixiu.shortdomainname.service;

import indi.zixiu.shortdomainname.domain.DomainName;
import indi.zixiu.shortdomainname.dto.DomainNameDto;
import indi.zixiu.shortdomainname.entity.DomainNameEntity;
import indi.zixiu.shortdomainname.repository.DomainNameRepository;
import indi.zixiu.shortdomainname.repository.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DomainNameService {
    @Autowired
    @Qualifier("domainNameRepositoryCapacity")
    private int domainNameRepositoryCapacity;  // 域名数据库能存储的最大域名数量

    @Autowired
    private DomainNameRepository domainNameRepository;

    @Autowired
    private ShortDomainNameGenerator shortDomainNameGenerator;

    public DomainNameService(int domainNameRepositoryCapacity, DomainNameRepository domainNameRepository, ShortDomainNameGenerator shortDomainNameGenerator) {
        this.domainNameRepositoryCapacity = domainNameRepositoryCapacity;
        this.domainNameRepository = domainNameRepository;
        this.shortDomainNameGenerator = shortDomainNameGenerator;
    }

    /**
     * 为一个长域名生成一个短域名，将这对长域名与短域名的绑定关系保存到数据库
     *
     * @param longName 长域名
     * @return AddDomainNameResult
     */
    public AddDomainNameResult addDomainName(String longName) {
        if (!DomainName.isValidDomainName(longName)) {
            return new AddDomainNameResult(AddDomainNameResult.CODE_INVALID_DOMAIN_NAME, "非法域名：" + longName, null);
        }

        domainNameRepository.lockForWrite();
        try {
            // 检查数据库中是否存在指定长域名
            DomainNameEntity domainNameEntity = domainNameRepository.findByLongName(longName);
            if (domainNameEntity != null) {
                DomainNameDto domainNameDto = convertDomainNameEntityToDto(domainNameEntity);
                return new AddDomainNameResult(Result.CODE_SUCCESS, Result.MESSAGE_SUCCESS, domainNameDto);
            }

            // 检查超出域名数据库能容纳的域名数量，避免内存溢出
            if (domainNameRepository.getDomainNameCount() >= domainNameRepositoryCapacity) {
                return new AddDomainNameResult(AddDomainNameResult.CODE_EXCEED_DOMAIN_NAME_REPOSITORY_CAPACITY, "超出域名数据库能容纳的域名数量", null);
            }

            // 生成域名记录，并写到数据库
            long shortName = shortDomainNameGenerator.nextShortDomainName();
            domainNameEntity = new DomainNameEntity(shortName, longName, (int) (System.currentTimeMillis() / 1000));
            domainNameRepository.insert(domainNameEntity);

            DomainNameDto domainNameDto = convertDomainNameEntityToDto(domainNameEntity);
            return new AddDomainNameResult(Result.CODE_SUCCESS, Result.MESSAGE_SUCCESS, domainNameDto);
        } finally {
            domainNameRepository.unlockForWrite();
        }
    }

    /**
     * 根据短域名查找域名记录
     *
     * @param shortName 62 进制短域名
     * @return DomainNameDto
     */
    public DomainNameDto getDomainNameByShortName(String shortName) {
        long shortNameInLong = DomainName.convertShortNameInBase62ToInLong(shortName);
        DomainNameEntity domainNameEntity = domainNameRepository.findByShortName(shortNameInLong, LockMode.READ);
        return convertDomainNameEntityToDto(domainNameEntity);
    }

    private DomainNameDto convertDomainNameEntityToDto(DomainNameEntity domainNameEntity) {
        String shortNameInBase62 = DomainName.convertShortNameInLongToInBase62(domainNameEntity.getShortName());
        return new DomainNameDto(shortNameInBase62, domainNameEntity.getLongName(), domainNameEntity.getCreatedTime());
    }

}
