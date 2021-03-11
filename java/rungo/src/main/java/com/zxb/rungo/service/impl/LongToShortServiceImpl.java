package com.zxb.rungo.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxb.rungo.entity.LongToShort;
import com.zxb.rungo.mapper.LongToShortMapper;
import com.zxb.rungo.service.ConversionService;
import com.zxb.rungo.service.LongToShortService;

@Service
public class LongToShortServiceImpl implements LongToShortService {
	@Autowired
	LongToShortMapper longToShortMapper;
	
	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return longToShortMapper.delete(id);
	}

	@Override
	public int insert(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return longToShortMapper.insert(map);
	}

	@Override
	public LongToShort select(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return longToShortMapper.select(map);
	}

	@Override
	public int update(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return longToShortMapper.update(map);
	}

}
