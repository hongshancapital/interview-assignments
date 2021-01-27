package com.luman.http.server;

import com.luman.http.Request;
import com.luman.http.Response;

import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.List;

public class HttpHandler implements Runnable{
    private final Socket socket;
    public HttpHandler(Socket accept) {
        this.socket = accept;
    }

    @Override
    public void run() {
        try {
            Request request = new Request(socket.getInputStream());
            List<String> auth = request.getHeader("Authorization");

            Response response = new Response(request);


            response.write(socket.getOutputStream());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
