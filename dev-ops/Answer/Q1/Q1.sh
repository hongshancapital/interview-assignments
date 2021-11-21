#!/bin/sh

mkdir collection

errortype="error|failed|invalid"
work_dir=`pwd`
log_path="$work_dir/collection"
report_url="http://127.0.0.1:1988/v1/push"
hour=(00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23 00)

gzip -d DevOps_interview_data_set.gz
# Convert time format
i=0
while ((i < 24)); do
  timewindow="${hour[$i]}:[0-5][0-9]:[0-5][0-9]/${hour[$i]}00-${hour[$(($i+1))]}00"
  sed -i "s/${timewindow}/g" DevOps_interview_data_set
  ((i++))
done

# clean log
for devicename in `cat DevOps_interview_data_set|grep -E "$errortype"|awk '{print $4}'|awk -F"[" '{print $1}'|sort|uniq`;
  do
    cat DevOps_interview_data_set|grep -E "$errortype"|grep "$devicename"|sed 's/://g'|awk '{$1=$2="";print $0}'|sort|uniq -c > $log_path/$devicename.log
done

cd $log_path
for filename in `ls *.log`
do
    while read -r line
    do
    occurrence=$(echo $line|awk '{print $1}')
    timewindow=$(echo $line|awk '{print $2}')
    deviceName=$(echo $line|awk '{print $3}')
    processName=$(echo $line|awk '{print $4}')
    processId=$(echo $line|awk '{print $5}')
    description=$(echo $line|cut -d' ' -f8-)
    curl -X POST -d "[{\"deviceName\": \"${deviceName}\", \"processId\": \"${processId}\", \"processName\": \"${processName}\", \"description\": \"${description}\", \"timewindow\": \"${timewindow}\", \"numberOfOccurrence\": \"${occurrence}\"}]" $report_url
  done < $filename
done

cd $work_dir
remove -rf $log_path
