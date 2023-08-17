#!/bin/bash
logpath="./interview_data_set"

#bash的对象不太好用，所以分别建立一些数组来存储各个元素
declare -a deviceName
declare -a processId
declare -a processName
declare -a description
declare -a timeWindow
declare -a numberOfOccurrence

n=0    #表示目前已经处理过的数组长度，便于作为所有循环的条件

#将一行记录拆解之后插入各个数组的函数
function AddLineToResult()
{
	i=0
	while [[ $i -lt $n ]]
	do
	if [[ $line_description == ${description[$i]} && $line_timeWindow == ${timeWindow[$i]} ]]   #如果错误信息和时间窗口相等则不需要插入，只需要次数+1
	then
		let numberOfOccurrence[$i]++
		processId[$i]=${processId[$i]}","$line_processId
		return
	fi
	let i++
	done
if [[ $i -eq $n	]]
then
	deviceName[${#deviceName[*]}]=$line_deviceName
	processId[${#processId[*]}]=$line_processId
	processName[${#processName[*]}]=$line_processName
	description[${#description[*]}]=$line_description
	timeWindow[${#timeWindow[*]}]=$line_timeWindow
	numberOfOccurrence[${#numberOfOccurrence[*]}]=1
	let n++    #成功添加一行新记录，各个数组长度+1
fi
}

function TimeToWindow()
{
	time=$1
	hour1=${time%%:*}
	hour2=$[10#$hour1+1]
	if [ $hour2 -lt 10 ]; then
	hour2="0"${hour2}
	fi
	line_timeWindow=${hour1}"00-"${hour2}"00"
}

function readlog()
{
	if [[ $6 =~ \((.*)\[([0-9].*)\]\)\: ]]
	then
		line_processId=${BASH_REMATCH[2]}
		line_processName=${BASH_REMATCH[1]}
		TimeToWindow $3
		line_deviceName=$4
		line_whole=$*
		line_description=${line_whole##*"$6"}
		AddLineToResult
	fi
}

while read line; do readlog $line; done < $logpath
let i=0
while [[ $i -lt $n ]]
do
	#将数组中存储的原数据转变为JSON格式
	if [[ $i -eq 0 ]]
	then
		logbody="[{\"deviceName\"=\""${deviceName[$i]}"\",\"processId\"=\""${processId[$i]}"\",\"processName\"=\""${processName[$i]}"\",\"description\"=\""${description[$i]}"\",\"timeWindow\"=\""${timeWindow[$i]}"\",\"numberOfOccurrence\"="${numberOfOccurrence[$i]}"},"
	elif [[ $i+1 -eq $n ]]
	then
		logbody=$logbody"{\"deviceName\"=\""${deviceName[$i]}"\",\"processId\"=\""${processId[$i]}"\",\"processName\"=\""${processName[$i]}"\",\"description\"=\""${description[$i]}"\",\"timeWindow\"=\""${timeWindow[$i]}"\",\"numberOfOccurrence\"="${numberOfOccurrence[$i]}"}]"
	else
		logbody=$logbody"{\"deviceName\"=\""${deviceName[$i]}"\",\"processId\"=\""${processId[$i]}"\",\"processName\"=\""${processName[$i]}"\",\"description\"=\""${description[$i]}"\",\"timeWindow\"=\""${timeWindow[$i]}"\",\"numberOfOccurrence\"="${numberOfOccurrence[$i]}"},"
	fi
	let i++
done
echo $logbody>>bash_output.txt   #转变之后的结果存储到文件备份

curl -i -k  -H "Content-type: application/json" -X POST -d "${logbody}" https://foo.com/bar 