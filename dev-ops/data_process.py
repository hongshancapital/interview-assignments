#!/usr/bin/env python
# -*- coding: utf-8 -*-
# Copyright (c) 2017 - hongzhi.wang <hongzhi.wang@moji.com> 
'''
Author: hongzhi.wang
Create Date: 2020-11-06
Modify Date: 2020-11-06
'''

from datetime import date
import requests
import json
import logging

server = "https://foo.com/bar"

Months = [date(2020, i, 12).strftime("%b") for i in range(1, 13)]


class ErrMessage:

    def __init__(self, message):
        self.message = message
        self.message_list = message.split()

    def get_time_window(self):
        hour = int(self.message_list[2].split(":")[0])
        return "0{}00-0{}00".format(hour, hour + 1)

    def get_device_name(self):
        return self.message_list[3]

    def get_process_id(self):
        return int(self.message_list[4].split("[")[1].split("]")[0])

    def get_process_name(self):
        return self.message_list[4].split("[")[0]

    def get_description(self):
        return " ".join(self.message_list[5:])

    def json(self, count):
        return {
            "deviceName": self.get_device_name(),
            "processId": self.get_process_id(),
            "processName": self.get_device_name(),
            "description": self.get_description(),
            "numberOfOccurrence": count,
            "timeWindow": self.get_time_window()
        }


class ErrMessages:
    def __init__(self, contents):
        self.contents = contents

    def handle_repeat_message(self):
        for pos in range(0, len(self.contents)):
            if "--- last message repeated 1 time ---" in self.contents[pos]:
                self.contents[pos] = self.contents[pos][:16] + self.contents[pos - 1][16:]

    def handle_multi_line(self):
        new_contents = []
        pos = 0
        while pos < len(self.contents):
            if self.contents[pos][:3] in Months:
                new_contents.append(self.contents[pos])
            else:
                new_contents[len(new_contents) - 1] = new_contents[len(new_contents) - 1] \
                                                      + "\n" + self.contents[pos]
            pos += 1
        self.contents = new_contents

    def to_json(self):
        json_list = []
        count = 0
        last_time_window = ""
        for content in self.contents:
            err_message = ErrMessage(content)
            if last_time_window == err_message.get_time_window():
                count += 1
            else:
                count = 1
            last_time_window = err_message.get_time_window()
            json_list.append(err_message.json(count))
        self.contents = json_list

    def send_data(self):
        for content in self.contents:
            response = requests.post(server, json=content)
            if response.status_code != 200:
                logging.warning("failed to send data {}".format(json.dumps(content)))

    def main(self):
        self.handle_repeat_message()
        self.handle_multi_line()
        self.to_json()
        self.send_data()


if __name__ == "main":
    with open("DevOps_interview_data_set") as f:
        ErrMessages(f.readlines()).main()
