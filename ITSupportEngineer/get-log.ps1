filter Convertto-DateTime
{
  param($OutputFormat='MM dd HH:mm:ss')

  Switch -regex ($_) {
    '一月|Jan' {$inputtime = $_ -replace '一月|Jan','01'}
    '二月|Feb' {$inputtime = $_ -replace '二月|Feb','02'}
    '三月|Mar' {$inputtime = $_ -replace '三月|Mar','03'}
    '四月|Apr' {$inputtime = $_ -replace '四月|Apr','04'}
    '五月|May' {$inputtime = $_ -replace '五月|May','05'}
    '六月|Jun' {$inputtime = $_ -replace '六月|Jun','06'}
    '七月|Jul' {$inputtime = $_ -replace '七月|Jul','07'}
    '八月|Aug' {$inputtime = $_ -replace '八月|Aug','08'}
    '九月|Sep' {$inputtime = $_ -replace '九月|Sep','09'}
    '十月|Oct' {$inputtime = $_ -replace '十月|Oct','10'}
    '十一月|Nov' {$inputtime = $_ -replace '十一月|Nov','11'}
    '十二月|Dec' {$inputtime = $_ -replace '十二月|Dec','12'}
  }

  try {
    [datetime]::ParseExact($inputtime,$OutputFormat, $null)
  } catch {}
}


$text = Get-Content D:\Jobs\Helpdesk_interview_data_set
$event = @()
foreach ($line in $text) {
    $log = $line -split '\s'
    $devicename = $log[3]
    if ($devicename -eq '---') {
        continue
    }
    $time = $($log[0..2] -join ' ') | Convertto-DateTime
    if (!$time) {
        $event[-1].description += " $($line.Trim())"
        continue
    }
    $timewindows = $time.ToString('MM-dd HH00')+'-'+$time.AddHours(1).tostring('HH00')
    if ($log[4] -match '^(.+)\[{1}([0-9]+)]:$') {
        $processid = $Matches[2]
        $processName = $Matches[1]
        $description = $log[5..99] -join ' '
    }
    elseif ($log[4] -match '^(.+)\[{1}([0-9]+)]$') {
        $processid = $Matches[2]
        $processName = $Matches[1]
        if ($log[5] -match '^\((.+)\[{1}([0-9]+)]\):$') {
            $processid += "($($Matches[2]))"
            $processName += " ($($Matches[1]))"
            $description = $log[6..99] -join ' '

        }
        elseif ($log[5] -match '^\((.+)\.{1}([0-9]+)\):$') {
            #$processid = ""
            $processName += "($($Matches[1]))"
            $description = $log[6..99] -join ' '

        }
        elseif ($log[5] -match '^\((.+)\):$') {
            #$processid = $Matches[2]
            $processName += "($($Matches[1]))"
            $description = $log[6..99] -join ' '

        }
        else {
            $description = $log[5..99] -join ' '
        }
    }
    else {
        $processid = ""
        $processName = ""
        $description = $log[4..999] -join ' '
    }
    $event += New-Object -TypeName psobject -Property @{
        deviceName = $log[3]
        processId = $processid
        processName = $processName
        description = $description
        timeWindow = $timewindows
        numberOfOccurrence = ""
    }
}
$group = $event | Group-Object timewindow,devicename,processid,processname
$json = $group | %{$($_.group)[0].numberofoccurrence = $_.count ; $($_.group)[0]} | ConvertTo-Json
Invoke-WebRequest -Uri "https://foo.com/bar" -Method Post -Body $json