#!/usr/bin/env python3

import json
import requests


log_list = []

with open('interview_data_set') as f:
    for line in f.readlines():
        # 假设只考虑包含error和abnormal的为错误日志
        if 'error' in line or 'abnormal' in line:
            log_parts = line.split()

            hour = log_parts[2].split(':')[0]
            device_name = log_parts[3]

            # 假设第6个字段是进程名称和pid，并处理两种不同格式
            if ']):' in log_parts[5]:
                process_id = log_parts[5].split('[')[1].rstrip(']):')
                process_name = log_parts[5].split('[')[0].lstrip('(')
            else:
                process_id = log_parts[5].split('.')[-1].rstrip('):')
                process_name = log_parts[5].rsplit('.', 1)[0].lstrip('(')

            description = ' '.join(log_parts[6:])
            time_window = hour + '00-' + str(int(hour) + 1).zfill(2) + '00'
            number_of_occurrence = 1  # 每一行是一个error，occurrence先设为1，后面再聚合

            log = {
                "deviceName": device_name,
                "processId": process_id,
                "processName": process_name,
                "description": description,
                "timeWindow": time_window,
                "numberOfOccurrence": number_of_occurrence
            }
            log_list.append(log)


# 将log按照相同的processName和description进行聚合，并统计occurrence
aggregated_data = {}

for log in log_list:
    process_name = log["processName"]
    description = log["description"]
    key = (process_name, description)

    if key in aggregated_data:
        aggregated_data[key]["numberOfOccurrence"] += log["numberOfOccurrence"]
    else:
        aggregated_data[key] = log

# 转为Json格式
log_json = json.dumps(list(aggregated_data.values()), indent=True)

print(log_json)

response = input("是否继续？(输入 'yes' 或 'no'): ")

if response.lower() == 'yes':
    # POST the JSON data to the server
    headers = {'Content-Type': 'application/json'}
    response = requests.post("https://foo.com/bar", data=log_json, headers=headers)

    if response.status_code == 200:
        print("Log data successfully sent to the server.")
    else:
        print(f"Failed to send log data. Status code: {response.status_code}")
else:
    print("exiting...")
