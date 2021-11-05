#! /usr/bin/
# -*- coding:utf-8 -*-
import re,json,requests

def main(file,url):
    json_dict=read_file(file)  #生成列表
    new_list=quchong(json_dict)   #去重
    new_list=count(json_dict,new_list)  #计数
    send_json(new_list,url)   #post
    # print(new_list)
    # print(json_dict)

def read_file(file):   #读取文件
    json_dict=[]
    with open(file) as file:
        lines= file.readlines()
    for text in lines:     #判断日志中的特殊行
        if '---' in text:     #判断是否是上一条
            text=pre_text
        elif 'syslogd' in text:    #判断特殊行
            t=0
            pre_text=text
            continue
        elif '	' in text and t!=1 :  #判断特殊行
            pre_text=pre_text.split('\n')[0]+text.split('	')[1]  #讲特殊行合并
            t+=1
            continue
        elif '	' in text and t==1:
            text=pre_text+text         #特殊行合并
        json_dict.append(analysis(text))      #将每行数据发给模块做分析
        pre_text=text
        # print(pre_text)
    return json_dict

def quchong(list):     #去重操作
    new_list = []
    for it in list:
        if it not in new_list:
            new_list.append(it)
    return new_list

def count(list,new_list):  #统计，利用list count来做统计
    # print(new_list)
        for it in new_list:
            total=list.count(it)
            it['numberOfOccurrence']=total
            # if total>1:
            #     print(it)
        return new_list

def send_json(list,url):     #将json数据post
        headers={'Content-Type': 'application/json;charset=utf-8'}
        try:
            response = requests.post(url=url, headers=headers, data=json.dumps(list),verify=False)
            if response.status_code == 200:
                # return response.json()
                print(response)
            else:
                print(response)
        except requests.ConnectionError as e:
            print('Error', e.args)

def analysis(dump):  #提取文件中的关键信息，生成字典
    # print(dump)
    data={}
    data['deviceName']=dump.split()[3]
    data['processName']=re.match('^[a-zA-Z\.]*',dump.split()[4]).group()
    data['processId'] = dump.split('[')[1].split(']')[0]
    data['description']=' '.join(dump.split()[5:-1])
    hour=re.match('^([0-9]{2})',dump.split()[2]).group()
    if int(hour)<9:
        data['timeWindow']='0'+hour+'00-0'+str(int(hour)+1)+'00'
    elif int(hour)==9:
        data['timeWindow'] ='0'+hour + '00-' + str(int(hour) + 1) + '00'
    else:
        data['timeWindow'] = hour + '00-' + str(int(hour) + 1) + '00'
    return data

if __name__=='__main__':
    file='C:/Users/josh.wang/PycharmProjects/Net Automation/interview_data_set'
    url='https://foo.com/bar'
    # url="http://coolaf.com"
    main(file,url)
