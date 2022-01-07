package org.domain.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.domain.domain.Link;

public interface LinkMapper {
    @Select("SELECT * FROM link where id = #{id}")
    Link selectLinkById(@Param("id") int id);

    @Select("SELECT * FROM link where url = #{url}")
    Link selectLinkByUrl(@Param("url") String url);

    @Insert("insert into link (#{url})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLink(Link link);
}
