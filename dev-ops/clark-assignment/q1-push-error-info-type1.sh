#!/bin/bash
statistics-error-withpid()
{
   #LOGFILENAME=$1
   #ERROR_KEYWORD=$2
   LOG_NAME=DevOps_interview_data_set
   ERROR_KEYWORD=com.apple.xpc.launchd.domain.pid.mdmclient
   #Get Process list
   PROCESS_ID_LIST=`grep -i error DevOps_interview_data_set  |grep pid | tr -d ')|(' | awk -F'pid' '{print $2}' | awk -F':' '{print $1}'| awk -F'.' '{print $NF}' | sort |uniq`
   for pid in $PROCESS_ID_LIST
   do
      DEVICE_NAME=`grep -i error DevOps_interview_data_set | grep $pid | awk '{print $4}'`      PROCESS_NAME=`grep -i error DevOps_interview_data_set | grep $pid | grep -o '.(.*)' | tr -d '(|)' | awk -F'.' '{print $(NF-1)}'` 
      DESCRIPTION=`grep -i error DevOps_interview_data_set | grep $pid |awk -F',' '{print $NF}'`
      #Generate time window
      HOUR=`grep -i error DevOps_interview_data_set | grep $pid |awk -F':' '{print $1}'| awk '{print $NF}'`
      if [[ $HOUR == 0* ]];then
        HOUR=`echo $HOUR | tr -d '0'`
        TIMEWINDOW=`echo 0${HOUR}00-0$(($HOUR + 1))00`
      else
        TIMEWINDOW=`echo ${HOUR}00-$(($HOUR + 1))00`
      fi
      #Count number of error occors
      NUMBEROFOCCUR=`grep -i error DevOps_interview_data_set | grep $pid |grep $HOUR:..:.. |wc -l`
      #echo -e "{\n\"deviceName\": $DEVICE_NAME,\n\"processId\": $pid,\n\"processName\": $PROCESS_NAME,\n\"description\": \"$DESCRIPTION\",\n\"timeWindow\": $TIMEWINDOW,\n\"numberOfOccurrence\": $NUMBEROFOCCUR\n}"
      echo -e "{\n\"deviceName\": $DEVICE_NAME,\n\"processId\": $pid,\n\"processName\": $PROCESS_NAME,\n\"description\": \"$DESCRIPTION\",\n\"timeWindow\": $TIMEWINDOW,\n\"numberOfOccurrence\": $NUMBEROFOCCUR\n}"> /tmp/error-info.json
      cat /tmp/error-info.json
      curl --insecure -H "Content-Type:application/json" -X POST -d@/tmp/error-info.json 'https://foo.com/bar'
      #curl --insecure -H "Content-Type:application/json" -X POST -d "{\"deviceName\": $DEVICE_NAME,\"processId\": $pid,\"processName\": $PROCESS_NAME,\"description\": \"$DESCRIPTION\",\"timeWindow\": $TIMEWINDOW,\"numberOfOccurrence\": $NUMBEROFOCCUR}" 'https://foo.com/bar'
   done

}
statistics-error-withpid
