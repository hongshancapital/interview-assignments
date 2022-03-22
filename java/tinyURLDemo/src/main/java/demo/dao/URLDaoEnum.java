package demo.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum URLDaoEnum {
    CACHE("URLCacheDao"), REDIS("URLRedisDao"), MYSQL("URLMysqlDao");

    private final String name;

    // 暂未使用，保证覆盖率先注释掉
//    public static URLDaoEnum getByName(String name) {
//        for (URLDaoEnum e : URLDaoEnum.values()) {
//            if (name.equals(e.getName())) {
//                return e;
//            }
//        }
//        return null;
//    }
}
