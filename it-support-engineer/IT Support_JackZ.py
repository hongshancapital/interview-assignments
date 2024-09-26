import re
import json
import requests


log_file = './interview_data_set'

def log_analysis():
    with open(log_file) as f:
        for line in f:
            if 'error:' in line:
                try:
                    deviceName = line.split()[3]
                    processId = re.search(r'\d+', line.split()[5]).group()
                    processName = line.split()[5].replace(processId, '')[1:-4]
                    timeWindow = line.split()[2][:-2]
                    description = ' '.join(line.split()[6:])
                except AttributeError as e:
                    continue
                finally:
                    yield {
                        'deviceName': deviceName,
                        'processId': [processId],
                        'processName': processName,
                        'timeWindow': timeWindow,
                        'description': description,
                        'numberOfOccurrence': 1
                    }

def data2JSON():
    data_dict = log_analysis()
    data_list = []
    for data in data_dict:
        if data_list and data['timeWindow'][:2] == data_list[-1]['timeWindow'][:2] \
                and data['processName'] == data_list[-1]['processName'] \
                and data['description'] == data_list[-1]['description']:
            data_list[-1]['numberOfOccurrence'] += 1
            data_list[-1]['timeWindow'] = data_list[-1]['timeWindow'][:5] + '-' + data['timeWindow'][:5]
            data_list[-1]['processId'].append(data['processId'][0])
        else:
            data_list.append(data)
    return json.dumps(data_list)

def upload():
    headers = {'Content-Type':'JSON'}
    json_data = data2JSON()
    print(json_data)

    response = requests.post(url='https://foo.com/bar', headers=headers, verify=False, data=json_data)
    if response.status_code == 200:
       print('success')
    else:
       print('error')


if __name__ == '__main__':
    upload()