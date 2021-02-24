import pandas as pd
import json

# Load data
print('Loading.......')
df = pd.read_csv('./log.csv', usecols=['Month','Date','Time','DeviceName','Process','Description'])
print(df)

# Sort with date, time, device name and process
print('Sorting with datetime.......')
df = df.sort_values(['Date','Time','DeviceName','Process'])
df['Time']=df['Time'].astype(str).str[:2]
df['Time']=df['Time'].apply(pd.to_numeric)

# Extract ProcessId
print('Extracting processId.......')
df['ProcessId'] = df['Process'].str.extract(r'(\[\d*\])', expand=False)
df['SubProcessId'] = df['Process'].str.extract(r'[(](.*\d?)[)]', expand=False)
df['SubProcessId'] = df['SubProcessId'].str.extract(r'(\.?\d+\.*)', expand=False)
df['SubProcessId'] = df['SubProcessId'].str.replace(r"\.","")
df['SubProcessId'] = df['SubProcessId'].apply(lambda x: f"[{x}]")
df['ProcessId'] = df['ProcessId'].map(str) + df['SubProcessId'].map(str)
df['ProcessId'] = df['ProcessId'].str.replace(r"\[nan\]","")

df['Process'] = df['Process'].str.replace(r"(\[\d*\])","")
df['Process'] = df['Process'].str.replace(r":","")

# Count by time (by one hour)
df_count = df.groupby(['DeviceName','ProcessId', 'Process','Time']).size().reset_index(name='numberOfOccurrence')
df = df.groupby(['DeviceName','ProcessId', 'Process', 'Time'])['Description'].apply(lambda tags: ','.join(tags))
result = pd.concat([df.reset_index(drop=True), df_count], axis=1,sort=False)

# Reorder indexes
result = result[['DeviceName','ProcessId', 'Process', 'Time','Description', 'numberOfOccurrence']]
print(result.dtypes)

# Revert to timeWindow by hour
for x in range(10):
    xplus = x+1
    timeWindow = "0" + str(x) + "00-" + str(xplus) + "00"
    result['Time'] = result['Time'].replace(x, timeWindow)

for x in range(10,24):
    xplus = x+1
    timeWindow = str(x) + "00-" + str(xplus) + "00"
    result['Time'] = result['Time'].replace(x, timeWindow)

# Replace first letter of index name with lower letter
result.rename(columns={'DeviceName':'deviceName','Process':'processName', 'Time':'timeWindow','Description':'description'},inplace=True)
print (result)

# Create json format
js = result.to_json(orient="records")
jsonfile = open('./file.json', 'w')
json.dump(json.loads(js), jsonfile)
jsonfile.write('\n')
