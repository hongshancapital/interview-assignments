#!/usr/bin/env python

import json
import re
import requests
import gzip
import sys
from os import path

"""
Author: Eric Zhang
Date:   2020/11/06
Desciption: This application is used to parse log and error statistics into JSON
                 and send it to url https://foo.com/bar

Error code:
0   successfully exit
1   file not found
2   HTTP POST failed

The output json is like the following:
{
    "logDetails": [
        {"deviceName", "xxxxx",
        "processId": "xxx",
        "processName": "xxx",
        "description": "xxx"
        "timeWindow": "xxx" },
        ...
    ],
    "errorStatistics": {
        "0000-0100": x,
        "0100-0200": x,
        ...
        "2300-0000": x
    }
}
"""


def logfile_handler(filename, start_regex):
    """
    using start_regex to find out each entire log item
    And send it to log parser
    """
    if(path.exists(filename) == False):
        exit_with_msg('File ' + filename + ' not found!', 1)

    pattern = re.compile(start_regex)
    one_entire_log = ''

    log_details = []  # stroing all parsed log
    time_window_statistics = {}  # stroing error statistics
    # entire json
    results_dict = {'logDetails': log_details,
                    'errorStatistics': time_window_statistics}

    # read log file
    log_file = gzip.open(filename, "rt")  # read txt from gz
    for line in log_file:
        if(pattern.match(line)): # first line of the log            
            if one_entire_log != '':
                # previous entire log is stored in var one_entire_log
                log_parser(one_entire_log, log_details, time_window_statistics)
            one_entire_log = line
        else: # the rest line of the log            
            one_entire_log = one_entire_log + line
    ## end of for

    # send last one to log parser
    log_parser(one_entire_log, log_details, time_window_statistics)

    log_file.close()
    return results_dict


def log_parser(log_item, log_details, time_window_statistics):
    """
    parsing log like: 
    May 13 23:54:26 BBAOMACBOOKAIR2 com.apple.xpc.launchd[1] (com.apple.mdworker.bundles[56353]): Could not find uid associated with service: 0: Undefined error: 0 501

    extracting the following fields:
    1. 设备名称: (deviceName)
    2. 错误的进程号码: (processId)
    3. 进程/服务名称: (processName)
    4. 错误的原因（描述）(description)
    5. 发生的时间（小时级），例如0100-0200，0300-0400, (timeWindow)
    6. 在小时级别内发生的次数 (numberOfOccurrence)
    """    
    regex = r"""^\w{3}\s\d{1,2}\s(?P<hour>\d{2}):\d{2}:\d{2}\s  # hour only
    (?P<deviceName>\w+?)\s                                      # deviceName
    (?P<processName>.*?)\[(?P<processId>.*?)\](\s\(.*?\))?:\s   # processId and ProcessName
    (?P<description>.*(\n.*)*)                                  # description
    """

    # verbose and multi mode
    pattern = re.compile(regex, re.VERBOSE | re.MULTILINE)
    m = pattern.match(log_item)
    if(m):
        one_log = m.groupdict()
        one_log['timeWindow'] = convertTimeWindow(one_log['hour'])
        del one_log['hour']
        # count error
        if one_log['timeWindow'] not in time_window_statistics:
            time_window_statistics[one_log['timeWindow']] = 0
        time_window_statistics[one_log['timeWindow']] += 1
        log_details.append(one_log)


def convertTimeWindow(hour):
    """
    convert hour to timeWindow
    02 -> 0200-0300
    """
    template = "{start_hour:0>2d}00-{end_hour:0>2d}00"
    start_hour = int(hour)
    end_hour = start_hour + 1
    if(end_hour == 24):
        end_hour = 0
    return template.format(start_hour=start_hour, end_hour=end_hour)


def exit_with_msg(msg, error_code):
    """
    exit with error msg and exit code
    """
    print('Error: ' + msg)
    sys.exit(error_code)


def post_json(url, log_json):
    try:
        r = requests.post(url, json=json.dumps(log_json))
        if(r.ok == False):
            exit_with_msg("failed to post json to url: " + url, 2)
    except:
        exit_with_msg("failed to post json to url: " + url, 2)


def main():
    # process log
    starting_regex = r'^\w{3}\s\d{1,2}\s\d{2}:\d{2}:\d{2}'
    filename = 'DevOps_interview_data_set.gz'
    result_dict = logfile_handler(filename, starting_regex)

    # post json
    url = 'https://foo.com/bar'
    post_json(url, result_dict)


if __name__ == "__main__":
    main()
