#!/usr/bin/python
#-*-coding:utf-8-*-
# vim: tabstop=4 shiftwidth=4 softtabstop=4
# copyright 2022 WShuai, Inc.
# All Rights Reserved.

# @File: logParser.py
# @Author: WShuai, WShuai, Inc.
# @Time: 2022/01/03 21:27

import os
import re
import gzip

class LogParser(object):
    def __init__(self, log_file):
        self.log_file = log_file
        self.pattern = re.compile('error|failed', re.IGNORECASE)
        self.err_list = []
        self.err_hash = {}
        self.total = 0
        return

    def parse_err_line(self, line):
        result = {}

        if line.find('):') >= 0:
            line_half = line.split('):')
            description = line_half[-1].strip()
            if line_half[0][-1] == ']':
                processId = line_half[0].split()[-1][1:].split('[')[-1][:-1]
                processName = line_half[0].split()[-1][1:].split('[')[0]
            else:
                processId = line_half[0].split('.')[-1]
                processName = '.'.join(line_half[0].split()[-1][1:].split('.')[0:-1])
        elif line.find(']:') >= 0:
            line_half = line.split(']:')
            description = ']:'.join(line_half[1:]).strip()
            processId = line_half[0].split()[-1].split('[')[-1]
            processName = ' '.join(line_half[0].split()[4:]).split('[')[0]
        else:
            pass

        timeWindow = '{}00-{:0>2}00'.format(line_half[0][:9].replace(' ', '-'), int(line_half[0][7:9]) + 1)
        deviceName = line_half[0].split()[3]

        result = {
            'timeWindow': timeWindow,
            'deviceName': deviceName,
            'processId': processId,
            'processName': processName,
            'description': description,
            'numberOfOccurrence': 1
        }
        return result

    def parse_time_window(self, err_info):
        if not err_info:
            return

        self.err_list.append(err_info)
        if err_info['timeWindow'] in self.err_hash.keys():
            if err_info['description'] in self.err_hash[err_info['timeWindow']].keys():
                self.err_hash[err_info['timeWindow']][err_info['description']].append(self.total)
                for index in self.err_hash[err_info['timeWindow']][err_info['description']]:
                    self.err_list[index]['numberOfOccurrence'] = len(self.err_hash[err_info['timeWindow']][err_info['description']])
            else:
                self.err_hash[err_info['timeWindow']].setdefault(
                    err_info['description'],
                    [self.total]
                )
        else:
            self.err_hash.setdefault(
                err_info['timeWindow'],
                {
                    err_info['description']: [self.total]
                }
            )

        self.total += 1
        return

    def parse(self):
        result = []
        if not os.path.isfile(self.log_file):
            return result

        with gzip.open(self.log_file) as file_handler:
            while True:
                line = file_handler.readline()
                if not line:
                    break
                if self.pattern.search(str(line, encoding = 'utf-8')):
                    #print(line.strip())
                    err_info = self.parse_err_line(str(line, encoding = 'utf-8').strip())
                    self.parse_time_window(err_info)

        return self.err_list