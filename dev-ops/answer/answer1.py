#!/usr/bin/env python3
import re
import requests

pattern = re.compile("^\w+\s\d+\s(?P<hour>\d{2}):\d{2}:\d{2}\s(?P<deviceName>\w+)\s(?P<processName>[a-zA-Z\.]+)\[(?P<processId>\d+)\][^:]*:(?P<description>.*)$")
res = {}

def add_log(hour,deviceName,processName,processId,description):
  if hour == '':
    return
  hour = "%02d00-%02d00" % (int(hour),int(hour)+1)
  res[(hour,deviceName,processName,processId,description)] = res.get((hour,deviceName,processName,processId,description),0) + 1

def main():
  filepath = 'DevOps_interview_data_set'
  with open(filepath) as fp:
    hour=deviceName=processName=processId=description=''
    for line in fp.readlines():
      m = pattern.match(line)
      if m:
        add_log(hour,deviceName,processName,processId,description)
        hour = m.group('hour')
        deviceName = m.group('deviceName')
        processName = m.group('processName')
        processId = m.group('processId')
        description = m.group('description')
      else:
        description += '\n' + line.rstrip()
    add_log(hour,deviceName,processName,processId,description)

  out = []
  for key in res:
    out.append({
      'deviceName': key[1],
      'processId': key[3],
      'processName': key[2],
      'description': key[4],
      'timeWindow': key[0],
      'numberOfOccurrence': res[key],
    })

  requests.post("https://https://foo.com/bar", json=out)
   
main()
