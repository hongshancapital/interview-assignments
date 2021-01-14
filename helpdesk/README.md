# Helpdesk Assignment

### 分析系统日志：Helpdesk_interview_data_set.gz

分析系统日志得到关键信息，用 Json 的格式 POST 上传至服务器( https://foo.com/bar )，key 的名称在括号里

1. 设备名称: (deviceName)
2. 错误的进程号码: (processId)
3. 进程/服务名称: (processName)
4. 错误的原因（描述）(description)
5. 发生的时间（小时级），例如 0100-0200，0300-0400, (timeWindow)
6. 在小时级别内发生的次数 (numberOfOccurrence)

# 配置

打开 data_analytics.php ,配置如下几个参数：

* new data_analytics('./Helpdesk_interview_data_set'); 设定日志文件路
* public $save = true; 是否将分析结果保存在本地
* public $send = false; 是否发送到服务器
* public $save_path; 分析结果的保存路径
* public $api_server; 服务器的接口地址

# Windows 用户

使用 PowerShell 运行 windows.ps1 

# macos 用户

打开终端运行 macos.sh

# 编写更多处理器

由于日志中包含各种格式的输出内容，可以在 data_analytics.php 的 stage_2 中编写更多格式的处理器。