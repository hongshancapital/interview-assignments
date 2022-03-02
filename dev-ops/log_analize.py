import gzip
import json
import re
import base64
import requests



def main():
    meta_dict = {}
    time_windows_dict = {}
    p = re.compile(r'(?P<mon>[A-Z][a-z][a-z])\s(?P<day>[123][0-9])\s'
                   r'(?P<hour>[0-9][0-9]):(?P<min>[0-9][0-9]):(?P<sec>[0-9][0-9])\s(?P<device>[A-Z0-9]+)\s'
                   r'(?P<processName>[A-Za-z.]+)\[(?P<processId>[0-9]+)\][\s]?[\(]?(?P<subProcessName>['
                   r'A-Za-z.]+)?[\[]?(?P<subProcessId>[0-9]+)?[\]]?[\)]?:(?P<description>.+)'
                   )
    with gzip.open('DevOps_interview_data_set.gz', 'rt') as f:
        for line in f:
            m = p.search(line)
            if m is None:
                continue
            key = base64.b64encode(
                bytes(m.group('device') + m.group('processName') + m.group('processId') + m.group('description'),
                      'utf-8'))
            cached_meta = meta_dict.get(key)
            if cached_meta is None:
                meta_dict[key] = init_meta_dict(m.group('device'), m.group('processName'), m.group('processId'),
                                                m.group('description'))
                time_windows_dict[key] = init_time_window_dict()
            time_windows_dict[key][format_hour(int(m.group('hour')))] = time_windows_dict[key][
                                                                            format_hour(int(m.group('hour')))] + 1
    datas = []
    f.close()
    for key, value in time_windows_dict.items():
        for time_windows, recurrence in value.items():
            if recurrence > 0:
                log = meta_dict[key]
                data = {'deviceName': log.device, 'processId': log.process_id, 'processName': log.process_name,
                        'description': log.description.strip(), 'timeWindow': time_windows,
                        'numberOfOccurrence': recurrence}
                datas.append(data)
    # Serializing json
    post_data(datas)


def post_data(data: dict):
    json_object = json.dumps({'data': data}, indent=4)
    print(json_object)
    # r = requests.post('https://foo.com/bar', json=json_object)


def format_hour(hour: int):
    return '{:02d}00-{:02d}00'.format(hour, hour + 1)


def init_meta_dict(device, processName, processId, desc: str) -> LogError:
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
