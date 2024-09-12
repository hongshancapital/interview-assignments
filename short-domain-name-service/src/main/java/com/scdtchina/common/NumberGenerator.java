package com.scdtchina.common;

/**
 * @author hai.yuan
 * 号码生成器
 */
public class NumberGenerator {

    private static volatile NumberGenerator numberGenerator = null;

    private final static String NUM62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private NumberGenerator(){}


    public static  NumberGenerator getInstance(){
        if (numberGenerator == null){
            synchronized (NumberGenerator.class){
                if(numberGenerator == null){
                    numberGenerator = new NumberGenerator();
                }
            }
        }
        return numberGenerator;
    }

    /**
     * 根据主键key，及进制生成编码
     * @param key 主键
     * @param toRadix 进制数
     * @return 短域名编码
     */
    public String encode(Long key, int toRadix){
        int min_Radix = 2;
        int max_Radix = 62;

        if (toRadix < min_Radix || toRadix > max_Radix) {
            toRadix = 2;
        }

        if (toRadix == 10) {
            return key+"";
        }
        char[] array = new char[62];

        int pos = 61;

        while(key>=toRadix){
            Long i = key%toRadix;
            array[pos] = NUM62.charAt(i.intValue());
            key = key/toRadix;
            pos--;
        }
        array[pos]=NUM62.charAt(key.intValue());

        StringBuffer buffer = new StringBuffer();

        for(int i =62;pos<i;pos++){
            buffer.append(array[pos]);
        }

        return buffer.toString();
    }

    /**
     * 根据编码，进制数解码出主键id
     * @param code 短域名编码
     * @param toRadix 进制数
     * @return 主键
     */
    public Long decode(String code,int toRadix){

        int min_Radix = 2;
        int max_Radix = 62;

        if (toRadix < min_Radix || toRadix > max_Radix) {
            toRadix = 2;
        }


        Double key =0.0;
        int length = code.length();

        char[] b = new char[length];
        code.getChars(0,length,b,0);

        for(int j =length-1,k=0;j>=0;j--,k++){
            char c = b[j];
            key+=(NUM62.indexOf(c)*Math.pow(toRadix,k));
        }

        return key.longValue();

    }

}
