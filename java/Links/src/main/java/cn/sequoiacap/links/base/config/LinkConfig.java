package cn.sequoiacap.links.base.config;

/**
 * @author : Liushide
 * @date :2022/4/5 17:33
 * @description : 链接通用配置
 */
public final class LinkConfig {

    /**
     * 私有构造方法避免 new 对象
     */
    private LinkConfig() { /* Do nothing */ }

    /**
     * 短号位数，6位短码可以生成 568亿组合，7位短码可以生成 35216亿组合，这里使用6位即可
     */
    public static final int DIGITS = 6;

    /**
     * 短链接前缀
     */
    public static final String SHORT_CODE_DNS = "https://sc.io/";
}
