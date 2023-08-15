import re
import requests
import csv
from collections import OrderedDict

class LogParser(object):
    def __init__(self, filename):
        self.f = open(filename, 'rt')
        self.csvfile = open('upload.csv', 'w')
        self.csvwriter = csv.writer(self.csvfile)
        # container of required data, which is ready to be uploaded
        # self.data_all = {}
        # enhancement: compatible with python version prior to 3.6
        self.data_all = OrderedDict()
        # regular expression pattern, with named groups
        self.pattern_log = r'^(?P<date>[a-zA-Z]{3} [0-9]{1,2}) (?P<logtime>\d{2}:\d{2}:\d{2}) (?P<device>\S+) (?P<processname>[a-zA-Z.]+)\[(?P<processid>\d+).*[\]\)]: (?P<description>.*)$'
        self.timeWindow = '00'
        self.upload_flag = False
        self.data_item_ready = None
    def __del__(self):
        self.f.close()
        self.csvfile.close()
        print('**result reference: upload.csv**')

    # upload
    def upload(self):
        for value in self.data_all.values():
            # payload = {
            #     'deviceName': value.get('deviceName'),
            #     'processId': value.get('processId'),
            #     'processName': value.get('processName'),
            #     'description': value.get('description'),
            #     'timeWindow': value.get('timeWindow'),
            #     'numberOfOccurrence':value.get('numberOfOccurrence')
            # }
            # foo.com is not reachable
            # requests.request('post', 'https://foo.com/bar', headers={}, data=payload)
            # write data to local csv file to mimic post upload
            self.csvwriter.writerow([value.get('deviceName'), value.get('processId'), value.get('processName'), value.get('description'), value.get('timeWindow'), value.get('numberOfOccurrence')])
        print('==mimic upload(write to csv)==')
        self.data_all.clear()
        self.upload_flag = False
    
    # parse log raw line text
    def parse_line(self):
        raw = self.f.readline()
        if not raw:
            raise EOFError()
        mysearch = re.search(self.pattern_log, raw, re.I)
        # matched
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

            if self.timeWindow == timeWindow:
                self.data_all.update(data_item)
            # if timeWindow come to another hour range:
            # 1. set upload_flag. it will upload local saved data_all and clean up it, this comes from the concern of running out of memory, expecially the log file is huge.
            # 2. temporily save the parsed current log line text, and add to data_all after upload and data_all cleaning up. Because this item belongs to next timeWindow
            else:
                self.timeWindow = timeWindow
                self.upload_flag = True
                self.data_item_temp = data_item
        # not matched
        # do enhancement is needed.
        else:
            print('==not match pattern==')

    def parse_and_upload(self):
        while True:
            try:
                self.parse_line()
                if self.upload_flag:
                    self.upload()
                    self.data_all.update(self.data_item_temp)
            except EOFError:
                self.upload()
                break
        


if __name__ == '__main__':

    try:
        log = LogParser('./../interview_data_set')
        log.parse_and_upload()

    except Exception as e:
        print(e)

