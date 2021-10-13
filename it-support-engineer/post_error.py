#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @Date    : 21-10-11 20:18:16 Monday
# @Author  : haojie_xu
# @Mail    : 8233500@qq.com
# @Version : v1.0.0
# @File    : D:/py_project/post_error.py
"""
由于需要分时间段统计error数量，故将‘时间段’，‘error数量’以及‘该时间段内error信息（item_error）’定为同级
而时间段内每个error信息定为下级。
    1、首先提取日志中error日志，并获取相应信息
    2、格式化获取的error数据
    3、将格式化为json的数据写入文件，并且post至指定url
"""

import json
import re
import requests

logfile = "interview_data_set"


def get_error(log):
    # 获取error日志
    file = log
    with open(file, "r") as f:
        result_arr = []
        for line_str in f.readlines():
            result_dict = {}  # 定义字典存储初步error结果
            if re.findall("error", line_str):
                b = re.split(r"\s", line_str, maxsplit=6)
                time_str = b[2]
                if re.findall(r"]\):", b[5]):  # 统一截取到的字符串格式
                    pr_str = re.sub(r"]\):", "", b[5])
                    pr_str = re.sub(r"\(", "", pr_str)
                    pr_str = re.sub(r"\[", ".", pr_str)
                elif re.findall(r"\):", b[5]):
                    pr_str = re.sub(r"\):", "", b[5])
                    pr_str = re.sub(r"\(", "", pr_str)
                    pr_str = re.sub(r"\[", ".", pr_str)
                else:
                    pr_str = b[5]
                process_str = pr_str
                pro_id = process_str.split(".")[-1]
                pro_name = process_str.split("." + pro_id)[0]
                result_dict["deviceName"] = b[3]
                result_dict["processId"] = pro_id
                result_dict["processName"] = pro_name
                result_dict["description"] = b[-1].replace("\n", "")  # 去除行末换行符
                result_dict["time"] = time_str
                result_arr.append(result_dict)
    return result_arr


def format_error_data(data):
    # error信息格式化为json
    error_data = []  # 定义格式化后的error数据
    hour_arr = ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
                "17", "18", "19", "20", "21", "22", "23"]  # 定义时间字符串
    for i in hour_arr:
        piece_data = {}
        error_sum = 0
        item_error = []  # 用于存放统计到的指定时间段的error
        index = hour_arr.index(i)
        if i != "23":
            next_hour = hour_arr[index + 1] + "00"
        else:
            next_hour = "0000"
        for each in data:
            if each["time"].split(":")[0] == i:
                item_error.append(each)
                error_sum += 1
            piece_data["timeWindow"] = i + "00" + "-" + next_hour
        piece_data["numberOfOccurrence"] = error_sum
        piece_data["item_error"] = item_error
        error_data.append(piece_data)

    return error_data


def post_error():
    # json数据写入文件，并且post至指定url
    url = "https://foo.com/bar"   # 指定的url
    headers = {
        "Content-Type": "application/json"
    }    # 指定请求头，post数据为json
    error_data = get_error(logfile)
    format_error = format_error_data(error_data)
    error_json = json.dumps(format_error, indent=4, ensure_ascii=False)
    print(error_json)
    json_file = "error.json"

    with open(json_file, 'w', encoding='utf-8') as f:
        f.write(error_json)

    try:
        req = requests.post(url=url, data=error_json, headers=headers, verify=False)
        print("post error data succeeded !!!")
        print(req)
    except requests.exceptions as e:
        print(e)


if __name__ == '__main__':
    post_error()
