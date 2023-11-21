$ErrorActionPreference = "Stop"

# 设置服务器 URL
$url = 'https://foo.com/bar'

# 日志文件路径
$logfile = 'C:\Users\admincn\Desktop\interview_data_set'

# 初始化存储日志条目的数组
$lineObjectArray = @()

# 逐行处理日志内容
foreach ($line in Get-Content -Path $logfile) {
    if ($line -match ':') {
        $lineArray = $line -split ' '
        if ($lineArray[3] -eq 'BBAOMACBOOKAIR2') {
            $obj = New-Object PSCustomObject
            $obj | Add-Member -MemberType NoteProperty -Name "deviceName" -Value $lineArray[3]
            $obj | Add-Member -MemberType NoteProperty -Name "processId" -Value ($lineArray[4] -replace '\D+\[(\d+)\]', '$1').Trim(':')
            $obj | Add-Member -MemberType NoteProperty -Name "processName" -Value ($lineArray[4] -replace '(\D+)\[\d+\]', '$1').Trim(':')
            $obj | Add-Member -MemberType NoteProperty -Name "description" -Value ($lineArray[5 .. ($lineArray.length-1)] -join ' ')
            $obj | Add-Member -MemberType NoteProperty -Name "hour" -Value ($lineArray[2] -split ':')[0]

            $lineObjectArray += $obj
        }
    }
}

# 初始化哈希表用于存储统计信息
$groupCounter = @{}

# 对日志条目进行统计
$lineObjectArray | ForEach-Object {
    $key = $_.deviceName + $_.processId + $_.processName + $_.description + $_.hour
    if ($groupCounter.Contains($key)) {
        $groupCounter[$key].numberOfOccurrence++
    } else {
        $timeWindow = '{0:D2}:00 - {1:D2}:00' -f $_.hour, (([int]$_.hour + 1) % 24)
        $obj = New-Object PSCustomObject
        $obj | Add-Member -MemberType NoteProperty -Name "deviceName" -Value $_.deviceName
        $obj | Add-Member -MemberType NoteProperty -Name "processId" -Value $_.processId
        $obj | Add-Member -MemberType NoteProperty -Name "processName" -Value $_.processName
        $obj | Add-Member -MemberType NoteProperty -Name "description" -Value $_.description
        $obj | Add-Member -MemberType NoteProperty -Name "timeWindow" -Value $timeWindow
        $obj | Add-Member -MemberType NoteProperty -Name "numberOfOccurrence" -Value 1

        $groupCounter[$key] = $obj
    }
}

# 将统计信息按照 timeWindow 排序，并生成 JSON
$sortedOutput = $groupCounter.Values | Sort-Object -Property timeWindow
$jsonPayload = $sortedOutput | ConvertTo-Json

# 输出 JSON 数据
Write-Output $jsonPayload

# 将 JSON 数据保存到文件
$jsonPayload | Set-Content -Path "C:\Users\admincn\Desktop\output-2.json"

# 发送 JSON 数据到服务器
#Invoke-RestMethod -Uri $url -TimeoutSec 5 -Method Post -InFile ".\output-1.json"
