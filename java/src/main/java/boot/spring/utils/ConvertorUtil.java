package boot.spring.utils;


/**
 * 62进制和10进制转换工具类
 */
public class ConvertorUtil {
		/**
	     * 十进制转62进制（仅限正整数）
	     * @param num 十进制数字
	     * @return java.lang.String 
	     */
	    public static String encode10To62(long num){
	        if(num <= 0){
	            return "0";
	        }
	        StringBuilder sb = new StringBuilder();
	        //余数
	        long remainder;
	        while (num > 0){
	            remainder = num % 62;
	            //0-9
	            if(remainder < 10){
	                sb.append((char)('0' + remainder));
	            }
	            //A-Z
	            else if(remainder < 36){
	                sb.append((char)('A' + remainder - 10));
	            }
	            //a-z
	            else{
	                sb.append((char)('a' + remainder - 36));
	            }
	            num = num / 62;
	        }
	        return sb.reverse().toString();
	    }

}