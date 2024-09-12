package com.oldnoop.shortlink;

import com.oldnoop.shortlink.service.ShortLinkService;
import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.LinkedBlockingDeque;

@State(Scope.Thread)
public class JMHLinkSearchTest {

    private ConfigurableApplicationContext context;

    private ShortLinkService service;

    private LinkedBlockingDeque<String> linkData = new LinkedBlockingDeque<>();

    private LinkedBlockingDeque<String> shortLinkData = new LinkedBlockingDeque<>();

    private int LINK_COUNT = 100 * 10000;

    /**
     * setup初始化容器的时候只执行一次
     */
    @Setup(Level.Trial)
    public void init() {
        context = new AnnotationConfigApplicationContext(ShortlinkApplication.class);
        service = context.getBean(ShortLinkService.class);
        prepareLinkData();
    }

    @Benchmark
    @Warmup(iterations = 1, time = 1)
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 5)
    public void testGetLink() {
        for (int i = 0; i < 10 * 10000; i++) {
            String shortLink = shortLinkData.poll();
            if (shortLink == null) {
                return;
            }
            service.search(shortLink);
        }
    }

    private void prepareLinkData() {
        for (int i = 0; i < LINK_COUNT; i++) {
            String x = RandomStringUtils.random(6, true, true);
            String y = RandomStringUtils.random(3, true, false);
            String a = RandomStringUtils.random(5, true, true);
            String b = RandomStringUtils.random(5, true, true);
            linkData.add(x + "." + y + "." + "/" + a + "/" + b);
        }
        while (!linkData.isEmpty()) {
            String link = linkData.poll();
            if (link == null) {
                continue;
            }
            String shortLink = service.create(link);
            shortLinkData.add(shortLink);
        }
    }


}
