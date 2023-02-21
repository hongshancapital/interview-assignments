package cn.sequoiacap.shorturl.service;

interface ShortUrlGenerator {
    /**
     * 具体如何根据长链接生成短链接 id
     * <br>
     * 可以继承该类实现不同的生成策略
     *
     * @param originalUrl 长链接
     * @param random      随机数, 用来解决生成的 id 冲突的情况, 冲突后可传入不同的值继续生成
     * @return 短链接 id
     */
    String generate(String originalUrl, int random);
}
