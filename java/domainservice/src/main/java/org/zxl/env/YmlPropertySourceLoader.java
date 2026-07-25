package org.zxl.env;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 加载 yaml 配置文件的时候 按照部署环境自定义修改某些配置
 */
@Slf4j
public class YmlPropertySourceLoader implements PropertySourceLoader {
    private static final Logger log = LoggerFactory.getLogger(YmlPropertySourceLoader.class);

    @Override
    public String[] getFileExtensions() {
        return new String[]{"yml", "yaml"};
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource)
            throws IOException {
        if (!ClassUtils.isPresent("org.yaml.snakeyaml.Yaml", null)) {
            throw new IllegalStateException("Attempted to load " + name
                    + " but snakeyaml was not found on the classpath");
        }
        List<Map<String, Object>> loaded = new YmlOriginTrackedYamLoader(resource).load();
        if (loaded.isEmpty()) {
            return Collections.emptyList();
        }
        // do some replace to "loaded list"
        // modify(loaded);
        List<PropertySource<?>> propertySources = new ArrayList<>(loaded.size());
        for (int i = 0; i < loaded.size(); i++) {
            String documentNumber = (loaded.size() != 1) ? " (document #" + i + ")" : "";
            propertySources.add(new OriginTrackedMapPropertySource(name + documentNumber,
                    loaded.get(i)));
        }
        return propertySources;
    }

}
