$logpath = '.\interview_data_set'
$log = Get-Content $logpath
$outputs = New-Object -TypeName System.Collections.ArrayList     #���յĽ�����飬�����������־��ץȡ���Ĺؼ���Ϣ

Function TimeToWindow ($time)       #�˺�������������ʱ�䣨����01:18:59��ת��Ϊʱ�䴰�ڣ�0100-0200��
{
	$time_parts = $time.split(':')
	$hour = [int]$time_parts[0]
	$nexthour = $hour+1
	if($hour -lt 10){
	$hour_string = "0"+ $hour.ToString()
	}
	else{
	$hour_string = $hour.ToString()
	}
	if($nexthour -lt 10){
	$nexthour_string = "0"+ $nexthour.ToString()
	}
	else{
	$nexthour_string = $nexthour.ToString()
	}
	return $hour_string + "00-" + $nexthour_string + "00"
}

Function AddErrorToOutput([string]$deviceName,[string]$processId,[string]$processName,[string]$description,[string]$timeWindow)   #�˺���������һ�д���õĴ�����־����������
{
	foreach($output in $outputs)
	{
		if (($output.description -eq $description)-and ($output.timeWindow -eq $timeWindow))      #�������������ʱ�䴰�ں�ĳ�����еĴ�����ͬ����ֻ��Ҫ����������+1
		{
			$output.processId += $processId      #��������ͬ����Ľ��̺������ԭ�����ͳ����
			$output.numberOfOccurrence += 1
			return
		}
	}
	$error = New-Object -TypeName PSObject -Property $properties @{     #ȫ�µĴ������½�һ�������Ҽ���������
	   'deviceName' = $deviceName
	   'processId' = ,$processId                                 #process IDʹ�����飬��Ϊ���ڶ��ID������ͬ�Ĵ���
	   'processName' = $processName
	   'description' = $description
	   'timeWindow' = $timeWindow
	   'numberOfOccurrence' = 1
   }
	$outputs.Add($error)
}

Function CollectError ($line)    #���ĺ������������������־�е�ÿһ��
{
	$mapping = $line -match '\((.*)\[(\d.*)\]\)\: (.*)'             #�����Ƿ��и�ʽ����(XXX[XXXX]): XXX�Ĵ�����ʾ
	if ($matches)
	{
		$part = $line.split(' ')                                    #���տո�ָ������Ա��ҵ�������Ϣ
		$deviceName = $part[3]
		$time = $part[2]
		$timeWindow = TimeToWindow $time                            #��ʱ��ת��Ϊ����
		$processId = $matches[2]
		$processname = $matches[1]
		$description = $matches[3]
		$adding = AddErrorToOutput $deviceName $processId $processName $description $timeWindow    #����������
	}
}


ForEach($log_line in $log)     #�����ú��ĺ�������
{
	$collecting = CollectError $log_line
}

$output_body = $outputs | Convertto-Json | Out-File .\json_output.txt    #�ѽ������ת��ΪJSON��ʽ��ͬʱ���ݵ��ļ�

Invoke-WebRequest https://foo.com/bar -Method POST -ContentType "application/json" -Body $output_body