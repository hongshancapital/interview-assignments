## Introduction
1. Implement by python.
2. Not familiar with Powershell script.

## Code Explaination:
1. parse raw log text line by line, using re
2. for the reason of saving memory, upload and clean up local data whenever a whole timeWindow finished.
  For example, parse all the log text lines which are with in Hour 00, and then upload all these data, and then clean up local memory variables. And then start parse text lines which are within Hour 01
3. Caculating numberOfOccurrence
  Set the tuple (deviceName, processId, processName, description, timeWindow) as the key,  and caculate it's occurrences

## Enhancement:
1. Parse log text more detailedly.
  For example:
  - handle "May 13 00:22:49 --- last message repeated 1 time ---"
  - handle extra lines of:
  May 13 00:30:00 BBAOMACBOOKAIR2 syslogd[113]: Configuration Notice:
        ASL Module "com.apple.cdscheduler" claims selected messages.
        Those messages may not appear in standard system log files or in the ASL database.
2. Multi-Thread
  Assume the logfile is extemely huge and the network latency is considerable, we should take memory and performace into consideration. For memory, it is menioned above. For performance, because uploading is network IO intensive task, so multi-thread is a good choice.

## Contact
- Name: 李楠
- Phone: 15201394670
- Mail: believelinan@aliyun.com

