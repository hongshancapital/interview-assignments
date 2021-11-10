package homework.shorturl.service;

import homework.shorturl.model.dto.UrlTransDTO;

public interface UrlTransService {

    UrlTransDTO transShort(UrlTransDTO dto) throws Exception;

    UrlTransDTO transLong(UrlTransDTO dto) throws Exception;
}
