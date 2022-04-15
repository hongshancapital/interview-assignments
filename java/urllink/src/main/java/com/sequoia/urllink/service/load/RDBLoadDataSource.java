package com.sequoia.urllink.service.load;

import com.sequoia.urllink.bean.TableMetaPojo;
import com.sequoia.urllink.constant.Global;
import com.google.common.collect.Lists;
import com.sequoia.urllink.util.ApplicationContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author liuhai
 * @date 2022.4.15
 */
public class RDBLoadDataSource implements LoadDataSource {
  private int count;
  private JdbcTemplate jdbcTemplate;
  private String sql;
  private List<TableMetaPojo> tableMetaPojos;

  public RDBLoadDataSource(List<TableMetaPojo> tableMetaPojos) {
    this.tableMetaPojos = tableMetaPojos;
    this.jdbcTemplate = (JdbcTemplate) ApplicationContextUtil.getBean(JdbcTemplate.class);
    insertDataSql();
  }

  @Override
  public void batchWrite(List<Map<String, Object>> dataList) {
    if (StringUtils.isNotBlank(sql)) {
      List<Object[]> paramList = Lists.newArrayListWithCapacity(dataList.size());
      Object[] param = null;
      TableMetaPojo pojo = null;
      int index = 0;

      for (Map<String, Object> data : dataList) {
        param = new Object[count];
        paramList.add(param);

        for (int i = 1, size = tableMetaPojos.size(); i < size; ++i) {
          pojo = tableMetaPojos.get(i);
          if (pojo.getType() == Global.ODPS_TYPE_COL) {
            param[index] = data.get(pojo.getName());
            ++index;
          }
        }

      }

      jdbcTemplate.batchUpdate(sql, paramList);
    }
  }

  private void insertDataSql() {
    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder.append(" INSERT INTO ").append(tableMetaPojos.get(0).getName());
    sqlBuilder.append(" ( ");
    TableMetaPojo pojo = null;

    for (int i = 1, size = tableMetaPojos.size(); i < size; ++i) {
      pojo = tableMetaPojos.get(i);
      if (pojo.getType() == Global.ODPS_TYPE_COL) {
        ++count;
        sqlBuilder.append(pojo.getName());
      }
    }

    sqlBuilder.append(" ) values (  ").append(StringUtils.repeat("?,", count - 1));
    sqlBuilder.append("? ) ");

    if (count < 1) {
      sqlBuilder.setLength(0);
    }

    sql = sqlBuilder.toString();
  }

}
