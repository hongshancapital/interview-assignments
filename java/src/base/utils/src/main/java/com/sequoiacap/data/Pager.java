package com.sequoiacap.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Pager<T>
	implements Serializable
{
	private static final long serialVersionUID = -5721800111931095465L;

	/**
	 * 状态值
	 */
	private int status = 200;

	/**
	 * 错误信息
	 */
	private String message;

	/**
	 * 页码
	 */
	private int page = 1;
	
	/**
	 * 每页数量
	 */
	private int size = 10;

	/**
	 * 数据
	 */
	private List<T> result;

	/**
	 * 总共页数
	 */
	private int totalPages;
	
	/**
	 * 总共数据的数量
	 */
	private int totalElements;

	/**
	 * 是否为首页
	 */
	private Boolean firstPage;
	
	/**
	 * 是否为末页
	 */
	private Boolean lastPage;

	/**
	 * 排序方向，应为ASC或DESC。本字段可选
	 */
	private String direction;
	
	/**
	 * 排序字段
	 */
	private String sort;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPage()
	{
		return page;
	}
	
	public void setPage(int page)
	{
		this.page = page;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void setSize(int size)
	{
		this.size = size;
	}
	
	public List<T> getResult()
	{
		return result;
	}
	
	public void setResult(List<T> content)
	{
		this.result = content;
	}
	
	public int getTotalPages()
	{
		return totalPages;
	}
	
	public void setTotalPages(int totalPages)
	{
		this.totalPages = totalPages;
	}
	
	public int getTotalElements()
	{
		return totalElements;
	}
	
	public void setTotalElements(int totalElements)
	{
		this.totalElements = totalElements;
	}
	
	public Boolean getFirstPage()
	{
		return firstPage;
	}
	
	public void setFirstPage(Boolean firstPage)
	{
		this.firstPage = firstPage;
	}
	
	public Boolean getLastPage()
	{
		return lastPage;
	}
	
	public void setLastPage(Boolean lastPage)
	{
		this.lastPage = lastPage;
	}
	
	public String getDirection()
	{
		return direction;
	}
	
	public void setDirection(String direction)
	{
		this.direction = direction;
	}

	public String getSort()
	{
		return sort;
	}

	public void setSort(String sort)
	{
		this.sort = sort;
	}

	public static <T> Pager<T> valueOf(Pageable page)
	{
		Pager<T> pager = new Pager<T>();

		pager.setPage(page.getPageNumber());
		pager.setSize(page.getPageSize());

		Sort sort = page.getSort();
		if (sort != null)
		{
			for(Sort.Order order: sort)
			{
				pager.setSort(order.getProperty());
				pager.setDirection(order.getDirection().toString());
			}
		}
		
		return pager;
	}

	public static <T> Pager<T> valueOf(Page<T> page)
	{
		Pager<T> pager = new Pager<T>();

		pager.setPage(page.getNumber() + 1);
		pager.setSize(page.getSize());
		
		pager.setTotalElements((int)page.getTotalElements());
		pager.setTotalPages(page.getTotalPages());

		pager.setFirstPage(page.isFirst());
		pager.setLastPage(page.isLast());

		Sort sort = page.getSort();
		if (sort != null)
		{
			for(Sort.Order order: sort)
			{
				pager.setSort(order.getProperty());
				pager.setDirection(order.getDirection().toString());
			}
		}

		pager.setResult(page.getContent());

		return pager;
	}

	public Pageable toPageable()
	{
		if (this.sort != null)
		{
			return PageRequest.of(this.page - 1, this.size,
				Sort.Direction.valueOf(this.direction), this.sort);
		}

		return PageRequest.of(this.page - 1, this.size);
	}

	public Pageable toPageable(Sort sort)
	{
		if (this.sort != null)
		{
			return PageRequest.of(this.page - 1, this.size,
				Sort.Direction.valueOf(this.direction), this.sort);
		}

		return PageRequest.of(this.page - 1, this.size, sort);
	}
	
	/**
	 * 增加默认排序的Pageable转换
	 * 
	 * @param direction 默认的排序方向
	 * @param name 黰认的排序字段
	 * 
	 * @return Pageable请求
	 */
	public Pageable toPageable(Sort.Direction direction, String name)
	{
		if (this.sort != null)
		{
			return PageRequest.of(this.page - 1, this.size,
				Sort.Direction.valueOf(this.direction), this.sort);
		}

		return PageRequest.of(this.page - 1, this.size, direction, name);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    public int getStartNum()
	{
		Integer[] num = pageNum(page, totalPages);
		return num[0];
	}

	public int getEndNum()
	{
		Integer[] num = pageNum(page, totalPages);
		return num[1];
	}

    public static Integer[] pageNum(int pageNum, int pageCount)
	{
		int startNum = 0;
		int endNum = 0;
		int sideNum = 4;// 分页系数 分页条中显示几个数字页码。 显示数字页码个数 = 2 * sideNum + 1

		if (pageCount <= sideNum)
		{
			endNum = pageCount;
		} else
		{
			if ((sideNum + pageNum) >= pageCount)
			{
				endNum = pageCount;
			} else
			{
				endNum = sideNum + pageNum;
				if ((sideNum + pageNum) <= (2 * sideNum + 1))
				{
					if ((2 * sideNum + 1) >= pageCount) {
						endNum = pageCount;
					} else {
						endNum = 2 * sideNum + 1;
					}
				} else
				{
					endNum = sideNum + pageNum;
				}
			}
		}

		if (pageNum <= sideNum)
		{
			startNum = 1;
		} else
		{
			if ((pageNum + sideNum) >= pageCount)
			{
				if ((2 * sideNum + 1) >= pageCount)
				{
					if ((pageCount - 2 * sideNum) >= 1)
					{
						startNum = pageCount - 2 * sideNum;
					} else
					{
						startNum = 1;
					}
				} else
				{
					startNum = pageCount - 2 * sideNum;
				}
			} else
			{
				if ((pageNum - sideNum) >= 1)
				{
					startNum = pageNum - sideNum;
				} else
				{
					startNum = 1;
				}
			}
		}

		Integer[] page = new Integer[2];

		page[0] = startNum;
		page[1] = Math.max(startNum, endNum);

		return page;
	}
	public String toSql()
    {
		return toSql(toPageable());
    }

    public static String toSql(Pageable page)
    {
    	StringBuilder sql = new StringBuilder();

    	Sort sort = page.getSort();

     	if (sort.isSorted())
     	{
     		sql.append("ORDER BY ");

     		Iterator<Sort.Order> it = sort.iterator();
      
		    while(it.hasNext())
		    {
		       Sort.Order order = it.next();
		
		       sql.append(order.getProperty());
		       
		       if (order.getDirection() != null)
		       {
		        sql.append(" ").append(order.getDirection());
		       }
		       
		       if (it.hasNext())
		        sql.append(", ");
		   }
     	}

     	sql.append(" LIMIT ").append(page.getPageSize())
     		.append(" OFFSET ").append(page.getOffset());

     return sql.toString();
    }
}
