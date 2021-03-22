package xiejin.java.interview.service;

import xiejin.java.interview.dto.request.LongUrlRequestDTO;
import xiejin.java.interview.dto.request.ShortUrlRequestDTO;
import xiejin.java.interview.dto.response.OriginalUrlResponseDTO;
import xiejin.java.interview.dto.response.ShortUrlResponseDTO;
import xiejin.java.interview.entity.Domain;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 长短域名映射表 服务类
 * </p>
 *
 * @author xiejin
 * @since 2021-03-20
 */
public interface DomainService extends IService<Domain> {

    ShortUrlResponseDTO saveShortUrl(LongUrlRequestDTO longUrlRequestDTO);

    OriginalUrlResponseDTO getOriginalUrl(ShortUrlRequestDTO requestDTO);
}
