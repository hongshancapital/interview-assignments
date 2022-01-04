package me.huchao.sd.config;

import me.huchao.sd.domain.url.manager.BucketManager;
import me.huchao.sd.domain.url.model.Bucket;
import me.huchao.sd.domain.url.repos.SlotRepos;
import me.huchao.sd.repos.SlotReposImpl;
import me.huchao.sd.repos.memory.MemorySlotRepos;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huchao
 */
@Configuration
public class SpringBeanConfig {

    @Bean
    public BucketManager bucketManager() {
        MemorySlotRepos memorySlotRepos = new MemorySlotRepos(10000);
        SlotRepos slotRepos = new SlotReposImpl(memorySlotRepos);
        Bucket bucket = new Bucket(slotRepos, 1000, 10);
        BucketManager bucketManager = new BucketManager(bucket);
        return bucketManager;
    }
}
