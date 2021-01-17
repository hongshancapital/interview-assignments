package com.wyd.http.server.metric;


import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

public final class WydHttpMetric {
    MetricRegistry registry = new MetricRegistry();
    ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
    private static final WydHttpMetric metric = new WydHttpMetric();

    public static WydHttpMetric getInstance() {
        return metric;
    }

    private WydHttpMetric() {
        reporter.start(1, TimeUnit.SECONDS);
    }


    public void queueMonitor(Class clazz, Queue queue,String name) {

        registry.register(MetricRegistry.name(clazz, name,"size"),
                new Gauge<Integer>() {
                    @Override
                    public Integer getValue() {
                        return queue.size();
                    }
                });
    }




}
