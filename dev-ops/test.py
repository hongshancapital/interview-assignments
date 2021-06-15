import gzip
import re
from datetime import datetime
from collections import Counter

#import requests


LOG_FILE = './DevOps_interview_data_set.gz'


def unzip(file):
    with gzip.open(file, 'rt') as f:
        file_content=f.read()
        return file_content


def main():
    #log_text = unzip(LOG_FILE)
    f=open('./test.data')
    #log_text=f.readlines()
    log_text=f.read()
    list=[]
    with open('./test.data') as file:
      for line in file:
          #pattern = "(?P<month>\w+) (?P<day>\d{2}) (?P<hour>\d{2}):(?P<minute>\d{2}):(?P<second>\d{2}) (?P<deviceName>\w+) (?P<processName>[\s\S]*)\[(?P<processId>\d+)\]\s(?P<description>[\s\S]*)")
        #my_pattern = "(?P<month>\w+) (?P<day>\d+) (?P<hour>\d+):(?P<minute>\d+):(?P<second>\d+) (?P<deviceName>\w+) (?P<processName>.*?)\[(?P<processId>\d+)\]([ :])? ?(?P<description>[\s\S]*)"
        my_pattern = "(?P<month>\w+) (?P<day>\d{2}) (?P<hour>\d{2}):(?P<minute>\d{2}):(?P<second>\d{2}) (?P<deviceName>\w+) (?P<processName>.*?)\[(?P<processId>\d+)\]\s(?P<description>.*)"
        #result=pattern.findall(log_text)
        text=re.match(my_pattern,line)
        hour=text.group('hour')
        timewindow=f'{int(hour):02d}00-{(int(hour) + 1)%24:02d}00'
        deviceName=text.group('deviceName')
        processName=text.group('processName')
        processID=text.group('processId')
        description=text.group('description')
        list.append(
          {
          timewindow,
          deviceName,
          processName,
          processID,
          description,
          })
        parsed_log_data = {}
        for index,items in enumerate(list):
          repeated_times = 1
      print(list)
      return list
      #print(hour)

    #parsed_log_items = re.findall(
    #    r'^(\S+ \d+ \d{2}:\d{2}:\d{2}) '
    #    r'(?:--- last message repeated (\d+) times? ---|'
    #    r'(\S+) (.+?)\[(\d+)\](?: \(.+?(?:\[\d+\])?\))?: '
    #    r'(.+(?:[\r\n]+^\t.+)*))',
    #    log_text, re.M)
    #print(result)

    #requests.post('https://foo.com/bar', json=result_list)


if __name__ == '__main__':
        main()
