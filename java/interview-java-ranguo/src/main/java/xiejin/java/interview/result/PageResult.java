package xiejin.java.interview.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @Author xiejin
 * @Date 2020/4/27
 * @Description 返回状态码类
 * @since v1.0.0
 */
@Data
public class PageResult<T> implements Serializable {

    private String aaaa;
	private static final long serialVersionUID = 432391136996810544L;
	
	/**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    private long total = 0;
    
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;
    
    /**
     * 当前分页总页数
     */
    public long getPages() {
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }

}
