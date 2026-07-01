package boot.spring.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CodeUtil {  
	
	public static Long getCode() {
		StringBuilder sb = new StringBuilder();
    	String pattern = "mmssms";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        sb.append(simpleDateFormat.format(new Date()));
        Integer i = new Random().nextInt(100000);
        sb.append(i);
		return Long.valueOf(sb.toString());
	}

}