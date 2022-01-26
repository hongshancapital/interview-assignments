from cgi import print_directory
import logging
import io
from operator import imod
from time import time
from urllib import request
import json


srcFile = open('DevOps_interview_data_set','r+')
dstFile = open('dst_data.jason','w+')

lines = srcFile.readlines()

count = 0
Error_old = 0
time_old = 0
for line in lines:
    
    #print(line)
    if "error =" in line:
       data = line.split("error =")
       deviceName = data[0].split(" ")[3]
       processId = data[0].split(" ")[5].split(".")[7]
       processId = processId[:-2]
       processId_change = processId
       processName = data[0].split(" ")[5].split(".")[6]
       description = data[1][1:]
       timeWindow_begin = data[0].split(" ")[2].split(":")[0] + ":00 - "
       timeWindow_end = str(int(data[0].split(" ")[2].split(":")[0])+1)+ ":00 "
       timeWindow = timeWindow_begin + timeWindow_end
       #print(processId_old)
       #print(processId)
       #print(processId_change == processId)
       if Error_old == data[1] and data[0].split(" ")[2].split(":")[0] == time_old:
           count = count + 1
           #processId_old = processId
           #print(count)
           #print(deviceName + ':' + data[1] + processId + processName + description+ timeWindow + str(count))
      
       else:
            
            request.post(url='https://foo.com/bar',data={'deviceName':deviceName,'processId':processId,'processName':processName,'description':description,'timeWindow':timeWindow,'numberOfOccurrence':count},headers={'Content-Type':'application/x-www-form-urlencoded'})
            #print(deviceName + ':' + data[1] + processId + processName + description+ timeWindow + str(count))
            count = 1
            Error_old = data[1]
            time_old = data[0].split(" ")[2].split(":")[0]
            #print(count)
