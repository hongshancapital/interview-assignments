import pandas as pd
import re
import os
import gzip
from json import loads, dumps
import numpy as np


input_type=".gz"
output_type=".txt"


def dataLoad(log_dir):
    log_alias=log_dir+"/log/"
    for top, dires,files in os.walk(log_alias):
        print(files) 
        for filename in files:
            log_name=os.path.splitext(filename)[0].lower()
            print(log_name)

            if os.path.splitext(filename)[-1].lower()==input_type:
                print(filename)
                with gzip.open (log_alias+filename,"rb") as f:
                    log_data=[line.rstrip().decode("utf-8") for line in f]
    return log_data,log_alias

def myLog(log_dir):
    log_data,log_alias = dataLoad(log_dir)
    df = pd.DataFrame({'record':log_data})
    print(len(df["record"]))
    df["record"].str.replace("\t","")

    df["month"]=[x.split(" ")[0] for x in df["record"]]
    df["day"]=[x.split(" ")[1] for x in df["record"]]
    df["time"]=[x.split(" ")[2] for x in df["record"]]
    df["hour"]=df["time"]
    df["hour_int"]=df["hour"]
    df["deviceName"]=[x.split(" ")[3] for x in df["record"]]
    df["process"]=[x.split(" ")[4:6] for x in df["record"]]
    df["processID"]=df["process"]
    df["description"]=[x.split(" ")[6:] for x in df["record"]]
    for i in range(len(df["description"])):
        df["hour"][i]=df["hour"][i][:2]
        df["process"][i]=" ".join(df["process"][i])
        df["process"][i]=df["process"][i].replace(":","")
        df["description"][i]=" ".join(df["description"][i])
        df["processID"][i]=" ".join(df["processID"][i])
        df["processID"][i]=df["processID"][i].replace(":","")
        try:
            df["hour_int"][i]=int(df["hour"][i])
        except ValueError:
            df["hour_int"][i]=99
            with open (log_alias+"exceptions_timestamp.log","a") as f:
                f.write(df["record"][i]+ os.linesep)
                f.close()
            continue
        
        try:
            processid=re.findall(r"\[(.*?)\]",df["processID"][i])
            df["processID"][i]=processid[-1]
            procesid_int=int(df["processID"][i])
        except (ValueError, IndexError):
            print(processid)
            df["processID"][i]="NA"
            continue
            
    print(len(df))
    df = df[df["hour_int"]!=99]
    print(len(df))
    df_groupby=df.groupby(["hour"]).count()
    print(df_groupby)
    df = pd.merge(df,df_groupby,how="left",on=["hour"])
    print(df)
    df_log=pd.DataFrame({"month":df["month_x"],"day":df["day_x"],"timeWindow":df["hour"],"deviceName":df["deviceName_x"],"processID":df["processID_x"],"processName":df["process_x"],"description":df["description_x"],"numberOfOccurrence":df["record_y"]})

    print(df_log)
    return df_log

def myJson(log_dir):
    df_log=myLog(log_dir)
    json_init = df_log.to_json(orient="table")
    parsed=loads(json_init)
    log_json=dumps(parsed,indent=4)
    print(log_json)
    with open (log_dir+"\log.json","w") as output:
        output.write(log_json)
    output.close()



if __name__=='__main__':
    log_dir= os.getcwd()
    print(log_dir)
    myJson(log_dir)
 








    

