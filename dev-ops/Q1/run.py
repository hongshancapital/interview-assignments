import gzip
import requests

GZ_NAME = 'DevOps_interview_data_set.gz'

lines = gzip.open(GZ_NAME, 'rb').read().decode('ascii').splitlines()

records = {}


def add_msg(time_window, msg, times=1):
	if time_window not in records: records[time_window] = {}
	if msg not in records[time_window]:
		records[time_window][msg] = 0
	records[time_window][msg] += times

msg = None
def add_full_line(line):
	global msg
	month, day, time, tmp = line.split(' ', 3)
	time_window = (month, day, time[:2])
	if tmp.find('--- last message repeated ') != 0: msg = tmp
	add_msg(time_window, msg)
	
line0 = lines[0]
lines = lines[1:]
for line in lines:
	if line[0] == '	':
		line0 += "\r" + line
		continue
	add_full_line(line0)
	line0 = line
	
add_full_line(line0)

result = []
for time_window in records:
	for msg, times in records[time_window].items():
#		print('>>', msg)
		device, other = msg.split(' ', 1)
		process_name, other = other.split('[', 1)
		process_id, other = other.split(']', 1)
		
		description = other[2:] if other[0] == ':' else other[1:]
		t = f'{int(time_window[2])+1}00'
		t = f'{time_window[2]}00-{t}' if len(t) == 4 else f'{time_window[2]}00-0{t}'
		result.append({
			'deviceName': device,
			'processId': process_id,
			'processName': process_name,
			'description': description,
			'timeWindow': t,
			'numberOfOccurrence': times
		})
		
for i in result:
	print(i['description'])
	
r = requests.post('https://foo.com/bar', json=result)
