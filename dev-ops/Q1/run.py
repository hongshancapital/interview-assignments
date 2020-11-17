import re
import pandas as pd
from datetime import datetime
import requests

pattern_1 = '(?P<timeWindow>[A-Z].*?:[0-9]{2}):[0-9]{2}(\s)(?P<deviceName>[\w]+)\s(?P<processName>[\w].*?\[(?P<processId>\d+)\]\)?):\s(?P<description>[\w].*)'
pattern_2 = '(?P<timeWindow>[A-Z].*?:[0-9]{2}):[0-9]{2}(\s)(?P<deviceName>[\w]+)\s(?P<processName>[\w].*?(?P<processId>\d+)\)?):\s(?P<description>[\w].*)'
pattern_3 = '(?P<timeWindow>[A-Z].*?:[0-9]{2}):[0-9]{2}(\s)(?P<deviceName>[\w]+)\s(?P<processName>[\w].*?\)?):\s(?P<description>[\w].*)'


def getGroupDict(s):
    patterns = [pattern_1, pattern_2, pattern_3]
    for pt in patterns:
        res = re.search(pt, s)
        if res:
            return res.groupdict()
    return None


def getTimeWindow(t):
    strTime = re.match(r'[A-Z].*\s(.*?):', t).groups()[0]
    sTime = datetime.strptime(strTime, "%H")
    etime = datetime.strptime(str((int(strTime) + 1) % 24), "%H")
    tw = sTime.strftime("%H00-") + etime.strftime("%H00")
    return tw


def getJsonData():
    # init dataframe
    df = pd.DataFrame()
    with open('DevOps_interview_data_set', 'r', encoding='utf-8') as file:
        for line in file.readlines():
            groupdict = getGroupDict(line)
            if groupdict:
                row = pd.DataFrame(groupdict, index=[0])
                df = df.append(row, ignore_index=True, sort=True)

    # dataframe apply timeWindow
    df['timeWindow'] = df['timeWindow'].apply(getTimeWindow)

    # numberOfOccurrence
    df_duplicated = df[df.duplicated()]
    series_new = df_duplicated.groupby(['timeWindow', 'description']).size()
    series_new = series_new.reset_index(drop=True)
    df_duplicated['numberOfOccurrence'] = series_new

    # merge dataframe
    df_final = pd.merge(df, df_duplicated, on=['timeWindow', 'deviceName', 'processName', 'processId', 'description'], how='outer').fillna(1)

    # dataframe drop duplicates
    df_final = df_final.drop_duplicates(keep='first')

    jsonObj = df_final.to_json(orient="records")

    print(jsonObj)
    return jsonObj


def post(jsonObj, url):
    response = requests.post(url, json=jsonObj, verify=False)
    if response.status_code == 200:
        print("Upload success")
    else:
        print("Upload failed")


def main():
    jsonObj = getJsonData()
    post(jsonObj, url="https://foo.com/bar")


if __name__ == "__main__":
    main()
