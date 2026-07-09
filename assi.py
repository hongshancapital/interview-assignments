import requests
import json
import re
from datetime import datetime
import gzip

with gzip.open('./interview_data_set.gz', 'rb') as f:
    file_content = f.read()
    file_content = file_content.decode()


# TimeWindow normalization
def normalize_time(time_str):
    time_obj = datetime.strptime(time_str, "%H:%M:%S")
    hour = time_obj.hour
    start_hour = hour
    end_hour = start_hour + 1
    time_range = f"{start_hour:02d}00-{end_hour:02d}00"
    return time_range


# Assume critical information defined as an error
logs = []
for line in file_content.splitlines():
    if 'error' in line:
        parts = line.split()
        processId = re.findall(r'\d+', parts[5])
        processName = re.sub(r'[:\d()\[\]]+', '', parts[5])
        if processName[-1] == '.':
            processName = processName[:-1]
        else:
            processName = processName
        log = {
            'deviceName': parts[3],
            'processId': ''.join(processId),
            'processName': processName,
            'description': ''.join(line.split()[6:]),
            'timeWindow': normalize_time(parts[2]),
            'numberOfOccurrence': 1
        }
        logs.append(log)

# calculate numberOfOccurrence
merged_data = {}
for d in logs:
    key = (d['deviceName'], d['processName'], d['description'], d['timeWindow'], d['numberOfOccurrence'])
    if key in merged_data:
        merged_data[key]['numberOfOccurrence'] += 1
    else:
        merged_data[key] = d
result = list(merged_data.values())


# upload
url = 'https://foo.com/bar'
headers = {'Content-Type': 'application/json'}
data = json.dumps(result)
# print(data)
response = requests.post(url, headers=headers, data=data, verify=False)
if response.status_code == 200:
    print('Upload successfully')
else:
    print('Upload failed')
    print('Status code:', response.status_code)
