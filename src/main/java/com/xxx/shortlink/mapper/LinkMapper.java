package com.xxx.shortlink.mapper;

import com.xxx.shortlink.entity.Link;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface LinkMapper {
    public static final String BASE_COLUMN = "id, short_link, original_link, create_time, update_time";

    @Select({
            "select",
            BASE_COLUMN,
            "from link",
            "where short_link=#{shortLink}",
    })
    public Link selectByShortLink(String shortLink);

    @Insert({
            "insert into link(short_link, original_link)",
            "values(#{shortLink}, #{originalLink})",
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int insert(String shortLink, String originalLink);
}
