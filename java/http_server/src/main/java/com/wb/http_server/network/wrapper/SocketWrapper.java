package com.wb.http_server.network.wrapper;

import java.io.IOException;


/**
 * @author bing.wang
 * @date 2021/1/15
 */
public interface SocketWrapper {
    void close() throws IOException;
}
