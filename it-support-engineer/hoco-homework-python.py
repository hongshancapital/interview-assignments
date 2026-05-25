import collections
import json
import requests
import re

url = 'https://foo.com/bar'
logfile = '.\interview_data_set.gz'
lineObjectArray = []

for line in open(logfile):
    lineArray = line.strip('\n').split(' ')
    if lineArray[3] == 'BBAOMACBOOKAIR2':
        lineObjectArray.append({
            "deviceName": lineArray[3],
            "processId": re.search('\[(\d+)]', line).group(0)[1:-1],
            "processName": ' '.join(lineArray[4:6]).replace('(\D+)\[\d+\]', '$1').strip(':'),
            "description": ' '.join(lineArray[6:]),
            "hour": lineArray[2].split(':')[0]
        })

groupCounter = {}

for obj in lineObjectArray:
    key = obj["deviceName"] + obj["processId"] + obj["processName"] + obj["description"] + obj["hour"]
    if key in groupCounter:
        numberOfOccurrence = groupCounter[key]["numberOfOccurrence"]
        numberOfOccurrence = numberOfOccurrence + 1
        groupCounter[key]["numberOfOccurrence"] = numberOfOccurrence
    else:
        groupCounter[key] = collections.OrderedDict(
            deviceName=obj["deviceName"],
            processId=obj["processId"],
            processName=obj["processName"],
            description=obj["description"],
            timeWindow='{0}:00 - {1}:00'.format(obj["hour"], str((int(obj["hour"]) + 1) % 24).rjust(2, '0')),
            numberOfOccurrence=1
        )


row_list = list(item[1] for item in groupCounter.items())
sorted(row_list, key=lambda item: item['timeWindow'])

with open('py-payload-v2.json', 'wt') as file:
    file.write(json.dumps(row_list))
x = requests.post(url, json=row_list, timeout=5)