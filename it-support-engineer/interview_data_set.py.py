import re
import json
import requests

log_file = '.\\interview_data_set'
log_list = ['deviceName','processId','processName','description','timeWindow','numberOfOccurrence']
pattern = 'error = | error:'
pattern1 = re.compile(r'\[(.*?)\]',re.S)
data2json = {}
data2list = []
jsonlist = []
num = 0

def timelevel(a):
    c = int(a) + 1
    b = a + '00-' + str(c).zfill(2) +'00'
    return b

with open(log_file, 'r') as f:
    for line in f:
        if re.search(pattern,line,flags=0):
            log_line = line.split()
            devicename = log_line[3]
            matchs = re.search(r'(.*)\[(.*)\]',log_line[5])
            if matchs:
                processid = matchs.group(2)
                processname = matchs.group(1)[1:]
            elif log_line[5]:
                processid = log_line[5].split('.')[-1][:-2]
                processname = log_line[5][1:].rstrip(log_line[5].split('.')[-1]).rstrip('.')
            timewindow = timelevel(log_line[2][0:2])
            descriPtion = ''
            for desclist in log_line[6:]:
                    descriPtion =  descriPtion + ' ' + desclist 
            #print(timeWindow,deviceName,processId,processName,description)
            data2json = {
                    'deviceName':devicename,
                    'processId':processid,
                    'processName':processname,
                    'description':descriPtion,
                    'timeWindow':timewindow,
                    'numberOfOccurrence':num
            }
            data2list.append((timewindow,processname))
            if data2list.count((timewindow,processname)) >1:
                 data2json['numberOfOccurrence'] = data2json['numberOfOccurrence'] + 1
            else:
                 jsonlist.append(data2json)


for jl in jsonlist:
     jl['numberOfOccurrence']=data2list.count((jl['timeWindow'],jl['processName']))

print(jsonlist)

with open('interview_data_set.json', 'w') as f:
    json.dump(jsonlist, f)

url = "https://foo.com/bar"
file_path = './/interview_data_set.json'
with open(file_path,'rb') as f:
     r = requests.post(url, files={'file':f},verify=False)

print(r.text)
