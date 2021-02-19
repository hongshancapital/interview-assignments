$logs = Import-Csv .\Helpdesk_interview_data_set.csv -Delimiter '|'
foreach($log in $logs){
    $a=$log.process -match '-([0-9]*)\+' #取出数字
    $log.processId=$Matches.1
    $b=$log.process -match '(.*)\+AFs'
    if($b){
    $Matches.1 -match '[a-z\.]+' #去除数字和括号
    $Matches.0 -match '[a-z\.]+[a-z]' #去除多余'.'
    $log.processName=$Matches.0
    }
    if($b -eq $false){
        $log.process -match '[a-z\.]+' #去除数字和括号
        $Matches.0 -match '[a-z\.]+[a-z]' #去除多余'.'
        $log.processName=$Matches.0
        }
    $spilt = $log.time -split ':' #拆分时间
    $hour = $spilt[0] # 取出小时
    $tempend = [int]$hour+1
    if($tempend -le 9)
    {
        $end = '0' + $tempend
    }
    else {
        $end = $tempend
    }
    $timewindow = $hour + '00-'+$end+'00'
    $log.timeWindow =$timewindow
}
#统计发生次数
foreach($log in $logs){
    $count = 0
    foreach($original in $logs){
        if($log.processId -eq $original.processId -and $log.processName -eq $original.processName -and $log.timeWindow -eq $original.timeWindow -and $log.description -eq $original.description -and $log.deviceName -eq $original.deviceName  ){
            $count++
        }
         $log.numberOfOccurrence =$count
    }

    }
    $logs | Select-Object -Property deviceName,processId,processName,description,timeWindow,numberOfOccurrence | ConvertTo-Json | Out-File .\result.json
    #api 上传
    Invoke-WebRequest -Uri http://foobar.com/bar -Method POST -Body ($logs | Select-Object -Property deviceName,processId,processName,description,timeWindow,numberOfOccurrence | ConvertTo-Json ) -ContentType "application/json"
