#!/bin/bash

while read -r line
do
  dev=$(echo $line | awk '{print ($4)}')
  num=$(echo $line | awk '{print ($5,$6)}')
  pro=$(echo $line | awk '{print ($5)}')
  des=$(echo $line | awk '{ for(i=1; i<=6; i++){ $i="" }; print $0 }')
  time=$(echo $line | awk '{print ($1" "$2" " $3)}')
  no=$(grep -rn -s $des DevOps_interview_data_set | wc -l)
  all=$( echo {\"deviceName\":\"$dev\"','"\"processId\":\"$num\",\"processName\":\"$pro\",\"description\":\"$des\",\"timeWindow\":\"$time\",\"numberOfOccurrence\":\"$no\""})
  echo $all
  curl -k -X POST -H "'Content-type':'application/json'" -d $f https://foo.com/bar

done < DevOps_interview_data_set
