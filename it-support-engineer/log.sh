#!/bin/bash

#Handling abnormal formats in log files by sed
gzcat interview_data_set.gz | sed -e '1h;2,$H;$!d;g;s/\n\t/ /g' > TMP1

#Replace the repeated character in the log "last message repeated 1 time"
cat TMP1 | grep -B 1 'last message repeated 1 time' | grep -v 'last message repeated 1 time' | grep -v '\-\-' >> TMP1

#Redirect replicated logs to the tmp file
cat TMP1 | grep -v 'last message repeated 1 time' > TMP2

#Using awk to generate JSON format for new log files
cat TMP2 | awk -f process.awk > log.json

#Delete Temporary Files
rm -f TMP1 TMP2

#Upload JSON file to foo.com
curl -X POST -H "Content-Type: application/json" -d @log.json https://foo.com/bar
