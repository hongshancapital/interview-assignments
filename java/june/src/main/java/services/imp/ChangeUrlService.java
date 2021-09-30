package services.imp;

import services.IChangeUrlService;
import utils.MapCache;

/**转换URLservice
 * @Description:
 * @Params
 * @Return
 * @Author Jun.Wong
 * @Date 2021/10/13 10:21
 */
public class ChangeUrlService implements IChangeUrlService {

    MapCache cache=new MapCache();
    @Override
    public String getValue(String key) {
        return cache.get(key);
    }

    @Override
    public String put(String url) {
        cache.set(url,url);
        return url;
    }
}
