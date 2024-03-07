#!/usr/bin/python
#-*-coding:utf-8-*-
# vim: tabstop=4 shiftwidth=4 softtabstop=4
# copyright 2022 WShuai, Inc.
# All Rights Reserved.

# @File: main.py
# @Author: WShuai, WShuai, Inc.
# @Time: 2022/01/03 21:53

import sys
import json

from Q1.logParser import LogParser
from Q1.commHttp import HttpClient

if __name__ == '__main__':
    log_file = r'../DevOps_interview_data_set.gz'

    logParser = LogParser(log_file)
    err_info = logParser.parse()

    http_client = HttpClient(r'https://foo.com', 443)
    result = http_client.post('/bar', json.dumps(err_info))
    print(result)
    sys.exit(0)