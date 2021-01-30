#!/usr/bin/python3
import re
f = open("./DevOps_interview_data_set","r")

for line in f:
    reg = re.compile('^\S+ \d+ (?P<timeWindow>\d+)[^ ]* (?P<deviceName>[\w+]*) (?P<processName>\S+)\[(?P<processId>\d+)[^ ]* (?P<description>.*)')
    regMatch = reg.match(line)
    linebits = regMatch.groupdict()
    print("{")
    for k, v in linebits.items() :
        print ("\""+k+"\": \""+v+"\"",end="")
        if (k != "description"):
            print(",")
    print("},")
f.close()
