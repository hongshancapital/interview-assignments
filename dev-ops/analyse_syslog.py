#!/usr/bin/env python3
import os
import re
import gzip
import time
import hashlib
import requests

# Get log contents from file or the other storage
class LoggerFetcher(object):
    def __init__(self, file_path):
        self._file_path = file_path

    def set_formatter(self, formatter):
        if os.path.exists(self._file_path):
            if formatter == "gzip":
                with gzip.open(self._file_path, "rb") as f:
                    self._file_content = f.readlines()
            elif formatter == "txt":
                with open(self._file_path, "rb") as f:
                    self._file_content = f.readlines()
            else:
                print("[Error] Unknown file formatter: %s" % formatter)
        else:
            print("[Error] File %s is not existed." % formatter)

    def get_stream(self, slot="all"):  # TODO: slot may control the lenth of logs
        parser = LoggerContentParser()
        return parser.parse_all(self._file_content, slot)


# Parse every log content, split different properties
class LoggerContentParser(object):
    # sample: May 13 23:58:26 BBAOMACBOOKAIR2 com.apple.xpc.launchd[1] (com.apple.mdworker.bundles[56381]): Service exited with abnormal code: 78
    #       Month\ Day\   Hours:Mins:Secs\  DeviceName\ ProcessName\[ProcessID\]\ (SubProcessInfo)?\: Message
    reg_normal = (
        r"(\w+)\ (\d+)\ (\d{2}\:\d{2}\:\d{2})\ (\w+)\ (.+?)\[(\d+)\](.+?)?\:\ (.+)"
    )
    reg_repeat = r"(\w+)\ (\d+)\ (\d{2}\:\d{2}\:\d{2}) --- last message repeated\ (\d+)\ time ---"
    _last_item = dict()

    def parse_one(self, raw):
        raw = raw.decode("utf-8")
        m = re.match(self.reg_repeat, raw)
        if (
            m is not None
        ):  # deal with the special log `--- last message repeated x time ---`
            self._last_item["ds"] = LoggerContentParser.parse_datetime(
                " ".join(m.group(1, 2, 3))
            )
            return [self._last_item] * int(m.group(4))
        else:
            m = re.match(self.reg_normal, raw)
            if m is not None:
                self._last_item = dict(
                    ds=LoggerContentParser.parse_datetime(" ".join(m.group(1, 2, 3))),
                    device_name=m.group(4),
                    process_name=m.group(5),
                    process_id=m.group(6),
                    subprocess=m.group(7),
                    message=m.group(8),
                )
                return [self._last_item]
            return []

    def parse_all(self, heaps, slot):
        structured = list()
        for item in heaps:
            # print("------", item, self.parse_one(item))
            structured += self.parse_one(item)
        return structured

    @staticmethod
    def parse_datetime(datetime_str):
        ds = time.strptime(datetime_str, "%b %d %H:%M:%S")
        return ds


class LoggerTrait:
    # calculate the trait hash for a single log
    @staticmethod
    def hash(device_name, process_id, process_name, message):
        # TODO: for advanced, it can remove the dynamic data from message content, such as the integer, hexadecimal string, file path string etc.
        return hashlib.sha256(
            str.encode("%s-%s-%s-%s" % (device_name, process_id, process_name, message))
        ).hexdigest()


# Main processer for logger
class LoggerProcesser(object):
    def __init__(self, func):
        self.counter = dict()
        self.start_at = ""
        if callable(func):
            self.callback_func = func
        else:
            self.callback_func = self.print_line

    def print_line(self, data):
        items = list(data.values())
        items.sort(key=lambda x: x["numberOfOccurrence"], reverse=True)
        for item, amount in items[0].items():
            print("{}: {}".format(item, amount), end="\t")
        print("")

    def process(self, stream, slot="hourly"):
        for item in stream:
            if slot == "hourly" and self.start_at != item["ds"].tm_hour:
                self.start_at = item["ds"].tm_hour
                if bool(self.counter):
                    self.round()
            self.count(item)
        if bool(self.counter):
            self.round()
        self.start_at = ""  # reset start_at

    def count(self, item):
        hash = LoggerTrait.hash(
            item["device_name"],
            item["process_id"],
            item["process_name"],
            item["message"],
        )
        if hash not in self.counter:
            self.counter[hash] = dict(
                numberOfOccurrence=1,
                deviceName=item["device_name"],
                processName=item["process_name"],
                description=item["message"],
                timeWindow=self._format_hour(item["ds"]),
                date=self._format_date(item["ds"]),
            )
        else:
            self.counter[hash]["numberOfOccurrence"] += 1

    def round(self):
        self.callback_func(self.counter)
        self.counter = dict()  # reset counter

    def _format_hour(self, ds):
        return "%02d00-%02d00" % (ds.tm_hour, (ds.tm_hour + 1) % 24)

    def _format_date(self, ds):
        return "%s-%s" % (ds.tm_mon, ds.tm_mday)


# Submit the logger reporter
class ReporterSubmitter(object):
    API_URL = "https://foo.com/bar"

    def post(data):
        r = requests.post(ReporterSubmitter.API_URL, json=data)


if __name__ == "__main__":
    print("-> start")
    file_handler = LoggerFetcher("./DevOps_interview_data_set.gz")
    file_handler.set_formatter("gzip")
    stream = file_handler.get_stream()

    # print top one in console
    # print("--- Top one in every hour:\n")
    # LoggerProcesser(None).process(stream)

    LoggerProcesser(ReporterSubmitter.post).process(stream)
    print("-> done")
