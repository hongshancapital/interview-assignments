package org.example.config;

import java.io.*;
import java.util.Properties;

public class ESConfig {

    // ES路径
    public static String gkg_search_host_url = ESConfig.getInstance().getValue("gkg_search_host_url");

    private Properties config = new Properties();

    private static final String CONFIG_PATH = "es.properties";

    private static class ClientHolder {
        private static final ESConfig INSTANCE = new ESConfig();
    }

    private static final ESConfig getInstance() {
        return ClientHolder.INSTANCE;
    }

    private ESConfig() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TO-DO 添加你需要的client到helper
    }

    private String getValue(String key){
        return this.config.getProperty(key);
    }

    /**
     * 初始化默认的client
     */
    private void init() throws IOException {
        InputStream in = null;
        File file = new File(getPath() + File.separator + CONFIG_PATH);
        if(file.exists()){
            FileInputStream fileInputStream = new FileInputStream(file);
            in = fileInputStream;
        }
        this.config.load(in);
    }

    private static String getPath() {
        String path = ESConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (System.getProperty("os.name").contains("dows")) {
            path = path.substring(1, path.length());
        }

        return path.substring(0, path.lastIndexOf("/"));
        // return path.replace("target/classes/", "");
    }
}
