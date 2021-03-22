package xiejin.java.interview.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import xiejin.java.interview.dto.request.LongUrlRequestDTO;
import xiejin.java.interview.dto.request.ShortUrlRequestDTO;
import xiejin.java.interview.dto.response.OriginalUrlResponseDTO;
import xiejin.java.interview.dto.response.ShortUrlResponseDTO;
import xiejin.java.interview.entity.Domain;
import xiejin.java.interview.enums.ResultEnum;
import xiejin.java.interview.exception.GlobalException;
import xiejin.java.interview.mapper.DomainMapper;
import xiejin.java.interview.service.DomainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xiejin.java.interview.util.DomainChangeUtil;

import java.util.Random;

/**
 * <p>
 * 长短域名映射表 服务实现类
 * </p>
 *
 * @author xiejin
 * @since 2021-03-20
 */
@Service
@Slf4j
public class DomainServiceImpl extends ServiceImpl<DomainMapper, Domain> implements DomainService {


    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public ShortUrlResponseDTO saveShortUrl(LongUrlRequestDTO longUrlRequestDTO) {
        //检测是否已经生成过
        Domain domain = this.baseMapper.selectOne(new QueryWrapper<Domain>()
                .lambda().select(Domain::getOriginalDomain)
                .eq(Domain::getOriginalDomain, longUrlRequestDTO.getOriginalUrl()));
        if (domain != null) throw new GlobalException(ResultEnum.RECORD_EXISTS,ResultEnum.RECORD_EXISTS.getMessage());

        String originalUrl = longUrlRequestDTO.getOriginalUrl();
        String[] strings = DomainChangeUtil.ShortText(originalUrl);
        //随机挑选一个作为短网址
        String shortUrl = strings[new Random().nextInt(strings.length)];

        //存入数据库中
        domain = new Domain()
                .setOriginalDomain(longUrlRequestDTO.getOriginalUrl())
                .setShortDomain(shortUrl);

        this.baseMapper.insert(domain);
        ShortUrlResponseDTO shortUrlResponseDTO = new ShortUrlResponseDTO();
        shortUrlResponseDTO.setShortUrl(shortUrl);
        return shortUrlResponseDTO;
    }


    @Override
    public OriginalUrlResponseDTO getOriginalUrl(ShortUrlRequestDTO requestDTO) {
        String shortUrl = requestDTO.getShortUrl().split("//")[1];
        Domain domain = this.baseMapper.selectOne(new QueryWrapper<Domain>().lambda()
                .select(Domain::getOriginalDomain)
                .eq(Domain::getState, 1)
                .eq(Domain::getShortDomain, shortUrl));
        if (domain == null)
            throw new GlobalException(ResultEnum.RECORD_STATE_EXPIRE, ResultEnum.RECORD_STATE_EXPIRE.getMessage());
        OriginalUrlResponseDTO dto = new OriginalUrlResponseDTO();
        dto.setOriginalUrl(domain.getOriginalDomain());
        return dto;
    }
}
