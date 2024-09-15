#!/bin/bash

#正则表达式 (时间) (设备) (进程名称) (进程ID) (日志信息)
regex="(\S+\s+\S+\s+\S+)\s(\S+)\s([^[]+?)\[(\S+)](.+)"
#定义一些集合存储数据
declare -A deviceSet processSet pidSet tEr tW
#定义一个字符串后面用于存储json结构 array[dict]
description="["

function split(){
if [[ $1 =~ $regex ]]; then
  time="${BASH_REMATCH[1]}"
  device="${BASH_REMATCH[2]}"
  process="${BASH_REMATCH[3]}"
  pid="${BASH_REMATCH[4]}"
  message="${BASH_REMATCH[5]}"
else
  echo "$1 正则未能匹配日志"
fi
}

function jsonTrans(){
    deviceName="["
    #集合转换为json[string] 集合中有多个元素
        for d in "${!deviceSet[@]}"; do
           deviceName+=\"${d}\"\,
        done
    deviceName="${deviceName%?}"\]

    processName="["
    #集合转换为json[string] 集合中有多个元素
        for pName in "${!processSet[@]}"; do
           processName+=\"${pName}\"\,
        done
    processName="${processName%?}"\]

    timeWindow="["
    #集合转换为json[string] 集合中有多个元素
        for t in "${!tEr[@]}"; do
           timeWindow+=\"${t}\"\,
        done
    timeWindow="${timeWindow%?}"\]

    processId=$(IFS=,; echo "[${!pidSet[*]}]")
    #删除掉[{},]最后一个字符串里面的,转换为[{}]
    description="${description%?}"\]
    #shell [{k:v}] 转换为json
    nO="["
    for key in "${!tEr[@]}"; do
        value="${tEr[$key]}"
        nO+="{\"date\":\"$key\",\"errorCount\":$value},"
        done
        numberOfOccurrence="${nO%?}"]
    #生成http requset body
    requestBody="{\"deviceName\":$deviceName,\"processId\":$processId,\"processName\":$processName,\"description\":$description,\"timeWindow\":$timeWindow,\"numberOfOccurrence\":$numberOfOccurrence}"

    curl -m 5 'https://foo.com/bar' \
    -H "Content-Type:application/json" \
    -X POST \
    -d "$requestBody"
}

function main(){
while IFS= read -r line; do
   errorLog=`echo "$line" | grep error`
   if [[ -n "$errorLog" ]]; then
      split "$line"
      deviceSet[$device]=1
      processSet[$process]=1
      pidSet[$pid]=1
      hour=$(date -d "$time" "+%m%d%H")
      if [[ -n tEr[$hour] ]]; then
         let tEr[$hour]++
      else
         tEr[$hour]=1
  fi
      des="{\"pid\":$pid,\"process\":\"$process\",\"errorLog\":\"$message\"},"
      description+=$des
   fi
done < interview_data_set
   jsonTrans
}
main;
