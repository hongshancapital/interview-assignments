
$logpath = '.\interview_data_set'
$log = Get-Content $logpath
$outputs = New-Object -TypeName System.Collections.ArrayList   

Function TimeToWindow ($time)      
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

Function AddErrorToOutput([string]$deviceName,[string]$processId,[string]$processName,[string]$description,[string]$timeWindow)   
{
	foreach($output in $outputs)
	{
		if (($output.description -eq $description)-and ($output.timeWindow -eq $timeWindow))     
		{
			$output.processId += $processId    
			$output.numberOfOccurrence += 1
			return
		}
	}
	$error = New-Object -TypeName PSObject -Property $properties @{    
	   'deviceName' = $deviceName
	   'processId' = ,$processId                             
	   'processName' = $processName
	   'description' = $description
	   'timeWindow' = $timeWindow
	   'numberOfOccurrence' = 1
   }
	$outputs.Add($error)
}

Function CollectError ($line)   
{
	$mapping = $line -match '\((.*)\[(\d.*)\]\)\: (.*)'           
	if ($matches)
	{
		$part = $line.split(' ')                                   
		$deviceName = $part[3]
		$time = $part[2]
		$timeWindow = TimeToWindow $time                           
		$processId = $matches[2]
		$processname = $matches[1]
		$description = $matches[3]
		$adding = AddErrorToOutput $deviceName $processId $processName $description $timeWindow    
	}
}


ForEach($log_line in $log)     
{
	$collecting = CollectError $log_line
}

$output_body = $outputs | Convertto-Json | Out-File .\output.txt   

Invoke-WebRequest https://foo.com/bar -Method POST -ContentType "application/json" -Body $output_body