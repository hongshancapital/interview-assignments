package com.hongshan.taskwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hongshan.taskwork.dao.LinkMapper;
import com.hongshan.taskwork.model.Link;

@Service
@Transactional
public class LinkService {

	@Autowired
	LinkMapper linkMapper;
	public void saveLink(Link link) {
		linkMapper.insert(link);
	}
}
