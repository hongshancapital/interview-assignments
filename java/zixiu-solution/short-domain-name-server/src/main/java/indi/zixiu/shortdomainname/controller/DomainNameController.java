package indi.zixiu.shortdomainname.controller;

import indi.zixiu.shortdomainname.dto.DomainNameDto;
import indi.zixiu.shortdomainname.service.AddDomainNameResult;
import indi.zixiu.shortdomainname.service.DomainNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/domainName")
public class DomainNameController {
    @Autowired
    private DomainNameService domainNameService;

    @PutMapping("/longName/{longName}")
    public AddDomainNameResult addDomainName(@PathVariable String longName) {
        return domainNameService.addDomainName(longName);
    }

    @GetMapping("/shortName/{shortName}")
    public DomainNameDto getDomainNameByShortName(@PathVariable String shortName) {
        return domainNameService.getDomainNameByShortName(shortName);
    }
}
