import json
import urllib.request

host = "localhost"
port = "82"
# 如果有邮箱服务服务器或者企业微信接口，后续可以直接发邮件或者微信通知。
# 获取健康信息
try:
    response = urllib.request.urlopen("http://"+host+":"+port+"/short_url/actuator/health")
except urllib.error.HTTPError as e:
    data = json.loads(e.read())
    if data["status"] == "DOWN":
        if data["components"]["db"]["status"] == "DOWN":
            print("db error:"+data["components"]["db"]["details"]["error"])
        if data["components"]["diskSpace"]["status"] == "DOWN":
            print("diskSpace error:"+data["components"]["diskSpace"]["details"]["error"])
        if data["components"]["ping"]["status"] == "DOWN":
            print("ping error:"+data["components"]["ping"]["details"]["error"])
        if data["components"]["redis"]["status"] == "DOWN":
            print("redis error:"+data["components"]["redis"]["details"]["error"])

# 获取内存信息
try:
    response = urllib.request.urlopen("http://"+host+":"+port+"/short_url/actuator/metrics/jvm.memory.max")
    data = json.loads(response.read())
    memory_max = data["measurements"][0]["value"]

    response = urllib.request.urlopen("http://"+host+":"+port+"/short_url/actuator/metrics/jvm.memory.used")
    data = json.loads(response.read())
    memory_used = data["measurements"][0]["value"]
    used = memory_used/memory_max
    if used > 0.75:
        print("memory used:"+str(used))
except urllib.error.HTTPError as e:
    print("get memory error:"+e.read())


# 获取CPU信息
try:
    response = urllib.request.urlopen("http://"+host+":"+port+"/short_url/actuator/metrics/system.cpu.usage")
    data = json.loads(response.read())
    usage = data["measurements"][0]["value"]
    if usage > 0.8:
        print("system cpu usage:"+str(usage))
except urllib.error.HTTPError as e:
    print("get system cpu usage error:"+e.read())
