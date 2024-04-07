# coding=utf-8
"""
时间：2022-02-21
作者：杨晓晨

要求：
分析系统日志得到关键信息，用 Json 的格式 POST 上传至服务器 ( https://foo.com/bar )，key 的名称在括号里
设备名称: (deviceName)
错误的进程号码: (processId)
进程/服务名称: (processName)
错误的原因（描述）(description)
发生的时间（小时级），例如 0100-0200，0300-0400, (timeWindow)
在小时级别内发生的次数 (numberOfOccurrence)
"""
import re
import json

import requests

# 本次实验文件位置
log_file = './interview_data_set'


def file_generator():
    """
    定义文件生成器，用于迭代产出每一行的分析结果
    :yield: dict
    """
    with open(log_file) as f:
        for line in f:
            if 'error:' in line:
                # print line.split()
                try:
                    deviceName = line.split()[3]
                    processId = re.search(r'\d+', line.split()[5]).group()
                    processName = line.split()[5].replace(processId, '')[1:-4]
                    timeWindow = line.split()[2][:-3]
                    description = ' '.join(line.split()[6:])
                except AttributeError as e:
                    continue
                finally:
                    yield {
                        'deviceName': deviceName,
                        'processId': [processId],
                        'processName': processName,
                        'timeWindow': timeWindow,
                        'description': description,
                        'numberOfOccurrence': 1
                    }


def data_analy():
    """
    迭代生成器并根据一定逻辑存储在列表中
    :return: jsonData
    """
    gen_obj = file_generator()
    data_list = []
    for data in gen_obj:
        if data_list and data['timeWindow'][:2] == data_list[-1]['timeWindow'][:2] \
                and data['processName'] == data_list[-1]['processName'] \
                and data['description'] == data_list[-1]['description']:
            data_list[-1]['numberOfOccurrence'] += 1
            data_list[-1]['timeWindow'] = data_list[-1]['timeWindow'][:5] + '-' + data['timeWindow'][:5]
            data_list[-1]['processId'].append(data['processId'][0])
        else:
            data_list.append(data)
    return json.dumps(data_list)


def main():
    headers = {'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36'}
    json_data = data_analy()
    response = requests.post(url='https://foo.com/bar', headers=headers, verify=False, data=json_data)
    if response.status_code == 200:
        print('数据上传成功！')
    else:
        print('数据上传失败！')


if __name__ == '__main__':
    """从本实例文件运行"""
    main()
