package com.luman.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Request {

    private String url;
    private String version;
    private Map<String, List<String>> headers = new LinkedHashMap<>();
    public Request(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line =reader.readLine();
        if( line ==null || line.length()==0){
            throw new RuntimeException("parse request error null");
        }
        String[] items = line.split(" ");
        if(items.length<3){
            throw new RuntimeException("parse request error");
        }
        this.url = items[1].split("\\?")[0];
        this.version = items[2];

        while(!"".equals(line=reader.readLine())){
            String[] headerLine = line.split(":");
            if(!headers.containsKey(headerLine[0])){
                headers.put(headerLine[0],new LinkedList<>());
            }
            headers.get(headerLine[0]).add(headerLine[1]);
        }
        System.out.println("read request finish");
    }


    public String getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getHeader(String key){
        return headers.get(key);
    }
}
