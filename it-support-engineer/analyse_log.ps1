param($log_file_name)

# 读取日志文件，优化日志文件信息
# 以timeWindow|deviceName|processName|processId|description 作为数组来统计在小时级别内发生的次数

$groupBy = @{}
Get-Content $log_file_name |  where {$_ -match "^[^\t]"} | % {
    $line = $_ -replace '"','' # 除去双引号，便于下面格式化JSON格式数据
    $line = $line -replace "'","" # 除去单引号，便于下面格式化JSON格式数据

    $line_a =  $line -split " "  

    $timeWindow = ($line_a[2] -split ":")[0]
    $deviceName = $line_a[3]
    $processName = ($line_a[4] -split "\[")[0]
    $processId = (($line_a[4] -split "\[")[1] -split "\]")[0]

    $description = ""
    For ($i = 5; $i -lt $line_a.Length; $i++) {
        $description += $line_a[$i]
    }

    $output = $timeWindow + "|"
    $output += $deviceName + "|"
    $output += $processName + "|"
    $output += $processId + "|"
    $output += $description

    $groupBy[$output] = $groupBy[$output] + 1
}

#   按需求以小时内产生的日志情况统计日志信息

$records_array = New-Object -TypeName System.Collections.ArrayList

foreach($i in $groupBy.Keys){
    $rec_array = $i -split '\|'
    $rec = '{' 
    $rec += '"deviceName" : "' + $rec_array[1] + '", '
    $rec += '"processId" : "' + $rec_array[3] + '", '
    $rec += '"processName" : "' + $rec_array[2] + '", '
    $rec += '"description" : "' + $rec_array[4] + '", '
    $rec += '"timeWindow" : "' + ('{0:d2}' -f $rec_array[0]) + "00-" + ('{0:d2}' -f ([System.Convert]::ToInt16($rec_array[0]) + 1)) + '00", '
    $rec += '"numberOfOccurrence" : "' + $groupBy[$i]
    $rec += '"}'

    $tmp = $records_array.Add($rec)
}

#   转化为JSON数据

$jsontable = ('{}' | ConvertFrom-Json )
$jsontable | Add-Member "records" ($records_array | ConvertFrom-Json)
$json_data = ($jsontable | ConvertTo-Json)

#   Post上传JSON数据

$url = "https://foo.com/bar"
Invoke-WebRequest -UseBasicParsing $url  -ContentType "application/json" -Method POST -Body $json_data
