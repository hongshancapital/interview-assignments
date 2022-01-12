import gzip
import json
import time
from pathlib import Path
import re

import requests

# 看日志本文得内容应该是mac电脑得日志，当前无法得知日志的scheme，所以根据日志得情况解析
# 其中一些地方没有做错误处理得逻辑简化脚本 实际工程要谨慎看待

# 定义需要用到得正则
TIME_RE = r"^\w\w\w \d{2} \d{2}.\d{2}.\d{2}"
LOG_RE = r"^(?P<time>\w\w\w \d{2} \d{2}.\d{2}.\d{2})\s?(?P<device>\w+)\s?(?P<process>.+\[\d+\]\s?(\(.+(\[\d+\]|\.\w+)\))?)\:(?P<message>.*)"


# 下载日志压缩文件并解压缩至本地 /tmp 目录
# 参数：url 下载链接
# 返回：文件绝对路径 这里是 /tmp/${name}.log
def get_log(url: str) -> Path:
    # 获取文件名
    name = Path(url).name
    # requests stream 下载原始文件
    resp = requests.get(url, stream=True)
    with open("/tmp/{}".format(name), "wb") as log_file_gz:
        for chunk in resp.raw:
            if chunk:
                log_file_gz.write(chunk)
    # gzip 解压文件 （压缩包的问题 requests 自动 gzip 会缺少数据） 所以这里单独处理
    with open("/tmp/{}".format(name.replace(".gz", ".log")), "wb") as log_file:
        with gzip.GzipFile("/tmp/{}".format(name)) as gzip_file:
            log_file.write(gzip_file.read())
    return Path("/tmp/{}".format(name.replace(".gz", ".log")))


# 解析并存储解析后的日志这里使用json
# 参数：文件绝对路径
# 返回：文件绝对路径 这里是 /tmp/${name}.json
def save_log(log: Path) -> Path:
    with open(log) as file:
        with open(str(log).replace(".log", ".json"), "w") as json_file:
            # 定义单行与多行数据
            one = ""
            many = ""
            # 逐行解析
            for line in file:
                # 如开始位置为时间则为单行否则为多行
                if re.match(TIME_RE, line[0:15]) is not None:
                    if len(many) != 0:
                        # 再次匹配到单行日志且多行变量不为空 将多行写入到文件
                        if format_log(many) is not None:
                            json_file.write(format_log(many) + "\n")
                        many = ""
                    else:
                        # 再次匹配到单行日志且多行变量为空 将单行写入到文件
                        if format_log(one) is not None:
                            json_file.write(format_log(one) + "\n")
                        one = ""
                    one = line
                # 开始位置不是时间为多行进行合并
                else:
                    if len(many) == 0:
                        # 多行为空则为行首 one + line
                        many = (one + line).replace("\n", "")
                    else:
                        # 多行不为空为行内 many + line
                        many = (many + line).replace("\n", "")
            json_file.write(many + "\n")
    return Path("{}".format(str(log).replace(".log", ".json")))


# 格式化日志为Json，通过正则匹配
# 参数：单行日志
# 返回：json
def format_log(line: str) -> json:
    # 日志格式分为四部分内容 1 time 2 device 3 process 4 message
    log_re = re.compile(LOG_RE)
    try:
        log = log_re.match(line).groupdict()
        return json.dumps(log)
    # 不符合正则得为 --- last message repeated 1 time 不进行记录
    except Exception as e:
        return None


# 数据上报
def push_data(file: Path):
    # 定义指标结果集与计数数组
    metrics = []
    count = []
    with open(file) as json_file:
        # 循环处理每行 json 数据
        for data in json_file:
            try:
                # 创建 metric
                data = json.loads(data)
                metric = {
                    'device_name': data['device'],
                    'process_name': data['process'].split(" ")[0].split("[")[0],
                    'process_id': data['process'].split(" ")[0].split("[")[1].split("]")[0],
                    'description': data['message'].strip(),
                    'time_window': str(time.strptime(data['time'], "%b %d %H:%M:%S").tm_hour).rjust(4, str(0)),
                    'number_of_occurrence': 1
                }
                # 创建 hash key
                key = "{}{}{}{}".format(metric['device_name'], metric['process_name'], metric['process_id'],
                                        metric['time_window'])
                # 判断 key 是否存在 count中
                if key not in count:
                    # 添加只 metrics 与 count
                    metrics.append(metric)
                    count.append(key)
                else:
                    # 存在 count 则 metric 得 统计值加1
                    index = count.index(key)
                    metrics[index]['number_of_occurrence'] = metrics[index]['number_of_occurrence'] + 1
            # 忽略末尾空行
            except Exception as e:
                pass
    # 循环结果集 进行数据上报
    for metric in metrics:
        try:
            # 示例域名缺乏有效得 SSL 证书 关闭验证（有风险不推荐）
            resp = requests.post("https://foo.com/bar", data=metric, verify=False)
            # 判断返回状态打印异常
            if resp.status_code != 200:
                print(metric, resp)
        except Exception as e:
            print(e)


if __name__ == '__main__':
    # 下载解压日志
    log_file = get_log("https://gitee.com/franckcl1989/demo/raw/master/DevOps_interview_data_set.gz")
    # 生成分析用的 json 日志（中间数据）
    json_file = save_log(log_file)
    # 数据上报
    push_data(json_file)
    # 可后续添加清理或者重跑函数
