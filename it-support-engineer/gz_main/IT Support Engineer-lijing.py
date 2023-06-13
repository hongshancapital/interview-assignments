#-*- coding:utf-8 -*-
import gzip
import re
import json
import requests


def getprocessname(listdata):
    try:
        if len(re.findall("(.*?\[.*?\]\):)",listdata[5])) > 0:
            return (listdata[4]+listdata[5]).replace(":","")
        else:
            return listdata[4].replace(":","")
    except:
        return " "

def getprocessid(listdata):
    try:
        return re.findall("[\d]+", listdata[4])[0]
    except:
        return " "

def getdeviceName(listdata):
    try:
        return listdata[3].split("[")[0]
    except:
        return " "

def gettimeWindow(listdata):
    # str(lst[2]).replace(':','')[2:]
    try:
        hour = str(listdata[2]).split(":")[0]
        if int(hour[0]) ==0 and int(hour[1]) < 9:
            return f'0{hour[1]}00-0{str(int(hour[1])+1)}00'
        elif int(hour[0]) ==0 and int(hour[1]) == 9:
            return '1000-1100'
        else:
            return f'{hour}00-{str(int(hour)+1)}00'
    except:
        return " "

def getdescription(listdata):
    try:
        if len(re.findall("(.*?\[.*?\]\):)", listdata[5])) > 0:
            return " ".join(listdata[6:])
        else:
            return " ".join(listdata[5:])

    except:
        return " "

def postdata(datas):
    '''
    上传数据
    :param datas: 上传数据
    :return: None
    '''
    url = 'https://foo.com/bar'
    res = requests.post(url,json = datas)
    if res.status_code == 200:
       print("数据上传成功。")

if __name__ == '__main__':
    '''
    分析系统日志：interview_data_set.gz
    分析系统日志得到关键信息，用 Json 的格式 POST 上传至服务器 ( https://foo.com/bar )，key 的名称在括号里
    设备名称: (deviceName)
    错误的进程号码: (processId)
    进程/服务名称: (processName)
    错误的原因（描述）(description)
    发生的时间（小时级），例如 0100-0200，0300-0400, (timeWindow)
    在小时级别内发生的次数 (numberOfOccurrence)
    '''
    #读取数据
    file_name = 'interview_data_set.gz'
    with gzip.open('interview_data_set.gz',mode="r") as f:
        datatxt = f.read().decode('utf8').replace("\n\t"," ").split("\n")
        f.close()
    #定义中间变量
    alldatarecode = {}
    alldatas = []
    newdatas = []
    #循环读取数据
    for line in datatxt:
        if "last message repeated" in line:
            continue
        if line == '':
            continue
        lst = line.strip().split(" ")
        lstid = re.findall("[\d]+",lst[4])
        processname = getprocessname(lst)
        processid = getprocessid(lst)
        deviceName = getdeviceName(lst)
        timeWindow = gettimeWindow(lst)
        description = getdescription(lst)
        item = {
            "processname": processname,
            "processid": processid,
            "deviceName": deviceName,
            "timeWindow": timeWindow,
            "description": description,
        }
        alldatas.append(item)

        # 记得每个进程 每个时间断的报错次数
        if processname in list(alldatarecode.keys()):
            if timeWindow in alldatarecode.get(processname).keys():
                alldatarecode.get(processname)[timeWindow] = alldatarecode.get(processname).get(timeWindow) + 1
            else:
                alldatarecode.get(processname)[timeWindow] = 1

        else:
            alldatarecode[processname] = {}
            alldatarecode.get(processname)[timeWindow] =  + 1

    for data in alldatas:
        data["numberOfOccurrence"] = alldatarecode[data.get("processname")][data.get("timeWindow")]
        #print(data)
        newdatas.append(data)

    # 上传数据
    postdata(newdatas)
