package cn.sequoiacap.domain.service;

import cn.sequoiacap.domain.common.IdGeneratorUtils;
import cn.sequoiacap.domain.dao.DomianDao;
import cn.sequoiacap.domain.entity.DomainEntity;
import cn.sequoiacap.domain.exception.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 短域名相关处理服务
 *
 * @author liuhao
 * @date 2021/12/10
 */
@Service
public class DomainService {

    @Autowired
    private DomianDao domianDao;

    /**
     * 根据长域名信息返回对应的短域名
     * <br>
     * 这个地方比较简单，所以异常就暂时忽略
     *
     * @param request    请求对象，主要是用来构建返回的短连接
     * @param longLink 接收到的长连接信息
     * @return 返回短连接
     */
    public String transformToShortLink(HttpServletRequest request, String longLink) {
        // 1 获取对应得短域名标识符
        String shortUuid = IdGeneratorUtils.getShortUuid();
        String urlPrefix = getUrlPrefix(request);
        String shortLink = urlPrefix + shortUuid;
        // 这个地方处理ID万一出现重复的问题可以重新处理，这种方式可以实现一个长域名可以对应多个短域名，每次进来的长域名都会生成一个短域名与之关联
        do {
            DomainEntity domainEntityByUuid = domianDao.getDomainEntityByUuid(shortUuid);
            if (domainEntityByUuid == null) {
                break;
            } else {
                shortUuid = IdGeneratorUtils.getShortUuid();
            }
        } while (true);
        // 构建域名实体
        DomainEntity domainEntity = new DomainEntity();
        domainEntity.setUuid(shortUuid);
        domainEntity.setShortDomain(shortLink);
        domainEntity.setLongDomain(longLink);
        domianDao.saveDomainEntity(domainEntity);
        return shortLink;
    }

    /**
     * 根据请求参数获取对应的请求连接信息 这个地方可以使用过滤器方式，提前将这个连接设置到请求头，这是为了简单，就先这么处理
     * 注意，这里在时间开发中，会有一个短域名解析到服务器上，基本上这个域名是固定的，这里做拼接，只是本demo的需要
     * 因此不考虑https的情况，否拼接端口等问题
     * @param request 请求对象
     * @return 返回拼接好的连接前缀，如：http://localhost:8080/
     */
    private String getUrlPrefix(HttpServletRequest request) {

        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(request.getServerName());
        // 这里是处理默认端口，如果是80，则可以不用拼接端口
        int serverPort = request.getServerPort();
        if (serverPort != 80) {
            sb.append(":").append(serverPort);
        }
        sb.append("/");
        return sb.toString();
    }

    /**
     * 根据一个短连接地址，返回对应的长连接地址
     *
     * @param shortLink 短连接地址
     * @return 返回长连接地址
     */
    public String getLongLinkByShortLink(String shortLink) {
        // 处理短连接地址中对应的uuid
        String uuid = shortLink.substring(shortLink.lastIndexOf("/")+1);
        // 这里做个判断，传递的参数有问题的情况
        if (StringUtils.hasText(uuid)) {
            DomainEntity domainEntity = domianDao.getDomainEntityByUuid(uuid);
            if (domainEntity == null) {
                throw new DomainException("对应的长连接信息不存在，请检查");
            }
            return domainEntity.getLongDomain();
        } else {
            throw new DomainException("传入的参数不对，请检查！");
        }
    }
}
