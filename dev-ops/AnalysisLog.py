#!/usr/bin/env python
# --coding:utf-8 --
import json
import re

import requests
import ssl
try:
    _create_unverified_https_context=ssl._create_unverified_context
except AttributeError:
    pass
else:
    ssl._create_default_https_context=_create_unverified_https_context


def get_device_name(device_name):
    # 进行相应的正则匹配和配对
    if device_name == 'BBAOMACBOOKAIR2':
        return device_name
    else:
        return None


def get_process_id(line):
    start = 0
    j = 0
    for i, item in enumerate(line):
        if item == " ":
            if j == 3:
                start = i
            j = j + 1
    end = re.search(": ", line).start()
    s = line[start: end]
    partern = re.compile("\d+")
    res = re.findall(partern, s)
    if len(res) > 0: return res[len(res) - 1]
    return res


def get_process_name(line):
    start = 0
    j = 0
    for i, item in enumerate(line):
        if item == " ":
            if j == 3:
                start = i
            j = j + 1
    end = re.search(": ", line).start()
    s = line[start: end].split(" ")[-1]
    m = ''.join(re.split("\d+", s)).replace("(", "").replace(")", "").replace("[", "").replace("]", "").replace(":", "")
    if m.endswith("."):
        return m[:-1]
    return m

def get_description(line):

    start = re.search(": ", line).start()
    return line[start + 1:]


def get_hour(time):
    hour = time.split(":")[0]
    return hour


def deal_file(file_name):
    map1 = {}
    with open(file_name, "r") as f:
        for line in f.readlines():
            device_name = get_device_name(line.split(" ")[3])
            if device_name is None: continue
            process_id = get_process_id(line)
            if process_id is None: continue
            process_name = get_process_name(line)
            if process_name is None: continue
            description = get_description(line)
            if description is None: continue
            hour = get_hour(line.split(" ")[2])
            if hour is None: continue

            key = hash(hour + "    " + device_name + "   " + process_id + "    " + process_name + "    " + description)
            if map1.get(key) is None:
                map2 = {}
                map2["hour"] = hour
                map2["device_name"] = device_name
                map2["process_id"] = process_id
                map2["process_name"] = process_name
                map2["description"] = description
                map2["number_of_occurrence"] = 1
                map1[key] = map2
            else:
                map1.get(key)["number_of_occurrence"] = map1.get(key).get("number_of_occurrence") + 1
    url = "https://foo.com/bar"
    l1 = list()
    for value in map1.values():
        print(json.dumps(value))
        # r = requests.post(url=url, data=value, verify=False)
        # print(r)





if __name__ == '__main__':
    deal_file("./DevOps_interview_data_set");