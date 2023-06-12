#!/bin/bash

if [ ! -e interview_data_set.log ]; then
  echo "downloading interview_data_set"
  curl -sSL https://github.com/scdt-china/interview-assignments/raw/master/it-support-engineer/interview_data_set.gz | gunzip > interview_data_set.log
else
  echo "interview_data_set already exists"
fi

#cat interview_data_set.log | awk '{split($3, time, ":");device=$4;timeWindow=sprintf("%02d00-%02d00", time[1], time[1]+1);split($5, process, "[[]"); $1=$2=$3=$4=$5=null;print device, sub("]","",process[2]), process[1], timeWindow, $0 }'
cat interview_data_set.log | awk '
/--- process repeated line ---/
{
    sub(/--- last message repeated 1 time ---/, prevLine)
}

/--- process cols after desc col ---/
{
    for (i = 4; i<=NF; i++) {
        if (i == 4) {
            prevLine = $i
        } else if (i == 5) {
            delete process
            split($5, process, "[[]")

            prevLine = prevLine" "process[2]" "process[1]
        } else {
            prevLine = prevLine" "$i
        }
    }
}

/--- process timewindow ---/
{
    split($3, time, ":");
    timeWindow = sprintf("%02d00-%02d00", time[1], time[1]+1)
}

{
    if ((prevLine, timeWindow) in logData) {
        logData[prevLine, timeWindow] = logData[prevLine, timeWindow] + 1
    } else {
        logData[prevLine, timeWindow] = 1
    }    
}

END {
    print "["
    n = 1
    for (item in logData) {

        deviceName = ""
        processId = 0
        processName = ""
        description = ""
        timeWindow = ""
        numberOfOccurrence = 1

        count = length(logData)


        delete line
        split(item, line, SUBSEP)

        numberOfOccurrence = logData[line[1], line[2]]
        timeWindow = line[2]

        delete tmp1
        split(line[1], tmp1, " ")
        deviceName = tmp1[1]
        processId = tmp1[2]
        processName = tmp1[3]

        for (i = 4; i<=length(tmp1); i++) {
            gsub(/"/, "\\\"", tmp1[i])
            if (i == 4) {
                description = tmp1[i]
            } else {
                description = description" "tmp1[i]
            }
        }

        printf "{\n\"deviceName\":\"%s\", \n\"processId\":%d, \n\"processName\":\"%s\", \n\"description\":\"%s\", \n\"timeWindow\":\"%s\", \n\"numberOfOccurrence\":%d \n}", deviceName, processId, processName, description, timeWindow, numberOfOccurrence

        if (n < count)
            print ", "

        n++
    }
    print "]"
}
' > logdata.json


echo "uploading log to foo.com"
response=$(curl -s -k -H 'Content-Type: application/json' -d @./logdata.json -X POST https://foo.com/bar)


if [ "$response" = "200" ]; then
  echo "Upload successful"
else
  echo "Upload failed $response"
fi
