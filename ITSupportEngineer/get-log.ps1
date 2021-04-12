#转换中英文月份至阿拉伯数字月份
filter Convertto-DateTime
{
  param($OutputFormat='MM dd HH:mm:ss')

  Switch -regex ($_) {
    '一月|Jan' {$inputtime = $_ -replace '一月|Jan','01'}
    '二月|Feb' {$inputtime = $_ -replace '二月|Feb','02'}
    '三月|Mar' {$inputtime = $_ -replace '三月|Mar','03'}
    '四月|Apr' {$inputtime = $_ -replace '四月|Apr','04'}
    '五月|May' {$inputtime = $_ -replace '五月|May','05'}
    '六月|Jun' {$inputtime = $_ -replace '六月|Jun','06'}
    '七月|Jul' {$inputtime = $_ -replace '七月|Jul','07'}
    '八月|Aug' {$inputtime = $_ -replace '八月|Aug','08'}
    '九月|Sep' {$inputtime = $_ -replace '九月|Sep','09'}
    '十月|Oct' {$inputtime = $_ -replace '十月|Oct','10'}
    '十一月|Nov' {$inputtime = $_ -replace '十一月|Nov','11'}
    '十二月|Dec' {$inputtime = $_ -replace '十二月|Dec','12'}
  }

  try {
    [datetime]::ParseExact($inputtime,$OutputFormat, $null)
  } catch {}
}

#powrshell解压gz函数
Function DeGZip-File{
    Param(
        $infile,
        $outfile = ($infile -replace '\.gz$','')
        )
    $input = New-Object System.IO.FileStream $inFile, ([IO.FileMode]::Open), ([IO.FileAccess]::Read), ([IO.FileShare]::Read)
    $output = New-Object System.IO.FileStream $outFile, ([IO.FileMode]::Create), ([IO.FileAccess]::Write), ([IO.FileShare]::None)
    $gzipStream = New-Object System.IO.Compression.GzipStream $input, ([IO.Compression.CompressionMode]::Decompress)
    $buffer = New-Object byte[](1024)
    while($true){
        $read = $gzipstream.Read($buffer, 0, 1024)
        if ($read -le 0){break}
        $output.Write($buffer, 0, $read)
        }
    $gzipStream.Close()
    $output.Close()
    $input.Close()
}

#下载日志压缩包至临时文件夹，如失败请解压后放至任一文件夹并修改读取日志 $text = Get-Content "$($TMPDIR)interview_data_set"
try {
    Import-Module BitsTransfer
    Start-BitsTransfer -Source "https://github.com/scdt-china/interview-assignments/raw/master/it-support-engineer/interview_data_set.gz" -Destination "$($TMPDIR)interview_data_set.gz"
    DeGZip-File "$($TMPDIR)interview_data_set.gz" "$($TMPDIR)interview_data_set"
}
catch {
    write-host "unzip interview_data_set.gz to tmpdir"
}

#读取并分析日志    
$text = Get-Content "$($TMPDIR)interview_data_set"
$event = @()
foreach ($line in $text) {
    $log = $line -split '\s'
    $devicename = $log[3]
    if ($devicename -eq '---') {
        continue
    }
    $time = $($log[0..2] -join ' ') | Convertto-DateTime
    if (!$time) {
        $event[-1].description += " $($line.Trim())"
        continue
    }
    $timewindows = $time.ToString('MM-dd HH00')+'-'+$time.AddHours(1).tostring('HH00')
    if ($log[4] -match '^(.+)\[{1}([0-9]+)]:$') {
        $processid = $Matches[2]
        $processName = $Matches[1]
        $description = $log[5..99] -join ' '
    }
    elseif ($log[4] -match '^(.+)\[{1}([0-9]+)]$') {
        $processid = $Matches[2]
        $processName = $Matches[1]
        if ($log[5] -match '^\((.+)\[{1}([0-9]+)]\):$') {
            $processid += "($($Matches[2]))"
            $processName += " ($($Matches[1]))"
            $description = $log[6..99] -join ' '

        }
        elseif ($log[5] -match '^\((.+)\.{1}([0-9]+)\):$') {
            #$processid = ""
            $processName += "($($Matches[1]))"
            $description = $log[6..99] -join ' '

        }
        elseif ($log[5] -match '^\((.+)\):$') {
            #$processid = $Matches[2]
            $processName += "($($Matches[1]))"
            $description = $log[6..99] -join ' '

        }
        else {
            $description = $log[5..99] -join ' '
        }
    }
    else {
        $processid = ""
        $processName = ""
        $description = $log[4..999] -join ' '
    }
    $event += New-Object -TypeName psobject -Property @{
        deviceName = $log[3]
        processId = $processid
        processName = $processName
        description = $description
        timeWindow = $timewindows
        numberOfOccurrence = ""
    }
}
$group = $event | Group-Object timewindow,devicename,processid,processname
$json = $group | %{$($_.group)[0].numberofoccurrence = $_.count ; $($_.group)[0]} | ConvertTo-Json

#打印分析结果
#timewindows 包含日期和时间段
#processid 阔话内为子进程ID（如果有）
write-host $json

#上传json
try {
    Invoke-WebRequest -Uri "https://foo.com/bar" -Method Post -Body $json
}
catch {
    write-host 'check foo.com connecting'
}