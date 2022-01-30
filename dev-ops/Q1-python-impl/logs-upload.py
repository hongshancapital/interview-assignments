#!/usr/bin/python
# -*- coding: UTF-8 -*-
import gzip
import re
import json
import requests

# 正则匹配: 提取小时,设备,进程名,进程编号和尾部日志内容
headPattern = re.compile(r'^\w.*\d{1,2}\s+(\d{2}):\d{2}:\d{2}\s+(\w+)\s+([a-zA-Z\.]*)\[(\d+)\].?\s+(.*)')
otherPattern = re.compile(r'^\w.*\d{1,2}\s+(\d{2}):\d{2}:\d{2}\s+(---.*---$)')
skipThreadPattern = re.compile(r'\(.*\).?\s+(.*)')


# 一次加载gzip文本内容(适合小文件,过大的文件需要优化成 Read In Chunks 模式)
def readGzipLogFile(file):
    try:
        with gzip.open(file, 'r') as f:
            return str(f.read(), encoding='utf8')
    except Exception as e:
        print('解析gzip文件异常:', e)
        return ''


# 日志格式预处理,去掉\n\t的字符串
def preCleanBreakLineTabFmtHandler(logContent):
    return logContent.replace('\n\t', '')


# 提取日志
def extractLog(s):
    m = headPattern.match(s)
    log = LogEntity.empty()
    # 提取日志头信息
    if m:
        log.timeWindow = timeWindowFmt(m.group(1))
        log.deviceName = m.group(2)
        log.processName = m.group(3)
        log.processId = m.group(4)
        cxt_match = skipThreadPattern.match(m.group(5))
        log.description = cxt_match.group(1) if cxt_match else m.group(5)
    else:
        m = otherPattern.match(s)
        # 提取时段和内容
        if m:
            log.timeWindow = timeWindowFmt(m.group(1))
            log.description = m.group(2)
    return log

# 时段区间函数
def timeWindowFmt(s):
    i = int(s, 10)
    return '2300-0000' if i == 23 else f"{s}00-{str('%02d' % (i+1))}00"

# 按时段汇总
def partitionByTimePeriod(logs):
    result = []
    for log in logs:
        if log.timeWindow == '':
            continue
        if len(result) == 0:
            result.append(log)
            continue
        hasFound = False
        for d in result:
            if d == log:
                d.numberOfOccurrence += 1
                hasFound = True
                break
        if not hasFound:
            result.append(log)
    print(len(result))
    return result


# 程序主要逻辑
def runtimeProg(gzfile, url):
    # 1. 读取gzip文件
    content = readGzipLogFile(gzfile)
    if len(content) == 0:
        print('解析日志文件失败')
        return
    # 2. 日志格式预处理
    logs = preCleanBreakLineTabFmtHandler(content).split('\n')
    print(len(logs))
    # 3. 提取日志
    logEntities = []
    for log in logs:
        logEntities.append(extractLog(log))
    print('logEntities size:', len(logEntities))
    # 4. 汇总日志
    data = partitionByTimePeriod(logEntities)
    # 5. HTTP方式上传内容
    sendToServer(url, data)


# 发送到服务器
def sendToServer(url, data):
    param = []
    header = {'Content-Type': 'application/json'}
    for d in data:
        param.append(d.__dict__)
    print(json.dumps(param))    
    r = requests.post(url, data=json.dumps(param), headers=header)
    print(r)


class LogEntity(object):
    '日志实体类'

    def __init__(self, deviceName, processId, processName, description, timeWindow, numberOfOccurrence):
        self.deviceName = deviceName
        self.processId = processId
        self.processName = processName
        self.description = description
        self.timeWindow = timeWindow
        self.numberOfOccurrence = numberOfOccurrence

    def __eq__(self, other):
        # 判断逻辑可按需求做调整
        # 判断时段,描述,设备属性业务语义相等
        # return self.timeWindow == other.timeWindow and self.description == other.description and self.deviceName == other.deviceName
        # 判断时段,描述,设备,进程id属性业务语义相等
        return self.timeWindow == other.timeWindow and self.description == other.description and self.deviceName == other.deviceName and self.processName == other.processName and self.processId == other.processId

    @classmethod
    def empty(self):
        return LogEntity('', '', '', '', '', 1)


# main程序入口
if __name__ == '__main__':
    # gzip文件路径
    logFile = '../DevOps_interview_data_set.gz'
    # 上传url地址
    url = 'https://foo.com/bar'
    # 程序编排执行
    runtimeProg(logFile, url)
