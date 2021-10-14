#!/usr/bin/env python3

import json
import os
import re
import requests
from requests.auth import HTTPBasicAuth
import argparse
import queue
import time
import threading

from utils import *
from exceptions import *


def get_args():
    parser = argparse.ArgumentParser(
        formatter_class=argparse.RawTextHelpFormatter,
        description="Parse log"
        )

    parser.add_argument('--logpath',
                        required=False,
                        default='/var/log/scdt-china',
                        action='store',
                        help='Log diretory')
    parser.add_argument('--tmpdir',
                        required=False,
                        default='/tmp/scdt-log',
                        action='store',
                        help='Temp diretory')
    parser.add_argument('--reline',
                        required=False,
                        default=None,
                        action='store',
                        help='regular expression for parse log')
    parser.add_argument('--url',
                        required=False,
                        default='https://foo.com/bar`',
                        action='store',
                        help='upload url')
    parser.add_argument('--user',
                        required=False,
                        default=None,
                        action='store',
                        help='username for upload')
    parser.add_argument('--password',
                        required=False,
                        default=None,
                        action='store',
                        help='password for upload')
    return parser.parse_args()


class QueueManage():
    def __init__(self, poolsize=None):
        self.poolsize = poolsize
        self.queue_list = []

    def create_queue_list(self):
        for i in range(0, self.poolsize):
            self.queue_list.append(queue.Queue())

        return self.queue_list


class ParseLog():
    def __init__(self, file=None, reline=None):
        self.file = file
        self.reline = reline
        
        self._prepare_parse()

    def _prepare_parse(self):
        """Prepare something befor parse
        """
        self.re = re.compile(self.reline)
        self._check_file()

    def _check_file(self):
        """Check file validity
        """
        if not istext(self.file):
            raise NotLogFile

    def _get_loglines(self, maxbytes):
        """Open file and return log lines with yield
        """
        with open(self.file) as fd:
            while True:
                lines = fd.readlines(maxbytes)
                if not lines: break
                yield lines

    def _parse(self, lines):
        """Parse log lines
        """
        result = []
        for line in lines:
            m = self.re.search(line)
            if m is None: continue
            result.append(m.groupdict())

        return result


class ScdtParseLog(ParseLog):
    def __init__(self, file=None, onetime_parse_bytes=None, reline=None, queue_list=None, keyword='error'):
        super().__init__(file=file, reline=reline)
        self.maxbytes = onetime_parse_bytes or 1048576
        self.keyword = keyword
        self.queue_list = queue_list

    def _valid_logline_to_queue(self, valid_lines):
        """Convert one err log line to dict
        """
        for valid_line in valid_lines:
            valid_dict = {}
            hour = valid_line['hour']
            valid_dict['deviceName'] = valid_line['deviceName']
            if 'launchd' in valid_line['process']:
                m = re.search(r'\S+ \((?P<processname>.+)[\[.](?P<processid>\d+)\]?\)', valid_line['process'])
                try:
                    valid_dict['processId'] = m.group('processid')
                    valid_dict['processName'] = m.group('processname')
                except:
                    pass
                m = re.search(r'(?P<description>^[^:]+)', valid_line['log'])
                valid_dict['description'] = m.group('description')
                if int(hour) < 10:
                    valid_dict['timeWindow'] = hour + '00-0' + str(int(hour) + 1) + '00' 
                else:
                    valid_dict['timeWindow'] = hour + '00-' + str(int(hour) + 1) + '00' 

                j = json.dumps(valid_dict)

                self.queue_list[int(hour)].put(j)

    def _get_valid_logline(self, raw_parsed_lines):
        """Filter wanted log lines
        """
        valid_loglines = []
        for l in raw_parsed_lines:
            if self.keyword in l['log']:
                valid_loglines.append(l)

        return valid_loglines

    def _stop_put_to_queue(self):
        """Identity for end of queue
        """
        for q in self.queue_list:
            q.put(None)

    def parse(self):
        """Parse log and write to queue
        """
        for lines in self._get_loglines(self.maxbytes):
            raw_parsed_lines = self._parse(lines)
            valid_lines = self._get_valid_logline(raw_parsed_lines)
            self._valid_logline_to_queue(valid_lines)

        self._stop_put_to_queue()

    def consume_queue(self, q, queue_index, tmpdir):
        """Get log line from queue and write to temp file
        """
        consume_interval = 5
        filepath = os.path.join(tmpdir, queue_index + '.log')
        with open(filepath, 'w') as fd:
            try:
                while True:
                    line = q.get()
                    if line is None:
                        fd.flush
                        break
                    fd.write(line)
                    fd.write('\n')
                    q.task_done()
            except queue.Empty:
                time.sleep(consume_interval)


class ScdtUploadLog():
    def __init__(self, logfile=None, url=None, user=None, password=None, upload_timeout=None, failedfile=None):
        self.logfile = logfile
        self.maxbytes = 1048576
        self.process_err_times = {}
        self.url = url
        self.user = user
        self.password = password
        self.upload_timeout = upload_timeout or 4
        self.failed_file = failedfile

        self._open_failed_file()

    def _open_failed_file(self):
        self.failed_fd = open(self.failed_file, 'w')

    def _get_log_lines(self):
        """Read lines from log file with maxbytes
        """
        with open(self.logfile) as fd:
            while True:
                lines = fd.readlines(self.maxbytes)
                if not lines: break
                yield lines

    def count_process_err_times(self):
        """Count every process error times
        """
        for lines in self._get_log_lines():
            for line in lines:
                d = json.loads(line)
                n = d['processName']
                if n not in self.process_err_times.keys():
                    self.process_err_times[n] = 1
                else:
                    self.process_err_times[n] += 1

    def _gen_upload_data(self, line):
        """generate post data
        """
        d = json.loads(line)
        body = {
                  "deviceName": d['deviceName'],
                  "processId": d['processId'],
                  "processName": d['processName'],
                  "description": d['description'],
                  "timeWindow": d['timeWindow'],
                  "numberOfOccurrence": self.process_err_times[d['processName']]
              }
        return body

    def _handle_failed_data(self, data):
        """Write upload failed log
        """
        self.failed_fd.write(data)
        self.failed_fd.write('\n')

    def upload(self):
        """Upload error log
        """
        headers = {'content-type': "application/json"}
        for lines in self._get_log_lines():
            for line in lines:
                body = self._gen_upload_data(line)
                try:
                    r = requests.post(
                        self.url,
                        json=body,
                        headers=headers,
                        timeout=self.upload_timeout,
                        auth = HTTPBasicAuth(self.user, self.password)
                        )
                    if r.status_code != requests.codes.created:
                        self._handle_failed_data(body)
                except:
                    self._handle_failed_data(body)

    def close(self):
        self.failed_fd.close()


def parselog(targetfile, reline, tmpdir):
    """Interface for parse log
    """
    poolsize = 24
    q = QueueManage(poolsize=poolsize)
    queue_list = q.create_queue_list()

    p = ScdtParseLog(file=targetfile, reline=reline, queue_list=queue_list)
    for i in range(0, poolsize):
        t = threading.Thread(target=p.consume_queue, args=(queue_list[i], str(i), tmpdir))
        t.start()

    p.parse()

def check_args(args):
    """Check args validity
    """
    if not os.path.exists(args.logpath):
        raise FileNotExist
    return True

def uploadlog(args):
    """Upload log
    """
    filelist = getfiles(args.tmpdir)
    failed_file = os.path.join(args.tmpdir, 'failed.log')
    u = ScdtUploadLog(url=args.url, user=args.user, password=args.password, failedfile=failed_file)
    for f in filelist:
        u.logfile = f
        u.process_err_times = {}
        u.count_process_err_times()
        u.upload()
        os.remove(f)

    u.close()

def main():
    args = get_args()
    check_args(args)
    reline = args.reline
    if reline is None:
        reline = '(?P<month>^\w+) (?P<day>\d+) (?P<hour>\d{2}):\d{2}:\d{2} '
        reline += '(?P<deviceName>\w+) (?P<process>[^:]+): (?P<log>.*)'

    filelist = getfiles(args.logpath)
    for f in filelist:
        parselog(f, reline, args.tmpdir)

    uploadlog(args)

if __name__ == '__main__':
    signal.signal(signal.SIGINT, handlesignal)
    signal.signal(signal.SIGTERM, handlesignal)
    main()