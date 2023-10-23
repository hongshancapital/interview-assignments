import re
import requests
from collections import OrderedDict
from requests.packages import urllib3 
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

class LogParser(object):
    def __init__(self, filename):
        self.f = open(filename, 'rt')
        self.data_all = OrderedDict()
        self.pattern_log = r'^(?P<date>[a-zA-Z]{3} [0-9]{1,2}) (?P<logtime>\d{2}:\d{2}:\d{2}) (?P<device>\S+) (?P<processname>[a-zA-Z_. ]+)\[(?P<processid>\d+).*[\]\)]: (?P<description>.*)$'
        self.pattern_log_repeat = r'.*last message repeated 1 time.*'
        self.timeWindow = None
        self.upload_flag = False
        self.data_item_temp = None
        self.count = 0
    def __del__(self):
        self.f.close()

    # upload
    def upload(self):
        payloads = []
        for value in self.data_all.values():
            payload = {
                'deviceName': value.get('deviceName'),
                'processId': value.get('processId'),
                'processName': value.get('processName'),
                'description': value.get('description'),
                'timeWindow': value.get('timeWindow'),
                'numberOfOccurrence':value.get('numberOfOccurrence')
            }
            payloads.append(payload)
            self.count += 1
        requests.request('post', 'https://foo.com/bar', headers={}, data=payloads, verify=False)
        self.data_all.clear()
        if self.data_item_temp:
            self.data_all.update(self.data_item_temp)
        self.upload_flag = False
        self.data_item_temp = None
    
    # parse raw log. Possibly combination of multi-lines
    def parse_line(self, raw):
        mysearch = re.search(self.pattern_log, raw, re.I)
        # if match
        if mysearch is not None:
            search_dict = mysearch.groupdict()
            deviceName = search_dict.get('device')
            processId = search_dict.get('processid')
            processName = search_dict.get('processname')
            description = search_dict.get('description')
            timeWindow = search_dict.get('logtime').split(':')[0]
            data_item_without_occurrence = {'deviceName':deviceName, 'processId':processId, 'processName':processName, 'description':description, 'timeWindow':timeWindow}
            key = tuple(data_item_without_occurrence.values())
            # deal with numberOfOccurrence
            if key in self.data_all:
                numberOfOccurrence = self.data_all.get(key).get('numberOfOccurrence') + 1
            else:
                numberOfOccurrence = 1
            data_item_without_occurrence.update({'numberOfOccurrence':numberOfOccurrence})
            data_item_with_occurrence = data_item_without_occurrence
            data_item = {key:data_item_with_occurrence}

            if self.timeWindow == None:
                self.timeWindow = timeWindow
                self.data_all.update(data_item)
            elif self.timeWindow == timeWindow:
                self.data_all.update(data_item)
            # if timeWindow come to another hour range:
            # 1. set upload_flag. it will indicate parse_and_upload() to upload local saved data_all and then clean up it. This design comes from the concern of running out of memory, expecially the log file is huge.
            # 2. temporily save the parsed current log line text, and add to data_all after upload and data_all cleaning up. Because this item belongs to next timeWindow.
            else:
                self.timeWindow = timeWindow
                self.upload_flag = True
                self.data_item_temp = data_item
        # if not match
        else:
            # **Warning**
            # this part is not supposed to be arrived. If so, this code should be updated.
            print('==[debug] {} not match pattern=='.format(raw))

    def parse_lines(self):
        while True:
            self.line_cur = self.line_next
            self.line_next = self.f.readline()
            # EOF
            if not self.line_next:
                self.parse_line(self.line_cur)
                raise EOFError()
                break
            # in case of one single-line log. This is the most cases in fact.
            if re.compile(self.pattern_log).match(self.line_next):
                self.parse_line(self.line_cur)
                break
            # in case like "May 13 12:11:51 --- last message repeated 1 time ---"
            elif re.compile(self.pattern_log_repeat).match(self.line_next):
                self.parse_line(self.line_cur)
                self.line_next = self.line_cur
                break
            # in case of single log item with multi-lines
            else:
                self.line_next = self.line_cur.rstrip('\r\n') + ' ' + self.line_next.strip()
            

    def parse_and_upload(self):
        self.line_cur = None
        self.line_next = self.f.readline()
        while True:
            try:
                self.parse_lines()
                if self.upload_flag:
                    self.upload()
            except EOFError:
                if self.data_item_temp:
                    self.data_all.update(self.data_item_temp)
                self.upload()
                break
        


if __name__ == '__main__':

    try:
        log = LogParser('../interview_data_set')
        log.parse_and_upload()

    except Exception as e:
        print(e)
    else:
        print('uploaded {} logs'.format(log.count))

