#############################################################################################################################################
###用途：该脚本用于分析interview_data_set日志文件，获取以下信息并用json格式POST上传至服务器https://foo.com/bar,测试用http://httpbin.org/post         ###
###1.设备名称:(deviceName)                                                                                                                 ###
###2.错误的进程号码:(processID)                                                                                                             ###
###3.进程/服务名称:(processName)                                                                                                            ###
###4.错误的原因(描述):(description)                                                                                                         ###
###5.发生的时间(小时级):(timeWindow)                                                                                                        ###
###6.小时级timeWindow发生的次数:(numberOfOccurrence)                                                                                        ###
###系统要求:Windows 10 or higher                                                                                                           ###
###用法: [powershell] /path/to/win_assignment.ps1 /path/to/interview_data_set                                                             ###
###作者:魏小忠                                                                                                                             ###
############################################################################################################################################

param([string]$fileName)

##$fileName = "interview_data_set"

#需要传递位置参数$fileName
if ($fileName -eq ""){
    echo "Usage: [powershell] /path/to/win_assignment.ps1 /path/to/interview_data_set"   
    exit 1
}
#获取设备名称:(deviceName) 
$deviceName = (Get-Content $fileName | %{$_.Split(' ')[3]} | Group-Object | sort count -desc | Select-Object -First 1).Name

#获取进程/服务名称:(processName) 
$processName = (((Get-Content $fileName | %{$_.Split(' ')[5]}) -match "com\.apple\..*\[[0-9]+\]") -replace '\(|\)|]|:', '' | %{$_.Split('[')[0]} | Group-Object | sort count -desc | Select-Object -First 1).Name

#获取错误的进程号码:(processID) 
$processIDs = ((Get-Content $fileName | %{$_.Split(' ')[5]}) -match "$processName") -replace '\(|\)|]|:', '' | %{$_.Split('[')[1]} | Get-Unique

#获取错误的原因(描述):(description)
$description = ((Get-Content $fileName) -match  "$processName" | Select-Object -First 2 | %{$_.Split(')')[-1]}) -replace "^:\s*",""


#发生的时间(小时级):(timeWindow)以及对应小时级timeWindow发生的次数:(numberOfOccurrence)
$hourlyEvents = (Get-Content $fileName) -match  "$processName" | %{$_.Split(' ')[2]} | Get-Unique | %{$_.Split(':')[0]} | Group-Object | sort Name 
$hourlytimeWindowandEvents=""
for($i=0; $i -lt $HourlyEvents.Count; $i++){
    $timeWindowStart = $hourlyEvents[$i].Name
    $numberofOccurence = $hourlyEvents[$i].Count
    $timeWindowEnd = [int]$timeWindowStart + 1
    if($timeWindowEnd -lt 10){
        $timeWindowEnd = "0" + $timeWindowEnd.ToString() + "00"
    }
    else{
        $timeWindowEnd = $timeWindowEnd.ToString() + "00"
    }
    $hourlytimeWindowandEvents  += "TimeWindow: " + $timeWindowStart + "00-" + $timeWindowEnd + "  numberofOccurence: " + $numberofOccurence + "`r`n"
}

#收集需要的信息并转换成JSON格式
$infobody = @{
    deviceName = "$deviceName";
    processName = "$processName";
    processIDs = "$processIDs";
    description = "$description";
    HourlytimeWindowandEvents = "$hourlytimeWindowandEvents"
}
$jsonbody = ConvertTo-Json -Depth 10 $infobody

#把JSON格式的信息上传至服务器https://foo.com/bar(正确测试需要合法的URL替代)
#$serverURL = "https://foo.com/bar"
$serverURL = "http://httpbin.org/post"
$resp = curl -UseBasicParsing -Uri $serverURL -ContentType "application/json" -Method POST -Body $jsonbody -ErrorAction SilentlyContinue
if($?){
    echo "Upload event info of $deviceName successfully!"
    echo ($resp.Content | ConvertFrom-Json).json   
}
else{
    echo "Upload event info of $deviceName failed! Please try with a working serverURL!"
    echo $jsonbody
}