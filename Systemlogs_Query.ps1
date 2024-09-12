#'***************************************************************************
#'* Author:     Shuai Tao
#'* Date:       21/11/2021
#'* Purpose:    Query the system log file and get the info
#'*             Timewindow,processID,Descriptions,numberOfOccurrence,Devicename
#'* Scriptname: Systemlogs_Query.ps1
#'* Notes:      Regular Expression
#'* Requisites: Regular Expression，Json
#'* Target OS:  windows 
#'* Revision:   0.1 Initial version
#'*             
#'*             
#'***************************************************************************


$ErrorActionPreference = "SilentlyContinue"
$url='https://foo.com/bar'
$b=Get-Content "C:\temp\gz\New folder\data_set.txt"|Select-String -Pattern "error"
$Results = New-Object -TypeName System.Collections.ArrayList
      
Function ErrorAdding([string]$deviceName,[string]$processId,[string]$description,[string]$TimeWindow)  
{
	foreach($Result in $Results)
	{
		if (($Result.description -eq $description)-and ($Result.timeWindow -eq $TimeWindow))     
		{
			$Result.processId += $processId      
			$Result.numberOfOccurrence += 1
			return
		}
	}
	$errormes = New-Object -TypeName PSObject -Property $properties @{     
	   'deviceName' = $deviceName
	   'processId' = $processId                           
	   'processName' = $processName
	   'description' = $description
	   'timeWindow' = $TimeWindow
	   'numberOfOccurrence' = 1
   }
	$Results.Add($errormes)
}


foreach($i in $b){
    $maps=$i.line -match "\d{2}:\d{2}:\d{2}"
    
     $Map=$i.line -match "\d{5}"
     $processid=$matches.values
         
    $r="May 13 (?<date>.*?\d{2}:\d{2}:\d{2})\s(?<device>.*BB.*[0-9])\s(?<process>.*.[A-Z0-9.-].*\d{3,5}.).:\s(?<descrip>.*(\D)*:.*:.*)"
    $i.line -match $r
    
    $d=$matches.date
    $time="{0:t}" -f [DateTime] $d
    $f= [string]::format("{0:d2}",[int]::Parse($time.Split(':')[0])+1)
    $TimeWindow = $time.Split(':')[0] +"00-" + "$f" + "00"
    #$processname=$matches.process
    $description=$matches.descrip
    $devicename=$matches.device
    
   ErrorAdding $deviceName $processId $description $TimeWindow 
}

     $TResult = $Results | Convertto-Json
     $TResult

     Invoke-WebRequest $url -Method POST -Body ($TResult | ConvertTo-Json ) -ContentType "application/json"
