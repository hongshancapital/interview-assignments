#!/bin/bash
#yum install epel-release
#yum install jq    #手边没有mac，用的centos，如果mac装jq 命令不同
file=/root/yzy/bash/interview_data_set
trimline(){
awk '
  /^[^[:blank:]]/ {                   
    if (length(mergedLines)>0) {
        print  mergedLines} # print previous merged line
        mergedLines = $0                  
        next
  }
  {                                   
    sub(/^[[:blank:]]+/, "")          
    mergedLines = mergedLines " " $0  
  }
  END {
    print mergedLines                 
  }
' $file
}
trimline |
awk '
    { split($0,errorcheck,/:/) }

        errorcheck[5] != "" {
        split($3,t,/:/)
        timeWindow = sprintf("%02d00-%02d00",t[1],t[1]+1)
        deviceName = $4
        processId = processName = $5
        gsub(/.*\[|].*/,"",processId)
        $1=$2=$3=$4=$5=$6=""        description = $0
        printf deviceName "?" processId "?" processName "?" description "?" timeWindow "?" "\n"
    }' |sort| uniq -c -f 3|sed 's/^  *\([0-9][0-9]*\) *\(.*\)/\2\1/'| jq -R 'split("?")|{deviceName:.[0],ProcessId:.[1],processName:.[2],description:.[3],timeWindow:.[4],numberOfOccurrence:.[5]}'|curl -i -k  -H "Content-type: application/json" -X POST -d '&0' https://foo.com/bar