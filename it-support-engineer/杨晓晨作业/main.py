# coding=utf-8
"""
设备名称: (deviceName)
错误的进程号码: (processId)
进程/服务名称: (processName)
错误的原因（描述）(description)
发生的时间（小时级），例如 0100-0200，0300-0400, (timeWindow)
在小时级别内发生的次数 (numberOfOccurrence)
"""
import re
import json
import traceback

log_file = './interview_data_set'


def file_generator():
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
                    yield False, line
                finally:
                    yield True, {
                        'deviceName': deviceName,
                        'processId': [processId],
                        'processName': processName,
                        'timeWindow': timeWindow,
                        'description': description,
                        'numberOfOccurrence': 1
                    }

def data_analy():
    gen_obj = file_generator()
    data_list = []
    fail_list = []
    for state, data in gen_obj:
        if state:
            if data_list and data['timeWindow'][:2] == data_list[-1]['timeWindow'][:2] \
                    and data['processName'] == data_list[-1]['processName'] \
                    and data['description'] == data_list[-1]['description']:
                data_list[-1]['numberOfOccurrence'] += 1
                data_list[-1]['timeWindow'] = data_list[-1]['timeWindow'][:5] + '-' + data['timeWindow'][:5]
                data_list[-1]['processId'].append(data['processId'][0])
            else:
                data_list.append(data)
        else:
            fail_list.append(data)

    with open(r'./log_json.json', 'w') as f:
        f.write(json.dumps(data_list, indent=1))

    with open(r'./log_fail.txt', 'w') as f:
        f.write('\n'.join(fail_list))

    print 'Done!'


if __name__ == '__main__':
    data_analy()
