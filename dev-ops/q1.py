#!/usr/bin/env python
# -*- coding:utf-8 -*-

import json
import time
import re
import requests
from datetime import datetime

testfile = "DevOps_interview_data_set"
testurl = "https://foo.com/bar"
#testurl = "http://localhost"

errorDict = {}
"""
errorDict = {
                "error" : [
                  {
                    "description" : "error 1 description",
                    "processId" : ["123", "345", "789"],
                    "processName": "com.apple.mdworker.bundles",
                    "timeWindow": "0100-0200",
                    "numberOfOccurrence":55
                  },
                  {
                    "description" : "error 1 description",
                    "processId" : ["12345", "2345", "6789"],
                    "processName": "com.apple.mdworker.bundles",
                    "timeWindow": "0200-0300",
                    "numberOfOccurrence":23
                  },
                  {
                    "description" : "error 2 description",
                    "processId" : ["12345", "2345", "6789"],
                    "processName": "com.apple.xpc.launchd.domain.pid.mdmclient",
                    "timeWindow": "0200-0300",
                    "numberOfOccurrence":17
                  }
                ]
                "deviceName": "BBAOMACBOOKAIR2"
            }
"""

def convert_timestamp(month_day_HH_MM_SS):
    today = datetime.today()
    year = today.year
    timestr = str(year) + " " + month_day_HH_MM_SS
    try:
        timestamp = int(time.mktime(time.strptime(timestr, '%Y %b %d %H:%M:%S')))
    except:
        print(month_day_HH_MM_SS)
        print(timestr)
        return False
    return timestamp


def get_processNameId(processNameId):
    """
    processNameId has 2 formats
    1. com.apple.mdworker.bundles[12535]
    2. com.apple.xpc.launchd.domain.pid.mdmclient.56260
    """
    # get str xxx between [xxx]
    if re.findall(re.compile(r'[[](.*?)[]]', re.S), processNameId) != []:
        # format 1
        processId = re.findall(re.compile(r'[[](.*?)[]]', re.S), processNameId)[0]
        processName = processNameId.split(r"[")[0]
    elif processNameId.split(".")[-1].isdigit():
        # format 2
        processId = processNameId.split(".")[-1]
        processName = processNameId[:-len(processId)][:-1] # [:-1] remove last "."
    else:
        print("[ERROR] not support format for: " + processNameId)
        return ("", "")
    return (processName, processId)


def data_convert(filename):
    content = ""
    with open(filename, 'r') as f:
        content += f.read()
    """
    some lines have no time prefix like:
    May 13 00:30:00 BBAOMACBOOKAIR2 syslogd[113]: Configuration Notice:
       	ASL Module "com.apple.AddressBookLegacy" claims selected messages.
	Those messages may not appear in standard system log files or in the ASL database.
    May 13 00:30:00 BBAOMACBOOKAIR2 syslogd[113]: Configuration Notice:
	ASL Module "com.apple.MessageTracer" claims selected messages.
	Those messages may not appear in standard system log files or in the ASL database.
    """
    content = content.replace("\n\t", "\t")  # remove \n\t 
    content = content.replace("\n ", " ")  # remove \n[blank]
    content = content.split("\n")
    resultDict = {}
    """
    resultDict = {
                  "error1 description" : {
                           "deviceName" : "xxxxx",
                           "timestampList" : [123456789, 12343435],
                           "processName" : "yyyyy",
                           "processIdList" : [123, 234, 4646]
                           },
                  "error2 description" : {
                           "deviceName" : "zzzzz",
                           "timestampList" : [123456789, 12343435],
                           "processName" : "aaaaa",
                           "processIdList" : [123, 234, 4646]
                           }
                  }
    """
    while "" in content:
        content.remove("")
    for line in content:
        month_day_HH_MM_SS = line[:15]
        timestamp = convert_timestamp(month_day_HH_MM_SS)
        startHour = datetime.fromtimestamp(timestamp/3600*3600).hour
        endHour = startHour + 1
        noTimeStr = line[16:]
        deviceName = noTimeStr.split()
        if deviceName == []:
            continue
        elif deviceName[0] == "---":
            # ignore this type of log : May 13 01:16:14 --- last message repeated 1 time ---
            continue
        elif deviceName[0] == "bogon":
            # ignore this type of log : May 13 14:26:58 bogon timed[158]: settimeofday({0x5ebb9332,0x1d51e}) == 0
            continue
        else:
            deviceName = deviceName[0]
            #print(deviceName)
            noTimeDeviceStr = noTimeStr[len(deviceName):].strip()
        format1 = re.findall(re.compile(r'[(].*\[[0-9]+\][)]', re.S), noTimeDeviceStr)
        # format1 like: com.apple.xpc.launchd[1] (com.apple.mdworker.bundles[12513]): Service exited with abnormal code: 78
        format2 = re.findall(re.compile(r'[(].*\.[0-9]+[)]', re.S), noTimeDeviceStr)
        # format2 like: com.apple.xpc.launchd[1] (com.apple.xpc.launchd.domain.pid.mdmclient.12523): Failed to bootstrap path: path = /usr/libexec/mdmclient, error = 108: Invalid path
        format3 = re.findall(re.compile(r'.*\[[0-9]+\]',re.S), noTimeDeviceStr)
        # format3 like: syslogd[113]: ASL Sender Statistics
        # or: Microsoft PowerPoint[9181]: assertion failed: 19E287: libxpc.dylib + 92807 [32B0E31E-9DA3-328B-A962-BC9591B93537]: 0x89
        if format1 != []:
            # get str: com.apple.mdworker.bundles[12513]
            processNameId = format1[0][1:-1]  # [1:-1] remove () from (xxx)
            (processName, processId) = get_processNameId(processNameId)
        elif format2 != []:
            # get str: com.apple.xpc.launchd.domain.pid.mdmclient.12523
            processNameId = format2[0][1:-1]  # [1:-1] remove () from (xxx) 
            (processName, processId) = get_processNameId(processNameId)
        elif format3 !=[]:
            # get str: syslogd[113]
            processNameId = format3[0]
            (processName, processId) = get_processNameId(processNameId)
        else:
            print(line)
            pass
        try:
            description = noTimeDeviceStr.split(":",1)[1].strip()
        except:
            print(line)
            pass
        if description in resultDict.keys():
            resultDict[description]["timestamp"].append(timestamp)
            resultDict[description]["processId"].append(processId)
        else:
            resultDict[description] = {
                    "deviceName" : deviceName,
                    "timestamp" : [timestamp],
                    "processName" : processName,
                    "processId" : [processId]
                    }

    return resultDict


if __name__ == '__main__':
    resultDict = data_convert(testfile)
    print json.dumps(resultDict, sort_keys=True, indent=4, separators=(',', ': '))
    r = requests.post(testurl, data=json.dumps(resultDict))
    print(r.text)


