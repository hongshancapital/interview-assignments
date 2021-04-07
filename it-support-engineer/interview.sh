#!/bin/bash
curl -o /mnt/hgfs/Dowloads/interview_data_set.gz -k https://github.com/scdt-china/interview-assignments/raw/master/it-support-engineer/interview_data_set.gz
gzip -d /mnt/hgfs/Dowloads/interview_data_set.gz
IFS=$'\n\n';for i in $(grep 'error' /mnt/hgfs/Downloads/interview_data_set|grep -v "subsequent error"|awk '{for (i=7;i<=NF;i++) {if (i<NF) {printf("%s ",$i)} else {print $i}}}'|sort -u);do grep $i /mnt/hgfs/Downloads/interview_data_set|awk '-F[ :]' '{$4="";$5="";$8="";print $0}END{print $i}';done|uniq -c >> tmp.txt
echo -e '{\n"error":\n[' >json.json
IFS=$'\n\n';
for i in `cat tmp.txt`;do
	device_name=$(echo "$i"| awk -F'[] []+' '{print $6}')
	process_id=$(echo "$i"| awk -F'[] []+' '{print $8}')
	process_name=$(echo "$i"| awk -F'[] []+' '{print $7}')
	description=$(echo "$i"| awk -F'[] []+' '{for (j=9;j<=NF;j++){printf("%s ",$j)}}')
	time_window=$(echo "$i"| awk -F'[] []+' '{print $5}')
	time_window_2=$(printf "%02d" $[10#$time_window + 1 ])
	number_of_occurrence=$(echo "$i"| awk -F'[] []+' '{print $2}')
	echo \{\"deviceName\":\"${device_name}\",\"processId\":\"${process_id}\",\"processName\":\"${process_name}\",\"description\":\"${description}\",\"timeWindow\":\"${time_window}00-${time_window_2}00\",\"numberOfOccurrence\":\"${number_of_occurrence}\"\},>>json.json
done
echo "" > tmp.txt
sed -i '$s#},#}#' json.json
echo -e ']\n}' >>json.json
curl -X POST -H'Content-Type: application/json' https://foo.com/bar -d@json.json -k
echo "" > json.json
