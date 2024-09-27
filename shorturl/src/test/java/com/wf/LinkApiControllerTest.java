package com.wf;

import com.wf.controller.LinkApiController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkApiControllerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void index() {
        LinkApiController lapi = new LinkApiController();
        lapi.index();
    }

    @Test
    void toShort() {
        LinkApiController lapi = new LinkApiController();
        lapi.toShort("www.baidu.com/123445a432");
        lapi.toShort("www.google.com/1234s45432");
        lapi.toShort("www.sina.com/123445f432");
        lapi.toShort("www.google.com/1234f45432");
        lapi.toShort("www.taobao.com/1234f45432");
        lapi.toShort("www.qq.com/12344543f2");
        lapi.toShort("www.bestbuy.com/12344f5432");
        lapi.toShort("www.sina.com/12344543f2");
        lapi.toShort("www.yahoo.com/1234454f32");
        lapi.toShort("www.zhihu.com/1234454f32");
        lapi.toShort("www.bilibli.com/1234454f32");
        lapi.toShort("www.fdsafdsa.com/123445fd432");
        lapi.toShort("www.basssssu.com/12344543fdsa2");
        lapi.toShort("www.china.com/12344543fdsa2");
        lapi.toShort("www.fdsafdsa.com/1fdsa23445432");

        lapi.index();
    }

    @Test
    void toLong() {
        LinkApiController lapi = new LinkApiController();
        lapi.toLong("ffdssa");
        lapi.toLong("fsadsa");
        lapi.toLong("fsassf");
        lapi.toLong("fsafds");
        lapi.toLong("fsasfd");
        lapi.toLong("fsaada");
        lapi.toLong("ffadas");
        lapi.toLong("fsfdas");
        lapi.toLong("ffadas");
    }
}