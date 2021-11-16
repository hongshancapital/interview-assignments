package pers.jenche.convertdomain.component;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import pers.jenche.convertdomain.utilitie.SHAEncryptUtil;

/**
 * Created with IntelliJ IDEA.
 *
 * @author jenche E-mail:jenchecn@outlook.com
 * @project convertdomain
 * @date 2021/11/15 14:57
 * @description 转换扩展组件，在组件各方法中完成转换。
 */
@Component
public class ConvertComponent {

    //定义一组缩短可用的数组
    final private String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    /**
     * 将一个字符串进行计算后输出成少于8位的字符串
     * 保证转换后的字符串没有可逆性而且需要有极低的碰撞性
     *
     * @param str{@link String}待转换的字符串
     * @param intShortLength    {@link int} 需要的编码字符串长度
     * @return {@link Pair}Pair是apache公司lang3中的一个数据类型具体使用方法请查看{@link org.apache.commons.lang3.tuple.Pair}
     */
    public String getShort(String str, int intShortLength) {
        //设定盐，可自行设定也可以不设定，如果不设定设置空
        String salt = "jenche";

        //得到当前字符串哈希值
        String strHashCode = SHAEncryptUtil.getSHA256Str(salt.concat(str).concat(String.valueOf(str.length())));

        //缩短处理
        return this.shortenHandle(strHashCode).substring(0, intShortLength);
    }

    /**
     * 使用SHA256加密后的字符串
     *
     * @param strHashCode {@link String} SHA256字符串
     * @return {@link String} 缩短后的字符串
     */
    private String shortenHandle(String strHashCode) {
        //定义输出数据
        String outData = "";

        for (int i = 0; i < (strHashCode.length() / 8); i++) {
            //取样1，取出前8位为一个16进制数据
            String strSpecimen1 = strHashCode.substring(i * 8, i * 8 + 8);
            outData = outData.concat(this.hexShortHandle(strSpecimen1));
        }

        return outData;
    }

    /**
     * 通过Hex计算出对应的字符
     *
     * @param strSpecimen {@link String} 计算的样本
     * @return {@link String} 输出一组计算的数据
     */
    private String hexShortHandle(String strSpecimen) {
        String outData = "";
        //取得Long数据结果
        long longSpecimen = 0x3FFFFFFF & Long.parseLong(strSpecimen, 16);

        for (int i = 0; i < 6; i++) {
            outData = outData.concat(chars[(int) (0x3D & longSpecimen)]);
            longSpecimen >>= 5;
        }
        return outData;
    }
}
