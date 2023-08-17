'''
IT_Support_Engineer_Assignment.py
'''
import pandas as pd
import re
import requests

class IT_Support_Engineer_Assignment:
    def __init__(self):
        self.fr = open('interview_data_set','r')
        self.url = 'https://foo.com/bar'
        self.pattren = re.compile(r'(\d{2}\:\d+\:\d+).?([A-Z]+[0-9]).?(\w+[\.\w]*)([[0-9]+?])\s*\S+\s*([\S\s]+)')
        self.index = ['0000-0100','0100-0200','0200-0300','0300-0400',
                    '0400-0500','0500-0600','0600-0700','0700-0800',
                    '0800-0900','0900-1000','1000-1100','1100-1200',
                    '1200-1300','1300-1400','1400-1500','1500-1600',
                    '1600-1700','1700-1800','1800-1900','1900-2000',
                    '2000-2100','2100-2200','2200-2300','2300-0000']
        self.columns = ['timewindow','deviceName','processName','processId','description','numberOfOccurrence']

    #格式化行
    def formatlines(self,data):
        lines = []
        for line in data:
            if line[0] == '\t':
                line=line.rstrip('\n')
                line=line.lstrip('\t')
                lines[-1] += line
                continue
            lines.append(line.rstrip('\n'))
        return lines

    #筛选关键字段
    def formatstr(self,data):
        new_data =[]
        for i in data:
            line = self.pattren.search(i)
            if not line:
                continue
            line = list(line.groups())
            new_data.append(line)
        return new_data

    #时间格式化为小时级
    def formattime(self,data):
        time =''

        for i in data:
            time += i
        return int(time[0:2])

    # 事件统计
    def countevent(self, data):

        for time in self.index:
            conut = 0
            for i in range(len(data) - 1):
                if data[i][0] == time:
                    conut += 1
                    data[i].append(str(conut))
        return data

    #操作具体字符串
    def loadstr(self,data):
        new_data = []
        for l in data:
            time = self.formattime(list(l[0]))
            for i in range(0,24):
                   if time == i:
                        l[0] = self.index[i]
                        new_data.append(l)
        return new_data

    #发送Json数据
    def sendfile(self,data):
        headers = {'Content-Type':'aoolication/json'}
        res = requests.post(url=self.url,data=data,headers=headers)
        return res

    def mian(self):
        data = self.fr.readlines()
        data = self.formatlines(data)
        data = self.formatstr(data)
        data = self.loadstr(data)
        data = self.countevent(data)
        df = pd.DataFrame(data,columns=self.columns)
        json= df.to_json(orient='index')
        res = self.sendfile(json)
        print(res)

if __name__ == '__main__':
    test = IT_Support_Engineer_Assignment()
    test.mian()