#!/bin/bash
logpath="./interview_data_set"


declare -a deviceName
declare -a processId
declare -a processName
declare -a description
declare -a timeWindow
declare -a numberOfOccurrence

n=0   


function AddLineToResult()
{
	i=0
	while [[ $i -lt $n ]]
	do
	if [[ $line_description == ${description[$i]} && $line_timeWindow == ${timeWindow[$i]} ]] 
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
	let n++  
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
echo $logbody>>output.txt

curl -i -k  -H "Content-type: application/json" -X POST -d "${logbody}" https://foo.com/bar