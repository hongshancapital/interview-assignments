package com.wjup.shorturl.service.serviceimpl;

import com.wjup.shorturl.entity.UrlEntity;
import com.wjup.shorturl.mapper.UrlMapper;
import com.wjup.shorturl.service.UrlService;
import com.wjup.shorturl.util.ShortUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * @description: TODO
 * @classname: UrlServiceImpl
 * @author niuxing@huaxiapawn
 * @date 2021/3/21
*/
@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlMapper urlMapper;

    @Override
    public UrlEntity findByShortUrlId(String shortUrlId) {
        return urlMapper.findByShortUrlId(shortUrlId);
    }

    @Override
    public void updateShortUrl(String shorlUrlId) {
        urlMapper.updateShortUrl(shorlUrlId,new Date());
    }

    @Override
    public UrlEntity findByPwd(String viewPwd, String shortUrlId) {
        return urlMapper.findByPwd(viewPwd,shortUrlId);
    }

    @Override
    public synchronized String createShortUrl(String longUrl, String viewPwd, HttpServletRequest request) {
        //根据换行进行分割
        String[] split = longUrl.split("\n|\r");
        StringBuffer msg = new StringBuffer();

        for (int i = 0; i < split.length; i++) {
            UrlEntity urlEntity = new UrlEntity();

            if (!split[i].contains("https://") && !split[i].contains("http://")) {
                split[i] = "http://" + split[i];
            }

            //随机字符串
//            String shortUrlId = ShortUtils.getStringRandom(6);
            //62进制字符串  两种方式生成短链接
//            这种方式可以解决 上述方案中随机数冲突的问题 采用的是布隆过滤器  同时也解决高并发访问量情况下多次磁盘IO  数据库压力的问题
            String shortUrlId = ShortUtils.to62url(longUrl);
            urlEntity.setShortUrlId(shortUrlId);
            urlEntity.setUuid(UUID.randomUUID().toString());
            urlEntity.setLongUrl(split[i]);
            urlEntity.setCreateTime(new Date());
            urlEntity.setViewPwd(viewPwd);

            int flag = urlMapper.createShortUrl(urlEntity);

            //使用当前服务的ip及 端口
            String toUrl = "/";
            int serverPort = request.getServerPort();
            //考虑到域名服务  Nginx默认为80端口  刚好在做微信相关的东西  所以这两个端口刚好有想到  独立处理下  ~~~
            if (serverPort == 80 || serverPort == 443) {
                toUrl = request.getScheme() + "://" + request.getServerName();
            } else {//可更改  根据当前服务端口域名进行调整
                toUrl = request.getScheme() + "://" + request.getServerName() + ":" + serverPort;
            }

            if (flag > 0) {
                msg.append(toUrl + "/" + shortUrlId + "<br>");
            }
        }
        return msg.toString();
    }


}
