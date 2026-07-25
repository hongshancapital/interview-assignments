# -*- coding: utf-8 -*-
import requests
import json
from requests.packages.urllib3.exceptions import InsecureRequestWarning
requests.packages.urllib3.disable_warnings(InsecureRequestWarning)
url = " https://foo.com/bar"
data = {
    'deviceName': "BBAOMACBOOKAIR2",
    'processId': 54434,
    'processName': 'BITCrashManager invokeDelayedProcessing',
    'description': 'Another exception handler was added. If this invokes any kind exit() after processing the exception, which causes any subsequent error handler not to be invoked, these crashes will NOT be reported to HockeyApp!',
    'timeWindow': '1400-1600',
    'numberOfOccurrence': 2,
}

# 以下有两种 ：
# 1。data参数传
a=requests.post(verify=False,url=url, data=json.dumps(data))
print(a)

# 2。json参数传
#requests.post(url=url, json=data)