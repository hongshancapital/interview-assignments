#!/usr/bin/python3

import json


class LogInfo:
    """
    class to define the log structure
    """
    def __init__(self, device, pid, pname, timestamp, occurrence, description):
        self.device = device
        self.pid = pid
        self.processname = pname
        self.timestamp = timestamp
        self.occurrence = occurrence
        self.description = description
    
    def to_dict(self):
        return self.__dict__



def create_hour_slot(hour):
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


def create_log_key_info(filename, outputfile, time_index=2, device_index=3, process_index=4):
    if not filename or not outputfile:
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
            result.append(logitem)
    # dump result to file with json format 
    for logitem in result:
        try:
            with open(outputfile, 'a') as out:
                json.dump(logitem.to_dict(), out, indent=4)
        except FileExistsError or FileNotFoundError:
            print("Error: not able to find output file.")
            return False
        except Exception:
            print("Error to write log files")
            return False
    
    return True


if __name__ == '__main__':
    input_file = '/Users/conding/repos/dc/interview-assignments/dev-ops/DevOps_interview_data_set'
    output_file = 'logs_info.json'
    create_log_key_info(input_file, output_file)
            







