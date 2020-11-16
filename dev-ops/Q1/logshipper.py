"""
logshipper.py: analyze log and post to web server
Auther: zack.cn@hotmail.com
"""

import logging
import gzip
import tempfile
import re
import json
from datetime import datetime, timedelta
from collections import Counter

import requests


def load_logfile(logfile):
    """merge multiline started by TAB character"""
    with gzip.open(logfile) as f:
        rawcontent = f.read().decode('utf-8')
        re_multiline = re.compile("\n\t+")
        content = re.sub(re_multiline, ' ', rawcontent)

    tf, path = tempfile.mkstemp()
    with open(path, 'w') as f:
        f.write(content)

    """process log with expected format"""
    processed_log_list = []
    re_pattern = re.compile(r"(?P<month>\w+) (?P<day>\d+) (?P<hour>\d+):(?P<minute>\d+):(?P<second>\d+) (?P<deviceName>\w+) (?P<processName>.*?)\[(?P<processId>\d+)\]([ :])? ?(?P<description>.*)")
    ln = 0
    with open(path) as f:
        for line in f:
            ln += 1
            matchline = re_pattern.match(line)
            if matchline:
                hour = matchline.group('hour')
                nexthour = (datetime.strptime(hour, '%H') + timedelta(hours=1)).strftime("%H")
                deviceName = matchline.group('deviceName')
                processId = matchline.group('processId')
                processName = matchline.group('processName')
                description = matchline.group('description')
                processed_row = '{}-{}#{}#{}#{}#{}'.format(hour, nexthour, deviceName, processId, processName, description)
                processed_log_list.append(processed_row)
            else:
                logging.warning("Not Match: {} {}".format(ln, line))
    return processed_log_list

def count_numberOfOccurrence(processed_log):
    """count hourly number of occourence"""
    result_list = []
    processed_log_collections = Counter(processed_log)
    for k,v in processed_log_collections.items():
        row_dict = {}
        items = k.split('#')
        row_dict['timeWindow'] = items[0]
        row_dict['deviceName'] = items[1]
        row_dict['processId'] = items[2]
        row_dict['processName'] = items[3]
        row_dict['description'] = items[4]
        row_dict['numberOfOccurrence'] = v
        result_list.append(row_dict)
    return json.dumps(result_list)

def post_log(log_json, url):
    """post json log to web server"""
    response = requests.post(url, json=log_json)
    if response.status_code == 200:
        logging.info("Log Uploaded Successfully!")
    else:
        response.raise_for_status()

def main():
    # load and extract log file
    logfile = "../DevOps_interview_data_set.gz"
    processed_log_list = load_logfile(logfile)

    # count number of log occourence in hourly time window
    log_json = count_numberOfOccurrence(processed_log_list)
    # print(log_json)

    # post log to web server
    post_url = "https://foo.com/bar"
    post_log(log_json, url=post_url)


if __name__ == "__main__":

    logging.basicConfig(format='%(levelname)s: %(message)s')
    main()
