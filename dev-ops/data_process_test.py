#!/usr/bin/env python
# -*- coding: utf-8 -*-
# Copyright (c) 2017 - hongzhi.wang <hongzhi.wang@moji.com> 
'''
Author: hongzhi.wang
Create Date: 2020-11-06
Modify Date: 2020-11-06
'''

import unittest
from data_process import *


class ErrMessageTest(unittest.TestCase):

    def setUp(self):
        self.err_message = ErrMessage(
            """May 13 00:01:58 BBAOMACBOOKAIR2 com.apple.xpc.launchd[1] (com.apple.mdworker.bundles[12513]): Could not find uid associated with service: 0: Undefined error: 0 501""")

    def test_time_window_format(self):
        self.assertEqual(self.err_message.get_time_window(), "0000-0100")

    def test_device_name(self):
        self.assertEqual(self.err_message.get_device_name(), "BBAOMACBOOKAIR2")

    def test_process_id(self):
        self.assertEqual(self.err_message.get_process_id(), 1)

    def test_process_name(self):
        self.assertEqual(self.err_message.get_process_name(), "com.apple.xpc.launchd")

    def test_description(self):
        self.assertEqual(self.err_message.get_description(),
                         "(com.apple.mdworker.bundles[12513]): Could not find uid associated with service: 0: Undefined error: 0 501")

    def test_json_output(self):
        self.assertEqual(
            self.err_message.json(1),
            {
                "deviceName": self.err_message.get_device_name(),
                "processId": self.err_message.get_process_id(),
                "processName": self.err_message.get_device_name(),
                "description": self.err_message.get_description(),
                "numberOfOccurrence": 1,
                "timeWindow": self.err_message.get_time_window()
            })


class ErrMessagesTest(unittest.TestCase):

    def test_repeated_message(self):
        err_messages = ErrMessages([
            """May 13 00:22:18 BBAOMACBOOKAIR2 com.apple.xpc.launchd[1] (com.apple.xpc.launchd.domain.user.914945058): Service "com.apple.xpc.launchd.unmanaged.loginwindow.594" tried to register for endpoint "com.apple.tsm.uiserver" already registered by owner: com.apple.TextInputMenuAgent""",
            "May 13 00:22:49 --- last message repeated 1 time ---"
        ])
        err_messages.handle_repeat_message()
        self.assertEqual(
            err_messages.contents[1],
            """May 13 00:22:49 BBAOMACBOOKAIR2 com.apple.xpc.launchd[1] (com.apple.xpc.launchd.domain.user.914945058): Service "com.apple.xpc.launchd.unmanaged.loginwindow.594" tried to register for endpoint "com.apple.tsm.uiserver" already registered by owner: com.apple.TextInputMenuAgent"""
        )

    def test_multi_line_messages(self):
        err_messages = ErrMessages(
            """May 13 00:29:49 BBAOMACBOOKAIR2 com.apple.xpc.launchd[1] (com.apple.mdworker.bundles[12610]): Service exited with abnormal code: 78
May 13 00:30:00 BBAOMACBOOKAIR2 syslogd[113]: Configuration Notice:
	ASL Module "com.apple.cdscheduler" claims selected messages.
	Those messages may not appear in standard system log files or in the ASL database.
May 13 00:30:00 BBAOMACBOOKAIR2 syslogd[113]: Configuration Notice:
	ASL Module "com.apple.install" claims selected messages.
	Those messages may not appear in standard system log files or in the ASL database.
May 13 00:30:00 BBAOMACBOOKAIR2 syslogd[113]: Configuration Notice:
	ASL Module "com.apple.callhistory.asl.conf" claims selected messages.
	Those messages may not appear in standard system log files or in the ASL database.""".split("\n")
        )
        err_messages.handle_multi_line()
        self.assertEqual(len(err_messages.contents), 4)
        self.assertEqual(err_messages.contents[1], """May 13 00:30:00 BBAOMACBOOKAIR2 syslogd[113]: Configuration Notice:
	ASL Module "com.apple.cdscheduler" claims selected messages.
	Those messages may not appear in standard system log files or in the ASL database.""")

    def test_to_json(self):
        err_messages = ErrMessages(
            """May 13 04:56:54 BBAOMACBOOKAIR2 com.apple.xpc.launchd[1] (com.apple.mdworker.bundles[52616]): Could not find uid associated with service: 0: Undefined error: 0 501
May 13 04:56:54 BBAOMACBOOKAIR2 com.apple.xpc.launchd[1] (com.apple.mdworker.bundles[52616]): Service exited with abnormal code: 78
May 13 05:02:10 BBAOMACBOOKAIR2 com.apple.xpc.launchd[1] (com.apple.xpc.launchd.domain.pid.mdmclient.52624): Failed to bootstrap path: path = /usr/libexec/mdmclient, error = 108: Invalid path
May 13 05:04:54 BBAOMACBOOKAIR2 com.apple.xpc.launchd[1] (com.apple.mdworker.bundles[52629]): Could not find uid associated with service: 0: Undefined error: 0 501
May 13 05:04:54 BBAOMACBOOKAIR2 com.apple.xpc.launchd[1] (com.apple.mdworker.bundles[52629]): Service exited with abnormal code: 78
May 13 05:04:54 BBAOMACBOOKAIR2 syslogd[113]: ASL Sender Statistics""".split("\n")
        )
        err_messages.to_json()
        self.assertEqual(err_messages.contents[1]["numberOfOccurrence"], 2)
        self.assertEqual(err_messages.contents[4]["numberOfOccurrence"], 3)


if __name__ == "main":
    unittest.main()
