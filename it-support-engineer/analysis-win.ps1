#Set-ExecutionPolicy -ExecutionPolicy Unrestricted -scope CurrentUser
tar -zxvf interview_data_set.gz 
Get-Content .\interview_data_set | Select-String "error|invalid|failed" | Select-String com > mac-service.log
Get-Content .\interview_data_set | Select-String "error|invalid|failed" | Select-String com -NotMatch | Select-String "Microsoft" -NotMatch >no-mac-service.log
Get-Content .\interview_data_set | Select-String "error|invalid|failed" | Select-String com -NotMatch | Select-String "Microsoft" >no-mac-service-MS.log
Get-Content .\interview_data_set | Select-String "error|invalid|failed" | Select-String com -NotMatch | Select-String "Teams" >> no-mac-service-MS.log

Get-Content .\mac-service.log | ForEach-Object {$_.split()[5]} | ForEach-Object {$_ -replace "[0-9]"  , '' }| ForEach-Object {$_.split(":")[0]} |  ForEach-Object {$_.split("[")[0]} |  ForEach-Object {$_.split(")")[0]} |  ForEach-Object {$_.split("(")[1]} | Sort-Object -unique > Mac_Service_ProcessName_Second
Get-Content .\no-mac-service.log | ForEach-Object {"$(($_ -split "\s+")[4])"}  |  ForEach-Object {$_.split("[")[0]} | Sort-Object -unique | where{$_ -ne ""} > No_Mac_Service_ProcessName
Get-Content .\no-mac-service-MS.log | ForEach-Object {"$(($_ -split "\s+")[4..7])"} | ForEach-Object {$_.split("[")[0]} | Sort-Object -unique | where{$_ -ne ""} > No_Mac_MS_Service_ProcessName


$Mac_Service_processName_Second=Get-Content .\Mac_Service_ProcessName_Second
$No_Mac_Service_processName=Get-Content .\No_Mac_Service_ProcessName
$No_Mac_MS_Service_processName=Get-Content .\No_Mac_MS_Service_ProcessName

Remove-Item .\Mac_Service_ProcessName_Second
Remove-Item .\No_Mac_Service_ProcessName
Remove-Item .\No_Mac_MS_Service_ProcessName
for ($i=0;$i -lt $Mac_Service_processName_Second.count;$i++)
{
    $filename=-Join($Mac_Service_processName_Second[$i],“.json”)
    New-Item $filename -type file -force

    Get-Content .\mac-service.log | Select-String $Mac_Service_processName_Second[$i] | ForEach-Object {($_ -split "\s+")[3] } | Sort -Unique >  Mac_Service_deviceName 
    $Mac_Service_deviceName = Get-Content .\Mac_service_deviceName
    Remove-Item Mac_service_deviceName
    
    Get-Content .\mac-service.log | Select-String $Mac_Service_processName_Second[$i] | ForEach-Object {"$(($_ -split "\s+")[4] )"} | ForEach-Object {"$($_.split("[")[0])"} | Sort-object -Unique >  Mac_Service_processName_First
    $Mac_Service_processName_First = Get-Content .\Mac_service_processName_First
    Remove-Item Mac_service_processName_First
    
    Get-Content .\mac-service.log | Select-String $Mac_Service_processName_Second[$i] | ForEach-Object {($_ -split "\s+")[4] } | ForEach-Object {$_.split("[")[1]} | ForEach-Object {$_.split("]")[0]} |Sort -Unique >  Mac_Service_processId
    $Mac_Service_processId=Get-Content .\Mac_Service_processId
    Remove-item Mac_service_processId
    
    Get-Content .\mac-service.log | Select-String $Mac_Service_processName_Second[$i] | ForEach-Object {"$(($_ -split "\s+")[6..30])"} | sort -Unique >Mac_Service_Description
    $Mac_Service_Description= get-content .\Mac_Service_Description
    Remove-Item Mac_Service_Description  
    $Mac_Service_processName=-Join($Mac_Service_processName_First,"---",$Mac_Service_processName_Second[$i])

    Write-Output " {" | Out-File -FilePath $filename
    Write-Output "$TAB $TAB `"deviceName`": `"$Mac_Service_deviceName`" ," | Out-File -Append -FilePath $filename
    Write-Output "$TAB $TAB `"processId`": `"$Mac_Service_processID`" ," | Out-File -Append -FilePath $filename
    Write-Output "$TAB $TAB `"proceeeName`": `"$Mac_Service_processName`" ," | Out-File -Append -FilePath $filename
    ##生成utf-8编码的文件，但是奇怪的是 pwsh本身已经是utf-8编码 写进描述中还是存在乱码问题
    Write-Output "$TAB $TAB `"description`": `"$Mac_Service_Description`" ," | Out-File -Encoding utf-8 -Append -FilePath $filename
    Write-Output "$TAB $TAB `"time`": [ " | Out-File -Append -FilePath $filename
    for ($hour=0;$hour -lt 24;$hour++)
    {
        $str1="00"
        $str2="59"
        $str3="0"

        if ($hour -le 9)
        {
            $hour=-Join($str3,$hour)
        }
        else
        {
            $hour=$hour
        }
        (Get-Content .\mac-service.log | Select-String $Mac_Service_processName_Second[$i] | ForEach-Object {($_ -split "\s+")[2] } | select-string ${hour}:[0-5][0-9]:[0-5][0-9] | Sort | Out-String).Trim("`r","`n") > time_list_temp
        Get-Content .\time_list_temp | where{$_ -ne ""} > time_list_temp_temp
        #Get-Content .\mac-service.log | Select-String $Mac_Service_processName_Second[$i] | ForEach-Object {($_ -split "\s+")[2] } | Where-Object ${hour}:[0-5][0-9]:[0-5][0-9] | Sort  > time_list_temp
        $time_list=Get-Content .\time_list_temp_temp
        $timewindos_first=-Join($hour,$str1)
        $timewindow_second=-Join($hour,$str2)
        Remove-Item time_list_temp
        $count=$time_list.count
        #关于这一块我依旧保持和写shell时同样的想法，只是由于时间关系，这块我并没有写pwsh的逻辑
        Write-Output "   {   " | Out-File -Append -FilePath $filename
        Write-Output "       `"timeWindow`":  `"$timewindos_first-$timewindow_second`"," | Out-File -Append -FilePath $filename
        Write-Output "       `"numberOfOccurrence`":  `"$count`" " | Out-File -Append -FilePath $filename
        Write-Output "  }," | Out-File -Append -FilePath $filename
        $time_list=@()
        ##hour由字符串转时间
        $hour = [convert]::ToInt32($hour, 10)
    }
 
    Write-Output "     } " | Out-File -Append -FilePath $filename
    Write-Output "   ] " | Out-File -Append -FilePath $filename
    Write-Output " }" | Out-File -Append -FilePath $filename
    Get-Content $filename | Where-Object ReadCount -ne 102 | Set-Content -Encoding Utf8 temp
    Get-Content .\temp > $filename
    Remove-Item temp
}
for ($j=0;$j -lt $No_Mac_Service_processName.count;$j++)
{
    $filename=-Join($No_Mac_Service_processName[$j],“.json”)
    New-Item $filename -type file -force

    Get-Content .\no-mac-service.log | Select-String $No_Mac_Service_processName[$j] | ForEach-Object {($_ -split "\s+")[3] } | Sort -Unique >  No_Mac_Service_deviceName 
    $No_Mac_Service_deviceName = Get-Content .\No_Mac_service_deviceName
    Remove-Item No_Mac_Service_deviceName
    
    Get-Content .\no-mac-service.log | Select-String $No_Mac_Service_processName[$j] | ForEach-Object {($_ -split "\s+")[4] } | ForEach-Object {$_.split("[")[1]} | ForEach-Object {$_.split("]")[0]} |Sort -Unique >  No_Mac_Service_processId
    $No_Mac_Service_processId=Get-Content .\No_Mac_Service_processId
    Remove-item No_Mac_Service_processId
    
    Get-Content .\no-mac-service.log | Select-String $No_Mac_Service_processName[$j] | ForEach-Object {"$(($_ -split "\s+")[5..30])"} | sort -Unique >No_Mac_Service_Description
    $No_Mac_Service_Description= get-content .\No_Mac_Service_Description
    Remove-Item No_Mac_Service_Description  
    #$No_Mac_Service_processName=-Join($No_Mac_Service_processName[$j]," ")
    $No_Mac_Service_processName_Temp=$No_Mac_Service_processName[$j]
    Write-Output " {" | Out-File -FilePath $filename
    Write-Output "$TAB $TAB `"deviceName`": `"$No_Mac_Service_deviceName`" ," | Out-File -Append -FilePath $filename
    Write-Output "$TAB $TAB `"processId`": `"$No_Mac_Service_processID`" ," | Out-File -Append -FilePath $filename
    Write-Output "$TAB $TAB `"proceeeName`": `"$No_Mac_Service_processName_Temp`" ," | Out-File -Append -FilePath $filename
    ##生成utf-8编码的文件，但是奇怪的是 指定了也没生效
    Write-Output "$TAB $TAB `"description`": `"$No_Mac_Service_Description `" ," | Out-File -Encoding utf-8 -Append -FilePath $filename
    Write-Output "$TAB $TAB `"time`": [ " | Out-File -Append -FilePath $filename
    for ($hour=0;$hour -lt 24;$hour++)
    {
        $str1="00"
        $str2="59"
        $str3="0"
        $time_list=@()
        if ($hour -le 9)
        {
            $hour=-Join($str3,$hour)
        }
        else
        {
            $hour=$hour
        }
        (Get-Content .\no-mac-service.log | Select-String $No_Mac_Service_processName[$j] | ForEach-Object {($_ -split "\s+")[2] } | select-string ${hour}:[0-5][0-9]:[0-5][0-9] | Sort -Unique| Out-String).Trim("`r","`n") > time_list_temp
        Get-Content .\time_list_temp | where{$_ -ne ""} > time_list_temp_temp
        #Get-Content .\mac-service.log | Select-String $Mac_Service_processName_Second[$i] | ForEach-Object {($_ -split "\s+")[2] } | Where-Object ${hour}:[0-5][0-9]:[0-5][0-9] | Sort  > time_list_temp
        $time_list=Get-Content .\time_list_temp_temp
        $timewindos_first=-Join($hour,$str1)
        $timewindow_second=-Join($hour,$str2)
        Remove-Item time_list_temp
        $count=$time_list.count
        #关于这一块我依旧保持和写shell时同样的想法，只是由于时间关系，这块我并没有写pwsh的逻辑
        Write-Output "   {   " | Out-File -Append -FilePath $filename
        Write-Output "       `"timeWindow`":  `"$timewindos_first-$timewindow_second`"," | Out-File -Append -FilePath $filename
        Write-Output "       `"numberOfOccurrence`":  `"$count`" " | Out-File -Append -FilePath $filename
        Write-Output "  }," | Out-File -Append -FilePath $filename
        $time_list=@()
        ##hour由字符串转时间
        $hour = [convert]::ToInt32($hour, 10)
    }
 
    Write-Output "     } " | Out-File -Append -FilePath $filename
    Write-Output " ] " | Out-File -Append -FilePath $filename
    Write-Output " }" | Out-File -Append -FilePath $filename
    Get-Content $filename | Where-Object ReadCount -ne 102 | Set-Content -Encoding Utf8 temp
    Get-Content .\temp > $filename
    Remove-Item temp
}
for ($k=0;$k -lt $No_Mac_MS_Service_processName.count;$k++)
{
    $filename=-Join($No_Mac_MS_Service_processName[$k],“.json”)
    New-Item $filename -type file -force

    Get-Content .\no-mac-service-MS.log | Select-String $No_Mac_MS_Service_processName[$k] | ForEach-Object {($_ -split "\s+")[3] } | Sort -Unique >  No_Mac_MS_Service_deviceName 
    $No_Mac_MS_Service_deviceName = Get-Content .\No_Mac_MS_Service_deviceName
    Remove-Item No_Mac_MS_Service_deviceName
    
    Get-Content .\no-mac-service-MS.log | Select-String $No_Mac_MS_Service_processName[$k] | ForEach-Object {"$(($_ -split "\s+")[4..7])"} | ForEach-Object {$_.split("[")[1]} | ForEach-Object {$_.split("]")[0]} |Sort -Unique >  No_Mac_MS_Service_processId
    $No_Mac_MS_Service_processId=Get-Content .\No_Mac_MS_Service_processId
    Remove-item No_Mac_MS_Service_processId
    
    Get-Content .\no-mac-service-MS.log | Select-String $No_Mac_MS_Service_processName[$k] | ForEach-Object {"$(($_ -split "\s+")[7..30])"} | sort -Unique >No_Mac_MS_Service_Description
    $No_Mac_MS_Service_Description= get-content .\No_Mac_MS_Service_Description
    Remove-Item No_Mac_MS_Service_Description  
    #$No_Mac_Service_processName=-Join($No_Mac_Service_processName[$j]," ")
    $No_Mac_MS_Service_processName_Temp=$No_Mac_MS_Service_processName[$k]
    Write-Output " {" | Out-File -FilePath $filename
    Write-Output "$TAB $TAB `"deviceName`": `"$No_Mac_MS_Service_deviceName`" ," | Out-File -Append -FilePath $filename
    Write-Output "$TAB $TAB `"processId`": `"$No_Mac_MS_Service_processId`" ," | Out-File -Append -FilePath $filename
    Write-Output "$TAB $TAB `"proceeeName`": `"$No_Mac_MS_Service_processName_Temp`" ," | Out-File -Append -FilePath $filename
    ##生成utf-8编码的文件，但是奇怪的是 指定了也没生效
    Write-Output "$TAB $TAB `"description`": `"$No_Mac_MS_Service_Description`" ," | Out-File -Encoding utf-8 -Append -FilePath $filename
    Write-Output "$TAB $TAB `"time`": [ " | Out-File -Append -FilePath $filename
    for ($hour=0;$hour -lt 24;$hour++)
    {
        $str1="00"
        $str2="59"
        $str3="0"
        $time_list=@()
        if ($hour -le 9)
        {
            $hour=-Join($str3,$hour)
        }
        else
        {
            $hour=$hour
        }
        (Get-Content .\no-mac-service-MS.log | Select-String $No_Mac_MS_Service_processName[$k] | ForEach-Object {($_ -split "\s+")[2] } | select-string ${hour}:[0-5][0-9]:[0-5][0-9] | Sort -Unique| Out-String).Trim("`r","`n") > time_list_temp
        Get-Content .\time_list_temp | where{$_ -ne ""} > time_list_temp_temp
        #Get-Content .\mac-service.log | Select-String $Mac_Service_processName_Second[$i] | ForEach-Object {($_ -split "\s+")[2] } | Where-Object ${hour}:[0-5][0-9]:[0-5][0-9] | Sort  > time_list_temp
        $time_list=Get-Content .\time_list_temp_temp
        $timewindos_first=-Join($hour,$str1)
        $timewindow_second=-Join($hour,$str2)
        Remove-Item time_list_temp
        Remove-Item time_list_temp_temp 
        $count=$time_list.count
        #关于这一块我依旧保持和写shell时同样的想法，只是由于时间关系，这块我并没有写pwsh的逻辑
        Write-Output "   {   " | Out-File -Append -FilePath $filename
        Write-Output "       `"timeWindow`":  `"$timewindos_first-$timewindow_second`"," | Out-File -Append -FilePath $filename
        Write-Output "       `"numberOfOccurrence`":  `"$count`" " | Out-File -Append -FilePath $filename
        Write-Output "  }," | Out-File -Append -FilePath $filename
        $time_list=@()
        ##hour由字符串转时间
        $hour = [convert]::ToInt32($hour, 10)
    }
 
    Write-Output "     } " | Out-File -Append -FilePath $filename
    Write-Output " ] " | Out-File -Append -FilePath $filename
    Write-Output " }" | Out-File -Append -FilePath $filename
    Get-Content $filename | Where-Object ReadCount -ne 102 | Set-Content -Encoding Utf8 temp
    Get-Content .\temp > $filename
    Remove-Item temp
}
Remove-Item analysis-win.json
Get-Content *.json | Add-Content analysis-win
Remove-Item *.json 
Remove-Item *.log
rename-Item analysis-win -NewName analysis-win.json
#curl -k -H "Content-Type:application/json" -X POST -d @analysis-win.json https://foo.com/bar
#Remove-Item analysis-win.json

