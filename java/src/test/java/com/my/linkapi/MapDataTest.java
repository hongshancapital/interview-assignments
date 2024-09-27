package com.my.linkapi;

import com.my.linkapi.dto.TrackEntityDto;
import com.my.linkapi.service.impl.MapSaveServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(SpringRunner.class)
public class MapDataTest {
    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void reclaimMemory(){
        MapSaveServiceImpl mapSaveService = new MapSaveServiceImpl(true);
        List<TrackEntityDto> trackList = Collections.synchronizedList(new ArrayList<>());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        TrackEntityDto trackEntity2 = new TrackEntityDto();
        trackEntity2.setKey("testkey");
        trackEntity2.setCdate(LocalDateTime.parse("2021-10-15 10:10:10", df));
        trackList.add(trackEntity2);

        TrackEntityDto trackEntity3 = new TrackEntityDto();
        trackEntity3.setKey("testkey2");
        trackEntity3.setCdate(LocalDateTime.parse("2025-10-15 10:10:10", df));
        trackList.add(trackEntity3);

        TrackEntityDto trackEntity4 = new TrackEntityDto();
        trackEntity4.setKey("testkey3");
        trackEntity4.setCdate(LocalDateTime.parse("2025-10-15 10:10:10", df));
        trackList.add(trackEntity4);

        Map<String, String> lmap = new ConcurrentHashMap<>();
        lmap.put("testkey","1");
        lmap.put("testkey2","2");

        try {
            Field trackListf = MapSaveServiceImpl.class.getDeclaredField("trackList");
            trackListf.setAccessible(true);
            trackListf.set(mapSaveService, trackList);

            Field lmapf = MapSaveServiceImpl.class.getDeclaredField("lmap");
            lmapf.setAccessible(true);
            lmapf.set(mapSaveService, lmap);
            mapSaveService.reclaimMemory();

            trackList = (List<TrackEntityDto>)trackListf.get(mapSaveService);
            lmap = (Map<String, String>)lmapf.get(mapSaveService);
            Assert.assertTrue("回收追踪list错误",trackList.size()<3);
            Assert.assertTrue("回收内容错误",lmap.get("testkey")==null);
        }catch (Exception e){
            Assert.assertTrue("测试执行失败",Boolean.FALSE);
        }
    }

    @Test
    public void reclaimMemory2(){
        MapSaveServiceImpl mapSaveService = new MapSaveServiceImpl(true);
        List<TrackEntityDto> trackList = Collections.synchronizedList(new ArrayList<>());

        Map<String, String> lmap = new ConcurrentHashMap<>();

        try {
            Field trackListf = MapSaveServiceImpl.class.getDeclaredField("trackList");
            trackListf.setAccessible(true);
            trackListf.set(mapSaveService, trackList);

            Field lmapf = MapSaveServiceImpl.class.getDeclaredField("lmap");
            lmapf.setAccessible(true);
            lmapf.set(mapSaveService, lmap);
            mapSaveService.reclaimMemory();

            trackList = (List<TrackEntityDto>)trackListf.get(mapSaveService);
            Assert.assertTrue("回收追踪list错误",trackList.size() == 0);
        }catch (Exception e){
            Assert.assertTrue("测试执行失败",Boolean.FALSE);
        }
    }

    @Test
    public void getValue(){
        MapSaveServiceImpl mapSaveService = new MapSaveServiceImpl(true);

        try {
            mapSaveService.save("testkey3", "1");

            Field trackListf = MapSaveServiceImpl.class.getDeclaredField("trackList");
            trackListf.setAccessible(true);

            List<TrackEntityDto> trackList = (List<TrackEntityDto>)trackListf.get(mapSaveService);
            Assert.assertTrue("回收内容错误","1".equals(mapSaveService.getData("testkey3")));
            Assert.assertTrue("回收内容错误",trackList.size() > 0);
        }catch (Exception e){
            Assert.assertTrue("测试执行失败",Boolean.FALSE);
        }
    }
}
