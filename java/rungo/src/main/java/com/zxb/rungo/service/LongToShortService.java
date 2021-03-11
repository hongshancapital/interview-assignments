package com.zxb.rungo.service;

import java.util.HashMap;
import com.zxb.rungo.entity.LongToShort;

/**
 * 文件上传Service
 */
public interface LongToShortService {
	
	int delete(Integer id);

    int insert(HashMap<String, Object> map);

    LongToShort select(HashMap<String, Object> map);

    int update(HashMap<String, Object> map);
}
