import os
import sys
import re
import requests
import json
import gzip

dirPath = os.path.dirname(os.path.realpath(__file__))
gzPath = dirPath + os.sep + 'interview_data_set.gz'
logPath = dirPath + os.sep + 'interview_data_set'
jsonPath = dirPath + os.sep + 'interview_data_set.json'

json_list = []
count_list = []

headers = {
    "Content-Type": "application/json; charset=UTF-8",
    }
url = "https://foo.com/bar"

def openFile(lp):
    with open(lp, 'r') as f:
        content_all = ''
        while True:
            if f.tell() == 0:
                content_all = f.readline()
            content = f.readline()
            if 'last message repeated 1 time' in content:
                content = content_all
            if content.startswith('May'):
                pushJson(str(content_all))
                content_all = ''
            content_all += content

            if content == '':
                pushJson(str(content_all))
                break

def pushJson(ca):
    # print(content_all)
    pattern = re.compile(r'(.*?)(\d+:\d+:\d+)(\s\w+\s)(.+\[\d+\].*?:)')
    result = pattern.search(ca)
    # print(result.groups())
    deviceName = result.group(3).strip()
    processId = result.group(4).split('[')[1].split(']')[0].strip()
    processName = result.group(4).split('[')[0].strip()
    description = pattern.split(ca)[-1].strip()
    hour_1 = result.group(2).split(':')[0]
    hour_2 = int(result.group(2).split(':')[0]) + 1 if result.group(2).split(
        ':')[0] != 23 else '00'
    json_dict = {
        'deviceName': deviceName,
        'processId': processId,
        'processName': processName,
        'description': description,
        'timeWindow':hour_1 + '00-' + '{:0>2d}'.format(hour_2) +'00'
    }
    # print(json_dict)
    if json_dict not in json_list:
        json_list.append(json_dict)
        count_list.insert(json_list.index(json_dict),1)
    else:
        count_list[json_list.index(json_dict)] +=1

def writeJson(jl,cl):
    for index, json_item in enumerate(jl):
        json_item['numberOfOccurrence'] = cl[index]
    # with open(jsonPath, "a", encoding="utf-8") as f:
    #     f.write(str(json_list))
    response = requests.post(url, data=json.dumps(json_list), headers=headers).text
    print(response)

def ungz(gp,lp):
    log_file = gzip.GzipFile(gp)
    open(lp, "wb+").write(log_file.read())
    log_file.close()

if __name__ == '__main__':
    ungz(gzPath, logPath)
    openFile(logPath)
    # print(json_list)
    # print(count_list)
    writeJson(json_list,count_list)