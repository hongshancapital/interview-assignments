import urllib.request
import psutil

host = "localhost"
port = "83"
# 如果有邮箱服务服务器或者企业微信接口，后续可以直接发邮件或者微信通知。
# 获取http_server 服务是否可访问
try:
    headers={"Authorization":"Basic dGVzdDp0ZXN0"}
    req = urllib.request.Request(url="http://"+host+":"+port+"/http_server/favicon.ico",headers=headers)
    response = urllib.request.urlopen(req)
    if response.getcode() != 200:
        print("http_server error:get ico error")
except urllib.error.HTTPError as e:
    print("http_server error:"+e.reason)
except Exception as e:
    print("http_server error:"+str(e.reason))

#判断内存使用
if psutil.virtual_memory().percent > 0.75:
    print("memory used:"+str(psutil.virtual_memory().percent))

#判断CPU使用
if psutil.cpu_percent(0) > 0.8:
    print("system cpu usage:"+str(psutil.cpu_percent(0)))
