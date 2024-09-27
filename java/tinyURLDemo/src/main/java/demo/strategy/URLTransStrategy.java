package demo.strategy;

public interface URLTransStrategy {
    /**
     * id 转短域名
     * @param id urlEntity 主键
     * @return 短域名
     */
    String encode(Integer id);

    /**
     * 短域名转 id
     * @param curURL 短域名
     * @return urlEntity 主键
     */
    // 暂未使用，保证覆盖率先注释掉
//    Integer decode(String curURL);
}
