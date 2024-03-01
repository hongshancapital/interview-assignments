$ErrorActionPreference = "Stop"
$url = 'https://foo.com/bar'
$logfile = '.\interview_data_set.gz'


$lineObjectArray = @()


foreach ($line in Get-Content -Path $logfile) {
    if ($line -match ':') {
        $lineArray = $line -split ' '
        if ($lineArray[3] -eq 'BBAOMACBOOKAIR2') {
            $obj = New-Object PSCustomObject
            $obj | Add-Member -MemberType NoteProperty -Name "deviceName" -Value $lineArray[3]
            $obj | Add-Member -MemberType NoteProperty -Name "processId" -Value ($lineArray[4] -replace '\D+\[(\d+)\]', '$1').Trim(':')
            $obj | Add-Member -MemberType NoteProperty -Name "processName" -Value ($lineArray[4] -replace '(\D+)\[\d+\]', '$1').Trim(':')
            $obj | Add-Member -MemberType NoteProperty -Name "description" -Value ($lineArray[5 .. ($lineArray.length-1)] -join ' ')
            $obj | Add-Member -MemberType NoteProperty -Name "hour" -Value ($lineArray[2] -split ':')[0]

            $lineObjectArray += $obj
        }
    }
}


$groupCounter = @{}


$lineObjectArray | ForEach-Object {
    $key = $_.deviceName + $_.processId + $_.processName + $_.description + $_.hour
    if ($groupCounter.Contains($key)) {
        $groupCounter[$key].numberOfOccurrence++
    } else {
        $timeWindow = '{0:D2}:00 - {1:D2}:00' -f $_.hour, (([int]$_.hour + 1) % 24)
        $obj = New-Object PSCustomObject
        $obj | Add-Member -MemberType NoteProperty -Name "deviceName" -Value $_.deviceName
        $obj | Add-Member -MemberType NoteProperty -Name "processId" -Value $_.processId
        $obj | Add-Member -MemberType NoteProperty -Name "processName" -Value $_.processName
        $obj | Add-Member -MemberType NoteProperty -Name "description" -Value $_.description
        $obj | Add-Member -MemberType NoteProperty -Name "timeWindow" -Value $timeWindow
        $obj | Add-Member -MemberType NoteProperty -Name "numberOfOccurrence" -Value 1

        $groupCounter[$key] = $obj
    }
}


$sortedOutput = $groupCounter.Values | Sort-Object -Property timeWindow
$jsonPayload = $sortedOutput | ConvertTo-Json


Write-Output $jsonPayload

$jsonPayload | Set-Content -Path ".\output-2.json"


Invoke-RestMethod -Uri $url -TimeoutSec 5 -Method Post -InFile ".\output-2.json"
