python版本：3.7.4

思路
1. 逐行遍历日志（需要考虑多行日志处理）
2. 从每行日志中获取目标内容（`deviceName`、`processId`、`processName`、`description`、`timeWindow`），以目标内容的hash作为key存入`Map<String, Map<String, String>>`
3. 每次存入map时，判断 "描述+时间区间" 的hash是否已经存在，不存在则添加到map中，存在则将对应value中的 "`numberOfOccurrence`" +1
4. 遍历完成后将所有的value存入一个list，转换成json
5. 发送post请求

备注：之前主要使用的语言是java，python用的比较少，写法上可能不太简洁，请见谅。