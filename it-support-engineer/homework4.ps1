#读取文件
$file = Get-Content '\\vmware-host\Shared Folders\Documents\Coding\Python\02-homework\interview_data_set'

#变量：分别记录有效数据与无效数据
$Data=$invalidData= @()

#记录并忽略无效行；格式化时间窗口为：“0000-0000”
$fileUnifyTime = $file | %{ 
    if($_ -match '(\S+) (\S+) (\d{2}:\d{2}:\d{2}) (\S+) (.+?)(\[\d+\])(.*)') 
        {
            [int]$H = $Matches[3].Split(":")[0]
            if ($H -eq 23)
                {
                    $timeWindow = "2300-0000"
                    $_.replace($Matches[3],$timeWindow)
                }
            else
                {
                    $timeWindow = "{0:d2}00-{1:d2}00" -f $H,($H+1)
                    $_.replace($Matches[3],$timeWindow)
                } 
         }
    else{$invalidData += $_}
}

#用正则获取关键字
$fileUnique = $fileUnifyTime | Group -NoElement
$fileUnifyTime | sort -Unique | %{
    if($_ -match '(\S+) (\S+) (\S{9}) (\S+) (.+?)(\[\d+\])(.*)')
        {
            $item = $_
            $Data += [PSCustomObject][Ordered]@{
            deviceName = $Matches[4];
            processName = $Matches[5];
            processId = $Matches[6].Trim("[]")
            description = $Matches[7].Trim(": ")
            timeWindow = $Matches[3];
            numberOfOccurrence = ($fileUnique | Where-Object {$_.Name -eq $item}).Count
           
            }
        }

    else{$invalidData += $_}
}


#转换为Json并Post
$json = $Data | ConvertTo-Json
$url = 'https://foo.com/bar'
#Invoke-RestMethod -Method Post -ContentType 'application/json;charset=utf-8' -Uri $url -Body $json