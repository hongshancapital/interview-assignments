package com.hszb.shorturl.model;

import com.hszb.shorturl.common.enums.ShortCodeGeneratorEnum;
import org.junit.Test;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/24 10:44 上午
 * @Version: 1.0
 * @Description:
 */
public class ShortCodeGeneratorEnumTest {

    @Test
    public void getStructureEnum1 () {
        assert ShortCodeGeneratorEnum.getStructureEnum(null) == null;
    }

    @Test
    public void getStructureEnum2 () {
        assert ShortCodeGeneratorEnum.getStructureEnum("Test") == null;
    }

    @Test
    public void getStructureEnum3 () {
        assert ShortCodeGeneratorEnum.getStructureEnum("id") != null;
    }
}
