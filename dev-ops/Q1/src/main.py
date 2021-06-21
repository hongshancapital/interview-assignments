import sys
import json
import re
import requests


server = "https://foo.com/bar"
pattern = re.compile('^\S+ \d+ (?P<timeWindow>\d+)[^ ]* (?P<deviceName>[\w+]*) (?P<processName>\S+)\[('
                     '?P<processId>\d+)[^ ]*: (?P<description>.*)')
logFile = sys.argv[1]
errorCollection = []

try:
    with open(logFile, 'r') as f:
        for line in f:
            matchObj = re.match(pattern, line)
            if matchObj:
                errorCollection.append(matchObj.groupdict())
except (FileNotFoundError, NameError) as e:
    print("Please find the file: %s" % e)


# 判断是否有必要继续分析日志
if not errorCollection:
    print("no error log needed to be analyzed")
    sys.exit(0)

# 对处理后的日志去重，统计出现的次数
errorCollectionRemoveDuplicate = []
[errorCollectionRemoveDuplicate.append(i) for i in errorCollection if not i in errorCollectionRemoveDuplicate]

result = []

for i in errorCollectionRemoveDuplicate:
    if errorCollection.count(i) >= 1:
        i["numberOfOccurrence"] = str(errorCollection.count(i))
        result.append(i)

result = json.dumps(result)
print(result)

# POST 上传到服务器
r = requests.post(server, json=result, verify=False)
if r.status_code == '200':
    print("上传成功")
else:
    print("上传失败")

