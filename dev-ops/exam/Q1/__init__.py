import re
import json
import requests


# 处理单行日志
def log_handler(log, result):
    pattern = re.match(r"May 13 (\d{2}):\d{2}:\d{2} (.*?) (.*?)\[(\d+)\].*?: (.*)", log)
    if pattern is not None:
        # 计算时间区间
        begin = pattern.group(1)
        end = int(begin) + 1
        if end < 10:
            end = "0" + str(end)
        time_window = "{}00-{}00".format(begin, end)
        processed_log = {
            "deviceName": pattern.group(2),
            "processId": pattern.group(4),
            "processName": pattern.group(3),
            "description": pattern.group(5),
            "timeWindow": time_window,
            "numberOfOccurrence": 1
        }
        # 根据日志内容生成哈希并判断是否已经存在
        key = "{}-{}-{}-{}-{}".format(
            processed_log["timeWindow"],
            processed_log["deviceName"],
            processed_log["processName"],
            processed_log["processId"],
            processed_log["description"]
        )
        if hash(key) in result:
            # 哈希存在则更新
            processed_log = result[hash(key)]
            processed_log["numberOfOccurrence"] = result[hash(key)]["numberOfOccurrence"] + 1
            result[hash(key)] = processed_log
        else:
            # 哈希不存在则添加
            result[hash(key)] = processed_log


if __name__ == '__main__':
    result = {}
    f = open("DevOps_interview_data_set", mode='r', encoding='utf-8')
    line = f.readline()
    concat_line = ""
    while line:
        past_line = line
        line = f.readline()
        if not line:
            # 处理最后一行日志
            log_handler(past_line.replace("\n", " "), result)
        line_pattern = re.match(r"May 13 \d{2}:\d{2}:\d{2}.*", line)
        if line_pattern is not None:
            # 时
            last_line_pattern = re.match(r"May 13 \d{2}:\d{2}:\d{2}.*", past_line)
            if last_line_pattern is not None:
                # 当前行为完整日志，且上一行也为完整日志时，处理上一行日志
                log_handler(past_line.replace("\n", " "), result)
                concat_line = ""
            else:
                # 当前行为完整日志，但上一行不为完整日志时，拼接上一行日志，处理拼接完成的日志，然后刷新拼接缓存
                concat_line += past_line.replace("\n", " ")
                log_handler(concat_line.replace("\t", ""), result)
                concat_line = ""
        else:
            # 当前行不为完整日志时，拼接上一行日志
            concat_line += past_line.replace("\n", " ")
    f.close()
    # 将map中的value保存为list，并转换为json
    param = json.dumps(list(result.values()))
    print("param：%s" % param)
    # 发送请求
    response = requests.post(url="https://foo.com/bar",
                             data=param,
                             headers={"Content-Type": "application/json"},
                             verify=False)
    print("status_code：%s" % response.status_code)
    print("response_text：%s" % response.text)
