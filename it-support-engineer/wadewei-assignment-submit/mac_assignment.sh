#!/bin/bash

##########################################################################################################################################
###用途：该脚本用于分析interview_data_set日志文件，获取以下信息并用json格式POST上传至服务器https://foo.com/bar,测试用http://httpbin.org/post     ###
###1.设备名称:(deviceName)                                                                                                              ###
###2.错误的进程号码:(processID)                                                                                                          ###
###3.进程/服务名称:(processName)                                                                                                         ###
###4.错误的原因(描述):(description)                                                                                                      ###
###5.发生的时间(小时级):(timeWindow)                                                                                                     ###
###6.小时级timeWindow发生的次数:(numberOfOccurrence)                                                                                     ###
###系统要求:macOS 10.10.x or higher                                                                                                     ###
###用法: [bash] /path/to/mac_assignment.sh /path/to/interview_data_set                                                                 ###
###作者:魏小忠                                                                                                                         ###
#########################################################################################################################################

#需要传递位置参数$1
if [ "$1" == "" ]; then
	echo "Usage: [bash] /path/to/mac_assignment.sh /path/to/interview_data_set"
	exit 1
fi

#获取设备名称:(deviceName) 
deviceName=`awk '{print $4}' $1 | sort | uniq -c | sort -r | head -1 | cut -d" " -f2`

#获取进程/服务名称:(processName) 
processName=`awk '{print $6}' $1 | grep -o "com.apple.[A-Za-z.]*" | sort | uniq -c | sort -r | head -1 | awk '{print $NF}'`

#获取错误的进程号码:(processID)  
processIDList=`grep -o "${processName}\[[0-9]*\]" $1 | grep -o "\[[0-9]*\]" | tr -d "[|]" | uniq`
processIDs=""
for processID in $processIDList
do
	processIDs="${processIDs} ${processID}"
done

#获取错误的原因(描述):(description)
description=`grep "$processName" $1 | awk -F")" '{print $NF}' | sed 's/^.\{2\}//g' | head -1`"  "
description=${description}`grep "$processName" $1 | awk -F")" '{print $NF}' | sed 's/^.\{2\}//g' | head -2 | tail -1`

#发生的时间(小时级):(timeWindow)以及对应小时级timeWindow发生的次数:(numberOfOccurrence)
eventHappenHours=`grep "$processName" $1 | awk '{print $3}' | awk -F':' '{print $1}' | uniq`
eventHappenTimesEveryhour=`grep "$processName" $1 | awk '{print $3}' | awk -F':' '{print $1}' | uniq -c | sed 's/^ *//g' | awk '{print $1}'`
eventHappenHoursLength=`grep "$processName" $1 | awk '{print $3}' | awk -F':' '{print $1}' | uniq | wc -l | sed 's/ //g' | bc` 
hourlyTimeWindowEvents=""
for ((i=1;i<=$eventHappenHoursLength;i++))
do
	timeWindowStart=`echo $eventHappenHours | cut -d" " -f$i`
	timeWindowEnd=`echo "${timeWindowStart}+1" | bc`
	numberOfOccurrence=`echo $eventHappenTimesEveryhour | cut -d" " -f$i`
	numberOfOccurrence=`echo ${numberOfOccurrence}/2 | bc`

	timeWindowStart="${timeWindowStart}00"
	if [ $timeWindowEnd -lt 10 ]; then
		timeWindowEnd="0${timeWindowEnd}00"
	else
		timeWindowEnd="${timeWindowEnd}00"
	fi
	hourlyTimeWindowEvents="${hourlyTimeWindowEvents}""Timewindow: ${timeWindowStart}-${timeWindowEnd} numberOfOccurrence: ${numberOfOccurrence}\n"
done 

#上传json格式的数据至服务器
jsonbody=`echo  "{ \"DeviceName\":\"${deviceName}\", \"processName\":\"${processName}\", \"ProcessIDs\":\"${processIDs}\", \"Descritpion\":\"${description}\", \"HourlyTimeWindowEvents\":\"${hourlyTimeWindowEvents}\" }" | python -m json.tool`
#serverURL="https://foo.com/bar"
serverURL="http://httpbin.org/post"
curl -H "Content-Type:application/json" -X POST -d "${jsonbody}" $serverURL > /tmp/respinfo.txt 2> /dev/null

if [ $? -eq 0 ]; then
	echo "Upload event info of ${deviceName} successfully!"
	sed -n '/\"json\":/,/"origin\":/p' /tmp/respinfo.txt | grep -v \"origin\" | sed  's/},/}/g'
else
	echo "Upload event info of ${deviceName} failed! Please try a working ${serverURL}"
	echo "$jsonbody"
fi

exit 0

