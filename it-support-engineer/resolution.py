#!/usr/bin/env python3
import gzip
import re
import requests
import json

def parse_log():
    """parse log and return payload"""
    with gzip.open("interview_data_set.gz", "rt") as f:
        lines = []
        for line in f:
            if not line.startswith('May'):
                lines[-1] += line
            elif '--- last message repeated' in line:
                lines.append(' '.join(line.split()[:3]+lines[-1].split()[3:]))
            else:
                lines.append(line)

    DATE = r'^(?P<date>[a-zA-Z]{3} [0-9]{1,2})'
    TIME = r'(?P<time>\d{2}):\d{2}:\d{2}'
    DEVICE = r'(?P<device>\S+)'
    PNAME_PID_DESC = r'(?P<pname>[a-zA-Z.]+)\[(?P<pid>\d+)\][: ](?P<desc>.*)$'
    PNAME_PID = r'(?P<pname>[a-zA-Z.]+)\[(?P<pid>\d+)\]'
    PPNAME_PPID_DESC = r'\((?P<ppname>[a-zA-Z.]+)[.[](?P<ppid>\d+)[]]{0,}\):(?P<desc>.*)$'
    pattern = re.compile(' '.join([DATE, TIME, DEVICE, PNAME_PID_DESC]))
    pattern2 = re.compile(' '.join([DATE, TIME, DEVICE, PNAME_PID, PPNAME_PPID_DESC]))
    result = {}
    for line in lines:
        matched_search = pattern.search(line)
        if matched_search is not None:
            result_key = matched_search.groups()
            *_, processId, _ = result_key
            if int(processId) == 1:
                matched_search2 = pattern2.search(line)
                if matched_search2 is not None:
                    result_key = matched_search2.groups()
                    result_key = result_key[:3] + result_key[5:]
            result[result_key] = result.get(result_key, 0) + 1

    payload = []
    for key in result.keys():
        _, timeWindow, deviceName, processName, processId, description = key
        payload.append({
            "deviceName": deviceName,
            "processId": processId,
            "processName": processName,
            "description": description,
            "timeWindow": timeWindow,
            "numberOfOccurrence": str(result[key])
            })
    return payload

if __name__ == '__main__':
    headers = {'Content-Type': 'application/json'}
    payload=json.dumps(parse_log())
    # url = "https://httpbin.org/post"
    url = 'https://foo.com/bar'
    resp = requests.post(url=url, headers=headers, data=payload)
    if resp.status_code == 200:
        print("successfully uploaded!")
    else:
        print("upload failed, status_code: {}".format(resp.status_code))
