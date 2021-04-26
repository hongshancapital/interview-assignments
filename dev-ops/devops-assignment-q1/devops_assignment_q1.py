import gzip
import re
from datetime import datetime

import requests


LOG_FILE = '../DevOps_interview_data_set.gz'


def unzip():
    with gzip.open(LOG_FILE, 'rt') as gf:
        return gf.read()


def main():
    log_text = unzip()
    parsed_log_items = re.findall(
        r'^(\S+ \d+ \d{2}:\d{2}:\d{2}) '
        r'(?:--- last message repeated (\d+) times? ---|'
        r'(\S+) (.+?)\[(\d+)\](?: \(.+?(?:\[\d+\])?\))?: '
        r'(.+(?:[\r\n]+^\t.+)*))',
        log_text, re.M)
    del log_text

    parsed_log_data = {}
    for index, (stime, repeated, dname,
                pname, pid, descr) in enumerate(parsed_log_items):
        repeated_times = 1
        if repeated:
            if index == 0:
                raise ValueError('No last message found')

            repeated_times = int(repeated)
            _, _, dname, pname, pid, descr = parsed_log_items[index - 1]

        log_time = datetime.strptime(stime, '%b %d %H:%M:%S')
        time_win = f'{log_time.hour:02d}00-{(log_time.hour + 1)  % 24:02d}00'
        log_key = (dname, pname, pid, descr, time_win)
        if log_key in parsed_log_data:
            parsed_log_data[log_key] += repeated_times
        else:
            parsed_log_data[log_key] = repeated_times
    del parsed_log_items

    result_list = []
    for (dname, pname, pid, descr, time_win), num in parsed_log_data.items():
        result_list.append({
            'deviceName': dname,
            'processId': pid,
            'processName': pname,
            'description': descr,
            'timeWindow': time_win,
            'numberOfOccurrence': num,
        })
    del parsed_log_data

    requests.post('https://foo.com/bar', json=result_list)


if __name__ == '__main__':
    try:
        main()
    except Exception as ex:
        print(f'Error occurred: {ex}')
