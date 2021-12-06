#!/usr/bin/env python3
# coding: utf-8 -*-
"""
Question 1

思路：

1. 利用关键字 `error`, `failed` 过滤出所有错误信息， 注意不区分大小写。
2. 遍历所有错误信息， 转换为格式化数据(dict 格式)， 此时不带 `numberOfOccurrence` 信息
3. 遍历所有错误信息的格式化数据的 list, 利用一个临时 dict 来去重， 计算 `numberOfOccurrence`
4. 发送结果

"""

import json
import re
from pathlib import Path

import requests

TARGET_URL = "https://foo.com/bar"
LOG_FILE = "../DevOps_interview_data_set"

ERROR_MSG_PATTERN = re.compile(r"error|failed", re.IGNORECASE)
WHITESPACE_PATTERN = re.compile(r"\s+")
PID_PATTERN = re.compile(r"\[\d+\]")


def extract_err_object(line):
    """
    将字符串转换为 error object

    Args:
      line: 一行日志

    Returns:
      Error Object

    Raises:
      None

    """
    elements = WHITESPACE_PATTERN.split(line)
    err_object = {
        "deviceName": "",
        "processId": "",
        "processName": "",
        "description": "",
        "timeWindow": ""
    }

    err_object["deviceName"] = elements[3]

    month = elements[0]
    date = elements[1]
    time = elements[2]
    hour = int(time.split(':')[0]) * 100
    end_hour = hour + 100
    if end_hour >= 2400:
        end_hour = 0
    time_start = f"{hour:04}"
    time_end = f"{end_hour:04}"
    err_object["timeWindow"] = f"{month}-{date}_{time_start}-{time_end}"

    remainings = ' '.join(elements[4:])
    colon_index = remainings.index(":")
    raw_process_info = remainings[0:colon_index]
    err_object["processId"] = ",".join(PID_PATTERN.findall(raw_process_info))
    err_object["processName"] = PID_PATTERN.sub('', raw_process_info)
    err_object["description"] = remainings[colon_index + 1:].strip()
    return err_object


def convert_counter_to_result(key, value):
    """
    将 Counter 转换为结果对象

    Args:
      key: 序列化的 error object
      value: 该 error object 出现的次数

    Returns:
      结果对象, 就是 error object 加上 numberOfOccurrence

    Raises:
      None

    """
    one_result = json.loads(key)
    one_result["numberOfOccurrence"] = value
    return one_result


def main():
    """Main Function"""
    all_err_lines = []
    with Path(LOG_FILE).open("r", encoding='utf-8') as target_file:
        all_err_lines = [
            line.strip() for line in target_file.readlines()
            if ERROR_MSG_PATTERN.search(line.strip()) is not None
        ]
    err_objects = [extract_err_object(line) for line in all_err_lines]
    counters = {}
    for one_err_obj in err_objects:
        err_obj_key = json.dumps(one_err_obj)
        counters[err_obj_key] = counters.get(err_obj_key, 0) + 1
    result = [
        convert_counter_to_result(key, value)
        for key, value in counters.items()
    ]
    response = requests.post(TARGET_URL, data=json.dumps(result))
    print(response.status_code, response.reason)


if __name__ == '__main__':
    main()
