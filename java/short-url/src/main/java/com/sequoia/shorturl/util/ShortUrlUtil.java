package com.sequoia.shorturl.util;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/***
 *
 *@
 *短8位UUID思想其实借鉴微博短域名的生成方式，但是其重复概率过高，而且每次生成4个，需要随即选取一个。
 *
 * 本算法利用62个可打印字符，通过随机生成32位UUID，由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，
 * 然后通过模62操作，结果作为索引取出字符，
 * 这样重复率大大降低 ,性能可以做出批量生成方式
 * @Author xj
 *
 * @Date 2021/06/27
 *
 * @version v1.0.0
 *
 */
public class ShortUrlUtil {

    public static List<String> list=new ArrayList<>();

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };


    public static String generateShortUuid() {
	    StringBuffer shortBuffer = new StringBuffer();
	    String uuid = UUID.randomUUID().toString().replace("-", "");
	    for (int i = 0; i < 8; i++) {
		   String str = uuid.substring(i * 4, i * 4 + 4);
		   int x = Integer.parseInt(str, 16);
		   shortBuffer.append(chars[x % 0x3E]);
	    }
	    return shortBuffer.toString();
    }

    public static synchronized List<String> getBatchShortUuidList(){
    	   if(list==null||list.size()==0){
			list=new ArrayList<>();
    	   	for(int i=0;i<1000;i++){
				list.add(generateShortUuid());
			}
		   }
    	   return list;
	}
    /** 测试 */
    /**
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println( " 8位短网址 " + generateShortUuid());
        }
    }*/

}
