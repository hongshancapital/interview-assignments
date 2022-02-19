#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# @Date    : 2022-01-09 19:24:00
# @Author  : Berners LK, A senior network engineer with more than ten years of work experience
# @Email   : Berners.lk@gmail.com
# @Certifacate : Cisco & Huawei certified Internetwork Expert,CCIE&HCIE

import pandas as pd
import re
import requests
import json


def DataCollector(filename):
    """ 1.abstract keywords and delete whitespace characters  2.record&ignore invalid lines  (3 put handled data in dictionary structure  3.return invalid Lines & dictionary structure """
    KeyWordDict = {
                    'Month':[],
                    'Day':[],
                    'Time':[],
                    'deviceName':[],
                    'processName':[],
                    'processId':[],
                    'description':[],
                    'timeWindow':[],
                    'numberOfOccurrence':[],
}
    invalidLine = []

    for logLine in open(filename, encoding = "utf-8"):
        regex = re.compile(r"^(\S+) (\S+) (\d{2}:\d{2}:\d{2}) (\S+) (.*?)(\[\d+\])(.*)").search(logLine)
        if regex:
            analyticalData = regex.groups()
            KeyWordDict['Month'].append(analyticalData[0])
            KeyWordDict['Day'].append(analyticalData[1])
            KeyWordDict['Time'].append(analyticalData[2])
            KeyWordDict['deviceName'].append(analyticalData[3])
            KeyWordDict['processName'].append(analyticalData[4])
            KeyWordDict['processId'].append(analyticalData[5].strip("\[\]"))
            KeyWordDict['description'].append(analyticalData[6].strip(" :"))
            hour, minite, second = [int(x) for x in KeyWordDict['Time'][-1].split(":")]
            if hour == 23:
                KeyWordDict['timeWindow'].append(str(hour).rjust(2,"0")+"00"+"-0000")
            else:
                KeyWordDict['timeWindow'].append(str(hour).rjust(2,"0")+"00"+"-"+str(hour+1).rjust(2,"0")+"00")
            KeyWordDict['numberOfOccurrence'].append('N/A')
        else:
            invalidLine.append(logLine)

    return (invalidLine, KeyWordDict)


def DataHandler(KeyWordDict):
    """transform dictionary strcuture into pandas DataFrame structure, and then analyze the number of log occurrence"""
    jsonList = []
    KeyWordDF = pd.DataFrame(KeyWordDict)
    for index, row in  KeyWordDF.groupby(['Month','Day','deviceName','processName','processId','description','timeWindow']).count().iterrows():
        jsonList.append({
                        'deviceName':index[2],
                        'processName':index[3],
                        'processId':index[4],
                        'description':index[5],
                        'timeWindow':index[6],
                        'numberOfOccurrence':str(row['numberOfOccurrence'])
                            })
    return jsonList


def post(json_payload):
    """POST: https://foo.com/bar"""
    url = 'https://foo.com/bar'
    request_headers = {'Content-Type': 'application/json'}

    resp = requests.post(url, data=json_payload, headers=request_headers, verify=False)
    if int(resp.status_code) != 200:
        raise Exception("Fail to Post")


if __name__ == '__main__':
    invalidLine, KeyWordDict = DataCollector('interview_data_set')  #initiate data
    jsonList = DataHandler(KeyWordDict)                             #analyze data & get the result
    json_payload = json.dumps(jsonList)
    post(json_payload)                                              #post data to Server
