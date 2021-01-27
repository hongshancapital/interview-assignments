package com.luman.http;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Response {
    private Request request;
    List<String> headers = new ArrayList<>();
    public Response(Request request) {
        this.request = request;
    }

    private void initHeader(HttpStatus status,String contentType){
        headers.add(request.getVersion()+" "+status.getStatus());
        headers.add("Connection: close");
        headers.add("Server: HttpServer");
        headers.add("Content-Type: "+contentType);
    }

    public void write(OutputStream outputStream) throws IOException, URISyntaxException {

        URL url  = Response.class.getResource("/views"+request.getUrl());
        if(url==null) {
            initHeader(HttpStatus.NOT_FOUND,ContentType.TEXT_HTML_VALUE);
        }else{
//            String fileType = Files.probeContentType(Paths.get(url.toURI()));
            switch (url.openConnection().getContentType()) {
                case "image/jpeg" -> initHeader(HttpStatus.OK, ContentType.IMAGE_JPEG_VALUE);
                case "image/png" -> initHeader(HttpStatus.OK, ContentType.IMAGE_PNG_VALUE);
                case "image/jpg" -> initHeader(HttpStatus.OK, ContentType.IMAGE_JPEG_VALUE);
                case "text/html" -> initHeader(HttpStatus.OK, ContentType.TEXT_HTML_VALUE);
                case "text/xml" -> initHeader(HttpStatus.OK, ContentType.TEXT_XML_VALUE);
                case "application/xml" -> initHeader(HttpStatus.OK, ContentType.APPLICATION_XML_VALUE);
                default -> initHeader(HttpStatus.OK, ContentType.APPLICATION_OCTET_STREAM_VALUE);
            }
        }
        DataOutputStream writer =new DataOutputStream(outputStream);
        for (String header : headers) {
            writer.writeBytes(header+"\r\n");
        }
        writer.writeBytes("\r\n");
        if(url!=null){
            InputStream resourceInputStream = url.openStream();
            int b = -1;
            while( (b=resourceInputStream.read())!= -1){
                writer.writeByte(b);
            }
        }
        writer.writeBytes("\r\n");
        writer.flush();
    }

    public void authFail(OutputStream outputStream) throws IOException {
        initHeader(HttpStatus.UNAUTHORIZED,ContentType.TEXT_HTML_VALUE);
        DataOutputStream writer =new DataOutputStream(outputStream);
        for (String header : headers) {
            writer.writeBytes(header+"\r\n");
        }
        writer.writeBytes("\r\n");
        writer.flush();
    }
}
