import pandas as pd 
import numpy as np
import json
import re
import requests

def time_translate(x):
    return f'{x[0:2]}00-{str(int(x[0:2])+1).zfill(2)}00'

def number_find(x):
    s = re.findall(r"(?<!\d)\d{3,5}(?!\d)", x)[0]
    return str(s)

def name_find(x):
    if '(' in x :
        try:
            aa = re.findall(r'[(](.*?)\d+[)]',x)[0]
            bb = re.sub('com.apple.xpc.launchd.','',aa)
            ss = bb[:-1]
            return ss
        except:
            aa = x.split('(com.apple.')[-1]
            bb = aa.split('[')[0]
            return bb
    else:
        aa = x.split('[')[0]
        return aa

    

    f = open("Helpdesk_interview_data_set", "r")
lines=f.readlines()
error_line=[]
abnormal_line=[]
for line in lines:
    if "error" in line:
        error_line.append(line.lower())
    else:
        abnormal_line.append(line.lower())
name={"DATA":error_line}
data=pd.DataFrame(name)
data1 = data['DATA'].str.split(' ',n=4,expand=True) 
data1.columns =list('abcde')
data2 = data1.rename(columns={'a':'Month','b':'Day','c':'Time','d':'DeviceName','e':'Content'})
data3 = data2['Content'].str.split(': ',n=1,expand=True)
data3.columns =list('ab')
data4 = data3.rename(columns={'a':'ProcessName','b':'Error description'})
data5 = pd.concat([data2,data4],axis=1)

data5['timeWindow'] = data5['Time'].map(time_translate)
data5['processId'] = data5['ProcessName'].map(number_find)
data5['processName'] = data5['ProcessName'].map(name_find)
data5['description'] = data5['Error description']
del data5['Content']
del data5['ProcessName']
del data5['Error description']
del data5['Month']
del data5['Day']
del data5['Time']
data5.to_excel('work.xls',index=False)
JSON = data5.to_json(orient="records",force_ascii=False)
headers = {"User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36"}
URL = "https://foo.com/bar"

r = requests.post(URL, data=JSON,headers=headers,verify=False)