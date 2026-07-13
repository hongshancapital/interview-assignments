#linux环境下（无mac环境）操作

         [root@localhost ~]# zcat interview_data_set.gz   #linux环境下

                             gzcat interview_data_set.gz     #Mac环境下操作

         [root@localhost ~]# zcat interview_data_set.gz | sed -e '1h;2,$H;$!d;g;s/\n\t/ /g' | awk -f log.awk > interview_data_set.json      #Linux环境下查看日志并通过sed和awk命令将日志进行分析合并输出为json文件。

                             gzcat interview_data_set.gz | sed -e '1h;2,$H;$!d;g;s/\n\t/ /g' | awk -f log.awk > interview_data_set.json     #Mac环境下查看日志并通过sed和awk命令将日志进行分析合并输出为json文件。


#其中log.awk内容如下：

#! /usr/bin/awk -f

BEGIN {
    pre_logline = ""
}

{
    processId = 0
    deviceName = ""
    processName = ""
    description = ""
    timeWindow = ""
}

{
    delete a
    split($3, a, ":")
    if (a[1] == "00")
        timeWindow = "0000-0100"
    else if (a[1] == "01")
        timeWindow = "0100-0200"
    else if (a[1] == "02")
        timeWindow = "0220-0300"
    else if (a[1] == "03")
        timeWindow = "0300-0400"
    else if (a[1] == "04")
        timeWindow = "0400-0500"
    else if (a[1] == "05")
        timeWindow = "0500-0600"
    else if (a[1] == "06")
        timeWindow = "0600-0700"
    else if (a[1] == "07")
        timeWindow = "0700-0800"
    else if (a[1] == "08")
        timeWindow = "0800-0900"
    else if (a[1] == "09")
        timeWindow = "0900-1000"
    else if (a[1] == "10")
        timeWindow = "1000-1100"
    else if (a[1] == "11")
        timeWindow = "1100-1200"
    else if (a[1] == "12")
        timeWindow = "1200-1300"
    else if (a[1] == "13")
        timeWindow = "1300-1400"
    else if (a[1] == "14")
        timeWindow = "1400-1500"
    else if (a[1] == "15")
        timeWindow = "1500-1600"
    else if (a[1] == "16")
        timeWindow = "1600-1700"
    else if (a[1] == "17")
        timeWindow = "1700-1800"
    else if (a[1] == "18")
        timeWindow = "1800-1900"
    else if (a[1] == "19")
        timeWindow = "1900-2000"
    else if (a[1] == "20")
        timeWindow = "2000-2100"
    else if (a[1] == "21")
        timeWindow = "2100-2200"
    else if (a[1] == "22")
        timeWindow = "2200-2300"
    else if (a[1] == "23")
        timeWindow = "2300-2400"
    else {
    }
    for (i = 4; i<=NF; i++) {
        if (i == 4) {
            pre_logline = $i
        } else {
            pre_logline = pre_logline" "$i
        }
    }
    if ((pre_logline, timeWindow) in countA) {
        countA[pre_logline, timeWindow] = countA[pre_logline, timeWindow] + 1
    } else {
        countA[pre_logline, timeWindow] = 1
    }
}

END {
    print "["
    n = 1
    sum = 0
    for (k in countA) {
        processId = 0
        deviceName = ""
        processName = ""
        description = ""
        timeWindow = ""
        c = length(countA)
        delete a
        split(k, a, SUBSEP)
	numberOfOccurrence = countA[a[1], a[2]]
        timeWindow = a[2]
        delete aa
        split(a[1], aa, " ")
        deviceName = aa[1]
        delete aaa
        split(aa[2], aaa, "]")
        delete aaaa
        split(aaa[1], aaaa, "[")
        processName = aaaa[1]
        processId = aaaa[2]
        for (i = 3; i<=length(aa); i++) {
            gsub(/"/, "\\\"", aa[i])
            if (i == 3) {
                description = aa[i]
            } else {
                description = description" "aa[i]
            }
        }
    	printf "  {\n    \"deviceName\":\"%s\", \n    \"processId\":%d, \n    \"processName\":\"%s\", \n    \"description\":\"%s\", \n    \"timeWindow\":\"%s\", \n    \"numberOfOccurrence\":%d \n  }", deviceName, processId, processName, description, timeWindow, numberOfOccurrence
        sum = sum + numberOfOccurrence
        if (n < c)
            print ", "
        else
            print ""
        n++
    }
    print "]"
}


#通过Json格式POST上传至服务器

         [root@localhost ~]# curl -X POST -H "Content-Type: application/json" -d @interview_data_set.json https://foo.com/bar

#日志分析合并后输出结果请见interview_data_set.json

#windows系统下的操作

1、通过超级管理员打开powershell，并将powershell的安全策略修改为RemoteSigned，执行命令如下：
set-executionpolicy remotesigned

2、执行脚本 log-windows.ps1

log-windows.ps1内容如下：

$server_url = "http://foo.com/bar"
$error_report = @{}

foreach($single_line in Get-Content "interview_data_set") {
    if ($single_line.contains("error")) {
         $msg = $single_line.split(" ", 5)
         $time = $msg[0] + " " + $msg[1] + " " + $msg[2]
         $timestamp = $msg[2].split(":", 3)
         $deviceName = $msg[3]
         $processMsg = $msg[4].split(":", 2)[0]
         $description = $msg[4].split(":", 2)[1]
         $processName = $processMsg.split("[")[0]
         $processId = $processMsg.split("[")[1].split("]")[0]

         if (!($error_report.ContainsKey($processName + $description))){
             $new_error = @{}
             $new_error["deviceName"] = $deviceName
             $new_error["processId"] = $processId
             $new_error["processName"] = $processName
             $new_error["description"] = $description
             $new_error["timeWindow"] = $timestamp[0] + $timestamp[1], $timestamp[0] + $timestamp[1]
             $new_error["numberOfOccurrence"] = 1
             $error_report[$processName + $description] = $new_error
         }
         else {
             $start_time = $error_report[$processName + $description]["timeWindow"].split(" ", 2)[0]
             $new_end = $timestamp[0] + $timestamp[1]
             $error_report[$processName + $description]["timeWindow"] = $start_time, $new_end
             $error_report[$processName + $description]["numberOfOccurrence"] = $error_report[$processName + $description]["numberOfOccurrence"] + 1
         }
    }
}

foreach($key in $error_report.Keys){
    Write-Host "======================="
    Write-Host "deviceName:" $($error_report[$key])["deviceName"]
    Write-Host "processName:" $($error_report[$key])["processName"]
    Write-Host "processId:" $($error_report[$key])["processId"]
    Write-Host "description:" $($error_report[$key])["description"]
    Write-Host "timeWindow:" $($error_report[$key])["timeWindow"]
    Write-Host "numberOfOccurrence:" $($error_report[$key])["numberOfOccurrence"]
}

##Invoke-WebRequest $server_url -Method POST -Body ($errorRecord | ConvertTo-Json -Depth 4) -ContentType "application/json"