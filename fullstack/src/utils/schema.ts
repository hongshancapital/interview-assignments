export const T_SHOTURL = `CREATE TABLE IF NOT EXISTS \`t_shorturl\` (
  \`id\` bigint(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '记录id',
  \`url_short\` varchar(8) NOT NULL DEFAULT '' COMMENT '短网址',
  \`url_long\` varchar(240) NOT NULL DEFAULT '' COMMENT '长网址',
  \`create_time\` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据库创建时间',
  PRIMARY KEY (\`id\`),
  UNIQUE KEY \`idx_url_short\` (\`url_short\`)
  UNIQUE KEY \`idx_url_long\` (\`url_long\`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短网址记录表';`
