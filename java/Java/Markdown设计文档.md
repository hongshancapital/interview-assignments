# 获取短链接接口流程
1. 取出参数longLink
2. 判断是否为空:
3. 如果为空,直接返回空
4. 否则: 在内存map中判断是否存在key为longLink的值
5. 如果存在,返回map中key为longLink对应的value
6. 否则: 将longLink生成唯一的hash编码值:long型编码
7. 将long型编码通过base62,通过求余拼接成最终结果字符串String
8. 将String存入map中,并做为接口返回值

# 获取长链接接口流程
1. 取出参数shortLink
2. 判断是否为空:
3. 如果为空,直接返回空
4. 否则: 在内存map中遍历寻找value为shortLink的值
5.  如果存在:直接返回key
6.  否则: 返回空

