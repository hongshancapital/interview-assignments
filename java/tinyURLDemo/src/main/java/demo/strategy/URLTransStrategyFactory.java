package demo.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class URLTransStrategyFactory {
    private final Map<String, URLTransStrategy> urlTransStrategyHashMap;

    @Autowired
    public URLTransStrategyFactory(Map<String, URLTransStrategy> urlTransStrategyHashMap) {
        this.urlTransStrategyHashMap = urlTransStrategyHashMap;
    }

    public URLTransStrategy getURLTransStrategy(String name) {
        URLTransStrategy urlTransStrategy = null;

        URLTransStrategyEnum urlTransStrategyEnum = URLTransStrategyEnum.getByName(name);
        if (Objects.isNull(urlTransStrategyEnum)) {
            log.error("URLTransStrategyFactory 工厂方法传入错误参数！");
            return null;
        }

        switch (urlTransStrategyEnum) {
            case BASE62:
                urlTransStrategy = this.urlTransStrategyHashMap.get(urlTransStrategyEnum.getName());
                break;
            default:
                log.error("URLTransStrategyFactory 工厂方法传入错误参数！");
        }

        return urlTransStrategy;
    }
}
