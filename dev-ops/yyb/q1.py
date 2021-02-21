#!/bin/python
# -*- coding: utf-8 -*-
import sys
import re
import requests
import json
import copy
from optparse import OptionParser

serverAdd = "https://foo.com/bar"

#command parameter form terminal
def parseOptions():
    parser = OptionParser()
    parser.add_option("-f", "--file", type="string",action="store", dest="fileName", help="given logs file")
    return parser.parse_args()

#used by logsFilter
def getDictValue(hour, deviceName, processId, processName, description):
    dict = {
        'hour':hour,
        'deviceName':deviceName,
        'processId':processId,
        'processName':processName,
        'description':description,
    }
    return dict

# filter logs through regular expression
def logsFilter(file):
    list = []
    pattern1 = "(?P<month>\w+) (?P<day>\d+) (?P<hour>\d+):(?P<minute>\d+):(?P<second>\d+) (?P<deviceName>\w+) \w+.*\s\((?P<processName>\w+.*)?\[?\.(?P<processId>\d+)?\]?\):\s(?P<description>\w+.*)"
    pattern2 = "(?P<month>\w+) (?P<day>\d+) (?P<hour>\d+):(?P<minute>\d+):(?P<second>\d+) (?P<deviceName>\w+) \w+.*\s\((?P<processName>\w+.*)\[(?P<processId>\d+)\]\):\s(?P<description>\w+.*)"
    pattern3 = "(?P<month>\w+) (?P<day>\d+) (?P<hour>\d+):(?P<minute>\d+):(?P<second>\d+) (?P<deviceName>\w+) \w+.*:\s(?P<processName>\w+.*):\s(?P<processId>\d+):\s(?P<description>\w+.*)"
    try:
        with open(file) as f:
            for line in f:
                if re.findall("Undefined error:", line.strip()):
                    res = re.match(pattern2, line.strip())
                    list.append(getDictValue(res.group('hour'), res.group('deviceName'), res.group('processId'), res.group('processName'), res.group('description')))
                elif re.findall("error =", line.strip()):
                    res = re.match(pattern1, line.strip())
                    list.append(getDictValue(res.group('hour'), res.group('deviceName'), res.group('processId'), res.group('processName'), res.group('description')))
                elif re.findall("with_error", line.strip()):
                    res = re.match(pattern3, line.strip())
                    list.append(getDictValue(res.group('hour'), res.group('deviceName'), res.group('processId'), res.group('processName'), res.group('description')))
                else:
                    pass
        return list
    except Exception as err:
        print(err)

## send json data to server via post format
def dataDelivery(dataInfo):
    if isinstance(dataInfo, dict):
        with open('data.json', 'a+') as f:
            f.write(json.dumps(dataInfo) +'\n')
        try:
            res = requests.post(serverAdd, json.dumps(dataInfo))
            if res.status_code == 200:
                print("upload succeeded")
            else:
                print("upload failed")
        except Exception as err:
            print(err)
    else:
        print("json format require，please check parameter")

# data calculate and format
def dataProcess(list):
    newList = copy.deepcopy(list)
    dataOfSender = {
       'deviceName':'',
       'processId':'',
       'processName':'',
       'description':'',
       'timeWindow':'',
       'numberOfOccurrence':'' #根据错误类型统计小时内出现的次数，不考虑processId
    }
    for i in newList:
        del i['processId']
    for i in list:
        if list.count(i) == 1:
            dataOfSender['deviceName'] = i['deviceName']
            dataOfSender['processId'] = i['processId']
            dataOfSender['processName'] = i['processName']
            dataOfSender['description'] = i['description']
            if int(i['hour']) < 9:
                dataOfSender['timeWindow'] = "{}00-0{}00".format(i['hour'], int(i['hour']) + 1)
            else:
                dataOfSender['timeWindow'] = "{}00-{}00".format(i['hour'], int(i['hour']) + 1)
            for j in newList:
                if j['hour'] == i['hour'] and j['deviceName'] == i['deviceName'] and j['description'] == i['description'] and j['processName'] == i['processName']:
                    dataOfSender['numberOfOccurrence'] = newList.count(j)
            dataDelivery(dataOfSender)
        else:
            print(i, "processId 每次都不同，没有发现相同的数据，这里没有做处理")
    
def main():
    (options, args) = parseOptions()
    if options.fileName != None:
        dataProcess(logsFilter(options.fileName))
    else:
        print('please check parameter: -f ')

if __name__ == '__main__':
    main()
