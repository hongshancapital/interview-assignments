package com.wwwang.assignment.shortenurl.shorten;

import com.wwwang.assignment.shortenurl.entity.ShortUrl;
import com.wwwang.assignment.shortenurl.shorten.scissor.IScissor;

/**
 * 裁剪信息的调度者
 */
public abstract class AbstractShorten {

    protected IScissor scissor;

    public AbstractShorten(IScissor scissor){
        this.scissor = scissor;
    }

    /**
     * 调用scissor去裁剪信息
     * @param info
     * @return
     */
    public abstract ShortUrl shorten(String info);

}
