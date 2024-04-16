package com.chencaijie.domainservice.service.impl;

import com.chencaijie.domainservice.bean.DomainStorage;
import com.chencaijie.domainservice.bean.ResultObject;
import com.chencaijie.domainservice.service.DomainService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Random;

/**
 *
 */
@Component
public class DomainServiceImpl implements DomainService {


    @Override
    public ResultObject getLongDomainName(String shortDomain) {
        String longDomainName = "";
        try {
            //从内存中读取域名
            longDomainName = DomainStorage.getDomainStorage().getDomainMap().get(shortDomain);
            if (StringUtils.isEmpty(longDomainName)) {
                return new ResultObject(ResultObject.FALSE, "", "未找到对应的域名");
            }
            return new ResultObject(ResultObject.SUCCESS, longDomainName, "");
        } catch (Exception e) {
            return new ResultObject(ResultObject.FALSE, "", e.toString());
        }
    }

    @Override
    public ResultObject saveDomainName(String domainName) {
        String shortDomainName = "";
        try {
            //先遍历存储map中是否已经存在当前要要存储的长域名
            for(Map.Entry<String,String> entry:DomainStorage.getDomainStorage().getDomainMap().entrySet())
            {
                if(entry.getValue().equals(domainName))
                {
                    return new ResultObject(ResultObject.SUCCESS, entry.getKey(), "");
                }
            }
            long freeMemory = Runtime.getRuntime().freeMemory();
            long totalMemory = Runtime.getRuntime().totalMemory();
            System.out.println("剩余内存：" + freeMemory / (1024 * 1024) + "Mb");
            System.out.println("总内存：" + totalMemory / (1024 * 1024) + "Mb");
            //当jvm内存使用超过90%，防止内存溢出不再进行存储
            if ((float) freeMemory / (float) totalMemory < 0.1) {
                System.out.println("剩余内存比例：" + (float) freeMemory / (float) totalMemory);
                return new ResultObject(ResultObject.FALSE, "", "当前内存占用过高，不再存储域名");
            }
            //将产生4组6位字符串
            String[] aResult = shortUrl(domainName);
            Random random = new Random();
            //产成4以内随机数
            int j = random.nextInt(aResult.length);
            //随机取一个作为短链
            shortDomainName = aResult[j];
            //把域名映射保存在内存中
            DomainStorage.getDomainStorage().getDomainMap().put(shortDomainName, domainName);
            return new ResultObject(ResultObject.SUCCESS, shortDomainName, "");

        } catch (Exception e) {
            return new ResultObject(ResultObject.FALSE, "", e.toString());

        }

    }


    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            System.out.println("MD5加密出现错误");
            return "";
        }
    }

    /**
     * 生成短链接
     *
     * @param url 长链接
     * @return 四个短地址数组，取任意一个即可
     */
    public static String[] shortUrl(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "ccj0503";

        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };

        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = getMD5(key + url);

        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];

        //生成4个短地址，8位一组
        for (int i = 0; i < 4; i++) {

            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);

            // 这里需要使用 long 型来转换，因为 Inteper.parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
            // long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);

            String outChars = "";

            //生成6次-6位短地址
            for (int j = 0; j < 6; j++) {

                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;

                // 把取得的字符相加
                outChars += chars[(int) index];

                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;

            }

            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;

        }

        return resUrl;
    }

}
