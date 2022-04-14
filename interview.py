import sys
import json
import re
import requests
preline = ""
curline = ""

dict_all = {} # 记录时间的dict， 每个时间段，每种错误的出现次数
list_all = [] # 按时间顺序，记录每行文本中，抽取的dict

def getTime(strs):
    hour, _, _ = strs.strip().split(':')
    start = int(hour)
    end = start + 1
    times = ""
    if end < 10:
        times = '0' + str(start) + "00-" + '0' + str(end) + "00"
    elif end == 10:
        times = '0' + str(start) + "00-" + str(end) + "00"
    else:
        times = str(start) + "00-" + str(end) + "00"
    return times

def proprocess(line):
    curline = line.strip()
    parts = curline.split(' ')
    _, _, time, deviceName = parts[0], parts[1], parts[2], parts[3]
    otherinfo = parts[4:]
    processinfo = ' '.join(otherinfo).strip().split(':')[0].strip().split(' ')[-1]
    errorinfo = ' '.join(' '.join(otherinfo).strip().split(':')[1:])
    errorinfo = errorinfo.strip()
    processinfo = processinfo.replace('(','').replace(')','')
    matchObj = re.search( r'(.*)\[(\d+)\]', processinfo, re.M|re.I)
    if matchObj != None:
        processName = matchObj.group(1)
        processId = matchObj.group(2)
        times = getTime(time)
        dicts = {}
        dicts['timeWindow'] = times
        dicts['deviceName'] = deviceName
        dicts['processId'] = processId
        dicts['processName'] = processName
        dicts['description'] = errorinfo
        list_all.append(dicts)
        if times not in dict_all:
            dict_all[times] = {errorinfo:1}
        else:
            errorinfo_dict = dict_all[times]
            if errorinfo not in errorinfo_dict:
                errorinfo_dict[errorinfo] = 1
            else:
                errorinfo_dict[errorinfo] = errorinfo_dict[errorinfo] + 1
    else:
        return

preLine = ""
for line in open(sys.argv[1]):
    curline = line.strip()
    parts = curline.split(' ')
    if parts[-1][-1] == ':' and parts[0] == "May":
        if preLine != "":
            proprocess(preLine.strip())
        preLine = curline
    elif parts[0] != "May":
        preLine =preLine +" "+ curline
        preLine = preLine.strip()
    elif parts[-1][-1] != ':' and parts[0] == "May":
        if preLine.strip() != "":
            proprocess(preLine.strip())
        preLine = ""
        proprocess(curline)

url = "https://foo.com/bar"
headers = {'Content-Type': 'application/json;charset=UTF-8'}

for line_dict in list_all:
    time = line_dict['timeWindow']
    errorinfo = line_dict['description']
    if time in dict_all:
        error_dict_count = dict_all[time]
        if errorinfo in error_dict_count:
            num = error_dict_count[errorinfo]
            line_dict['numberOfOccurrence'] = num
            j = json.dumps(line_dict)
            print(j)
            res = requests.request("post",url,json=j,headers=headers)
            print(res.status_code)
            print(res.text)
