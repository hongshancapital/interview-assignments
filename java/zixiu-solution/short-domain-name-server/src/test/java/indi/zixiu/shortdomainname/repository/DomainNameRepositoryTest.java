package indi.zixiu.shortdomainname.repository;

import indi.zixiu.shortdomainname.entity.DomainNameEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;

public class DomainNameRepositoryTest {
    private DomainNameRepository domainNameRepository;

    @BeforeEach
    public void setup() {
        domainNameRepository = new DomainNameRepository();
    }

    @Test
    public void testGetDomainNameCount() {
        int domainNameCount = domainNameRepository.getDomainNameCount();
        Assertions.assertEquals(0, domainNameCount, "初始域名数量应为 0");

        domainNameRepository.insert(createDomainNameEntity());
        Assertions.assertEquals(domainNameCount + 1, domainNameRepository.getDomainNameCount(), "插入一条域名后，域名数量应加 1");
    }

    @Test
    public void testInsert_success() {
        DomainNameEntity insertedDomainNameEntity = createDomainNameEntity();
        long shortName = insertedDomainNameEntity.getShortName();
        Assertions.assertNull(domainNameRepository.findByShortName(shortName));

        domainNameRepository.insert(insertedDomainNameEntity);
        DomainNameEntity foundDomainNameEntity = domainNameRepository.findByShortName(shortName);
        Assertions.assertNotNull(foundDomainNameEntity);
        Assertions.assertEquals(insertedDomainNameEntity.getShortName(), foundDomainNameEntity.getShortName());
        Assertions.assertTrue(insertedDomainNameEntity.getLongName().equals(foundDomainNameEntity.getLongName()));
        Assertions.assertEquals(insertedDomainNameEntity.getCreatedTime(), foundDomainNameEntity.getCreatedTime());
    }

    @Test
    public void testInsert_whenDuplicateShortName_thenException(){
        DomainNameEntity domainNameEntity1 = new DomainNameEntity(3093342976L, "foo.example.com", 1642743556);
        DomainNameEntity domainNameEntity2 = new DomainNameEntity(3093342976L, "bar.example.com", 1642743557);
        domainNameRepository.insert(domainNameEntity1);
        Assertions.assertThrows(DuplicateKeyException.class, () -> domainNameRepository.insert(domainNameEntity2));
    }

    @Test
    public void testInsert_whenDuplicateLongName_thenException() {
        DomainNameEntity domainNameEntity1 = new DomainNameEntity(3093342976L, "foo.example.com", 1642743556);
        DomainNameEntity domainNameEntity2 = new DomainNameEntity(4956672704L, "foo.example.com", 1642772671);
        domainNameRepository.insert(domainNameEntity1);
        Assertions.assertThrows(DuplicateKeyException.class, () -> domainNameRepository.insert(domainNameEntity2));
    }

    @Test
    public void testFindByShortName() {
        DomainNameEntity insertedDomainNameEntity = createDomainNameEntity();
        long shortName = insertedDomainNameEntity.getShortName();
        Assertions.assertNull(domainNameRepository.findByShortName(shortName));

        domainNameRepository.insert(insertedDomainNameEntity);
        DomainNameEntity foundDomainNameEntity = domainNameRepository.findByShortName(shortName);
        Assertions.assertNotNull(foundDomainNameEntity);
        Assertions.assertEquals(insertedDomainNameEntity.getShortName(), foundDomainNameEntity.getShortName());
        Assertions.assertTrue(insertedDomainNameEntity.getLongName().equals(foundDomainNameEntity.getLongName()));
        Assertions.assertEquals(insertedDomainNameEntity.getCreatedTime(), foundDomainNameEntity.getCreatedTime());
    }

    @Test
    public void testFindByLongName() {
        DomainNameEntity insertedDomainNameEntity = createDomainNameEntity();
        String longName = insertedDomainNameEntity.getLongName();
        Assertions.assertNull(domainNameRepository.findByLongName(longName));

        domainNameRepository.insert(insertedDomainNameEntity);
        DomainNameEntity foundDomainNameEntity = domainNameRepository.findByLongName(longName);
        Assertions.assertNotNull(foundDomainNameEntity);
        Assertions.assertEquals(insertedDomainNameEntity.getShortName(), foundDomainNameEntity.getShortName());
        Assertions.assertTrue(insertedDomainNameEntity.getLongName().equals(foundDomainNameEntity.getLongName()));
        Assertions.assertEquals(insertedDomainNameEntity.getCreatedTime(), foundDomainNameEntity.getCreatedTime());
    }

    private DomainNameEntity createDomainNameEntity() {
        return new DomainNameEntity(3093342976L, "foo.example.com", 1642743556);
    }
}
