"""
Author:Lionel
Tel: +86 13821818560
"""
"""
思路：
1. 按行读取log文件，用空格分割每一行字符串
2. 
"""
Function format_time($time)
{
    $split_time=$time.split(':')
    $hour1=[int]$split_time[0]
    $hour2=$hour1+1
    if ($hour1 -lt 10)
    {
        $hour1='0'+$hour1.ToString()+'00'
    }
    else
    {
        $hour1=$hour1.ToString()+'00'
    }

    if ($hour2 -lt 10)
    {
        $hour2='0'+$hour2.ToString()+'00'
    }
    else
    {
        $hour2=$hour2.ToString()+'00'
    }
    
    $time_str=$hour1+'-'+$hour2

    return $time_str
}
# 读取日志文件
$log_file='.\interview_data_set'
$log=Get-Content $log_file

# 创建数组，准备按行接收日志数据
$log_arr=New-Object -TypeName System.Collections.ArrayList

$event_list=New-Object -TypeName System.Collections.ArrayList
ForEach($line in $log)
{
    $line_split=$line.split(" ",7)
    $line_split[5] -match '\((.*)\[(\d.*)\]\)\:'
    if($matches)
    {
	$deviceName = $line_split[3]
	$time = $line_split[2]
	$timeWindow = format_time($time)
	$processId = $matches[2]
	$processName = $matches[1]
	$description = $line_split[6]

        $event=$deviceName+'_@'+$processName+'_@'+$description+'_@'+$timeWindow
        if ($event_list -notcontains $event){$event_list.Add($event)}
	$log_all=$event+'_@'+$processId
	$log_arr.Add($log_all)
        
    }
}


$result=New-Object -TypeName System.Collections.ArrayList


foreach($i in $event_list)
{
   $processId_list=New-Object -TypeName System.Collections.ArrayList
   $numberOfOccurrence=0
   foreach($j in $log_arr)
    {
        if (($i.Split('_@')[0] -eq $j.Split('_@')[0]) -and ($i.Split('_@')[1] -eq $j.Split('_@')[1]) -and ($i.Split('_@')[2] -eq $j.Split('_@')[2]) -and ($i.Split('_@')[3] -eq $j.Split('_@')[3]))
        {
           $processId_list.Add($j.Split('_@')[4])
           $numberOfOccurrence +=1           
        }
    }
    $error = New-Object -TypeName PSObject -Property $properties @{
	'deviceName' = $i.Split('_@')[0]
    	'processId' = $processId_list
    	'processName' = $i.Split('_@')[1]
    	'description' = $i.Split('_@')[2]
    	'timeWindow' = $i.Split('_@')[3]
    	'numberOfOccurrence' = $numberOfOccurrence
	
	}
    
    $result.Add($error)
}
$output = $result | Convertto-Json | Out-File .\json_output.txt
Invoke-WebRequest http://coolaf.com -Method POST -ContentType "application/json" -Body $output