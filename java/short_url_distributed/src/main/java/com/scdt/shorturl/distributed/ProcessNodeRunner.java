package com.scdt.shorturl.distributed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Component
public class ProcessNodeRunner implements CommandLineRunner {
    private final ProcessNode processNode;
    public ProcessNodeRunner(ProcessNode processNode) {
        this.processNode = processNode;
    }

    /**
     * 注册当前节点到zk
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        final ExecutorService service = Executors.newSingleThreadExecutor();
        final Future<?> status = service.submit(processNode);
        try {
            status.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
            service.shutdown();
        }
    }

}
