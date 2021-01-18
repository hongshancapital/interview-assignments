#!/bin/bash

work_directory=`cd $(dirname "$0");pwd`
file_name="DevOps_interview_data_set"
cd $work_directory

old_ifs=$IFS
IFS=$'\n'

rm -fr result.data
line_prefix=`cat $file_name | head -n 1 | awk '{print $1" "$2" "}'`
post_server="https://foo.com/bar"

#send http post request with curl
curl_json()
{
	json_data=$1
	echo $json_data
	# -k skip ssl verify
	curl_cmd="curl --write-out \"%{http_code}\" -s --max-time 3 -k -d '$json_data' -H \"Content-Type: application/json\" -X POST $post_server -o /dev/null"
	#echo $curl_cmd
	status_code=`eval $curl_cmd`
	if [ $? -ne 0 ]
	then
		echo "curl post error."
	fi

	if [ "$status_code" == "200" ]
	then
		echo "curl post data success."
	else
		echo "curl post data failied, status code:$status_code."
	fi
	echo "-------------------------------------------------------------------"
}

# get all process name
for process_name in `cat $file_name | awk '{print $5}' | awk -F '[' '{print $1}' | sort | uniq`
do
	for hour in {0..23..1}
	do
		start_hour=`printf "%02d" "$hour"`
		end_hour=`printf "%02d" "$(( hour + 1 ))"`
		if [ $end_hour -eq 24 ]
		then
			end_hour="00"
		fi

		# get all line for each hour.
		number_of_occurrence=`cat $file_name | grep "$process_name" | grep "$line_prefix$hour" | wc -l`
		time_window="${start_hour}00-${end_hour}00"
		first_line=`cat $file_name | grep "$process_name" | grep "$line_prefix$hour" | head -n 1`
		device_name=`echo $first_line | awk '{print $4}'`
		process_id=`echo $first_line | awk -F '[][]+' '{print $2}'`
		# clear 0-3 columns
		description=`echo $first_line | awk -F ':' '{ for(i=1; i<=3; i++){ $i="" }; print $0 }' | sed 's/^[ \t]*//g' | sed 's/[ \t]*$//g'`
		if [ $number_of_occurrence -ne 0 ] && [ -n "$process_id" ]
		then
			echo "{\"deviceName\":\"$device_name\",\"processId\":\"$process_id\",\"processName\":\"$process_name\",\"description\":\"$description\",\"timeWindow\":\"$time_window\",\"numberOfOccurrence\":\"$number_of_occurrence\"}" >> result.data
		fi
	done
done

# report parse result
for line in `cat result.data`
do
	curl_json $line	
done
