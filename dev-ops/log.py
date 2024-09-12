import gzip
import json
import re
import base64
import requests
# 日志指纹：日志
meta_dict = {}
# [日志指纹][时间段]=n
time_windows_dict = {}
#结果集
datas = []


def main():
    global datas
    # 日期开头的才是新行，适应多行日志的情况
    p_new_line = re.compile(r'(?P<mon>[A-Z][a-z][a-z])\s(?P<day>[123][0-9])\s'
                             r'(?P<hour>[0-9][0-9]):(?P<min>[0-9][0-9]):(?P<sec>[0-9][0-9])\s'
                             r'(?P<content>.*)')
    p = re.compile(r'(?P<device>[A-Z0-9]+)\s'
                   r'(?P<processName>[A-Za-z0-9_.]+)\[(?P<processId>[0-9]+)\][\s]?[\(]?(?P<subProcessName>['
                   r'A-Za-z0-9_.]+)?[\[]?(?P<subProcessId>[0-9]+)?[\]]?[\)]?:(?P<description>.+)'
                   )
    with gzip.open('DevOps_interview_data_set.gz', 'rt') as f:
        log_error = None
        for line in f:
            m = p_new_line.match(line)
            if m is None:  # 是否是新行，如果不是就将内容追加到上一条记录中
                log_error.description += line
                continue
            else:
                # 新行，将上一条记录提交
                if log_error is not None:
                    log_account(log_error, m.group('hour'))
                    log_error = None
                m2 = p.search(m.group('content'))
                if m2 is None:  # 没有进程信息的记录
                    log_error = LogError('null', 'null', 'null', m.group('content'))
                else:  # 完整记录
                    log_error = LogError(m2.group('device'), m2.group('processName'), m2.group('processId'), m2.group('description'))
        if log_error is not None:  # 提交最后一条记录
            log_account(log_error, m.group('hour'))
    # Serializing json
    post_data()


def log_account(log_error, hour: str):
    global meta_dict
    global time_windows_dict
    global datas
    key = base64.b64encode(
        bytes(log_error.device + log_error.process_name + log_error.process_id + log_error.description, 'utf-8'))
    cached_meta = meta_dict.get(key)
    if cached_meta is None:
        meta_dict[key] = log_error
        time_windows_dict[key] = init_time_window_dict()
    time_windows_dict[key][format_hour(int(hour))] += 1


def post_data():
    global meta_dict
    global time_windows_dict
    global datas

    for key, value in time_windows_dict.items():
        for time_windows, recurrence in value.items():
            if recurrence > 0:
                log = meta_dict[key]
                data = {'deviceName': log.device, 'processId': log.process_id, 'processName': log.process_name,
                        'description': log.description.strip(), 'timeWindow': time_windows,
                        'numberOfOccurrence': recurrence}
                datas.append(data)
    json_object = json.dumps({'data': datas}, indent=4)
    # print(json_object)
    with open('result.json', 'w') as f:
        f.write(json_object)
    # r = requests.post('https://foo.com/bar', headers={'Content-Type': 'application/json'}, json=json_object)
    # print(r.text)


def format_hour(hour: int):
    return '{:02d}00-{:02d}00'.format(hour, hour + 1)


# def init_meta_dict(device, processName, processId, desc: str) -> LogError:
def init_meta_dict(device, processName, processId, desc: str):
    return LogError(device, processName, processId, desc)


def init_time_window_dict():
    window_dict = {}
    for h in range(24):
        key = format_hour(h)
        window_dict[key] = 0
    return window_dict


class LogError:
    def __init__(self, device: str, process_name: str, process_id: str, description: str):
        self.device = device
        self.process_name = process_name
        self.process_id = process_id
        self.description = description

    def __eq__(self, other):
        if isinstance(other, self.__class__):
            return self.__dict__ == other.__dict__
        else:
            return False

    def __ne__(self, other):
        return not self.__eq__(other)


if __name__ == "__main__":
    main()