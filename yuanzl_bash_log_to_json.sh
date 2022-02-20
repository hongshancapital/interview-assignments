#!/usr/bin/env bash
## bash yuanzl_bash_log_to_json.sh interview_data_set

filename=$1

IFS=$'\n'
for ((i=0; i<=23; i++))
do
if (( $i <= 9 ));then
for line in `cat ${filename} | grep error | grep "May 13 0${i}"`
do
  deviceName=`echo ${line} | awk '{print $4}' | sed 's/[ \t]*$//g'`
  processId=`echo ${line} | awk '{print $5}' | tr -cd "0-9" | sed 's/[ \t]*$//g'`
  processName=`echo ${line} | awk '{print $5}' | sed 's/[ \t]*$//g'`
  description=`echo ${line}|awk '{for (i=6;i<=NF;i++)printf("%s ", $i);print ""}' | sed 's/[ \t]*$//g'`
  tmp_description=`echo ${line}|awk '{for (i=7;i<=NF;i++)printf("%s ", $i);print ""}' | sed 's/[ \t]*$//g'`
  tmp_timeWindow=`echo ${line} | awk '{print $3}' | sed 's/[ \t]*$//g' | cut -c 2`
  if (( $i == 9 ));then
  timeWindow=0900-1000
  else
  timeWindow=`echo 0${tmp_timeWindow}00-0$((${tmp_timeWindow}+1))00`
  fi
  numberOfOccurrence=`cat ${filename} | grep error | grep "May 13 0${i}" | grep -o ${tmp_description} | wc -l | sed 's/^[ \t]*//g'`
  
  echo -n {\"deviceName\":\"${deviceName}\"\, \"processId\":\"${processId}\"\, \"processName\":\"${processName}\"\, \"description\":\"${description}\"\, \"timeWindow\":\"${timeWindow}\"\, \"numberOfOccurrence\":\"${numberOfOccurrence}\"\}," " >> result.txt
done
else
for line in `cat ${filename} | grep error | grep "May 13 ${i}"`
do
  deviceName=`echo ${line} | awk '{print $4}' | sed 's/[ \t]*$//g'`
  processId=`echo ${line} | awk '{print $5}' | tr -cd "0-9" | sed 's/[ \t]*$//g'`
  processName=`echo ${line} | awk '{print $5}' | sed 's/[ \t]*$//g'`
  description=`echo ${line}|awk '{for (i=6;i<=NF;i++)printf("%s ", $i);print ""}' | sed 's/[ \t]*$//g'`
  tmp_description=`echo ${line}|awk '{for (i=7;i<=NF;i++)printf("%s ", $i);print ""}' | sed 's/[ \t]*$//g'`
  tmp_timeWindow=`echo ${line} | awk '{print $3}' | sed 's/[ \t]*$//g' | cut -c 1-2`
  timeWindow=`echo ${tmp_timeWindow}00-$((${tmp_timeWindow}+1))00`
  numberOfOccurrence=`cat ${filename} | grep error | grep "May 13 ${i}" | grep -o ${tmp_description} | wc -l | sed 's/^[ \t]*//g'`
  
  echo -n {\"deviceName\":\"${deviceName}\"\, \"processId\":\"${processId}\"\, \"processName\":\"${processName}\"\, \"description\":\"${description}\"\, \"timeWindow\":\"${timeWindow}\"\, \"numberOfOccurrence\":\"${numberOfOccurrence}\"\}," " >> result.txt
done
fi
done
