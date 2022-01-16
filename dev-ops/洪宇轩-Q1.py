import re,json,requests

MONTH = '(?:[Jj]an(?:uary|uar)?|[Ff]eb(?:ruary|ruar)?|[Mm](?:a)?r(?:ch|z)?|[Aa]pr(?:il)?|[Mm]a(?:y|i)?|[Jj]un(?:e|i)?|[Jj]ul(?:y)?|[Aa]ug(?:ust)?|[Ss]ep(?:tember)?|[Oo](?:c|k)?t(?:ober)?|[Nn]ov(?:ember)?|[Dd]e(?:c|z)(?:ember)?)'
MONTHNUM = '(?:0?[1-9]|1[0-2])'
MONTHNUM2 = '(?:0[1-9]|1[0-2])'
MONTHDAY = '(?:(?:0[1-9])|(?:[12][0-9])|(?:3[01])|[1-9])'
HOUR = '(?:2[0123]|[01]?[0-9])'
MINUTE = '(?:[0-5][0-9])'
SECOND = '(?:(?:[0-5]?[0-9]|60)(?:[:.,][0-9]+)?)'
TIME = '(?!<[0-9]){}:{}(?::{})(?![0-9])'.format(HOUR, MINUTE, SECOND)
POSINT = '(?:[1-9][0-9]*)'
NONNEGINT = '(?:[0-9]+)'
WORD = '\w+'
NOTSPACE = '\S+'
SPACE = '\s*'
DATA = '.*?'
JAVACLASS = '(?:[a-zA-Z$_][a-zA-Z$_0-9]*\.)*[a-zA-Z$_][a-zA-Z$_0-9]*'

def get_timeWindow(timestamp):
  res = re.match('({}):{}:{}'.format(HOUR, MINUTE, SECOND), timestamp)
  start = res.group(1)
  end = int(start) + 1
  if end < 10:
    end = '0' + str(end)
  return '{}00-{}00'.format(start, end)

data = {}
post_body = []

if __name__ == '__main__':
  with open("./DevOps_interview_data_set") as f:
    line = f.readline()
    while line:
      res = re.match('{} \d+ ({}) ({}) ({})\[(\d+)\](?: \({}(?:\[|\.)\d+(?:\]|)\))?: (.+)'.format(MONTH, TIME, DATA, NOTSPACE, DATA), line)
      if res != None:
        m = {
          'timestamp': res.group(1),
          'deviceName': res.group(2),
          'processId': res.group(4),
          'processName': res.group(3),
          'description': res.group(5),
          'timeWindow': get_timeWindow(res.group(1))
        }
        # print(json.dumps(m))
        key = '{}|{}|{}|{}|{}'.format(m['deviceName'], m['processId'], m['processName'], m['description'], m['timeWindow'])
        if key not in data:
          data[key] = 0
        data[key] = data[key] + 1
      else:
        pass
      line = f.readline()
  for k, v in data.items():
    post_body.append({
      'deviceName': k.split('|')[0],
      'processId': k.split('|')[1],
      'processName': k.split('|')[2],
      'description': k.split('|')[3],
      'timeWindow': k.split('|')[4],
      'numberOfOccurrence': v
    })
  response = requests.post('https://foo.com/bar', data=json.dumps(post_body), headers={'Content-Type':'application/json'}, verify=False)
  print(response)