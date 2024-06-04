import re,os,requests,json,time
pattern = re.compile(r'\n\t')  
logFile = open('DevOps_interview_data_set')
Array = logFile.read()

sedLog = re.sub(r'\n\t', '', str(Array))

lines = sedLog.split('\n')  


timeWindows = ("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23")

Headers = {'Content-Type': 'application/json'}
Url = "http://www.foo.com/bar"

for timeIndex,timeValue in timeWindows:
  numberOfOccurrence = 1
  timeValue = timeWindows[int(timeIndex)]
  errLogJson = {}
  #key:description
  for line in lines:
     lineSplit = line.split(" ")
     if len(lineSplit) > 5:
       timeWindow = lineSplit[2].split(":")[0]
       if timeWindow == timeValue:
         #print(errTimeHour)
         
         timeWindow = timeValue + "00-" + timeWindows[int(timeIndex)+1]+"00"
         deviceName = lineSplit[3]
         #print(deviceName)
         
         processId = re.findall(r"[0-9]+",lineSplit[4])
         #print(processId)
         #description = str(lineSplit[5]).replace(r"[0-9]+","")
         
         processName = lineSplit[4]
         #print(processName)
         
         description = ''
         lineSplitSub = lineSplit[6:]
         lineSplitSub = "#$".join(lineSplitSub).replace(r"#$"," ")
         #print(lineSplitSub)
         
         description = re.sub('[0-9]+', '', str(lineSplit[5]).replace(r"[0-9]+","")) + " " + lineSplitSub
         #print(description) 
         if errLogJson.has_key(description):
           errLogJson[description]["numberOfOccurrence"] = errLogJson[description]["numberOfOccurrence"]+1
           #print(description)
         else:
           errLogJson[description]={"deviceName":deviceName,"processId":processId,"processName":processName,"description":description,"timeWindow":timeWindow,"numberOfOccurrence":numberOfOccurrence}
           #print(errLogJson[description])
  for key in errLogJson:
    #print(key ,"------------",errLogJson[key]["numberOfOccurrence"])
    response = requests.post(Url, headers=Headers, data = json.dumps(errLogJson[key]["numberOfOccurrence"])) 
    print response.text
    time.sleep(2)
