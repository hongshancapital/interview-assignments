package indi.zixiu.shortdomainname.service;

import indi.zixiu.shortdomainname.domain.DomainName;
import indi.zixiu.shortdomainname.dto.DomainNameDto;
import indi.zixiu.shortdomainname.entity.DomainNameEntity;
import indi.zixiu.shortdomainname.repository.DomainNameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DomainNameServiceTest {
    private int domainNameRepositoryCapacity = 2;
    private DomainNameService domainNameService;

    @BeforeEach
    public void setup() {
        domainNameService = new DomainNameService(domainNameRepositoryCapacity, new DomainNameRepository(),
                new ShortDomainNameGenerator(0, 0));
    }

    @Test
    public void testAddDomainName_givenInvalidDomainName_thenReturnFailure() {
        AddDomainNameResult addDomainNameResult = domainNameService.addDomainName("a");
        Assertions.assertEquals(AddDomainNameResult.CODE_INVALID_DOMAIN_NAME, addDomainNameResult.getCode());
    }

    @Test
    public void testAddDomainName_whenLongNameExisted_thenReturnExistedDomainName() {
        String longName = "example.com";
        AddDomainNameResult addDomainNameResult = domainNameService.addDomainName(longName);
        Assertions.assertEquals(Result.CODE_SUCCESS, addDomainNameResult.getCode());
        DomainNameDto domainNameDto1 = addDomainNameResult.getData();
        Assertions.assertNotNull(domainNameDto1);

        addDomainNameResult = domainNameService.addDomainName(longName);
        Assertions.assertEquals(Result.CODE_SUCCESS, addDomainNameResult.getCode());
        DomainNameDto domainNameDto2 = addDomainNameResult.getData();
        Assertions.assertNotNull(domainNameDto2);

        Assertions.assertTrue(domainNameDto1.getShortName().equals(domainNameDto2.getShortName()));
        Assertions.assertTrue(domainNameDto1.getLongName().equals(domainNameDto2.getLongName()));
        Assertions.assertEquals(domainNameDto1.getCreatedTime(), domainNameDto2.getCreatedTime());
    }

    @Test
    public void testAddDomainName_whenExceedDomainNameRepositoryCapacity_thenReturnFailure() {
        for (int i = 0; i <= domainNameRepositoryCapacity; i++) {
            String longName = i + ".com";
            AddDomainNameResult addDomainNameResult = domainNameService.addDomainName(longName);
            if (i < domainNameRepositoryCapacity) {
                Assertions.assertEquals(Result.CODE_SUCCESS, addDomainNameResult.getCode());
            } else {
                Assertions.assertEquals(AddDomainNameResult.CODE_EXCEED_DOMAIN_NAME_REPOSITORY_CAPACITY, addDomainNameResult.getCode());
            }
        }
    }

    @Test
    public void testGetDomainNameByShortName() {
        String longName = "example.com";
        AddDomainNameResult addDomainNameResult = domainNameService.addDomainName(longName);
        DomainNameDto domainNameDto1 = addDomainNameResult.getData();
        String shortName = domainNameDto1.getShortName();
        DomainNameDto domainNameDto2 = domainNameService.getDomainNameByShortName(shortName);

        Assertions.assertTrue(domainNameDto1.getShortName().equals(domainNameDto2.getShortName()));
        Assertions.assertTrue(domainNameDto1.getLongName().equals(domainNameDto2.getLongName()));
        Assertions.assertEquals(domainNameDto1.getCreatedTime(), domainNameDto2.getCreatedTime());
    }
}
