#!/user/bin/python 3.8.9
import re
import json
import requests

# 以只读方式读取日志文件
with open('interview_data_set', 'r') as f:
    lines = f.readlines()
# 准备接收目标日志内容的数据结构，列表
valid_lines = []
# 遍历日志，把匹配规则的行添加到valid_lines中，准备后续分析。
for i in range(len(lines)):
    a = [item for item in lines[i].split(' ', 6)]
    try:
        if re.split('\(|\[|\]|:', a[5])[2].isdigit():
            # 正则分隔字符串以判断特征：第6个元素不符合形如'(xxx[000])'的行都不是分析的目标
            valid_lines.append(a)
        else:
            # 不符合特征的第一种情况：能正常正则分割，但不匹配规则，对应位置不是纯数字。
            print('line %s is not error log' % i)
    except Exception as e:
        # 不符合特征的第二种情况：正则分割分不出来第三个元素，out of range。
        print(i, e)
# 准备接收整理完格式日志的列表
formatted_lines = []
# 遍历日志，整理成目标格式。这里都是单条记录，不计数，最后统一计算。
for item in valid_lines:
    deviceName = item[3]
    processId = re.split('\(|\[|\]|:', item[5])[2]
    processName = re.split('\(|\[|\]|:', item[5])[1]
    description = item[6].replace('\n', '')
    """
    拿到时间格式字符串，按':'分割，取第一个元素确定时间段。
    '-'前面的部分3、4位补0，
    '-'后面的部分先变成整数，加1，再变成字符串，不足两位的向前补0，再把3、4位补上0    
    """
    timeWindow = item[2].split(':')[0] + '00-' + str(int(item[2].split(':')[0]) + 1).zfill(2) + '00'
    # 构造json格式数据，暂时没有processID和计数的key，那两部分需要后续处理。
    temp = [{'deviceName': deviceName, 'processName': processName, 'description': description,
             'timeWindow': timeWindow}, processId]
    formatted_lines.append(temp)

# 准备事件列表：对除了processID以外，完全一样的消息去重
event_list = []
for item in formatted_lines:
    if item not in event_list:
        event_list.append(item[0])
# 准备计算列表：把同时段，相同事件的processID以列表形式添加到字典里，每个事件是一个列表元素。
calculation_list = []
for i in event_list:
    processId_list = []
    for j in formatted_lines:
        if i == j[0]:
            processId_list.append(j[1])
    i['processID'] = processId_list
    calculation_list.append(i)
# 准备jason数据列表
json_list = []
for item in calculation_list:
    item['numberOfOccurrence'] = len(item['processID'])
    # processID列表的长度就是同时段出现相同事件的次数，把它也添加到json数据结构中。
    json_list.append(item)

json_str = json.dumps(json_list, ensure_ascii=False)
with open('output.txt','w') as f1:
    f1.write(json_str)
"""
最后一步一直报如下证书错误，代码被注释掉了。输出结果请参考output.txt。
requests.exceptions.SSLError: HTTPSConnectionPool(host='foo.com', port=443): 
Max retries exceeded with url: /bar (Caused by SSLError(SSLCertVerificationError(1, 
'[SSL: CERTIFICATE_VERIFY_FAILED] certificate verify failed: unable to get local issuer certificate (_ssl.c:1125)')))
"""
if json_str:
    url = "https://foo.com/bar"
    headers = {'Content-Type': 'application/json; charset=UTF-8'}
    res = requests.request("post", url, json=json_str, headers=headers)
    print(res.status_code)
    print(res.text)
