#!/usr/bin/python
# -*- coding: utf-8 -*-

import re
import json
import request

path = 'E:/download/interview_data_set'



def run():
	
	f = open(path,'r')
	strListOri = []
	strListFinalTmp = []
	strListFinal = []

	while True:
		ss = f.readline()
		if len(ss)==0:
			break
		else:
			strListOri.append(ss)

	for i in range(0,len(strListOri)):
		strListFirstHarf = re.split(": ",strListOri[i],1)

		if len(strListFirstHarf)<2:
			continue

		strListSecondHarf = re.split(r"\s+",strListFirstHarf[0],4)
		deviceName = strListSecondHarf[3]
		processId = re.match(r"(.*?)\[(.\d*)\]",strListSecondHarf[4]).group(2)
		processName = re.match(r"(.*?)\[(.\d*)\]",strListSecondHarf[4]).group(1)
		description = strListFirstHarf[1]
		timeWindowInt = int(re.split(":",strListSecondHarf[2])[0])
		if timeWindowInt>23:
			timeWindow = "{:0>2d}00-0000".format(timeWindowInt)
		else:
			timeWindow = "{:0>2d}00-{:0>2d}00".format(timeWindowInt,(timeWindowInt+1))
		numberOfOccurrence = 1
		data = {"deviceName":deviceName,"processId":processId,"processName":processName,"description":description,"timeWindow":timeWindow,"numberOfOccurrence":numberOfOccurrence}
		strListFinalTmp.append(data)

	for i in strListFinalTmp:
		if i not in strListFinal:
			strListFinal.append(i)

	for  j in range(len(strListFinal)):
		n = strListFinalTmp.count(strListFinal[j])
		strListFinal[j]["numberOfOccurrence"] = n

res = requests.post(url = url,data = json.dumps(strListFinal),verify = False)

if __name__ == '__main__':
	run()
