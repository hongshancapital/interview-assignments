from fileinput import filename
import gzip
import sys
from time import strptime
import re
import pandas as pd
import requests
from isort import file

#参数及全局变量区
filename=sys.argv[0]
desturl="https://foo.com/bar"
httpheader={'Content-Type': 'application/json'}
df=pd.DataFrame(columns=["deviceName","processId","processName","description","timeWindow"])

#时间窗口子串生成函数
def hourstr(h):
    if h==23:
        nexth=0
    else:
        nexth=h+1
    return "{:0>2d}00-{:0>2d}00".format(h,nexth)

#解析单行日志函数
def statlog(h,str):
    infolist=str.split(" ",1)
    devicename=infolist[0]
    process=infolist[1].split(":",1)[0]
    processid=re.findall("\[(.+?)\]",process)[0]
    processname=process.split("[")[0]
    description=str.split(":",1)[1]
    hourseg=hourstr(h)
    global df
    df=df.append({"deviceName":devicename,"processId":processid,"processName":processname,"description":description,"timeWindow":hourseg},ignore_index=True)

#判定单条日志解析次数函数
def loop(str):
    #日志中有“last message repeated”的，表示上一条日志再次重复N次，作为N条内容相同日志进行解析
    if str[16:].startswith("--- last message repeated".encode("utf8")):
        return int(logstr.decode("utf8").split(" ")[7])+1
    else:
        return 1

#主程序
with gzip.open(filename,"rb") as filep:
    logstrlist=filep.readlines()
    #待分析日志，需要保证这条日志永远是格式正确可被解析的
    analystr=logstrlist[0].decode("utf8")
    n=1
    p=1
    while p<len(logstrlist):
        logstr=logstrlist[p]
        try:
            dtime=strptime(logstr[0:15].decode("utf8"),"%b %d %H:%M:%S")
            n=loop(logstr)
            h=strptime(analystr[0:15],"%b %d %H:%M:%S").tm_hour
            #如果日志重复上条，仅认为内容重复，记录日志的时间为当前时间而非原始内容日志的记录时间
            if n>1:
                h=dtime.tm_hour
            i=0
            while i<n:
                statlog(h,analystr[16:])
                i+=1
            #如果日志不是重复上条，意味着是一条新的日志，则需要更新待分析日志为当前日志
            if n==1:
                analystr=logstr.decode("utf8")
        #如果在日志不以时间为开头，视其为上一条日志的内容，追加入待分析日志中
        except ValueError:
            analystr=analystr+logstr.decode("utf8")
        p+=1
    statlog(h,analystr[16:])

#使用pandas的DataFrame进行聚合计数操作并转换为json
df=df.groupby(["deviceName","processId","processName","description","timeWindow"]).size().reset_index(name="numberOfOccurrence")
sendstr = df.to_json(orient="records")
#发送
requests.post(url=desturl,headers=httpheader,data=sendstr)