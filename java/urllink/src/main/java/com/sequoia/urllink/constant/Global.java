package com.sequoia.urllink.constant;

/**
 * 常量管理
 * @author liuhai
 * @date 2022.4.15
 */
public interface Global {
  String USER_INFO_KEY = "FuLuUser";
  int OBJECT_POOL_MAX_SIZE =1000;
  /**
   * 批量插入数据大小
   */
  int BATCH_INSERT_SIZE = 1000;
  //文本类型的日期
  String DATE_FORMAT_REG = "^\\d{4}\\-\\d{2}\\-\\d{2}.*";
  //数值格式
  String FORMAT_NUMBER_REG = "[\\-\\+]?(?:\\d{1,3},)*\\d{1,3}(?:\\.\\d+)*";
  int HASH_LENGTH = 32;
  String HASH_SALT = "FuLuService+-*/123";
  String INT_FORMAT_NUMBER_REG = "[\\-\\+]?(?:\\d{1,3},)*\\d{1,3}";
  /**
   * 保留的小数点位数
   */
  int NUMBER_FORMAT_SCALE = 2;
  int ODPS_TYPE_COL = 2;//字段
  int ODPS_TYPE_PT = 3;//分区
  String SWAGGER2_BASE_PACKAGE = "com.sequoia.urllink.controller";

  /**
   * 短域名中间路径
   */
  String SHORT_MIDDLE_PATH = "/u";
}
