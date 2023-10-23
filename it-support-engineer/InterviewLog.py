#!/usr/bin/env python3
import re
import json
import requests

def main(logfile,url):
    # 将原始日志进行字符串处理
    json_dict=list_logs(logfile)  
    # 去除重复日志
    new_list=group(json_dict)
    # 统计日志发生次数
    new_list=count(json_dict,new_list)
    # Post Json文件到服务器
    send_json(new_list,url) 
    # print(json_dict)

def list_logs(filepath):   
    json_dict=[]
    with open(filepath) as file:
        lines= file.readlines()
    for text in lines:     
        if '---' in text:     
            text=pre_text
        elif 'Notice:' in text:    
            t=0
            pre_text=text
            continue
        elif '	' in text and t!=1 :  
            pre_text=pre_text.split('\n')[0]+text.split('	')[1]
            t+=1
            continue
        elif '	' in text and t==1:
            text=pre_text+text        
        json_dict.append(analysis(text))
        pre_text=text
    return json_dict

def group(list):   
    new_list = []
    for it in list:
        if it not in new_list:
            new_list.append(it)
    return new_list

def count(list,new_list): 
    # print(new_list)
        for it in new_list:
            total=list.count(it)
            it['numberOfOccurrence']=total
            # if total>1:
            #     print(it)
        return new_list

def send_json(list,url): 
        requests.packages.urllib3.disable_warnings()
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

def analysis(dump):
    data={}
    data['deviceName']=dump.split()[3]
    data['processName']=re.match('^[a-zA-Z\.]*',dump.split()[4]).group()
    data['processId'] = dump.split('[')[1].split(']')[0]
    data['description']=' '.join(dump.split()[5:-1])
    hour=re.match('^([0-9]{2})',dump.split()[2]).group()
    if int(hour)<9:
        data['timeWindow']='0'+hour[1]+'00-0'+str(int(hour)+1)+'00'
    elif int(hour)==9:
        data['timeWindow'] ='0'+hour + '00-' + str(int(hour) + 1) + '00'
    else:
        data['timeWindow'] = hour + '00-' + str(int(hour) + 1) + '00'
    return data

if __name__=='__main__':
    logpath='.\interview_data_set'
    url='https://foo.com/bar'
    main(logpath,url)