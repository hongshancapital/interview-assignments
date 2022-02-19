# Set-ExecutionPolicy Unrestricted

#-----------------------------------------------------------------------------#
# Filename:    interview_data_set.ps1                                                       
# Revision:    1.0                                                             
# Date:        2021/12/05                                                     
# Author:      Li Qin                                                         
# Email:       liqin0127@163.com                                                
# Description:                                            
#-----------------------------------------------------------------------------#

$logFile = '.\interview_data_set'
$dataDict = @{}
$timeWindow = @{"00"="0000-0100";"01"="0100-0200";"02"="0200-0300";"03"="0300-0400";
                "04"="0400-0500";"05"="0500-0600";"06"="0600-0700";"07"="0700-0800";
                "08"="0800-0900";"09"="0900-1000";"10"="1000-1100";"11"="1100-1200";
                "12"="1200-1300";"13"="1300-1400";"14"="1400-1500";"15"="1500-1600";
                "16"="1600-1700";"17"="1700-1800";"18"="1800-1900";"19"="1900-2000";
                "20"="2000-2100";"21"="2100-2200";"22"="2200-2300";"23"="2300-0000"}

function dataPost() {
    $body = $dataDict.Values | ConvertTo-Json 
    # echo $body
    [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Ssl3
    $resp = Invoke-WebRequest -Uri "https://foo.com/bar" -Method POST -ContentType "application/json" -Body $body
    echo $resp
}

function handleLog($line) {
    if ($line -eq '') { return }
    $re = ".+\s(\d+:\d+:\d+?)\s(.+?)\s(.+?):\s(.+)$"
    $res = [regex]::match($line,$re)
    $date_time = $timeWindow[$res.groups[1].Value.split(":")[0]]
    $device = $res.groups[2].Value.Trim()
    $description = $res.groups[4].Value.Trim()
    $process_name = $res.groups[3].Value.split("[")[0].Trim()
    $process_id = $res.groups[3].Value.split("[")[1].split("]")[0].Trim()

    $index = -Join($date_time,"|",$device,"|",$process_name,"|",$process_id,"|",$description.Substring(0, 5))
    if ($dataDict.ContainsKey($index)) {
        $dataDict[$index]["numberOfOccurrence"] += 1
    } else {
        $dataDict.Add($index,@{"deviceName"=$device;"processId"=$process_id;"processName"=$process_name;"description"=$description;"timeWindow"=$date_time;"numberOfOccurrence"=1})
    }
}

function readLog() {
    $loglines = Get-Content $logFile
    $tempLine = ""
    foreach ($line in $loglines) {
        if ($line -match 'last message repeated 1 time') {
            continue
        } elseif ($line -notmatch '^May') {
            $tempLine=-Join($tempLine,$line)
        } else {
            handleLog $tempLine
            $tempLine = $line
        }
    }
    handleLog $tempLine
}

readLog
dataPost