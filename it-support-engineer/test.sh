#!/bin/bash

while read -r line
do
  dt=$(echo $line | awk '{print ($1" "$2" " $3)}')
  ser_nm=$(echo $line | awk '{print ($4)}')
  process_nm=$(echo $line | awk '{print ($5,$6)}')
  error_msg=$(echo $line | awk '{ for(i=1; i<=6; i++){ $i="" }; print $0 }')


  Occour_msg=$(grep -rn -s $error_msg interview_data_set | wc -l)

  summary_msg=$( echo {\"TimeWindow\":\"$dt\"','"\"DeviceName\":\"$ser_nm\",\"ProcessName\":\"$process_nm\",\"Description\":\"$error_msg\",\"NumOfOccour\":\"$Occour_msg\""})
   echo $summary_msg

  # curl -k -X POST -H "'Content-type':'application/json'" -d $summary_msg https://foo.com/bar

done < interview_data_set
