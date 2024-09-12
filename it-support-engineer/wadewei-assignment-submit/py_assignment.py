#!/usr/bin/env python
# -*- coding: utf-8
'''
##################################################################################################################################################
####用途：该脚本用于分析interview_data_set日志文件，获取以下信息并用json格式POST上传至服务器https://foo.com/bar (测试用http://httpbin.org/post)      ###
####1.设备名称:(deviceName)                                                                                                                     ###
####2.错误的进程号码:(processID)                                                                                                                 ###
####3.进程/服务名称:(processName)                                                                                                                ###
####4.错误的原因(描述):(description)                                                                                                             ###
####5.发生的时间(小时级):(timeWindow)                                                                                                            ###
###6.小时级timeWindow发生的次数:(numberOfOccurrence)                                                                                            ###
####系统要求:macOS 10.10.x or higher, Windows 10 or higher                                                                                                           ###
####用法: [python] /path/to/assignment.py /path/to/interview_data_set                                                                          ###
####作者:魏小忠                                                                                                                                ###
#################################################################################################################################################
'''

import sys
import re
import json
import requests

#定义方法获取需要的信息列表，为过滤需要的条目做准备
def getInfo(filelines, pattern, *args):
    infoList = []
    if len(args) == 2:
        sep = args[0]
        col = args[1]
        for fileline in filelines:
            result = re.search(pattern, fileline.split(sep)[col])
            if result:
                infoList.append(result.group(1))
    elif len(args) == 0:
        for fileline in filelines:
            result = re.search(pattern, fileline)
            if result:
                infoList.append(result.group(1))
    return infoList

if __name__ == "__main__":
    #文件作为参数传递
    if len(sys.argv) != 2:
        print("Usage: [python] /path/to/assignment.py /path/to/interview_data_set")
        exit(1)
    try:
        with open(sys.argv[1], "r") as file:
                filelines = file.readlines()
    except Exception as e:
        print("Please provide a valid text file!")
        exit(1)
    
    #获取设备名称:(deviceName) 
    pattern = "(.+)"
    infoList = getInfo(filelines,pattern,' ', 3)
    infoSet = set(infoList)
    infoDict = dict()
    for info in infoSet:
        infoDict[info] = infoList.count(info)
    deviceName = max(infoDict,key=infoDict.get)
    #print(deviceName)

    #获取进程/服务名称:(processName)
    pattern = "(com\.apple[A-Za-z\.]+)"
    infoList = getInfo(filelines, pattern, ' ', 5)
    infoSet = set(infoList)
    infoDict = dict()
    for info in infoSet:
        infoDict[info] = infoList.count(info)
    processName = max(infoDict,key=infoDict.get)
    #print(processName)

    #获取错误的进程号码:(processID) 
    pattern = processName + "\[([0-9]+)\]"
    infoList = getInfo(filelines, pattern, ' ', 5)
    infoSet = set(infoList)
    processIDs = ' '.join(infoSet)
    #print(processIDs)

    

    #获取错误的原因(描述):(description)
    pattern = processName + "\[[0-9]+\]\):\s+(.*)"
    infoList = getInfo(filelines, pattern)
    infoSet = set(infoList)
    description = ' '.join(infoSet)
    #print(description)

    #发生的时间(小时级):(timeWindow)以及对应小时级timeWindow发生的次数:(numberOfOccurrence)
    #pattern = "(\d\d):\d\d:\d\d.*" + processName + "\[[0-9]+\]"
    #infoList = getInfo(filelines,pattern)
    pattern = "(\d\d):\d\d:\d\d.*(" + processName + "\[[0-9]+\])"
    infoList = []
    for fileline in filelines:
        result = re.search(pattern, fileline)
        if result:
            infoList.append((result.group(1),result.group(2)))
    infoSet = set(infoList)
    
    infoList = [int(item[0]) for item in infoSet]
    infoListUniq = list(set(infoList))
    infoListUniq.sort()
    hourlyTimeWindowEvents = ""
    for item in infoListUniq:
        timeWindowStart = item
        numberOfOccurence = infoList.count(item)
        timeWindowEnd = item + 1
        timeWindowStart = str(timeWindowStart) + "00" if len(str(timeWindowStart)) == 2 else "0" + str(timeWindowStart) + "00"
        timeWindowEnd = str(timeWindowEnd) + "00" if len(str(timeWindowEnd)) == 2 else "0" + str(timeWindowEnd) + "00"
        eventHappenTimeWindow = "TimeWindow: " + timeWindowStart + "-" + timeWindowEnd + "  numberOfOccurence: " + str(numberOfOccurence) + "\n"
        hourlyTimeWindowEvents += eventHappenTimeWindow
    #print(hourlyTimeWindowEvents)

    infoBody = {
        "deviceName":deviceName,
        "processName":processName,
        "processIDs":processIDs,
        "description":description,
        "hourlyTimeWindowEvents":hourlyTimeWindowEvents
    }
    jsonInfoBody = json.dumps(infoBody)
    requestheader = {'Content-Type': 'application/json'}
    #serverURL = https://foo.com/bar 
    serverURL = "http://httpbin.org/post"
    response = requests.post(serverURL,headers=requestheader,data=jsonInfoBody)
    if response:
        print("Upload event info of %s successfully!" % (deviceName))
        jsonObj = json.loads(response.text)['json']
        print(json.dumps(jsonObj,sort_keys=True,indent=4,separators=(',',': ')))
    else:
        print("Upload event info of %s failed! Please try a working %s" %(deviceName, serverURL))
        print(jsonInfoBody)

















