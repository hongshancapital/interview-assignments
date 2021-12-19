package com.my.linkapi.service.impl;

import com.my.linkapi.dto.TrackEntityDto;
import com.my.linkapi.service.MapSaveService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Service
public class MapSaveServiceImpl implements MapSaveService {
    private static Logger logger = LoggerFactory.getLogger(LinkLengthConversionServiceImpl.class);
    private static Map<String, String> lmap = new ConcurrentHashMap<>();
    private static List<TrackEntityDto> trackList = Collections.synchronizedList(new ArrayList<>());
    private static ExecutorService poll = Executors.newSingleThreadExecutor();
    private static Integer ttls = 18000;// 秒计时-5小时

    public MapSaveServiceImpl(){
        poll.submit(new Callable()
		{
			@Override
			public Object call()
			{
                while (true) {
                    reclaimMemory();
                }
			}
		});
    }

    public MapSaveServiceImpl(Boolean noInit){
    }

    protected Map<String, String> getLmap(){
        return lmap;
    }

    protected List<TrackEntityDto> getTrackList(){
        return trackList;
    }

    public void reclaimMemory(){
        try {
            logger.debug("开始执行");
            //复制一个队列
            List<TrackEntityDto> trackListCopy = new ArrayList<>(getTrackList());
            //确定过期内容
            int i = 0;
            Iterator iterator = trackListCopy.iterator();
            while (iterator.hasNext()) {
                TrackEntityDto trackEntity = (TrackEntityDto)iterator.next();
                LocalDateTime cdate = trackEntity.getCdate();
                LocalDateTime maxDate = cdate.plusSeconds(ttls);
                if (maxDate.isAfter(LocalDateTime.now())) {
                    break;
                }
                i++;
            }
            // 确定删除内容
            int removeCount = 0;
            for (int x = 0; x < i; x++) {
                TrackEntityDto trackEntity = trackListCopy.get(x);
                String rmkey = trackEntity.getKey();
                getLmap().remove(rmkey);
                getTrackList().remove(x - removeCount);
                removeCount++;
            }
            logger.debug("执行结束");
            Thread.sleep(500);
        }catch (Exception e){
            logger.error("回收内存执行错误", e);
        }
    }

    public void save(String key, String value){
        getLmap().put(key, value);
        TrackEntityDto trackEntity = new TrackEntityDto();
        trackEntity.setKey(key);
        trackEntity.setCdate(LocalDateTime.now());
        getTrackList().add(trackEntity);
    }

    public String getData(String key){
        return getLmap().get(key);
    }
}
