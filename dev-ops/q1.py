# -*- coding: utf-8 -*-
# @Time : 2021/1/22 8:39
# @Author : duanjj
# @File : q1.py
import re
import requests
import json

def send(deviceName,processName,processId,description,timeWindow,numberOfOccurrence):
    url = 'https://foo.com/bar'
    keys = {
        'deviceName': deviceName,
        'processName': processName,
        'processId': processId,
        'description': description,
        'timeWindow': timeWindow,
        'numberOfOccurrence': numberOfOccurrence
    }
    print(keys)
    r = requests.post(url,json=json.dumps(keys))
    if r.status_code == 200:
        print("upload successfully!")
    else:
        print("ERROR: invoke interface failed")

def parse_log(file):
    #初始化数据结构
    dev_programs_dict = {}
    programname_id_dict = {}
    programname_describe_dict = {}
    error_num = {}
    starhour = None

    #正则列表
    pattern = '\w+\s\d{2}\s(?P<hour>\d{2}):[0-9]{2}:[0-9]{2}\s(?P<deviceName>[\w]+)\s.*\]\s\((?P<processName>\w+.*\w)[\.\[](?P<processId>\d+)\]?\):\s(?P<description>[\w].*)'
    pattern_error1 =' error:'
    pattern_error2 = ' error ='
    #处理文件
    with open(file) as f:
        for line in f:
            if re.findall(pattern_error1, line.strip()) or re.findall(pattern_error2, line.strip()):
                r = re.match(pattern,line.strip())
                hour = r.group('hour')
                deviceName = r.group('deviceName')
                processName = r.group('processName')
                processId = r.group('processId')
                description = r.group('description')
                if starhour != None and starhour != hour:
                    #发送数据
                    timeWindow = starhour + '00-' + hour + '00'
                    for dev in dev_programs_dict.keys():
                        for i in range(len(dev_programs_dict[dev])):
                            send_deviceName = dev
                            send_processName = dev_programs_dict[dev][i]
                            send_processId = programname_id_dict[dev_programs_dict[dev][i]]
                            send_description = programname_describe_dict[dev_programs_dict[dev][i]]
                            send_numberOfOccurrence = error_num[dev_programs_dict[dev][i]]
                            send(send_deviceName,send_processName,send_processId,send_description,timeWindow,
                                 send_numberOfOccurrence)
                    dev_programs_dict = {}
                    programname_id_dict = {}
                    programname_describe_dict = {}
                    error_num = {}
                    starhour = hour

                #生成devicename和programname的对应关系
                if deviceName not in dev_programs_dict.keys():
                    pro_list = []
                    pro_list.append(processName)
                    dev_programs_dict[deviceName] = pro_list
                elif processName not in dev_programs_dict[deviceName]:
                    dev_programs_dict[deviceName].append(processName)
                #生成programname和processId的对应关系
                if processName not in programname_id_dict.keys():
                    processId_list = []
                    processId_list.append(processId)
                    programname_id_dict[processName] = processId_list
                elif processId not in programname_id_dict[processName]:
                    programname_id_dict[processName].append(processId)
                # 生成programname和describe的对应关系
                if processName not in programname_describe_dict.keys():
                    description_list = []
                    description_list.append(description)
                    programname_describe_dict[processName] = description_list
                elif description not in programname_describe_dict[processName]:
                    programname_describe_dict[processName].append(description)
                # 生成programname和numberOfOccurrence的对应关系
                if processName not in error_num.keys():
                    error_num[processName] = 1
                else:
                    error_num[processName] += 1
                starhour = hour
    #发送最后一小时的
    timeWindow = starhour + '00-' + '2400'
    for dev in dev_programs_dict.keys():
        for i in range(len(dev_programs_dict[dev])):
            send_deviceName = dev
            send_processName = dev_programs_dict[dev][i]
            send_processId = programname_id_dict[dev_programs_dict[dev][i]]
            send_description = programname_describe_dict[dev_programs_dict[dev][i]]
            send_numberOfOccurrence = error_num[dev_programs_dict[dev][i]]
            send(send_deviceName, send_processName, send_processId, send_description, timeWindow,
                 send_numberOfOccurrence)
    f.close()
if __name__ == '__main__':
    file = 'DevOps_interview_data_set'
    parse_log(file)