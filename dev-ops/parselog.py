#!/usr/bin/env python3
import re
import json
import ssl
import gzip
from urllib import request
logPath = "./DevOps_interview_data_set.gz"
reportUrl = "https://foo.com/bar"

def parseLog():
    with gzip.open(logPath,"rt") as logFile:
        strList = logFile.readlines()
        print(strList[-1])
        strListToParse = []
        tmpStr = None
        shouldSkip = False
        strToParse = "";
        resultDic = {}
        for index in range(len(strList)):
            if shouldSkip:
                shouldSkip = False
                continue
            if tmpStr == None:
                tmpStr = strList[index].replace("\n","")
            if index < len(strList) - 1:
                if (strList[index + 1].startswith("\t")):
                    tmpStr += re.sub("\n|\t","",strList[index + 1])
                    shouldSkip = True
                elif "last message repeated" in strList[index + 1]:
                    strListToParse.append(strList[index].replace("\n",""))
                    strToParse = strList[index].replace("\n","")
                    tmpStr = None
                    shouldSkip = True
                else:
                    strListToParse.append(tmpStr)
                    strToParse = tmpStr
                    tmpStr = None
                    shouldSkip = False
            else:
                strListToParse.append(strList[index].replace("\n",""))
                strToParse = strList[index].replace("\n", "")
                tmpStr = None
            if tmpStr == None:
                timeWindow = getTimeWindow(int(strToParse.split(":",1)[0][-2:]))
                deviceName = strToParse.split(" ",4)[-2]
                processName = strToParse.split(" ",5)[-2].split("[")[0]
                processId = strToParse.split("[")[-1].split("]")[0]
                description = strToParse.split("]",1)[-1].replace(":","",1)
                singleLog = {}
                singleLog["description"] = description
                singleLog["timeWindow"] = timeWindow
                singleLog["deviceName"] = deviceName
                singleLog["processName"] = processName
                singleLog["processId"] = processId
                singleLog["numberOfOccurrence"] = 1
                if timeWindow in resultDic:
                    if description in resultDic[timeWindow]:
                        resultDic[timeWindow][description]["numberOfOccurrence"] = resultDic[timeWindow][description]["numberOfOccurrence"] + 1
                    else:
                        resultDic[timeWindow][description] = singleLog
                else:
                    resultDic[timeWindow] = {}
        return json.dumps(resultDic)

def getTimeWindow(hour):
    assert hour < 24
    return "%02d00-%02d00" % (hour,hour+1)

def reportParsedLog(jsonLog):
    ctx = ssl._create_unverified_context()
    req = request.Request(reportUrl)
    req.add_header("Content-Type","application/json")
    with request.urlopen(req,data=prasedLog.encode("utf-8"),context=ctx) as f:
        print(f.read().decode("utf-8"))

if __name__ == '__main__':
    prasedLog = parseLog()
    reportParsedLog(parseLog())