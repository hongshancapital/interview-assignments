import re, json
import urllib, ssl

def format_line(line):
    json_line = {
        'deviceName': '',
        'processId': 0,
        'processName': '',
        'description': '',
        'timeWindow': '',
        'numberOfOccurrence': 0
    }
    pattern = r'\w+ \d+ (\d+):\d+:\d+ (\w+) ([\.|\w+]+)\[(\d+)\]( \(([\.|\w]+)\[(\d+)\]\))?: (.*(\n?.*)*)'
    filter_line = re.match(pattern, line)
    if filter_line:
        json_line["deviceName"] = filter_line.group(2)
        json_line['timeWindow'] = filter_line.group(1)
        json_line['description'] = filter_line.group(8)
        if filter_line.group(6) != None:
            json_line['processName'] = filter_line.group(6)
        else:
            json_line['processName'] = filter_line.group(3)
        if filter_line.group(7) != None:
            json_line['processId'] = filter_line.group(7)
        else:
            json_line['processId'] = filter_line.group(4)
    return json_line

def post_json(list):
    ctx = ssl.create_default_context()
    ctx.check_hostname = False
    ctx.verify_mode = ssl.CERT_NONE
    for i in list:
        params = urllib.urlencode(i)
        # f = urllib.urlopen("https://foo.com/bar", params, context=ctx)
        # print(f.read())
        print(json.dumps(i))

def main(startword, file_path):
    file = open(file_path, 'r')
    new_line = ''
    line_list = []
    try:
        while True:
            text_line = file.readline()
            # check multiline
            if text_line.startswith(startword):
                new_line = format_line(new_line)
                line_list.append(new_line)
                new_line = text_line
            elif text_line and not text_line.startswith(startword):
                new_line = new_line + text_line
            else:
                new_line = format_line(new_line)
                line_list.append(new_line)
                break
    finally:
        file.close()
    # remove duplicate line
    tmp_list = line_list
    for i in tmp_list:
        for j in line_list:
            if i["timeWindow"] == j["timeWindow"] and i["processId"] == j["processId"] and i["description"] == j["description"]:
                i["numberOfOccurrence"] = i["numberOfOccurrence"] + 1
    line_list = []
    for i in tmp_list:
        if i not in line_list:
            line_list.append(i)
    post_json(line_list)

if __name__ == "__main__":
    main("May",'DevOps_interview_data_set')
