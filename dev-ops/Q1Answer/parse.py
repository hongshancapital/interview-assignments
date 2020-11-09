import click
import json
from datetime import datetime, timedelta
import re

class RecordsReader(object):
    def __init__(self, file):
        self.file_obj = file

    def __iter__(self):
        lines = []
        for line in self.file_obj.readlines():
            if not lines:
                lines.append(line)
            else:
                if re.search(r'^(\w+\s\d+\s\d\d:\d\d:\d\d)', line):
                    yield Record(lines)
                    # May 13 01:16:14 --- last message repeated 1 time ---
                    if "--- last message repeated 1 time ---" in line:
                        # copy the last message with new time stamp again
                        lines[0] = line[0:15] + lines[0][15:]
                    else:
                        lines = [line]
                else:
                    lines.append(line)
        if lines:
            yield Record(lines)

#
# class RecordJsonEncoder(json.JSONEncoder):
#     def default(self, o):
#         if isinstance(o, Record):
#             return o.get_dict()
#         return json.JSONEncoder.default(self, o)


class Record(object):
    def __init__(self, lines):
        self.failed_to_parse = []
        # May 13 00:17:59 BBAOMACBOOKAIR2 com.apple.xpc.launchd[1] (com.apple.mdworker.bundles[12555]): Service exited with abnormal code: 78
        match = re.search(r'^(\w+\s\d+\s\d\d:\d\d:\d\d)\s(\S+)\s(\S+)\[(\d+)\](\s\(\S+\))?: (.+)', lines[0])
        if match:
            self.time_in_str = match.group(1)
            self.datetime = datetime.strptime(self.time_in_str, '%B %d %H:%M:%S')
            self.device_name = match.group(2)
            self.process_name = match.group(3)
            self.process_id = match.group(4)
            self.description = match.group(6)
            if len(lines) > 1:
                self.description = self.description + "".join(lines[1:])
        else:
            self.failed_to_parse = lines

    def get_time_window(self):
        floor_hour = self.datetime.hour
        one_hour_later = self.datetime + timedelta(hours=1)
        ceiling_hour = one_hour_later.hour
        return "{:02d}00-{:02d}00".format(floor_hour, ceiling_hour)

    def get_dict(self):
        if self.__dict__:
            return {
                "deviceName": self.device_name,
                "processId": self.process_id,
                "processName": self.process_name,
                "description": self.description,
                "timeWindow": self.get_time_window(),
                "date": "{}-{}".format(self.datetime.month, self.datetime.day)
            }
        else:
            None


@click.command()
@click.option('--logfile', help='file path of the log file')
@click.option('--output', help='the output file in json format')
def main(logfile: str, output: str) -> None:
    with open(logfile, 'r') as input_stream:
        with open(output, "w") as output_stream:
            dumpable_record_list = []
            for i in iter(RecordsReader(input_stream)):
                if i.failed_to_parse:
                    print("WARN: failed to parse logs - '{}'".format(''.join(i.failed_to_parse)))
                    continue
                else:
                    dumpable_record_list.append(i.get_dict())
            json.dump(dumpable_record_list, output_stream, indent=2)


if __name__ == "__main__":
    main()