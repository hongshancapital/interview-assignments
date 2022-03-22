package demo.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class URLDaoFactory {
    private final Map<String, URLDao> urlDaoHashMap;

    @Autowired
    public URLDaoFactory(Map<String, URLDao> urlDaoHashMap) {
        this.urlDaoHashMap = urlDaoHashMap;
    }

    public URLDao getURLDao(URLDaoEnum urlDaoEnum) {
        URLDao urlDao = null;

        switch (urlDaoEnum) {
            case CACHE:
            case REDIS:
            case MYSQL:
                urlDao = this.urlDaoHashMap.get(urlDaoEnum.getName());
                break;
            default:
                log.error("URLDaoFactory 工厂方法传入错误参数！");
        }

        return urlDao;
    }
}
