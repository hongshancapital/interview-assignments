### 分析系统日志：Helpdesk_interview_data_set.gz

分析系统日志得到关键信息，用Json的格式POST上传至服务器(https://foo.com/bar)，key的名称在括号里

1. 设备名称: (deviceName)
2. 错误的进程号码: (processId)
3. 进程/服务名称: (processName)
3. 错误的原因（描述）(description)
4. 发生的时间（小时级），例如0100-0200，0300-0400, (timeWindow)
4. 在小时级别内发生的次数 (numberOfOccurrence)

分别使用

1. Bash 或其他脚本语言，假设在Mac环境下，进行操作
2. Powershell，假设在windows环境下，进行操作
