package com.wyd.http.servlet;

import com.wyd.http.server.net.Request;
import com.wyd.http.server.net.Response;

public abstract class DefaultServlet {


    public void service(Request request, Response response){
        String authenticate = request.getAuthenticate();
    }

    public void  init(){}


    public void  stop(){

    }
}
