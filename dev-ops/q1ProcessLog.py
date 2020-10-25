#!/usr/bin/env python3

import re
import json
from itertools import islice 
from datetime import datetime

def getEachInfo(s, patt):
    """
    return a dictionay with the matched result
    """

    matchRes = patt.match(s)
    res = {}
    if matchRes:
        deviceName = matchRes.group("deviceName")
        processId = matchRes.group("processId")
        processName = matchRes.group("processName")
        description = matchRes.group("description")

        # "May 13 00:30"
        time = matchRes.group("timeWindow")
        startStr = re.match(r'[A-Z].*\s(.*?):', time).groups()[0]
        startTime = datetime.strptime(startStr, "%H")
        end = (int(startStr) + 1) % 24
        endtime = datetime.strptime(str(end), "%H")
        timeWindow = startTime.strftime("%H00-") + endtime.strftime("%H00")

        # update the res dictionary
        res["deviceName"] = deviceName
        res["processId"] = processId
        res["processName"] = processName
        res["description"] = description
        res["timeWindow"] = timeWindow
        return res
    return ""

def readFile(fileName):
    """
    return a list with all the needed keys and values
    """
    resList = []
    p = re.compile(r'((?P<timeWindow>[A-Z].*?:[0-9]{2}):[0-9]{2}(\s))(?P<deviceName>[\w]+)\s(?P<processName>[\w].*?\[(?P<processId>\d+)\]\)?):\s(?P<description>[\w].*)')
    with open(fileName ) as fobj:
        for line in fobj:
            if line.endswith(":\n"):
                multipP = re.compile(r'((?P<timeWindow>[A-Z].*?:[0-9]{2}):[0-9]{2}(\s))(?P<deviceName>[\w]+)\s(?P<processName>[\w].*?\[(?P<processId>\d+)\]\)?):\s(?P<description>[\w].*\.)', re.DOTALL)
                mergedLines = line.strip() + " " + " ".join([x.strip() for x in islice(fobj, 2)])
                info = getEachInfo(mergedLines, multipP)
                if info:
                    resList.append(info)
            else:
                info = getEachInfo(line, p)
                if info:
                    resList.append(info)
    return resList

def getOccurrence(resList):
    """
    get the result for numberOfOccurrence
    """
    occurrenceNumDict = {}
    for log in resList:
        timeWindow = log.get("timeWindow")
        if occurrenceNumDict.get(timeWindow):
            occurrenceNumDict[timeWindow] += 1
        else:
            occurrenceNumDict[timeWindow] = 1
    return occurrenceNumDict
    

def main():
    # resList = readFile("test.txt")
    resList = readFile("DevOps_interview_data_set")

    repeatTimesDict = getOccurrence(resList)

    resList.append(repeatTimesDict)

    print(json.dumps(resList))

if __name__ == "__main__":
    main()