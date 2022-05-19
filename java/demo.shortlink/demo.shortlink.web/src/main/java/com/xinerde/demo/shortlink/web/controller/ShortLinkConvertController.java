package com.xinerde.demo.shortlink.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xinerde.demo.shortlink.core.constants.ErrorCode;
import com.xinerde.demo.shortlink.core.domain.AjaxMessageEntity;
import com.xinerde.demo.shortlink.service.ShortLinkService;
import com.xinerde.demo.shortlink.web.vo.LinkVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 长短域名转换控制器
 * 
 * @since 2022年5月18日
 * @author guihong.zhang
 * @version 1.0
 */
@RestController
@RequestMapping("/")
@Api(tags = "域名服务api控制器")
public class ShortLinkConvertController {
	private final ShortLinkService shortLinkService;

	/**
	 * 构造方法注入
	 *
	 * @param 构造参数
	 */
	@Autowired
	public ShortLinkConvertController(ShortLinkService shortLinkService) {
		this.shortLinkService = shortLinkService;
	}

	/**
	 * 根据长链接获取短链接
	 * 
	 * @param longLink 长链接
	 * @return 短链接
	 */
	@ApiOperation(value = "生成短域名", notes = "根据长域名生成短域名")
	@ApiImplicitParam(name = "longLink", value = "长域名", dataType = "String")
	@RequestMapping("/fetchShortLink")
	public AjaxMessageEntity<LinkVO> fetchShortLink(String longLink) {
		AjaxMessageEntity<LinkVO> ajaxMessageEntity = new AjaxMessageEntity<>();
		try {
			String shortLink = shortLinkService.fetchShortLink(longLink);
			LinkVO shortLinkVO = new LinkVO(null, shortLink);
			ajaxMessageEntity.setData(shortLinkVO);
		} catch (Exception e) {
			ajaxMessageEntity.setCode(ErrorCode.FAIL);
			ajaxMessageEntity.setMsg(e.getLocalizedMessage());
		}
		return ajaxMessageEntity;
	}

	/**
	 * 根据短链接获取长链接
	 * 
	 * @param shortLink 短链接
	 * @return 长链接
	 */
	@ApiOperation(value = "获取长域名", notes = "根据短域名获取长域名")
	@ApiImplicitParam(name = "shortLink", value = "短域名", dataType = "String")
	@RequestMapping("/fetchLongLink")
	public AjaxMessageEntity<LinkVO> fetchLongLink(String shortLink) {
		AjaxMessageEntity<LinkVO> ajaxMessageEntity = new AjaxMessageEntity<>();
		try {
			String longLink = shortLinkService.fetchLongLink(shortLink);
			LinkVO longLinkVO = new LinkVO(null, longLink);
			ajaxMessageEntity.setData(longLinkVO);
			return ajaxMessageEntity;
		} catch (Exception e) {
			ajaxMessageEntity.setCode(ErrorCode.FAIL);
			ajaxMessageEntity.setMsg(e.getLocalizedMessage());
		}
		return ajaxMessageEntity;
	}
}
