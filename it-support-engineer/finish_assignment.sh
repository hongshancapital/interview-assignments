#!/bin/bash
if [ $# == 0 ];then
    echo "Usage: ./finish_assignment.sh log-file"
    exit 1
elif [ ! -f $1 ];then
    echo "File does not exist!"
    exit 1
fi

############################
# Analyse log
############################
result=$(cat $1 \
 | sed 's/\"/QUOTA/g' `# Replace double quotation mark with QUOTA` \
 | sed "s/'//g" `# Delete single quotation mark` \
 | sed 'N;s/\n\t/ /' `# Merge the lines which begin with a TAB symbol to the upper line` \
 | sed 'N;s/\n\t/ /' `# Do the above operation once more` \
 `# The awk command below is used to get ERROR statics group by hour|deviceName|processName|processId|description` \
 `# Note that all the logs are being handled since ERROR rules was not given` \
 | awk -F' ' '{split($3,time,":"); \
 split($5,process,"["); \
 split(process[2],id_no,"]"); \
 pn=process[1]; \
 pid=id_no[1]; \
 des=""; \
 for(i=6;i<=NF;i++)des=des$i" "; \
 count[time[1]"|"$4"|"pn"|"pid"|"des]++} \
 END {for(i in count) {print i"|"count[i]}}' \
 | sort `# Sort by field Hour` \
 `# The awk command below is used to make JSON format data` \
 | awk -F'|' 'BEGIN {print "["} \
 {start_hour=$1"00"; \
 end_hour=$1+1; \
 if(end_hour<10){end_hour="0"end_hour"00"} \
 else{end_hour=end_hour"00"}; \
 recs=recs"\t{\"deviceName\" : \""$2"\", \
 \"processId\" : \""$4"\", \
 \"processName\" : \""$3"\", \
 \"description\" : \""$5"\", \
 \"timeWindow\" : \""start_hour"-"end_hour"\", \
 \"numberOfOccurrence\" : \""$6"\"},\n" } \
 END {print substr(recs,1,length(recs)-2)"\n]"}') 
 
#echo $result
############################
# Post the result
############################
curl -i -X POST -H 'Content-Type':'application/json' -d '$result' -k https://foo.com/bar