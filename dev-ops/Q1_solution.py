#!/usr/bin/python3

import json
import requests
import os


class LogInfo:
    """
    class to define the log structure
    """
    def __init__(self, device, pid, pname, timestamp, occurrence, description):
        self.deviceName = device
        self.processId = pid
        self.processName = pname
        self.timeWindow = timestamp
        self.numberOfOccurrence = occurrence
        self.description = description
    
    def to_dict(self):
        return self.__dict__



def create_hour_slot(hour: str):
    if not hour:
        return ''

    next_hour = int(hour) + 1
    time_slot = hour + '-' + str(next_hour).rjust(2, '0')
    return time_slot


def get_process_info(process):
    if not process:
        return -1, ''
    
    sindex = process.find('[')
    eindex = process.find(']')
    if sindex > -1 and eindex > -1:
        pid = int(process[sindex+1:eindex])
        pname = process[:sindex]
    else:
        return -1, process

    return pid, pname


def create_log_key_data(filename: str, time_index=2, device_index=3, process_index=4):
    if not filename:
        print('Please enter the log file name.')
        return False
    
    result = []
    map = {}
    try:
        with open(filename, mode='r') as f:
            # sort logs based on its error type
            for line in f:
                if 'error' in line:
                    index = line.find('error')
                    key = line[index:]
                    if key in map:
                        map[key].append(line)
                    else:
                        map[key] = [line]
    except FileExistsError or FileNotFoundError:
        print("Error: not able to find input file.")
        return False

    # sort each type of error logs based on its timestamp and caculate the occurrence
    for key in map:
        occurrenceMap = {}
        for log in map[key]:
            parts = log.split()
            timehour = parts[time_index].split(':')[0]
            if timehour in occurrenceMap:
                occurrenceMap[timehour] += 1
            else:
                occurrenceMap[timehour] = 1
        # create the loginfo objects
        for item in occurrenceMap:
            timestamp = create_hour_slot(item)
            pid, pname = get_process_info(parts[process_index])
            logitem = LogInfo(parts[device_index], pid, pname, timestamp, occurrenceMap[item], key)
            result.append(logitem.to_dict())
    # create the json format data 
    if not result:
        json_data = []
    else:
        json_data = json.dumps(result)
    
    return json_data
    

def upload_data(logData, url):
    if not url:
        print("Please enter the valid url.")
        return

    try:
        response = requests.post(url, data=logData)
        if response.status_code < 300 and response.status_code >= 200:
            print("Successfully upload the log data.")
            return True
        else:
            print("Error to upload the log data to remote: " + response.text)
            return False
    except requests.RequestException as error:
        print(error)
        return False

    

if __name__ == '__main__':
    dirname = os.path.dirname(__file__)
    input_file = os.path.join(dirname, 'DevOps_interview_data_set')
    data = create_log_key_data(input_file)
    # print(data)
    url = "https://foo.com/bar"
    upload_data(data, url)
            







