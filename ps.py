import sys
import time
import re
import json
import http.client



def insert_dict(my_dict,my_list):
    match = False
    for i in range(len(my_list)):
        if my_dict['processId']==my_list[i]['processId'] and my_dict['timeWindow']==my_list[i]['timeWindow'] and my_dict['deviceName']==my_list[i]['deviceName']:
            my_list[i]['numberOfOccurrence'] = my_list[i]['numberOfOccurrence'] + 1
            my_list[i]['description'].append(my_dict['description'][0])
            match =True
            break

        #没有进程记录
        else:
            match=False

    if match ==False:
        my_list.append(my_dict)

        my_list[-1]['numberOfOccurrence'] = 1

    # 初始化第一次时候判断是否存在数据，没有就初始化数据
    if my_list:
        pass
    else:
        my_list.append(my_dict)
        my_list[0]['numberOfOccurrence'] = 1


def analy_log(log):
    numberOfOccurrence = 0
    log_split = per_log.split(":")
    # get logtime
    timeWindow = log_split[0]
    # get log_que
    deviceName = log_split[2].split(" ")[1]
    service_split = log_split[2].split(" ")
    service = service_split[2]
    pattern = r'\[\d{1,5}\]'
    ret = re.findall(pattern, service)
    # get main pid
    if not ret:
        return None
    else:
        processId = ret[0].replace('[', '').replace(']', '')
    # get detail name mainpro+childpro
    processName = re.sub(pattern, " ", service)
    # get des
    des_tmp = log_split[3:]
    description=[]
    description.append(''.join(des_tmp).strip(' '))
    log_json = {'timeWindow': timeWindow, 'processName': processName, 'deviceName': deviceName, 'processId': processId, 'numberOfOccurrence': numberOfOccurrence,
                'description': description}
    return log_json

def one_line():
    with open(log_path) as caroline:
        for line in caroline:
            line = line.strip()
            m = re.match(r'[a-zA-Z]{3,} \d{2} \d{2}:\d{2}:\d{2}.*', line, re.I)
            if m:
                yield a_date, line
            else:
                yield other, line
    yield EOF, ''


# load logs
log_path =  "E:\interview_data_set\interview_data_set"
a_date, other, EOF = 0,1,2
complete_line = []
my_list=[]

for kind, content in one_line():
    if kind in [a_date, EOF]:
        if complete_line:
            per_log=' '.join(complete_line)

            if analy_log(per_log):
                my_dict = analy_log(per_log)
                insert_dict(my_dict, my_list)

        complete_line = [content]
    else:
        complete_line.append(content)

my_dict={'data':my_list}

json_data=json.dumps(my_dict)


headers = {"content-type": "application/json","Auth": "andrew.zhao"}
conn = http.client.HTTPSConnection("foo.com")
conn.request('POST', '/bar', json_data, headers)
response = conn.getresponse()
conn.close()


