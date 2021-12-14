<#
Author:从恩Lionel
Tel: +86 13821818560

思路：
1. 按行读取log文件,查找符合要求的error日志
2. 每一行重新组合成两个字符串，分别装进两个数组，event_list和log_arr
    deviceName____processName____description____timeWindow
    deviceName____processName____description____timeWindow____processId
3. 用event_list中的每一项去比对log_arr中的每一项，前四个键完全一致的，
   把processId装进一个数组，并统计processId的个数numberOfOccurrence。
4. 每一次内层循环结束，按字典格式整理数据，放进result数组里。
5. 把result数组转换成json格式，上传到服务器。
#>
Function format_time($time)
<#
时间处理函数，把形如“hh:mm:ss”的时间转换成“XX00-XX00”的格式
1. 补全两位：如果时间在1-9点，就前面补个0；如果是10-12，就不做操作
2. 时间后面再补2个0，与时间+1的值组成“XX00-XX00”的格式
#>
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

# 创建数组，准备按行接收日志字符串
$log_arr=New-Object -TypeName System.Collections.ArrayList
# 创建数组，准备按行接收事件字符串
$event_list=New-Object -TypeName System.Collections.ArrayList
#按行匹配，找到有形如“(xxx[xxx]):xxx”的字符串的行，组合成两个字符串，分别放进数组
ForEach($line in $log)
{
    $line_split=$line.split(" ",7)
    $check=$line_split[5] -match '\((.*)\[(\d.*)\]\)\:'
    if($check)
    {
	$deviceName = $line_split[3]
	$time = $line_split[2]
	$timeWindow = format_time($time)
	$processId = $matches[2]
	$processName = $matches[1]
	$description = $line_split[6]

        $event=$deviceName+'____'+$processName+'____'+$description+'____'+$timeWindow
        if ($event_list -notcontains $event){$event_list.Add($event)}
	$log_all=$event+'____'+$processId
	$log_arr.Add($log_all)

    }
}
# 创建数组，准备接收统计好的事件字典
$result=New-Object -TypeName System.Collections.ArrayList
#外层循环遍历事件，即不包含processId和发生次数的其他信息。

foreach($i in $event_list)
{
   $processId_list=New-Object -TypeName System.Collections.ArrayList
   [System.Collections.Generic.Dictionary[String,Object]]$error=@{}
   $numberOfOccurrence=0
   #外层遍历每次创建新的数组接收所有processId，创建新的字典，numberOfOccurrence归零，防止重复统计。
   $i_split=$i.Split("____",[System.StringSplitOptions]::RemoveEmptyEntries)
   #日志内容末尾有换行符，分隔完字符串要去掉空格，不然拿索引取不到正确的值。
   foreach($j in $log_arr)
    {
        #内层循环每次遍历完log_arr数组，做好统计再把总的结果放进result数组
        $j_split=$j.Split("____",[System.StringSplitOptions]::RemoveEmptyEntries)
        if (($i_split[0] -eq $j_split[0]) -and ($i_split[1] -eq $j_split[1]) -and ($i_split[2] -eq $j_split[2]) -and ($i_split[3] -eq $j_split[3]))
        {
           $processId_list.Add($j_split[4])
           $numberOfOccurrence +=1
        }
    }

	    $error['deviceName'] = $i_split[0]
    	$error['processId'] = $processId_list
    	$error['processName'] = $i_split[1]
    	$error['description'] = $i_split[2]
    	$error['timeWindow'] = $i_split[3]
    	$error['numberOfOccurrence'] = $numberOfOccurrence


    $result.Add($error)
}
#处理完毕，转换成json格式，并保存txt文件在脚本目录下。
$output = $result | Convertto-Json | Out-File .\ps_output.txt
#处理结果上传服务器
Invoke-WebRequest http://coolaf.com -Method POST -ContentType "application/json" -Body $output
Read-Host "处理完毕，按任意键退出……"