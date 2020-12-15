$ServiceApi = 'http://foo.com/bar'

$PrevHour = $null
$PrevDeviceName = $null
$PrevProcessId = $null
$PrevProcessName = $null
$PrevDescription = $null

$Occurrence = @{}

$LogFile = '.\DevOps_interview_data_set'

function Invoke-FooBarRequest {
    param (
        $ReqDeviceName,
        $ReqProcessId,
        $ReqProcessName,
        $ReqDescription,
        $ReqHour
    )

    $key = [string]$ReqHour+$ReqProcessName
    $Occurrence[$key] += 1
    $Params = @{
        deviceName=$ReqDeviceName;
        processId=$ReqProcessId;
        processName=$ReqProcessName;
        description=$ReqDescription;
        timeWindow=[string]::Format("{0:d2}00-{1:d2}00", $ReqHour, $ReqHour+1);
        numberOfOccurrence=$Occurrence[$key];
    }
    Invoke-WebRequest -Uri $ServiceApi -ContentType "application/json" -Method Post -Body (ConvertTo-Json $Params)
}

foreach ($line in Get-Content $LogFile) {
    $Parts = $line.trim().split(' ')
    $groups = [regex]::match($Parts[2], '(\d\d):(\d\d):(\d\d)').groups
    if ($groups[0].Success -ne $true) {
        $PrevDescription = $PrevDescription + '\n' + $line.trim()
        continue
    }
    $Hour = [int]$groups[1].Value

    $DeviceName = $Parts[3]
    if ($DeviceName -eq '---') {
        Invoke-FooBarRequest $PrevDeviceName $PrevProcessId $PrevProcessName $PrevDescription $PrevHour
        $PrevHour = $Hour
        continue
    }
    $i = 4
    $ProcessId = $null
    $ProcessName = $null
    do {
        $groups = [regex]::match($Parts[$i], '([\S]+)\[(\d+)\]').groups
        if ($groups[0].Success -ne $true) {
            if ($null -eq $ProcessName) {
                $ProcessName = $Parts[$i]
            } else {
                $ProcessName = $ProcessName + ' ' + $Parts[$i]
            }
        } else {
            $ProcessName = ($ProcessName + ' ' + $groups[1].Value).trim()
            $ProcessId = $groups[2].Value
        }
        $i += 1
    } until ($null -ne $ProcessId)
    $Description = $Parts[$i..$Parts.Count] -join ' '

    if ($null -ne $PrevHour) {
        Invoke-FooBarRequest $PrevDeviceName $PrevProcessId $PrevProcessName $PrevDescription $PrevHour
    }

    $PrevHour = $Hour
    $PrevDeviceName = $DeviceName
    $PrevProcessId = $ProcessId
    $PrevProcessName = $ProcessName
    $PrevDescription = $Description
}
if ($null -ne $PrevHour) {
    Invoke-FooBarRequest $PrevDeviceName $PrevProcessId $PrevProcessName $PrevDescription $PrevHour
}
