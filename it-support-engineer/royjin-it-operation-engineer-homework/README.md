# Roy Jin IT Support engineer homework


## Requirements

分析系统日志得到关键信息，用 Json 的格式 POST 上传至服务器 ( https://foo.com/bar )，key 的名称在括号里

1. 设备名称: (deviceName)
2. 错误的进程号码: (processId)
3. 进程/服务名称: (processName)
4. 错误的原因（描述）(description)
5. 发生的时间（小时级），例如 0100-0200，0300-0400, (timeWindow)
6. 在小时级别内发生的次数 (numberOfOccurrence)

分别使用

1. Bash 或其他脚本语言，假设在 Mac 环境下，进行操作
2. Powershell，假设在 windows 环境下，进行操作


## How to use the script

```
❯ sh find_log_info.sh
USAGE: find_log_info.sh OPTIONS
LEGEND:
  [] - input required
   * - required field
OPTIONS:
  -f   [log_file]

EXAMPLES:
    find_log_info.sh -f interview_data_set
```


## Output examples

```
{
  "deviceName": "BBAOMACBOOKAIR2",
  "processId": "1",
  "processName": "com.apple.xpc.launchd",
  "description": "Could not find uid associated with service: 0: Undefined error: 0 501",
  "timeWindow": "17:55",
  "numberOfOccurrence": "653"
}
{
  "deviceName": "BBAOMACBOOKAIR2",
  "processId": "1",
  "processName": "com.apple.xpc.launchd",
  "description": "Service exited with abnormal code: 78",
  "timeWindow": "17:55",
  "numberOfOccurrence": "654"
}
{
  "deviceName": "BBAOMACBOOKAIR2",
  "processId": "976",
  "processName": "AMPDeviceDiscoveryAgent",
  "description": "- Can't handle disconnect with invalid ecid",
  "timeWindow": "17:56",
  "numberOfOccurrence": "228"
}
{
  "deviceName": "BBAOMACBOOKAIR2",
  "processId": "55145",
  "processName": "xpcproxy",
  "description": "_dirhelper_userdir: 557: bootstrap_look_up returned (ipc/send) invalid destination port",
  "timeWindow": "17:56",
  "numberOfOccurrence": "1"
}
{
  "deviceName": "BBAOMACBOOKAIR2",
  "processId": "55164",
  "processName": "xpcproxy",
  "description": "_dirhelper_userdir: 557: bootstrap_look_up returned (ipc/send) invalid destination port",
  "timeWindow": "17:57",
  "numberOfOccurrence": "1"
}
  ```