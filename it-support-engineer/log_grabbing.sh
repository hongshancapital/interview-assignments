#!/bin/bash

i=0


line_num=1

starttime=$(date -d'May 13 00:00:00' +%s)

endtime=`expr $starttime + 3600`

bb=第$i时至$(($i+1))时
cat interview_data_set | grep -i error > ./errorlog.txt

cat ./errorlog.txt | grep -E -o '[a-zA-Z]{3}.[0-9]{2}.([0-9]{2}:){2}[0-9]{2}' | while read current_date
do
#   echo $current_date    
   current_time=`date --date="${current_date}" +%s`
#   echo ${current_time}
   if [ ${current_time} -ge ${endtime} ];then
          let i++
      bb=第$i时至$(($i+1))时
      endtime=`expr $endtime + 3600` 
        fi
    eval sed -i '${line_num}s/"${current_date}"/"${bb}"/' ./errorlog.txt
    let line_num++
done

# echo -e "timeWindow\t\tdeviceName\t\tprocessName\t\tprocessID\t\tdescription" > result_1.txt 

cat errorlog.txt | while read each_raw_line
do
   field_second=`echo $each_raw_line | awk -F '[)(]' '{print $2}'`
   if [ -z ${field_second} ]
      then 
        if [ $(echo $each_raw_line | awk -F '[][]' '{print NF}') -ge 4 ]
	 then
	 timeWindow=$(echo $each_raw_line |awk -F '[][]' '{print $1}' | cut -f 1 -d " ")
	 deviceName=$(echo $each_raw_line | awk -F '[][]' '{print $1}' | cut -f 2 -d " ")
	 processName=$(echo $each_raw_line | awk -F '[][]' '{print $1}' | cut -f 3- -d " ")
	 processID=$(echo $each_raw_line | awk -F '[][]' '{print $2}')
	 description=$(echo $each_raw_line | cut -f 2- -d ":" | sed  's/^ //')
        else
         timeWindow=$(echo $each_raw_line | awk -F '[][]' '{print $1}' | awk '{print $1}')
         deviceName=$(echo $each_raw_line | awk -F '[][]' '{print $1}' | awk '{print $2}')
         processName=$(echo $each_raw_line | awk -F '[][]' '{print $1}' | awk '{print $3}')
         processID=$(echo $each_raw_line | awk -F '[][]' '{print $2}')
         description=$(echo $each_raw_line | awk -F '[][]' '{print $3}' | sed 's/^: //')
        fi
   else
      timeWindow=`echo $each_raw_line | awk -F '[)(]' '{print $1}' | awk '{print $1}'`
      deviceName=`echo $each_raw_line | awk -F '[)(]' '{print $1}' | awk '{print $2}'`
      processName=`echo $each_raw_line | awk -F '[)(]' '{print $2}' |  grep -o -E '([a-zA-Z]{3,}\.){2,}[a-zA-Z]{3,}'`
      processID=`echo $each_raw_line | awk -F '[)(]' '{print $2}' | grep -o -E '[0-9]{5}'`
      description=$(echo $each_raw_line | awk -F '[)(]' '{print $3}'| sed  's/^: //')
   fi
echo -e "$timeWindow|$deviceName|$processName|$processID|$description"  >> result_1.txt 
done 

#cat result_1.txt | awk -F '|' '{printf("%s %s %s ",$1,$2,$3)};{for(i=5;i<=NF;i++)printf("%s ",$i);print ""}' | sort | uniq -c | sort -nr -k 1 > result_temp.txt 
cat result_1.txt | awk -F '|' '{print $1"|"$2"|"$3"|"$5"|"}' | sort | uniq -c | sort -nr -k 1 > result_temp.txt 

cat result_temp.txt | while read line
do

#  a=$(echo $line | awk -F '|' '{split($1,a," ")}')
  numberOfOccurrence=$(echo $line | awk -F '|' '{print $1}' | awk '{print $1}')
  timeWindow=$(echo $line | awk -F '|' '{print $1}' | awk '{print $2}')
  deviceName=$(echo $line | awk -F '|'  '{print $2}')
  processName=$(echo $line | awk -F '|' '{print $3}')
#  description=$(echo $line | awk '{for(i=5;i<=NF;i++)printf("%s ",$i);print ""}')
  description=$(echo $line | awk -F '|' '{print $4}')

  echo {\"numberOfOccurrence\":\"$numberOfOccurrence\"','"\"timeWindow\":\"$timeWindow\",\"deviceName\":\"$deviceName\",\"processName\":\"$processName\",\"description\":\"$description\""} >> result_final.txt
done

rm -f result_1.txt
rm -f errorlog.txt
rm -f result_temp.txt
