//package com.liukun.shortdomain.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.xml.bind.SchemaOutputResolver;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * <p>
// * <b>Class name</b>:
// * </p>
// * <p>
// * <b>Class description</b>: Class description goes here.
// * </p>
// * <p>
// * <b>Author</b>: kunliu
// * </p>
// * <b>Change History</b>:<br/>
// * <p>
// *
// * <pre>
// * Date          Author       Revision     Comments
// * ----------    ----------   --------     ------------------
// * 2021/10/7       kunliu        1.0          Initial Creation
// *
// * </pre>
// *
// * @author kunliu
// * @date 2021/10/7
// * </p>
// */
//@Slf4j
//@SpringBootTest
//public class GuidUtilTest {
//
//    @Test
//    public void testGetUid() {
//        GuidUtil guidUtil = new GuidUtil();
//        for (int i = 0; i < 50; i++) {
//            Thread t = new Thread(() -> {
//                for (int j = 0; j < 100; j++) {
//                    long guid = guidUtil.getGuid();
//                    System.out.println(guid);
//                }
//            });
//            t.start();
//        }
//    }
//}