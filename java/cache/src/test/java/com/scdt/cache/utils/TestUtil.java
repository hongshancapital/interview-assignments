package com.scdt.cache.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class TestUtil {

    public final static String TEST_DIR = "/temp/cache/";

    public final static int DEFAULT_STORAGE_BLOCK_SIZE = 16 * 1024 * 1024;

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static Random rnd = new Random();

    private static final NumberFormat MEM_FMT = new DecimalFormat("##,###.##");

    public static String randomString(int len )
    {
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

}