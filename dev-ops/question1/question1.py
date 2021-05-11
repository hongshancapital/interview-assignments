# -*- coding: utf-8 -*-

############################ Copyrights and license ############################
#                                                                              #
# Copyright 2020 Xiaopeng Liu <liuxp87@gmail.com>                              #
#                                                                              #
# This file is used for assignment only                                        #
# https://github.com/xpdable/                                                  #
#                                                                              #
#                                                                              #
# You should not use it for any commerical usage                               #
#                                                                              #
################################################################################
import datetime
import re
import json
import sys
import getopt
import requests


def process_log(log_path: str, error_keywords: list = None) -> list:

	"""
	Parse the given log

	:param log_path: str: the path of log file
	:param error_keywords: list: list of keywords to filter, no filter if is None
	:return: list of dict() contains keys:
			deviceName, processId, processName, description, timeWindow, numberOfOccurrence

	"""

	# Read file lines into a list
	with open(log_path) as f:
		raw = f.readlines()

	# Loop the list items from length n to 1
	# 1) Check if starts with at formated time
	#    if not try to append previous line tail.
	#    if so parse the time to time window
	# 2) Check the line of log is valid and meaningful by RegEx matcher, drop and move next line if not
	# 3) Check the line contains any of the keywords, drop if no keyword matched
	# 4) Get deviceName
	# 5) Parse processId, processName
	# 6) Get description
	# 7) Count occurrence
	# 8) Merge numberOfOccurrence
	raw_list = list()
	for i in range(len(raw)-1, -1, -1):
		# 1)
		timeline = raw[i][0:14]
		time_window = parse_time_window(timeline)
		if time_window == "":
			raw[i-1] = raw[i-1] + raw[i]
			continue
		else:
			event_dict = dict()
			# 2)
			standard_long_line_matcher = "^.+\d\d\s\d\d:\d\d:\d\d\s.+\[\d+\](|\s\(.+(\[\d+\]|\.\d+|)\)):\s.+(\n.+)*$"  # noqa
			if re.search(standard_long_line_matcher, raw[i]) is None:
				print("skip ... {}".format(raw[i]))
				continue
			# 3)
			if error_keywords is not None:
				if not match_keyword(raw[i], error_keywords):
					continue
			# 4)
			device_name = raw[i].strip().split(' ')[3]
			event_dict['deviceName'] = device_name
			# 5)
			process_str = raw[i].split(device_name)[1].strip().split(':')[0]
			description = raw[i].split(device_name)[1].strip().split(':')[1]

			long_reg = "^.+\[\d+\]\s\(.+\[\d+\]\)$" # noqa
			long_reg_with_dot_number = "^.+\[\d+\]\s\(.+\.\d+\)$" # noqa
			long_reg_without_number = "^.+\[\d+\]\s\(.+\)$" # noqa
			short_reg = "^.+\[\d+\]$" # noqa

			process_name = ""
			process_id = ""
			try:
				if re.search(long_reg, process_str) is not None:
					# e.g.  com.apple.xpc.launchd[1] (com.apple.mdworker.bundles[123])
					# -> com.apple.xpc.launchd (com.apple.mdworker.bundles)  
					# -> process id : 123
					process_name_pair = process_str.strip().split(' ')
					process_name = "{} {})".format(process_name_pair[0].strip().split('[')[0], process_name_pair[1].strip().split('[')[0]) # noqa
					process_id = process_name_pair[1].strip().split('[')[1].split(']')[0]
				elif re.search(short_reg, process_str) is not None:
					# e.g. VTDecoderXPCService[666]
					# -> VTDecoderXPCService
					# -> process id : 666
					process_name = process_str.strip().split('[')[0]
					process_id = process_str.strip().split('[')[1].split(']')[0]
				elif re.search(long_reg_with_dot_number, process_str) is not None:
					# e.g.  com.apple.xpc.launchd[1] (com.apple.mdworker.bundles.888)
					# -> com.apple.xpc.launchd (com.apple.mdworker.bundles)  
					# -> process id : 888
					process_name_pair = process_str.strip().split(' ')
					process_name = "{} {}".format(process_name_pair[0].strip().split('[')[0], process_name_pair[1].strip().rsplit('.')[0]) # noqa
					process_id = process_name_pair[1].strip().rsplit('.')[1].split(')')[0]
				elif re.search(long_reg_without_number, process_str) is not None:
					# e.g.  com.apple.xpc.launchd[123] (com.apple.mdworker.bundles.debug)
					# -> com.apple.xpc.launchd (com.apple.mdworker.bundles.debug)  
					# -> process id : 123
					process_name_pair = process_str.strip().split(' ')
					process_name = "{} {}".format(process_name_pair[0].strip().split('[')[0], process_name_pair[1].strip()) # noqa
					process_id = process_name_pair[0].strip().split('[')[1].split(']')[0]
				else:
					process_name = process_str
					process_id = "-1"
			except IndexError:
				print("parse failed, skipping...")

			event_dict['processId'] = process_id
			event_dict['processName'] = process_name
			# 6)
			event_dict['description'] = description
			event_dict['timeWindow'] = time_window
			# event_dict['numberOfOccurrence'] = 0
			raw_list.append(json.dumps(event_dict))
	# 7)
	grouped_event_dict = dict()
	for item in raw_list:
		if item in grouped_event_dict:
			grouped_event_dict[item] += 1
		else:
			grouped_event_dict[item] = 1
	# 8)
	grouped_event_list = list()
	for key, value in grouped_event_dict.items():
		event = json.loads(key)
		event['numberOfOccurrence'] = value
		grouped_event_list.append(json.dumps(event))

	# print(grouped_event_list)
	# print(len(grouped_event_list))
	return grouped_event_list


def match_keyword(base_str: str, keywords: list) -> bool:
	"""

	:param base_str: a string to be serached
	:param keywords: list of str that not None
	:return:
		True if one or more items in the keywords list occurred in base_str
		False if not any keyword is in base_str
	"""
	unmatch_conut = 0
	for w in keywords:
		if w in base_str:
			continue
		else:
			unmatch_conut = unmatch_conut + 1
	if unmatch_conut == len(keywords):
		return False
	else:
		return True


def parse_time_window(t_str: str, _default_year: str = "2020") -> str:
	"""

	:param t_str: a given str from log e.g. 13 May 23:23:15
	:param _default_year: 2020
	:return:
		str of time window e.g. 202009082300
	"""
	try:
		dateobj = datetime.datetime.strptime("{} {}".format(_default_year, t_str), '%Y %b %d %H:%M:%S')
		time_window = dateobj.strftime('%Y%M%d%H00')
	except ValueError:
		return ""
	return time_window


def do_request(url: str = 'http://foo.com/upload', headers: dict = None, payload: str = ""):
	"""
	Do a simple request
	:param url:
	:param headers:
	:param payload:
	:return:
	"""
	response = requests.request("POST", url, headers=headers, data=payload)
	print(response.text.encode('utf8'))


def main():
	"""
	usage:
		e.g.  python question1.py -p DevOps_interview_data_set -e error,warn
			  python question1.py -p DevOps_interview_data_set
	"""
	path = ""
	error_keywords = ""
	myopts, args = getopt.getopt(sys.argv[1:], "p:e:")
	for o, val in myopts:
		if o == '-p':
			path = val
		elif o == '-e':
			error_keywords = val
		else:
			print("wrong args")
	if error_keywords != "":
		list_keywords = error_keywords.split(",")
	else:
		list_keywords = None

	grouped_event_list = process_log(path, list_keywords)
	# print(json.dumps(grouped_event_list))
	default_headers = {
		'Content-Type': 'application/json;charset=UTF-8'
	}
	do_request(headers=default_headers, payload=json.dumps(grouped_event_list))


if __name__ == "__main__":
	main()
