import requests
import json


data = {'deviceName': 'BBAOMACBOOKAIR2', 'processId': '1', 'processName': 'com.apple.xpc', 'description': 'Could not find uid associated with service: 0: Undefined error: 0 501', 'timeWindow': '0100-0200', 'numberOfOccurrence': '17'}

r = requests.post('https://foo.com/bar', data=data)
print(r.text)

