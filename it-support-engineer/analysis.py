#!/usr/bin/env python3
# encoding: utf-8
# ===============================================================================
#
#         FILE:
#
#        USAGE:
#
#  DESCRIPTION:
#
#      OPTIONS:  ---
# REQUIREMENTS:  ---
#         BUGS:  ---
#        NOTES:  ---
#       AUTHOR:  x2x4
#      COMPANY:
#      VERSION:  1.0
#      CREATED:
#     REVISION:  ---
# ===============================================================================

import re
from typing import AnyStr, Dict, List, Union
from datetime import datetime
from hashlib import sha1
import json

# 输出格式
# {
#     sha1"process_name+pid": LogDetail,
#     ...
# }


class DataStruct(dict):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        
    def to_json(self):
        return json.dumps(self)
    

class LogDetail(object):
    device_name: AnyStr
    process_id: int
    process_name: AnyStr
    description: List[AnyStr]
    time_window: Dict
    
    def to_json(self):
        return json.dumps(self.__dict__())

    def __dict__(self):
        return {
            "deviceName": self.device_name,
            "processId": self.process_id,
            "processName": self.process_name,
            "description": "\n".join(self.description),
            "timeWindow": self.time_window
        }


def _gen_time_windows() -> dict:
    return {
        "0000-0100": {"numberOfOccurrence": 0},
        "0100-0200": {"numberOfOccurrence": 0},
        "0200-0300": {"numberOfOccurrence": 0},
        "0300-0400": {"numberOfOccurrence": 0},
        "0400-0500": {"numberOfOccurrence": 0},
        "0500-0600": {"numberOfOccurrence": 0},
        "0600-0700": {"numberOfOccurrence": 0},
        "0700-0800": {"numberOfOccurrence": 0},
        "0800-0900": {"numberOfOccurrence": 0},
        "0900-1000": {"numberOfOccurrence": 0},
        "1000-1100": {"numberOfOccurrence": 0},
        "1100-1200": {"numberOfOccurrence": 0},
        "1200-1300": {"numberOfOccurrence": 0},
        "1300-1400": {"numberOfOccurrence": 0},
        "1400-1500": {"numberOfOccurrence": 0},
        "1500-1600": {"numberOfOccurrence": 0},
        "1600-1700": {"numberOfOccurrence": 0},
        "1700-1800": {"numberOfOccurrence": 0},
        "1800-1900": {"numberOfOccurrence": 0},
        "1900-2000": {"numberOfOccurrence": 0},
        "2000-2100": {"numberOfOccurrence": 0},
        "2100-2200": {"numberOfOccurrence": 0},
        "2200-2300": {"numberOfOccurrence": 0},
        "2300-0000": {"numberOfOccurrence": 0},
    }


def _get_time_windows(hour: int) -> str:
    if 0 <= hour < 23:
        return "%02d00-%02d00" % (hour, hour+1)
    elif hour == 23:
        return "%02d00-%02d00" % (hour, 0)
    
    raise RuntimeError("Hour not 0 - 23, data: {}".format(hour))


def _find_repeated(data: DataStruct, line: str, last_key: str) -> (bool, dict, str):
    # May 13 00:22:49 --- last message repeated 1 time ---
    re_str = re.compile(r'^(?P<time>\w+\s\d\d?\s\d\d:\d\d:\d\d)\s---\slast\smessage\srepeated\s(?P<repeat>\d+)\stime\s---.*')
    if last_key == '':
        return False, data, last_key
    t = re_str.search(line)
    if t is not None and 'repeat' in t.groupdict() and 'time' in t.groupdict():
        _time = datetime.strptime(t.groupdict()['time'], "%b %d %H:%M:%S")
        _time_window_key = _get_time_windows(_time.hour)
        data[last_key].time_window[_time_window_key]['numberOfOccurrence'] += int(t.groupdict()['repeat'])
        return True, data, last_key
    return False, data, last_key


def _find_more_info(data: DataStruct, line: str, last_key: str) -> (bool, dict, str):
    re_str = re.compile(r'^(?:\s|\t)+.*')
    if last_key == '':
        return False, data, last_key
    t = re_str.search(line)
    if t is not None:
        data[last_key].description.append(line)
        return True, data, last_key
    return False, data, last_key
    

def _find_normal(data: DataStruct, line: str) -> (bool, dict, str):
    # May 13 00:04:20 BBAOMACBOOKAIR2 syslogd[113]: ASL Sender Statistics
    # May 13 00:23:50 BBAOMACBOOKAIR2 timed[158]: settimeofday({0x5ebacd96,0x52ddf}) == 0
    # May 13 00:28:17 BBAOMACBOOKAIR2 VTDecoderXPCService[960]: DEPRECATED USE in libdispatch client: Changing the target of a source after it has been activated; set a breakpoint on _dispatch_bug_deprecated to debug
    re_str = re.compile(r'^(?P<time>\w+\s\d\d?\s\d\d:\d\d:\d\d)\s(?P<device_name>[^\s]+)\s(?P<process_info>[a-zA-Z0-9\-_]+)\[(?P<pid>\d+)\]:\s(?P<error_info>.*)')
    is_find = False
    _key = ''

    t = re_str.search(line)
    if t is not None \
            and 'time' in t.groupdict() \
            and 'device_name' in t.groupdict() \
            and 'process_info' in t.groupdict() \
            and 'pid' in t.groupdict() \
            and 'error_info' in t.groupdict():
        is_find = True
        process_info = t.groupdict()['process_info']
        pid = t.groupdict()['pid']
    
        _key = sha1(str(process_info+pid).encode()).hexdigest()
        _time = datetime.strptime(t.groupdict()['time'], "%b %d %H:%M:%S")
        _time_window_key = _get_time_windows(_time.hour)

        if _key not in data:
            # New data
            log_detail = LogDetail()
            log_detail.device_name = t.groupdict()['device_name']
            log_detail.process_id = t.groupdict()['pid']
            log_detail.process_name = t.groupdict()['process_info']
            log_detail.description = list()
            log_detail.description.append(t.groupdict()['error_info'])
            log_detail.time_window = _gen_time_windows()
            log_detail.time_window[_time_window_key]['numberOfOccurrence'] += 1
            data.update({
                _key: log_detail
            })
        else:
            # existed data, need more job like add...
            data[_key].description.append(t.groupdict()['error_info'])
            data[_key].time_window[_time_window_key]['numberOfOccurrence'] += 1
        
    return is_find, data, _key


def _find_xpc_launchd(data: DataStruct, line: str) -> (bool, dict, str):
    # 几种类型分析
    # May 13 00:01:58 BBAOMACBOOKAIR2 com.apple.xpc.launchd[1] (com.apple.mdworker.bundles[12513]): Could not find uid associated with service: 0: Undefined error: 0 501
    # 真实的进程(com.apple.mdworker.bundles[12513])
    # May 13 00:01:58 BBAOMACBOOKAIR2 com.apple.xpc.launchd[1] (com.apple.xpc.launchd.domain.pid.mdmclient.53157): Failed to bootstrap path: path = /usr/libexec/mdmclient, error = 108: Invalid path
    # (com.apple.xpc.launchd.domain.pid.mdmclient.53157)
    
    # ^(\w+\s\d\d?\s\d\d:\d\d:\d\d)\s([^\s]+)\scom\.apple\.xpc\.launchd\[1\]\s\((\w+(?:\.\w+)+)(?:\[|\.)(\d+)\]?\):\s(.*)
    re_str = re.compile(r'^(?P<time>\w+\s\d\d?\s\d\d:\d\d:\d\d)\s(?P<device_name>[^\s]+)\scom\.apple\.xpc\.launchd\[1\]\s\((?P<process_info>[a-zA-Z\-]+(?:\.[a-zA-Z\-_]+)+)(?:\[|\.)(?P<pid>\d+)\]?\):\s(?P<error_info>.*)')
    is_find = False
    _key = ''

    t = re_str.search(line)
    if t is not None \
            and 'time' in t.groupdict() \
            and 'device_name' in t.groupdict() \
            and 'process_info' in t.groupdict() \
            and 'pid' in t.groupdict() \
            and 'error_info' in t.groupdict():
        # print(line)
        # print(t.groupdict()['time'])
        # print(t.groupdict()['device_name'])
        # print(t.groupdict()['process_info'])
        # print(t.groupdict()['pid'])
        # print(t.groupdict()['error_info'])
        is_find = True
        process_info = t.groupdict()['process_info']
        pid = t.groupdict()['pid']
    
        _key = sha1(str(process_info+pid).encode()).hexdigest()
        _time = datetime.strptime(t.groupdict()['time'], "%b %d %H:%M:%S")
        _time_window_key = _get_time_windows(_time.hour)
        # print(_time_window_key)
    
        if _key not in data:
            # New data
            log_detail = LogDetail()
            log_detail.device_name = t.groupdict()['device_name']
            log_detail.process_id = t.groupdict()['pid']
            log_detail.process_name = t.groupdict()['process_info']
            log_detail.description = list()
            log_detail.description.append(t.groupdict()['error_info'])
            log_detail.time_window = _gen_time_windows()
            log_detail.time_window[_time_window_key]['numberOfOccurrence'] += 1
            data.update({
                _key: log_detail
            })
        else:
            # existed data, need more job like add...
            data[_key].description.append(t.groupdict()['error_info'])
            data[_key].time_window[_time_window_key]['numberOfOccurrence'] += 1

    return is_find, data, _key

    
def run(lines: AnyStr) -> DataStruct:
    data = DataStruct()
    
    lines_list = lines.split('\n')
    last_key = ''
    for line in lines_list:
        is_find, data, last_key = _find_more_info(data, line, last_key)
        if is_find:
            continue
        is_find, data, last_key = _find_repeated(data, line, last_key)
        if is_find:
            continue
        is_find, data, last_key = _find_xpc_launchd(data, line)
        if is_find:
            continue
        is_find, data, last_key = _find_normal(data, line)
        if is_find:
            continue
            
    return data
