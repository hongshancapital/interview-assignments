# Staff IT Engineer, Beijing - Owner:Hui Yu 
# email: qch1933@163.com
# Date July, 20, 2023
# requirement: 
# 分析系统日志得到关键信息，用 Json 的格式 POST 上传至服务器 ( https://foo.com/bar )，key 的名称在括号里
# 设备名称: (deviceName)
# 错误的进程号码: (processId)
# 进程/服务名称: (processName)
# 错误的原因（描述）(description)
# 发生的时间（小时级），例如 0100-0200，0300-0400, (timeWindow)
# 在小时级别内发生的次数 (numberOfOccurrence)
# After running this script, it will post resulte to server in json format as required.

# can handle one message has multiple lines 

import json
from collections import Counter
import requests
import sys
import os
import re


def fetch_values(line,Statstic):
    #fetch 4 values in each line and added tuple to list
    description = line.split(':',3)[-1].strip('\n').strip()
    # details = []
    segments=line.split()
    deviceName=segments[3]
    rawProcessName=segments[4]
    processName=rawProcessName[0:rawProcessName.rfind('[')]
    rawProcessID=rawProcessName[rawProcessName.rfind('['):rawProcessName.rfind(']')]
    processId=rawProcessID.strip('[')
    rawTime=segments[2].split(':')[0]
    timeWindowBegin="{}00".format(str(int(rawTime)).zfill(2))
    timeWindowEnd="{}00".format((str(int(rawTime)+1)).zfill(2))
    timeWindow="{}-{}".format(timeWindowBegin, timeWindowEnd)
    Statstic.append((deviceName, processName, processId, description, timeWindow))



def main():
    output_dir = './'
    os.chdir(output_dir)

    file_name = "./interview_data_set"
    Statstic = []  # [(tuple),(tuple)]

    lineList = []

    Statstic = []  # [(tuple),(tuple)]

    with open(file_name, mode='rt', encoding='utf-8')as f:
        while True:
            line = f.readline()
            line = line.strip('\n')
            line = line.strip('^\t')
            if len(line) == 0:
                break
            # 处理多行，如 
            # May 13 00:30:00 BBAOMACBOOKAIR2 syslogd[113]: Configuration Notice:
	        #     ASL Module "com.apple.cdscheduler" claims selected messages.
	        #     Those messages may not appear in standard system log files or in the ASL database.
            #  如果日期格式开始的行
            if re.search('\w[3]\s\d{2}:\d{2}:\d{2}',line,flags=re.M):
                # 第一行日期格式开始
                if not lineList:
                    lineList.append(line)
                    # print(lineList)
                else: # 第二行日期格式开始
                    PreviousLine = line
                    mergeLine = ' '.join(lineList)
                    # 处理之前的行
                    fetch_values(mergeLine,Statstic)
                    lineList.clear()
                    lineList.append(PreviousLine)
            else: # 非日期开始的行
                lineList.append(line)
        if lineList:
            mergeLine = ' '.join(lineList)
            # 处理最后一行
            fetch_values(mergeLine,Statstic)

    # 统计小时级别内发生的次数
    counter = Counter(Statstic)
    # print(f'counter is ', counter)

    statsticResult = []
    statsticDic = {}
    keyIndex = ("deviceName", "processName", "processId", "description", "timeWindow", "numberOfOccurrence")

    for key, raw_Occurrence in counter.items():
        deviceName, processId, processName, description, timeWindow = key
        numberOfOccurrence = raw_Occurrence
        statsticDic = dict(zip(keyIndex,(*key, numberOfOccurrence)))
        statsticResult.append(statsticDic)


    #Upload json to server
    headers = {
        "Content-type": "application/json; charset=UTF-8"
    }
    json_data=json.dumps(statsticResult,indent = 2)

    with open(file_name+'.json','wt') as p:
        print (json_data,file=p) 

    print(json_data)

    response = requests.post(url='https://foo.com/bar', headers=headers, verify=False, data=json_data)

    print("The result json formate is uploaded to server: https://foo.com/bar,please check it")



if __name__ == '__main__':
    main()

      