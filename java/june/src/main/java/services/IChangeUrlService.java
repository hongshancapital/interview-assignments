package services;

/**
 * @Description:
 * @Params
 * @Return
 * @Author Jun.Wong
 * @Date 2021/10/13 10:45
 */
public interface IChangeUrlService {
    /**
     * 获取段链接
     * @param key
     * @return
     */
    public  String getValue(String key);

    /**
     * 设置到缓存
     * @param url
     * @return
     */
    public  String put(String url);
}
