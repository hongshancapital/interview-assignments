#!/bin/bash
#定义error关键字变量
strA="error"

#循环读取行
while read -r line
do
	#获取错误描述
	error_msg=$(echo $line | awk '{ for(i=1; i<=6; i++){ $i="" }; print $0 }')
	#筛选不包含error行
	result=$(echo $error_msg | grep "${strA}")
  	if [[ "$result" != '' ]]
  	then
		#获取日志时间
		time=$(echo $line | awk '{print ($1" "$2" " $3)}')
		#获取设备名称
		dev_name=$(echo $line | awk '{print ($4)}')
		#获取进程名称
		process_name=$(echo $line | awk '{print ($5,$6)}')
		#获取进程ID
		process_id=$(echo $process_name | awk '{print ($2)}' | tr -cd "[0-9]")
		#统计当前进程报错数量
		Occour_msg=$(grep -rn -s $error_msg interview_data_set | wc -l)
		#格式为json格式
		summary_msg=$( echo {\"timeWindow\":\"$time\"','"\"deviceName\":\"$dev_name\",\"processName\":\"$process_name\",\"processId\":\"$process_id\",\"description\":\"$error_msg\",\"numberOfOccurrence\":\"$Occour_msg\""})
		echo $summary_msg
	
	fi
        #POST上传至服务器
        #curl -k -X POST -H "'Content-type':'application/json'" -d $summary_msg https://foo.com/bar
done < interview_data_set

