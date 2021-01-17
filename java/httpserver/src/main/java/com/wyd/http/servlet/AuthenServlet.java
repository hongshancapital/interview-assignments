package com.wyd.http.servlet;

import com.wyd.http.server.constants.Constants;
import com.wyd.http.server.net.Request;
import com.wyd.http.server.net.Response;

import java.util.Base64;
import java.util.Map;

import static com.wyd.http.server.constants.Constants.BASE64_KEY;

public class AuthenServlet extends DefaultServlet {


    @Override
    public void service(Request request, Response response) {
        String authen = Constants.AUTHENTICATE;
        Map<String, String> map = request.getSession().getCache().asMap();
        String authenValue = map.get(authen);
        if (authenValue == null) {
            byte[] bytes = request.getHeader().get(authen);
            if (bytes == null || bytes.length == 0) {
                response.setGetStatus(Constants.FLAG_401);
                return;
            } else {
                String[] bearers = new String(bytes).split(Constants.AUTHEN_BASIC);
                map.put(authen, authenValue = new String(Base64.getDecoder().decode(bearers[1])));
            }
        }
        if(!authenValue.equals(BASE64_KEY)){
            response.setGetStatus(Constants.FLAG_401);
        }


    }
}
