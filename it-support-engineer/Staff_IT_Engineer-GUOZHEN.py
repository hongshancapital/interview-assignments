# Staff IT Engineer, Beijing - Owner:Guo Zhen
# Date July, 13, 2023
# After running this script, it will post resulte to server in json format
# meanwhile 3 files 'interview_data_set_singleline','ascendingfrequenterror.csv'和‘jsondatadump.txt’ will be generated in local path 

import json
import csv
from collections import Counter
import pandas as pd
from requests.packages import urllib3
import requests
import re

with open('./interview_data_set', 'r') as f:
    a = f.read()
# this pattern is to match Month day time format '\b[a-zA-Z]{3}\b\ \d{2}\ \d{2}\:\d{2}\:\d{2}'
a = re.sub(r'\n(?!\b[a-zA-Z]{3}\b\ \d{2}\ \d{2}\:\d{2}\:\d{2})', '', a)
#if line is not started with \n and this formate, then replace with ' '
with open('./interview_data_set_singleline', 'w') as f:
    f.write(a)
#this will generate a clean single line version log

file_name = "./interview_data_set_singleline"
file = open(file_name, "r")
datachart = []

for line in file.readlines():
#	if line.startswith('\t'):
#		pass
#	else:
#fetch 4 values in each line and added tuple to list
	description = line.split(':',3)[-1].strip('\n').strip()
	details = []
	pieces=line.split()
	deviceName=pieces[3]
	raw_processName=pieces[4]
	processName=raw_processName[0:raw_processName.rfind('[')]
	raw_processId=raw_processName[raw_processName.rfind('['):raw_processName.rfind(']')]
	processId=raw_processId.strip('[')
	rawtime=pieces[2].split(':')[0]
	starthour="{}00".format(str(int(rawtime)).zfill(2))
	nexthour="{}00".format((str(int(rawtime)+1)).zfill(2))
	timeWindow="{}-{}".format(starthour, nexthour)
	datachart.append((deviceName, processName, processId, description, timeWindow))


#counter accumulates amounts, merges 5th value into previous 4 values, zip together to list

counter = Counter(datachart)
final_chart = []
final_dict = {}
key_index = ("deviceName", "processName", "processId", "description", "timeWindow", "numberOfOccurrence")
for key, raw_Occurrence in counter.items():
    deviceName, processId, processName, description, timeWindow = key
    numberOfOccurrence = raw_Occurrence
    final_dict = dict(zip(key_index,(*key, numberOfOccurrence)))
    final_chart.append(final_dict)



#Print out Top 50 frequent error list and generate a csv report 
df=pd.DataFrame(final_chart)
pd.set_option('display.max_rows',None)
#pd.set_option('display.max_colwidth',None)
#most_frequent_alert=df.sort_values(by='numberOfOccurrence',ascending=False).head(50)
most_frequent_alert=df.sort_values(by='numberOfOccurrence',ascending=False)
#print(most_frequent_alert)
most_frequent_alert.to_csv('ascendingfrequenterror.csv', index=False)


requests.packages.urllib3.disable_warnings()  

#Upload json to server
headers = {
    "Content-type": "application/json; charset=UTF-8"
}
json_data=json.dumps(final_chart,indent = 2)
response = requests.post(url='https://foo.com/bar', headers=headers, verify=False, data=json_data)
with open('./jsondatadump.txt','wt') as p:
	print (json_data,file=p) 

print("脚本运行成功，请分别查看生成的两个文件'ascendingfrequenterror.csv'和‘jsondatadump.txt’")

#if response.status_code == 200:
#	print('Submit successed')
#else:
#	print('Submit failed')