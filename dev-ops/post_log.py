# -*- coding: utf-8 -*-
import datetime
import sys
import re
import requests


def handler(file_path):
    """
    处理函数
    :param file_path:
    :return:
    """
    # 文件大小有限，可全部加载到内存中，再按行解析
    # 文件大时，此方式不可取，可采用，分块加载的方式处理。
    with open(file_path) as f:
        current_time_window = None  # timeWindow
        for line in f.readlines():
            print('>>>', line)
            reg = re.compile(r'(?P<time>\w+\s\d+\s[:|\d+]+)\s(?P<deviceName>\w+)\s(?P<processName>[\w+|\.]+)\[(?P<processId>\d+)\].*?\:(?P<message>.*)')
            match_obj = reg.match(line)
            if match_obj:
                data_dict = match_obj.groupdict()
                if data_dict.get('time'):
                    dt = datetime.datetime.strptime(data_dict['time'], '%b %d %H:%M:%S')
                    # 不以时间开头的日志，暂时忽略
                    continue

                # 首次或时间窗口变动时，重新初始化时间窗口
                if not current_time_window or current_time_window != dt.hour:
                    current_time_window = dt.hour

                data_dict.pop('time')
                data_dict.update({
                    'timeWindow': '{:0>2}00-{:0>2}00'.format(current_time_window-1, current_time_window)
                })

                resp = requests.post('https://foo.com/bar', json=data_dict)
                if resp.status_code != 200:
                    raise Exception('提交数据接口返回错误，信息：{}'.format(resp.content))
            else:
                print('pass , no data math.')


if __name__ == '__main__':
    file_path = sys.argv[1]
    if not file_path:
        print('需要指定文件参数，如：python post_log.py <path/to/file>')
        sys.exit(1)
    try:
        handler(file_path=file_path)
    except Exception as err:
        print('处理时出错，错误信息：{}'.format(err))
        sys.exit(1)
