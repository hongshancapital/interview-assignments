#!/bin/bash
# 作者：赵晓波 <qq：2829376494> <email：2829376494@qq.com>

# 日期：2021/12/03
# 分析系统日志得到关键信息，用 Json 的格式 POST 上传至服务器 ( https://foo.com/bar )，key 的名称在括号里
#设备名称: (deviceName)
#错误的进程号码: (processId)
#进程/服务名称: (processName)
#错误的原因（描述）(description)
#发生的时间（小时级），例如 0100-0200，0300-0400, (timeWindow)
#在小时级别内发生的次数 (numberOfOccurrence)
#
# 适用：MAC OS
# 语言：中文
#
# 注意：日志文件需要放在与脚本同一目录下

datasource=interview_data_set
#数据清洗后的临时文件,运行结束需要删除
echo "">json.tmp
#最终生成的json文件
echo "">outjson.json
#开始清洗数据,转换成可批处理的格式
cat $datasource | grep error |sed 's/):/@/g'|while read line;
do
tempchar=$(echo $line|cut -d'@' -f1);
beizhu=$(echo $line | cut -d'@' -f2);
Htime=$(echo $tempchar|cut -d' ' -f3|cut -d':' -f1);
Hname=$(echo $tempchar|cut -d' ' -f4);
PandN=$(echo $tempchar|cut -d' ' -f6|awk -F'(' '{print $2}'|sed 's/\[/./g'|sed 's/\]//g');
Pnumber=$(echo ${PandN:0-5})
Pname=$(echo ${PandN%.*})
echo $Htime'@'$Hname'@'$Pnumber'@'$Pname'@'$beizhu >>json.tmp
done;
#数据清洗结束
#提取数据
purgedata=json.tmp
num=1
cat json.tmp|while read pdline;
do
    pd=$(echo $pdline)
    Atimed=$(echo $pd|cut -d'@' -f1)
    deviceName=$(echo $pd|cut -d'@' -f2)
    processId=$(echo $pd|cut -d'@' -f3)
    processName=$(echo $pd|cut -d'@' -f4)
    description=$(echo $pd|cut -d'@' -f5)
    timeWindowtmp=$(cat json.tmp|grep $processName|sort -t $'@' -k 1,1 -su|cut -d'@' -f1)
    #计算timeWindow、numberOfOccurrence数据
    for i in $(echo $timeWindowtmp)
    do
    timeWindow=$(echo $i 00-$(printf "%02d" $(expr $i + $num ))00|awk '{print $1$2}')
    numberOfOccurrence=$(cat json.tmp|grep $processName|grep $i|sort -t $'@' -k 1,1 -su|wc -l)

    #输出json格式数据到outjson.json
    echo -e "\n\t{" >>outjson.json
        echo -e "\t\t\"deviceName\": \"${deviceName}\"," >>outjson.json
        echo -e "\t\t\"processId\": \"${processId}\"," >>outjson.json
        echo -e "\t\t\"processName\":\"${processName}\"," >>outjson.json
        echo -e "\t\t\"description\":\"${description}\"," >>outjson.json
        echo -e "\t\t\"timeWindow\":\"${timeWindow}\"," >>outjson.json
        echo -e "\t\t\"numberOfOccurrence\":\"${numberOfOccurrence}\"" >>outjson.json
    echo -e "\n\t}" >>outjson.json
    #POST 上传至服务器 ( https://foo.com/bar )
    post_json=$(echo -e "\n\t{"
        echo -e "\t\t\"deviceName\": \"${deviceName}\","
        echo -e "\t\t\"processId\": \"${processId}\","
        echo -e "\t\t\"processName\":\"${processName}\","
        echo -e "\t\t\"description\":\"${description}\","
        echo -e "\t\t\"timeWindow\":\"${timeWindow}\","
        echo -e "\t\t\"numberOfOccurrence\":\"${numberOfOccurrence}\""
    echo -e "\n\t}" )

    post_jsontest=$(curl -i -X POST -H "'Content-type':'application/json'" -d '$post_json'  https://foo.com/bar)
    echo $post_jsontest

    done;
done;
#清理临时文件
`rm -rf json.tmp`

