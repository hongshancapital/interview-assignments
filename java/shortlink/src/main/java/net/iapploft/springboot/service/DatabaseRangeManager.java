package net.iapploft.springboot.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 数据分区 假设按number分区 划分范围如下
 * 第一个DB分区  100000000000~199999999999
 * 第二个DB分区  200000000000~299999999999
 * 第三个DB分区  300000000000~399999999999
 * 第四个DB分区  400000000000~499999999999
 * 第五个DB分区  500000000000~599999999999
 * .....
 */
@Service
public class DatabaseRangeManager {
    /**
     * 模拟注册中心注册所有范围
     */
    private final static ConcurrentHashMap<RangeBigInteger,ConcurrentHashMap<String,String>>  registerServers= new ConcurrentHashMap<RangeBigInteger,ConcurrentHashMap<String,String>>();
    static {
        registerServers.put(new RangeBigInteger(new BigInteger("100000000000"),new BigInteger("199999999999")),new ConcurrentHashMap<>());
    }


    /**
     * 初始化模拟数据
     */
    @PostConstruct
    public void init() {
        getAbleDatabaseByCode(new BigInteger("100111000001")).putIfAbsent("01Lh5ykp","https://iapploft.net/");
    }
    /**
     * 根据code获取可用DB
     * @param code
     * @return
     */
    public ConcurrentHashMap<String, String> getAbleDatabaseByCode(BigInteger code) {

        RangeBigInteger lastRange = getRangeWithCode(code);
        if ( lastRange == null ) {
            // 没有找到则模拟自动顺序扩充分区
            RangeBigInteger last = registerServers.keySet().stream().skip(registerServers.keySet().size()-1).findFirst().orElse(new RangeBigInteger(new BigInteger("100000000000"),new BigInteger("199999999999")));
            registerServers.put(new RangeBigInteger(last.min.add(new BigInteger("100000000000")),last.max.add(new BigInteger("100000000000"))),new ConcurrentHashMap<>());
            lastRange = getRangeWithCode(code);
        }
        return registerServers.get(lastRange);
    }

    /**
     * 根据code获取分区范围
      * @param code
     * @return
     */
    private RangeBigInteger getRangeWithCode(BigInteger code){
        for (RangeBigInteger rangeBigInteger : registerServers.keySet() ) {
            if ( code.compareTo(rangeBigInteger.getMin()) != -1 && code.compareTo(rangeBigInteger.getMax()) != 1 ) {
                return rangeBigInteger;
            }
        }
        return null;
    }

    @Getter
    @AllArgsConstructor
    static class RangeBigInteger {
        BigInteger min;
        BigInteger max;
    }

//    public static void main(String[] args) {
//        registerServers.put(new RangeBigInteger(new BigInteger("200000000000"),new BigInteger("299999999999")),new ConcurrentHashMap<>());
//        for (RangeBigInteger rangeBigInteger : registerServers.keySet() ) {
//          System.out.println(rangeBigInteger.min);
//        }
//        String ids = "";
//        List<String> stringList = Arrays.asList( ids.split(",") );
//        System.out.println(stringList);
//    }
}
