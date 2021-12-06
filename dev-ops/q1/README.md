# Assignment Q1

思路：

1. 利用关键字 `error`, `failed` 过滤出所有错误信息， 注意不区分大小写。
2. 遍历所有错误信息， 转换为格式化数据(dict 格式)， 此时不带 `numberOfOccurrence` 信息
3. 遍历所有错误信息的格式化数据的 list, 利用一个临时 dict 来去重， 计算 `numberOfOccurrence`
4. 发送结果
