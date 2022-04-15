package com.sequoia.urllink.service.load;

import java.util.List;
import java.util.Map;

/**
 * @author liuhai
 * @date 2022.4.15
 */
public interface LoadDataSource {

  void batchWrite(List<Map<String, Object>> dataList);
}
