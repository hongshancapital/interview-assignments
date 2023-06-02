#!/usr/bin/python
# -*- coding: utf-8 -*-
import re
import json
import requests
url = "https://foo.com/bar"
f = open("DevOps_interview_data_set", encoding="utf8")
done = 0
list = []
while not done:
    l = f.readline()
    if l:
        deviceName =  l.split()[3]
        s = l.split()[5]
        processId = re.findall(r'\d+',s)
        processName = l.split()[4]
        err = "error"
        if err in l:
            description = l.split(":")[3]
        else:
            continue
        try:
            timeB = l.split()[2].split(":")[0]
            timeA = int(timeB)+int(1)
            timeWindow = timeB+"00"+"-"+(str(timeA).zfill(2))+"00"
        except:
            continue

        global range
        range = timeWindow
        log = {"deviceName": deviceName, "processId": processId, "processName": processName, "description": description, "timeWindow": timeWindow}
        j1 = json.dumps(log, ensure_ascii=False, indent=2)
        requests.post(url, json=j1)
    else:
        done = 1
    list.append(range)
dict = {}
for key in list:
    dict[key] = dict.get(key, 0) + 1
j2 = json.dumps(dict, ensure_ascii=False, indent=2)
requests.post(url, json=j2)
f.close()


