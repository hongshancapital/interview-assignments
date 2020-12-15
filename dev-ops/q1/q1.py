import re
import json
import urllib2
import datetime

SERVICE_API = 'http://foo.com/bar'

log_file = './DevOps_interview_data_set'


def send_request(device_name, process_id, process_name, description, hour, occurrence):
    key = str(hour) + process_name
    if key not in occurrence:
        occurrence[key] = 1
    else:
        occurrence[key] += 1
    req = urllib2.Request(SERVICE_API)
    req.add_header('Content-Type', 'application/json')
    urllib2.urlopen(req, json.dumps({
        'deviceName': device_name,
        'processId': process_id,
        'processName': process_name,
        'description': description,
        'timeWindow': '{:02d}00-{:02d}00'.format(hour, hour+1),
        'numberOfOccurrence': occurrence[key],
    }))


def main():
    prev_hour = None
    prev_device_name = None
    prev_process_id = None
    prev_process_name = None
    prev_description = None

    occurrence = {}

    with open(log_file) as f:
        for line in f:
            parts = line.strip().split(' ')
            try:
                ts = datetime.datetime.strptime(line[:15], '%b %d %H:%M:%S')
            except:
                # continued line
                prev_description += ('\n' + line.strip())
                continue

            hour = ts.hour
            rest = line[16:].strip()
            if rest.startswith('---'):
                # repeated line
                send_request(prev_device_name, prev_process_id, prev_process_name, prev_description,
                             prev_hour, occurrence)
                prev_hour = hour
                continue

            device_name = parts[3]
            i = 4
            process_id = None
            process_name = ''
            while process_id is None:
                m = re.match(r'(\S+)\[(\d+)\]', parts[i])
                if m:
                    process_id = m.group(2)
                    process_name += ' ' + m.group(1)
                else:
                    process_name += ' ' + parts[i]
                i += 1
            process_name = process_name.strip()
            description = ' '.join(parts[i:])

            if prev_hour:
                send_request(prev_device_name, prev_process_id, prev_process_name, prev_description,
                             prev_hour, occurrence)
            prev_device_name = device_name
            prev_process_id = process_id
            prev_process_name = process_name
            prev_description = description
            prev_hour = hour

    if prev_hour:
        send_request(prev_device_name, prev_process_id, prev_process_name, prev_description,
                     prev_hour, occurrence)


if __name__ == '__main__':
    main()