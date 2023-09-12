#!/bin/bash
> tmp1.txt >tmp2.txt >tmp3.txt >resultshell.json >33.txt
tr '\n' ' ' < interview_data_set | sed 's/May 13 /\n/g' > tmp1.txt && cut -c 1-2,9- tmp1.txt | grep -Ev "com.apple.xpc.launchd|syslogd|last message" | sed '/^$/d' > tmp2.txt
sort tmp2.txt | uniq -c > tmp3.txt

while read line; do
    deviceName=$(echo $line | awk '{print $3}')
    description=$(echo $line  | awk 'BEGIN { FS="]:" } { for (i=2; i<=NF; i++) print $i }'| sed 's/^ //' | tr '\n' ' ' | sed 's/ $//')
    numberOfOccurrence=$(echo $line  | awk '{print $1}')
    timeWindow=$(echo $line  | awk '{print $2}')
    processName=$(echo $line  | awk -F "[" '{print $1}' | awk '{print $4}')
    processId=$(echo $line  | awk -F "]:" '{print $1}' | awk -F "[" '{print $2}') 
    echo "$deviceName##$processId##$processName##$description##$timeWindow##$numberOfOccurrence" >> 33.txt
done < tmp3.txt

echo "[" > resultshell.json
awk -F '##' '{printf("{\"deviceName\":\"%s\",\"processId\":\"%s\",\"processName\":\"%s\",\"description\":\"%s\",\"timeWindow\":\"%s\",\"numberOfOccurrence\":\"%s\"},\n", $1, $2, $3, $4, $5, $6)}' 33.txt >> resultshell.json
sed -i '$ s/.$//' resultshell.json
echo "]" >> resultshell.json
