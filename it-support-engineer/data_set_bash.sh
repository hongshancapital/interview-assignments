#!/bin/bash
# A bash script on analyze the top 100 data and output with json format
gzcat interview_data_set.gz | head -n 100 | sed -e '1h;2,$H;$!d;g;s/\n\t/ /g' | awk -f log_weber.awk> interview_data_set.json
curl -X POST -H "Content-Type: application/json" -d @interview_data_set.json https://foo.com/bar
