package com.scdt.interview.assignments.service;

import com.scdt.interview.assignments.bean.dto.LinkInfo;
import com.scdt.interview.assignments.bean.vo.LongLinkParam;
import com.scdt.interview.assignments.bean.vo.ShortLinkParam;

public interface LinkService {

    public LinkInfo generateShortUrl(LongLinkParam longLinkParam);
    public LinkInfo getLongUrl(ShortLinkParam shortLinkParam);

}
