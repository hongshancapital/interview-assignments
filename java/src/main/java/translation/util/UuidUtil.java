package translation.util;

import java.util.UUID;

/**
 *
 * @author: hello
 * @since: 2023/2/22
 */
public class UuidUtil {
    public static String toUuid(){
        return  UUID.randomUUID().toString().replaceAll("-", "");
    }
}