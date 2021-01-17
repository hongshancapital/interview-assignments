package com.wyd.http.server;

import com.wyd.http.Bootstrap;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BootstrapTest {

    private static final String path = "/Users/wangyadong/IdeaProjects/interview-assignments/java/httpserver/src/test/com/wyd/http/conf/conf.properties";

    @Test
    public void testLoadConf() throws IOException {
        // pwd+filename
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(path);
        assertNotNull(fileInputStream);
        properties.load(fileInputStream);
        assertNotNull(properties);
    }

    @Test
    public void testLoadConfWith() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.init(path);

    }

    public void testBootstrap() {
        Bootstrap bootstrap = new Bootstrap();
//        bootstrap.start("learnNginx", new InetSocketAddress(1222));
        String[] args = new String[6];
        args[0] = "start";
        args[1] = path;
        args[2] = "learnNginx";
        args[3] = "1222";
        Bootstrap.main(args);
    }

    public static void main(String[] args) throws IOException {
        BootstrapTest test = new BootstrapTest();
//        test.testLoadConf();
        test.testBootstrap();
    }
}
