package com.asan.shorturlserver.utils;

public class ShortUrlUtil {

    //62位字符库，顺序已打乱
    private static final String CHARS = "YLZmtacRiVrB0p4F1wgoSzPUMTNWdnkGyb3D6EC7Jfe9KA2qXlj8uOhvxIHsQ5";
    private static final long SCALE = 62;

    //常用短码10进制起点
    public static final long LENGTH_1_NUM = 0;                 //1位短码区间起始值
    public static final long LENGTH_2_NUM = 62;                //2位短码区间起始值
    public static final long LENGTH_3_NUM = 3844;              //3位短码区间起始值
    public static final long LENGTH_4_NUM = 238328;            //4位短码区间起始值
    public static final long LENGTH_5_NUM = 14776336;          //5位短码区间起始值
    public static final long LENGTH_6_NUM = 916132832;         //6位短码区间起始值



    /**
     * @Description:  10进制-62进制，自定义进制
     *
     * @MethodName   encode62
     * @param number   10进制数字
     * @return      java.lang.String
     */
    public static String encode62(long number) {
        StringBuilder result=new StringBuilder();
        while(number > SCALE - 1) {
            result.append(CHARS.charAt((int) (number % SCALE)));
            number = number / SCALE;
        }
        result.append(CHARS.charAt((int) number));
        return result.reverse().toString();

    }


    public static void main(String[] args) {
        System.out.println(encode62(238328123));
    }

}