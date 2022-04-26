package com.scc.base;

import com.scc.base.entity.JsonResult;
import org.junit.Test;

/**
 * @author renyunyi
 * @date 2022/4/26 2:12 PM
 * @description JsonResult test
 **/
public class JsonResultTests {

    @Test
    public void test(){
        JsonResult.getErrorResult("failed");
        JsonResult.getSuccessResult("success");
        JsonResult.getErrorResult();
        JsonResult.getSuccessResult();

        JsonResult jsonResult = new JsonResult(0, "success");
        jsonResult.toString();
        jsonResult.setCode(0);
        jsonResult.setData("success");
        jsonResult.setMessage("message");
    }

}
