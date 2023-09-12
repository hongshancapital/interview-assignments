# 解析系统日志
$logFilePath = "H:\AI\Json\data"  # 日志文件路径
$logData = Get-Content -Path $logFilePath -Raw

# 初始化统计数据
$logs = $logData -split "`n" | ForEach-Object {
    $logEntry = $_ -split " "
    $description = if ($logEntry.Length -gt 6) { $logEntry[6..($logEntry.Length - 1)] -join " " } else { "" }
    [PSCustomObject]@{
        'Timestamp'    = $logEntry[0] + " " + $logEntry[1]
        'DeviceName'   = $logEntry[3]
        'ProcessId'    = if ($logEntry[4]) { $logEntry[4].Trim('[]') } else { "" }
        'ProcessName'  = if ($logEntry[5]) { $logEntry[5].Trim('():') } else { "" }
        'Description'  = $description
        'TimeWindow'   = $logEntry[2]
    }
}

# 统计发生次数
$logStats = $logs | Group-Object -Property 'Timestamp', 'DeviceName', 'ProcessId', 'ProcessName', 'Description' | ForEach-Object {
    [PSCustomObject]@{
       # 'Timestamp'           = $_.Group[0].Timestamp
        'DeviceName'          = $_.Group[0].DeviceName
        'ProcessId'           = $_.Group[0].ProcessId
        'ProcessName'         = $_.Group[0].ProcessName
        'Description'         = $_.Group[0].Description
        'TimeWindow'          = $_.Group[0].TimeWindow
        'NumberOfOccurrence'  = $_.Count
       # 'TimeWindow'          = $_.Group[0].Timestamp.Split(' ')[2] + "-" + $_.Group[0].Timestamp.Split(' ')[3]
        
    }
}

# 转换为JSON格式
$jsonData = $logStats | ConvertTo-Json

# 保存JSON文件
$jsonFile = "logs.json"
$jsonData | Out-File -FilePath $jsonFile


# 发送 POST 请求上传数据
$url = "https://foo.com/bar"  # 服务器 URL
$headers = @{ "Content-Type" = "application/json" }
$response = Invoke-RestMethod -Uri $url -Method Post -Body $jsonData -Headers $headers

# 检查响应状态码
if ($response.StatusCode -eq 200) {
    Write-Host "上传成功！"
} else {
    Write-Host "上传失败：" $response.StatusCode
}
#>

Write-Host "日志分析结果已发送，并保存为jsonFile"