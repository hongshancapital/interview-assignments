package ligq.url;

import java.time.ZoneOffset;

/**
 * 常量类
 * @author ligq
 * @since 2021-10-19
 */
public interface Constants {
    /** url存储时，创建时间秒数 与 url值之间的分隔符 */
    String valSeparator = "|";
    String valSeparatorRegex = "\\|";

    /** 时区为东八区 */
    ZoneOffset zone = ZoneOffset.of("+8");
}
