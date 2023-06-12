if (-not (Test-Path "interview_data_set.log")) {
    Write-Host "downloading interview_data_set"
    Invoke-WebRequest -Uri "https://github.com/scdt-china/interview-assignments/raw/master/it-support-engineer/interview_data_set.gz" -OutFile "interview_data_set.gz"
    Expand-Archive -Path "interview_data_set.gz" -DestinationPath "." | Out-Null
    Rename-Item -Path ".\interview_data_set" -NewName "interview_data_set.log" -Force
}
else {
    Write-Host "interview_data_set already exists"
}

$logs = Get-Content "interview_data_set.log"

$logData = @{}
$prevLine = ""

$lines = $logs -split '\r?\n'

$combinedLines = @()
$currentLine = ""

foreach ($line in $lines) {
    if ($line -match '^\w{3} \d{2} \d{2}:\d{2}:\d{2}') {
        if ($currentLine -ne "") {
            $combinedLines += $currentLine
        }
        $currentLine = $line
    } else {
        $currentLine += " $line"
    }
}

if ($currentLine -ne "") {
    $combinedLines += $currentLine
}

foreach ($log in $combinedLines) {

    if ($log -like "*--- last message repeated *") {
        $log = $prevLine
    }
    $prevLine = $log
    $log = $log.Trim()

    if ($log.Length -ge 20) {
        $logParts = $log.Split(" ", 6)
        
        $hour = [int]$logParts[2].Split(":")[0]
        $start = "{0:D4}" -f $hour
        $end = "{0:D4}" -f ($hour+1)
        $timeWindow = "$start-$end"

        $deviceName = $logParts[3]
        $processInfo = $logParts[4]
        $description = $logParts[5]
        if($processInfo.Contains("[")) {
            $processName = $processInfo.Split("[")[0]  
            $processId = $processInfo.Split("[")[1].Split("]")[0]
        }

        $key = "$timeWindow|$deviceName|$processId|$processName|$description"
        if ($logData.ContainsKey($key)) {
            $logData[$key]++
        } else {
            $logData[$key] = 1
        }
    }
}

$output = @()

foreach ($item in $logData.Keys) {
    $fields = $item -split "\|"

    $deviceName = $fields[1]
    $processId = $fields[2]
    $processName = $fields[3]
    $description = $fields[4..($fields.Length - 1)] -replace '"', '\"' -join ' '
    $timeWindow = $fields[0]
    $numberOfOccurrence = $logData[$item]

    $logEntry = @{
        "deviceName" = $deviceName
        "processId" = [int]$processId
        "processName" = $processName
        "description" = $description
        "timeWindow" = $timeWindow
        "numberOfOccurrence" = $numberOfOccurrence
    }

    $output += $logEntry
}

$jsonData = $output | ConvertTo-Json

Set-Content -Path "./logdata.json" -Value $jsonData

Write-Host "uploading log to foo.com"
$response = Invoke-RestMethod -Uri "https://foo.com/bar" -Method Post -ContentType "application/json" -InFile "logdata.json" -UseBasicParsing

if ($response -eq "200") {
    Write-Host "Upload successful"
}
else {
    Write-Host "Upload failed $response"
}