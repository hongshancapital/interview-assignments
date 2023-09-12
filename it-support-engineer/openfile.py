#!/usr/local/bin/python
#coding:utf-8
import re
import json
from collections import Counter


def test():
    lastline=""
    lists=[]
    with open('interview_data_set', 'r') as f:
        linelist = f.readlines()
        for i, lines in enumerate(linelist):
            length = len(linelist)
            if lines.find("May 13") != -1:
                if i != 0:
                    dicts=chklastlines(lastline.replace("\n", ""))
                    if dicts != None:
                        lists.append(dicts)
                if i == length - 1:
                    dicts=chklastlines(lines.replace("\n", ""))
                    if dicts != None:
                        lists.append(dicts)
                lastline=lines
            else:
                lastline=lastline+lines.replace("\n", "")
    f.close()
    writeliststofile(lists)

def chklastlines(strlasts):
    dicts={}
    if strlasts.find("syslogd")==-1 and strlasts.find("last message repeated 1 time")==-1 and strlasts.find("com.apple.preference.displays.MirrorDisplays")==-1:
        if strlasts.find("com.apple.xpc.launchd[1]")==-1:
            con = re.split('\]\:|\)\:|\]\)\:',strlasts,maxsplit=1)
            dicts["timeWindow"]=con[0].split(" ")[2][:2]
            dicts["deviceName"]=con[0].split(" ")[3]
            dicts["processName"]=con[0].split("[", 2)[0].split(" ")[4]
            dicts["processId"]=con[0].split("[",2)[1]
            dicts["description"]=con[1]
            return dicts
        else:
            return None


def writeliststofile(lists):
    with open('a1.json', 'w') as b:
        json.dump(lists, b, ensure_ascii=False, indent=2)
        b.close()
        with open("a1.json", "r") as f:
            json_obj = json.load(f)
        str_list = [json.dumps(item, sort_keys=True) for item in json_obj]
        str_counter = Counter(str_list)
        trdist=[]
        for item, count in str_counter.items():
            if count > 0:
                item_dict = json.loads(item)
                item_dict["numberOfOccurrence"] = count
                trdist.append(item_dict)
        f.close()
        output = json.dumps(trdist, indent=4)
        with open('outputpy.json', 'w') as fpytojs:
            fpytojs.write(output)

test()
