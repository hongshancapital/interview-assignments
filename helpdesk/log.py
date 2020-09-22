import requests
import json
import math
import os
import csv
import re
import sys
import time
import datetime

#global variables
err_json = {}
err_month = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec']

# read from log file
def readFromCSV():
	cur_dir = os.path.dirname(os.path.realpath(__file__))
	file = cur_dir + "/Helpdesk_interview_data_set"
	
	with open(file) as f:
		my_list = [i for i in f if i != '']
	return my_list

# get error process log per line
def getProcessInfo(logline):
	each_processInfo = {}
	for x in range(4,7):
		if re.search(r"\[(\w+)\]", logline[x]) == None:
			continue
		else:
			each_processInfo['processId'] = re.search(r"\[(\w+)\]", logline[x]).group(1)
			each_processInfo['processName'] = logline[x][0:logline[x].find('[')]
			each_processInfo['description'] = ' '.join(logline[x+1:])
			return each_processInfo


def main():
	err_log = readFromCSV()
	err_number = 1
	for index, string_line in enumerate(err_log):
		err_json[err_number] = {}
		# if error not start with Month date
		if string_line.split()[0] not in err_month:
			err_json[err_number-1]['description'] += err_log[index]
			continue
		# this is to handle repeated error log
		if string_line.split()[3] == "---":
			err_json[err_number-1]['numberOfOccurrence'] += 1
			continue
		if string_line == None:
			break

		# convert hour to timeWindow
		err_time = datetime.datetime.strptime(string_line.split()[2], '%H:%M:%S')
		time_to_window = err_time.strftime('%H') + '00-' + str('{0:02d}'.format(int(err_time.strftime('%H'))+1)) + '00'

		# get error log info per line
		each_processInfo = getProcessInfo(string_line.split())

		# start adding log to json file
		breakAll = False
		for k, v in err_json.items():
			if err_number == 1:
				break
			if not bool(v.values()):
				break
			# this is to handle repetitive error within per timeWindow
			elif v['processId']==each_processInfo['processId'] and v['timeWindow']==time_to_window and v['description']==each_processInfo['description']:
				err_json[k]['numberOfOccurrence'] += 1
				breakAll = True
				break
			else:
				if err_number in err_json[err_number]:
					del err_json[err_number]
				continue
		if breakAll == True:
			del err_json[err_number]
			continue
		else:
			err_json[err_number]['processId'] = each_processInfo['processId']
			err_json[err_number]['processName'] = each_processInfo['processName']
			err_json[err_number]['description'] = each_processInfo['description']
			err_json[err_number]['numberOfOccurrence'] = 0
			err_json[err_number]['timeWindow'] = time_to_window
			err_json[err_number]['deviceName'] = string_line.split()[3]

		err_number += 1
		
	# print(json.dumps(err_json, indent=4))
	return err_json


if __name__== "__main__":
	temp = main()
	with open('err_json.json', 'w') as outfile:
		json.dump(temp, outfile)