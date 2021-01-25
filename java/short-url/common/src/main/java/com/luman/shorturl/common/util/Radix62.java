package com.luman.shorturl.common.util;

import com.google.common.collect.ImmutableBiMap;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

public class Radix62 {
    private static final BigInteger RADIX62=new BigInteger("62");
    private static final BigInteger MAX_RADIX62= new BigInteger("61");
    private static final ImmutableBiMap<Integer,Character> INT_ALPHABET;
    private static final ImmutableBiMap<Character,Integer> ALPHABET_INT;
    static{
        String radix64="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        ImmutableBiMap.Builder<Integer,Character> builder = ImmutableBiMap.builder();
        for(int i=0,l=radix64.length();i<l;i++){
            builder.put(i,radix64.charAt(i));
        }
        INT_ALPHABET=builder.build();
        ALPHABET_INT=INT_ALPHABET.inverse();
    }
    public static String to62Radix(int value){
        return to62Radix((long)value);
    }
    public static int toInt(String value){
        return (int)toLong(value);
    }
    public static String to62Radix(long value) {
        if(value < 1)
            throw new IllegalArgumentException("num must be greater than 0.");
        StringBuilder sb = new StringBuilder();
        int remainder;
        do {
            remainder = (int)(value % 62);
            char c=INT_ALPHABET.get(remainder);
            sb.append(c);
            value = value / 62;
        } while (value > 61);
        sb.append(INT_ALPHABET.get((int)value));
        return sb.reverse().toString();
    }

    public static long toLong(String value) {
        if(value==null || value.trim().length() == 0 ){
            throw new IllegalArgumentException("str must not be empty.");
        }

        value = Pattern.compile("^0+").matcher(value).replaceAll("");
        long num = 0;
        for(int i = 0; i < value.length(); i++){
            int index = ALPHABET_INT.get(value.charAt(i));
            num += index * ((long)(Math.pow(62, value.length() - i - 1)));
        }
        return num;
    }

    public static String to62Radix(BigInteger value){
        if(value.compareTo(BigInteger.ONE) < 0)
            throw new IllegalArgumentException("num must be greater than 0.");
        StringBuilder sb = new StringBuilder();
        int remainder;
        do {
            remainder = value.remainder(RADIX62).intValue();
            char c=INT_ALPHABET.get(remainder);
            sb.append(c);
            value = value.divide(RADIX62);
        } while (value.compareTo(MAX_RADIX62)>0);
        sb.append(INT_ALPHABET.get(value.intValue()));
        return sb.reverse().toString();
    }
    public static BigInteger toBigInteger(String value){
        if(value==null || value.trim().length() == 0 ){
            throw new IllegalArgumentException("str must not be empty.");
        }
        value =Pattern.compile("^0+").matcher(value).replaceAll("");
        BigInteger num = BigInteger.ZERO;
        for(int i = 0; i < value.length(); i++){
            int index = ALPHABET_INT.get(value.charAt(i));
            num = num.add(new BigInteger(Long.toString(index * ((long)(Math.pow(62, value.length() - i - 1))))));
        }
        return num;
    }
    public static String to62Radix(byte[] value){
        return to62Radix(new BigInteger(1,value));
    }
    public static byte[] toBytes(String value){
        return toBigInteger(value).toByteArray();
    }
    public static String to62Radix(float value){
        return to62Radix(Float.floatToIntBits(value));
    }
    public static float toFloat(String value){
        return Float.intBitsToFloat(toInt(value));
    }
    public static String to62Radix(double value){
        return to62Radix(Double.doubleToLongBits(value));
    }
    public static double toDouble(String value){
        return Double.longBitsToDouble(toLong(value));
    }
    public static String to62Radix(BigDecimal value){
        return to62Radix(value.toBigInteger());
    }
}