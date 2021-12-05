#-----------------------------------------------------------------------------#
# Filename:    interview_data_set.py                                                       
# Revision:    1.0                                                             
# Date:        2021/12/05                                                     
# Author:      Li Qin                                                         
# Email:       liqin0127@163.com                                                
# Description:                                            
#-----------------------------------------------------------------------------#

import sys
import os
import time
import re
import json

try:
    import requests
except ImportError:
    import pip
    pip.main(['install', "requests"])
    import requests

time_windows = {
    '00': '0000-0100',
    '01': '0100-0200',
    '02': '0200-0300',
    '03': '0300-0400',
    '04': '0400-0500',
    '05': '0500-0600',
    '06': '0600-0700',
    '07': '0700-0800',
    '08': '0800-0900',
    '09': '0900-1000',
    '10': '1000-1100',
    '11': '1100-1200',
    '12': '1200-1300',
    '13': '1300-1400',
    '14': '1400-1500',
    '15': '1500-1600',
    '16': '1600-1700',
    '17': '1700-1800',
    '18': '1800-1900',
    '19': '1900-2000',
    '20': '2000-2100',
    '21': '2100-2200',
    '22': '2200-2300',
    '23': '2300-0000',      
}
json_obj = dict()


def unique_key(date_time,device,process_name,process_id,description):
    return date_time + "|" + device + "|" + process_name + "|" + process_id + "|" + description[0:10]


def dataPost():
    header = {
        'content-type': 'application/json'
    }
    dataJson = list(json_obj.values())
    resp = requests.post("https://foo.com/bar", data=json.dumps(dataJson), verify=False, headers=header)
    if int(resp.status_code) != 200:
        raise ValueError("post error")
    

def handleLog(logline):
    global json_obj

    res = re.compile(r'^.+ (\d+:\d+:\d+?) (.+?) (.+?): .*').search(logline)
    if res:
        date_time = time_windows[res.group(1).split(':')[0]]
        device = res.group(2)
        process_name = res.group(3).split('[')[0].strip()
        process_id = res.group(3).split('[')[1].split(']')[0].strip()
        
        if res.group(3).endswith('])'):
            description = logline.split(']):')[-1].strip()
        else:
            description = logline.split(']:')[-1].strip()

        occur_key =  unique_key(date_time,device,process_name,process_id,description)

        if occur_key in json_obj.keys():
            json_obj[occur_key]['numberOfOccurrence'] += 1
        else:
            json_obj[occur_key] = {
                'deviceName': device,
                'processId': process_id,
                'processName': process_name,
                'description': description,
                'timeWindow': date_time,
                'numberOfOccurrence': 1
            }


def main(logfile="interview_data_set"):
    fp = open(logfile, "r")
    temp_line = ""
    for line in fp.readlines():
        if "last message repeated 1 time" in line:
            continue
        elif not line.startswith("May"):
            if temp_line != "":
                temp_line = temp_line + line.strip()
            else:
                continue
        else:
            handleLog(temp_line)
            temp_line = line
    handleLog(temp_line)
    fp.close()
    dataPost()


if __name__ == '__main__':
    if len(sys.argv) > 1:
        sys.exit(main(sys.argv[1]))
    else:
        sys.exit(main())