#!/bin/bash
# Author Roy Jin 2023-08-16
# In order to reuse this script add manually step here, the purpose is to make sure scirpt to support more functions
# wget https://github.com/scdt-china/interview-assignments/blob/7effc5f4f7c1205bc538968283965fc6b3386f36/it-support-engineer/interview_data_set.gz .
# gunzip interview_data_set.gz interview_data_set


usage()
{
cat << EOF
USAGE: $(basename $0) OPTIONS
LEGEND:
  [] - input required
   * - required field
OPTIONS:
  -f   [log_file]

EXAMPLES:
    $(basename $0) -f interview_data_set 

EOF
}

#Get Inputs
while getopts "f:" OPTION
do
  case $OPTION in
    f) log_file=$OPTARG;;
    ?) usage
       exit 1;;
  esac
done


# if no args given, then dump usage and quit:
if [ -z "${log_file}" ]; then
  usage;
  exit;
fi


# url variable to send data 
url="https://foo.com/bar"




# Parse log file
while read -r line; do
	line_error=$(echo "$line" | grep -i "error\|failed\|invalid\|abnormal")
	line_error_num=$(echo "$line" | grep -i "error\|failed\|invalid\|abnormal" | wc -l)
	if [[ $line_error_num -gt 0 ]]; then
		
		deviceName=$(echo "$line_error" | awk '{print $4}')

		# Extract process id  
		processId=$(echo "$line_error" | cut -d ' ' -f 5 | grep -o '[0-9]*')
		
		# Extract process name
		processName=$(echo "$line_error" | cut -d ' ' -f 5 | cut -d'[' -f1)

		# Extract error description
		description=$(echo "$line_error" | cut -d' ' -f 7-)

		timeWindow=$(echo "$line_error" | cut -c 8-12 )
		
		key="$processId"

		# If unique, initialize count
		if [ -z "${entries[$key]+abc}" ]; then
		numberOfOccurrence=1
		
		# If duplicate, increment count  
		else  
		numberOfOccurrence=$((entries[$key]+1))
		fi

		entries[$key]=$numberOfOccurrence

		# Create JSON 
		json=$(jq -n \
			--arg deviceName "$deviceName" \
			--arg processId "$processId" \
			--arg processName "$processName" \
			--arg description "$description" \
			--arg timeWindow "$timeWindow" \
			--arg numberOfOccurrence "$numberOfOccurrence" \
			'{deviceName: $deviceName, processId: $processId, processName: $processName, description: $description, timeWindow: $timeWindow, numberOfOccurrence: $numberOfOccurrence}'
		)
		  # Write to file
		echo "$json" >> "macos_log_error.json"
		# Counter for file names
		count=1 
		# Increment counter
		((count++))

	else
	   echo "No ERROR found from system log"
        fi
	
done < $log_file

data="macos_log_error.json"

# Post data 
curl -X POST -H "Content-Type: application/json" -d "$data" $url
