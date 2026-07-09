import json
import re
import requests
import gzip

# File entry and data governance
with gzip.open("interview_data_set.gz", "rt") as f:
    lines = f.readlines()
    lines = [line.strip() for line in lines if line.startswith("May")]

# Associated log consolidation
formatted_lines = []
for i in range(len(lines)):
    line = lines[i]
    if line.split()[3] == "---" and i > 0:
        formatted_lines[-1] += " " + " ".join(line.split()[4:])
    else:
        formatted_lines.append(line)

# Parse the number of logs and statistics
out = {}
out2 = {}

for line in formatted_lines:
    # Time hour
    timeField = int(line.split()[2][:2])
    timeWindow = "{0:02d}:00-{1:02d}:00".format(timeField, timeField + 1)
    # DeviceName
    deviceName = line.split()[3]
    # Reset line
    line = " ".join(line.split()[4:])
    # idName and description
    sep = line.index(": ")
    idName = line[:sep]
    # processName
    processName = re.sub(r"\s+\(.*?\)|\[.*?\]|:.*?$", "", idName)
    # processId
    processId = re.search("\[([0-9]+)\]", idName)
    if processId:
        processId = processId.group(1)
    # Description and formatting
    description = line[sep+2:].replace('"', '#')
    # Count by key
    k = f"{timeWindow}@{deviceName}@{processName}"
    if k not in out:
        out[k] = 0
    if k not in out2:
        out2[k] = {"p": {}, "d": {}}
    if "p" not in out2[k]:
        out2[k]["p"] = {}
    if "d" not in out2[k]:
        out2[k]["d"] = {}
    out[k] += 1
    if processId:
        if processId not in out2[k]["p"]:
            out2[k]["p"][processId] = 0
        out2[k]["p"][processId] += 1
    if description:
        if description not in out2[k]["d"]:
            out2[k]["d"][description] = 0
        out2[k]["d"][description] += 1

# Final result
out_list = []
for k in out.keys():
    timeWindow, deviceName, processName = k.split("@")
    ids = " ".join(out2[k]["p"].keys()) if out2[k]["p"].keys() else ""
    desc = " --->>> ".join(out2[k]["d"].keys()) if out2[k]["d"].keys() else ""
    out_list.append({
        "timeWindow": timeWindow,
        "numberOfOccurrence": str(out[k]),
        "deviceName": deviceName,
        "processName": processName,
        "processId": ids,
        "description": desc
    })

# Output json file
out_file = "pyout.json"
with open(out_file, "w") as f:
    json.dump(out_list, f)

# Post Json to API server
uri = "https://foo.com/bar"
headers = {"Content-Type": "application/json"}
with open(out_file, "r") as f:
    json_data = f.read()
response = requests.post(uri, headers=headers, data=json_data)
print(response.status_code)  # should print 200 if successful