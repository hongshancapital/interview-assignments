#!/usr/bin/python3
# -*- coding: UTF-8 -*-

import re
import json
import requests


def logToJson(data, jsonData):
    lines = data.split(re.search('[A-Za-z]* ([0-1][0-9])* ', data).group())  # 用月份和日期分割并合并多行日志
    del lines[0]  # 删除分割后第一个空值
    print("日志总条数" + str(len(lines)))
    for line in lines:
        if '--- last message repeated ' in line:  # 解决重复日志被折叠问题
            number = int(re.search(' \d* ', line).group())
            while number > 0:
                jsonData[key]['numberOfOccurrence'] += 1
                number -= 1
        else:
            deviceName = line.split(' ')[1]
            processId = re.search('[\d*]', line).group()[1:-1]
            processName = line.split('[')[0].split(' ')[-1]
            description = re.split(': ', line, 1)[-1][0:-1]  # 使用冒号+空格进行一次匹配避免错误描述被分割
            pattern = re.search('[0-2][0-9]', line).group()
            timeWindow = '00' + pattern + '-00' + '{:0>2d}'.format(int(pattern) + 1)
            all = {"deviceName": deviceName,
                   "processId": processId,
                   "processName": processName,
                   "description": description,
                   "timeWindow": timeWindow,
                   "numberOfOccurrence": 1}
            key = str(timeWindow + processId + description)  # 使用时间标记+进程ID+错误描述判断是否为相同报错
            if key in jsonData:
                jsonData[key]['numberOfOccurrence'] += 1
            else:
                jsonData[key] = all


if __name__ == '__main__':
    f = open('interview_data_set')
    data = f.read()
    f.close()
    jsonData = {}  # 建立空字典
    logToJson(data, jsonData)  # 调用处理函数

    jsonfile = json.dumps(list(jsonData.values()))  # 格式化json文件
    print(jsonfile)
    req = requests.post(url='https://foo.com/bar', json=jsonfile, verify=False)
    print(req)
    print('结束')
