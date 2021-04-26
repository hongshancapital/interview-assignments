import re
import json
import requests

log_list = []
log_dict = {}
matchpatter = re.compile(r'\d+')

# 处理日志，并返回json文件
def log_json():
    with open('scdt_china/DevOps_interview_data_set', 'r') as f:
        for line in f.readlines():
            data_meat = line.split()
            if data_meat[3] == 'BBAOMACBOOKAIR2' and data_meat[5][0] == '(':
                log_dict['deviceName'] = data_meat[3]
                a = re.findall(matchpatter, data_meat[5])
                log_dict['processName'] = data_meat[4]
                log_dict['description'] = ' '.join(data_meat[6:])
                log_dict['timeWindow'] = data_meat[2]
                if len(a) == 1:
                    log_dict['processId'] = a[0]
                elif len(a) > 1:
                    log_dict['processId'] = a[-1]
                log_list.append(log_dict)
    return json.dumps(log_list)

# 提交post到指定url
def log_post(url, data):
    requests.post(url, data=data)


if __name__ == '__main__':
    url = 'https://foo.com/bar'
    data = log_json()
    log_post(url, data)
