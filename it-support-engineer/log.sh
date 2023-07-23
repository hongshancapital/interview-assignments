#!/bin/bash
#处理日志文件中异常格式
gzcat interview_data_set.gz | sed -e '1h;2,$H;$!d;g;s/\n\t/ /g' > TMP1
#替换日志中repeated字符
cat TMP1 | grep -B 1 'last message repeated 1 time' | grep -v 'last message repeated 1 time' | grep -v '\-\-' >> TMP1
#将repeated日志写到日志文件
cat TMP1 | grep -v 'last message repeated 1 time' > TMP2
#awk处理新日志文件生成json格式
cat TMP2 | awk -f process.awk > log.json
#上传
curl -X POST -H "Content-Type: application/json" -d @log.json https://foo.com/bar
