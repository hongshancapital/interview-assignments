import com.addr.node.service.DomainService;
import com.addr.node.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class Test {

    @Autowired
    private DomainService domainService;

    @org.junit.jupiter.api.Test
    public void longD(){
        String shortd = "http://000hwINp";
        System.out.println(domainService.queryDomain(shortd));
    }
    @org.junit.jupiter.api.Test
    public void shrotD(){
        Map map = new HashMap();
        String longDomain="https://www.baidu.com/?tn=98654048_hao_pg";
        String sd = CommonUtils.shorten(longDomain);
        map.put("longDomain", longDomain);
        map.put("shortDomain", sd);
        System.out.println(domainService.insertDomain(map));
    }
}
