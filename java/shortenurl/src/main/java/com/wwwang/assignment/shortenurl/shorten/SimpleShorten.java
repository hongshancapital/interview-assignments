package com.wwwang.assignment.shortenurl.shorten;

import com.wwwang.assignment.shortenurl.entity.ShortUrl;
import com.wwwang.assignment.shortenurl.shorten.scissor.IScissor;

/**
 * 直接把长信息交给scissor去处理的调度着，没有特殊处理
 */
public class SimpleShorten extends AbstractShorten{

    public SimpleShorten(IScissor scissor){
        super(scissor);
    }

    @Override
    public ShortUrl shorten(String info){
        return scissor.cut(info);
    }

}
