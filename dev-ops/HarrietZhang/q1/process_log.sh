#!/bin/bash
if [[ -z $1 ]]; then
	echo "Usage: $basename $0 DevOps_interview_data_set"
	exit -1
fi

cat $1 | awk -F" " 'BEGIN {printf("%s","\[")} {deviceName=$4;
gsub(/[^0-9]+/,"",$6);processId=$6;if(processId=="")next;
processName=$5;
description="";{for(i=7;i<=NF;i++) description=(description" "$i)};gsub(/"/,"\\\"",description)
gsub(/:.*/,"",$3);h1=$3;h2=$3+1;if(h2==24)h2="00";if(length(h2)<2)h2=sprintf("0%s",h2);timeWindow=sprintf("%s00-%s00",h1,h2);
dict[timeWindow]=dict[timeWindow]+1
printf("{\"deviceName\":\"%s\",\"processId\":\"%s\",\"processName\":\"%s\",\"description\":\"%s\",\"timeWindow\":\"%s\"},", deviceName,processId,processName,description,timeWindow)} END {printf("%s","\{");for (key in dict)printf("\"%s\":%s,", key, dict[key]);printf("%s","\}");printf("%s","\]")}' > format.json

if [ $? != 0 ];then
	echo "Process log fail!"
	exit -1
fi

curl https://foo.com/bar -F "file=@/Users/harrietzhang/Downloads/interview/q1/format.json" -H "token: 222" -v

if [ $? != 0 ];then
	echo "Post file fail!"
	exit -1
fi
