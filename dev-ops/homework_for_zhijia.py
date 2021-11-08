import json
import re
import requests


def task(file_path, req_url):
    json_dict = read_file(file_path)
    new_list = remove_duplicate(json_dict)
    new_list = count(json_dict, new_list)
    send_json(new_list, req_url)


def read_file(file_path):
    """
    读取文件
    :param file_path:
    :return:
    """
    json_dict = []
    with open(file_path) as file_path:
        lines = file_path.readlines()
    pre_text = ''
    t = ''
    for text in lines:
        if '---' in text:
            text = pre_text
        elif 'syslogd' in text:
            t = 0
            pre_text = text
            continue
        elif '	' in text and t != 1:
            pre_text = pre_text.split('\n')[0]+text.split('	')[1]
            t += 1
            continue
        elif '	' in text and t == 1:
            text = pre_text+text
        json_dict.append(analysis(text))
        pre_text = text
    return json_dict


def analysis(dump):
    """
    分析
    :param dump:
    :return:
    """
    data = {}
    data['deviceName'] = dump.split()[3]
    data['processName'] = re.match('^[a-zA-Z\.]*',dump.split()[4]).group()
    data['processId'] = dump.split('[')[1].split(']')[0]
    data['description'] = ' '.join(dump.split()[5:-1])
    hour = re.match('^([0-9]{2})', dump.split()[2]).group()
    if int(hour) < 9:
        data['timeWindow'] = hour+'00-0'+str(int(hour)+1)+'00'
    else:
        data['timeWindow'] = hour + '00-' + str(int(hour) + 1) + '00'
    return data


def remove_duplicate(json_dict):
    """
    去重
    :param json_dict:
    :return:
    """
    new_list = []
    for it in json_dict:
        if it not in new_list:
            new_list.append(it)
    return new_list


def count(old_list, new_list):
    """
    统计
    :param old_list:
    :param new_list:
    :return:
    """
    for it in new_list:
        total = old_list.count(it)
        it['numberOfOccurrence'] = total
    return new_list


def send_json(list_data, req_url):
    """
    发送信息
    :param list_data:
    :param req_url:
    :return:
    """
    headers = {'Content-Type': 'application/json;charset=utf-8'}
    try:
        response = requests.post(url=req_url, headers=headers, data=json.dumps(list_data), verify=False)
        if response.status_code == 200:
            print("请求发送成功")
        else:
            print(f"请求发送失败,原因是{response.status_code}")
    except requests.ConnectionError as e:
        print('Error', e.args)


if __name__ == '__main__':
    file = 'D:/安装包/interview_data_set'
    url = 'https://foo.com/bar'
    task(file, url)
