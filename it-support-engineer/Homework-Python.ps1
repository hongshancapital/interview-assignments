$ErrorActionPreference = "Stop"

$url='https://foo.com/bar'
$logfile=$logfile='C:\Users\admincn\Desktop\interview_data_set'
$lineObjectArray=@()

foreach($line in get-content -Path $logfile) 
{
    if($line -match ':')
    {
      $lineArray = $line -split ' '
      if($lineArray[3] -eq 'BBAOMACBOOKAIR2') 
      {
          $obj = [pscustomobject]@{
            deviceName = $lineArray[3]
            processId = ($lineArray[4] -replace '\D+\[(\d+)\]', '$1').Trim(':')
            processName = ($lineArray[4] -replace '(\D+)\[\d+\]', '$1').Trim(':')
            description = $lineArray[5 .. ($lineArray.length-1)] -join ' '
            hour = ($lineArray[2] -split ':')[0]
          }
          $lineObjectArray += $obj
      }
    }
}

$groupCounter=@{}

$lineObjectArray | foreach {
    $key=$_.deviceName + $_.processId + $_.processName + $_.description + $_.hour
    if($groupCounter.Contains($key)) 
    {
        $groupCounter[$key].numberOfOccurrence++
    }
    else 
    {
        $groupCounter[$key]= [pscustomobject]@{ 
            deviceName = $_.deviceName
            processId = $_.processId
            processName = $_.processName
            description = $_.description
            timeWindow = '{0:D2}:00 - {1:D2}:00' -f $_.hour,(([int]$_.hour + 1)%24)
            numberOfOccurrence = 1
        }
    }
}

$groupCounter.Values | sort-object -Property timeWindow | convertto-json | out-file -FilePath .\ps-payload-v2.json
Invoke-WebRequest -Uri $url -TimeoutSec 5 -Method Post -InFile .\ps-payload-v2.json