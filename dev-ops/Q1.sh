#!/bin/bash

# only MacOS tested
gzcat DevOps_interview_data_set.gz | sed -e '1h;2,$H;$!d;g;s/\n\t/ /g' | awk -f log_ana.awk > data.json
curl -X POST -H "Content-Type: application/json" -d @data.json https://foo.com/bar
