#!/bin/bash

# format data before counting
# handle lines not start with datetime
step1=$(awk -v ORS= 'NR > 1 && /^May/ {print "\n"} {print} END {print "\n"}' interview_data_set |

# handel repeated lines
awk '{ if($4 != "---") { print $0; $1=$2=$3=""; f=$0 } else { $0=$1" "$2" "$3f; print $0 }  }' |

# parse the log
awk '{
    # get time hour
	timeField = substr($3, 1, 2)
	timeWindow = sprintf("%02d:00-%02d:00", timeField, timeField + 1)

	# get deviceName
	deviceName = $4

	# reset line
	$1=$2=$3=$4=""

	# seprate idName and description
	sep = index($0, ": ")
	idName = substr($0, 1, sep - 1)

	# get processId and processName
	match( idName, /\[[0-9]+\]/)	
	processName = substr(idName, 5, RSTART - 5)
	processId = substr(idName, RSTART + 1, RLENGTH - 2)

	#get description and replace double qoute to avoid Json output conflict
	description = substr($0, sep + 2)
	gsub("\"", "#", description)

	#count by key
	k = timeWindow"@"deviceName"@"processName
	out[k]++
	out2[k]["p"][processId]++
	out2[k]["d"][description]++

} END {
	#print the result
	for(i in out) {
		for (j in out2[i]["p"]) {
			ids = ids ? ids" "j : j
		}
		for (l in out2[i]["d"]) {
			desc = desc ? desc" ===>>> "l : l
		}
		print out[i]"@"i"@"ids"@"desc
		ids = ""
		desc = ""
	}
}' | sort -t"@" -k2,2 -k1,1nr
)

# get the line count
lineCount=$(echo "$step1" | wc -l)

# preprare json output
outjson=$(
	echo "$step1" | awk -F"@" -v lineCount="$lineCount" 'BEGIN { print "[" }
	{
		printf "{\n"
		printf "\"timeWindow\": \"%s\",\n", $2
		printf "\"numberOfOccurrence\": \"%s\",\n", $1
		printf "\"deviceName\": \"%s\",\n", $3
		printf "\"processName\": \"%s\",\n", $4
		printf "\"processId\": \"%s\",\n", $5
		printf "\"description\": \"%s\"\n}", $6
		if(NR!=lineCount) { printf ",\n" }
		else { printf "\n" }
	} 
	END { print "]" }' 
)
# output to file
echo "$outjson" > sh-out.json

# send post json to API server
# curl -X POST https://foo.com/bar -H "Content-Type: application/json" -d "$outjson"