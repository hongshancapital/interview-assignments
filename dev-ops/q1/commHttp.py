#!/usr/bin/python
#-*-coding:utf-8-*-
# vim: tabstop=4 shiftwidth=4 softtabstop=4
# copyright 2017 Wshuai, Inc.
# All Rights Reserved.

# @author: WShuai, Wshuai, Inc.

import requests

'''
Http Client
pip install requests
'''
class HttpClient(object):
    '''
    host - str: 主机地址, 如: 127.0.0.1 或 www.baidu.com
    port - str or int: 主机端口
    '''
    def __init__(self, host, port):
        self.host = host
        self.port = port
        return

    def post(self, url, json_body):
        '''
        url - str: URL资源路径
        json_body - json object: 参数
        '''
        request_url = '{0}:{1}{2}'.format(self.host, self.port, url)
        #print('request url is {}'.format(request_url))
        try:
            result = requests.post(request_url, data = json_body, timeout = 15)
            print('result is {}'.format(result))
            print(result.status_code)
            if result.status_code != 200:
                data = False
            else:
                data = result.text
        except Exception as e:
            print('Exception: {}'.format(e))
            data = False
        return data

    def get(self, url, json_body):
        '''
        url - str: URL资源路径
        json_body - json object: 参数
        '''
        request_url = 'http://{0}:{1}/{2}'.format(self.host, self.port, url)
        try:
            result = requests.get(request_url, data = json_body, timeout = 15)
            print(result.status_code)
            if result.status_code != 200:
                data = False
            else:
                data = result.text
        except Exception as e:
            data = False
        return data
