#powershell 方式分析日志并上传
#——————————————————————————————————————————————————
#过滤掉无效数据
$Log = Get-Content -Path "C:\Users\Dex_DDup\Desktop\WangLei-IT Support Engineer Assignment\interview_data_set"
$error_Log = $log -match 'error' -replace '\):','#' -replace '\]:','#'-replace '\(' -replace '\[','.' -replace '\]' 

#定义URL变量
$url = "https://foo.com/bar"
#定义日期变量
$date ='May 13 '

#循环处理每行日志
foreach ($line in $error_Log){
    #获取time_window
    $time = $line.split(':')[0].split(' ')[2]
    $time_Window=('{0:d4}' -f [int]$time)+'-'+('{0:d4}' -f ([int]$time+[int]1))
    #获取设备名称
    $dev_Name = $line.split('#')[0].split(' ')[3]
    #获取进程ID
    $process_Id = $line.split('#')[0].split('.')[-1]
    #获取进程名称
    $process_Name = $line.split('#')[0].split(' ')[4] -replace ('.'+$process_Id )
    #获取错误描述
    $error_Msg = $line.split('#')[1]
    #统计当前进程报错数量
    $Occour_msg = ($error_Log | Select-String $process_Name | Select-String $date+$time+':').count
    #格式为json格式
    $data1 = @{
        "timeWindow" = "$time_Window"
        "deviceName" = "$dev_Name"
        "processName" = "$process_Name"
        "processId" = "$process_Id"
        "description" = "$error_Msg"
        "numberOfOccurrence" = "$Occour_msg"
    }
    $body1 = $data1 | ConvertTo-Json
    echo $body1
    #POST上传至服务器
    #$unlock = Invoke-RestMethod -Method Post -ContentType 'application/json;charset=utf-8' -Uri $url -Body $body1
}


