
function Get-ProcessInfo {
    param (
        [string]$logPart
    )

    if ($logPart -match '\(\d+\):$') {
        $processId = $logPart -replace '[\(\):\[\]]', ''
        $processName = ($logPart -split '[\(\):\[\]]')[0]
    } else {
        $processId = ($logPart -split '\.')[-1] -replace '\):$', ''
        $processName = ($logPart -split '\.)')[0]
    }

    return $processName, $processId
}

$logData = Get-Content 'interview_data_set'

# Initialize an array to store log data
$logList = @()

foreach ($line in $logData) {
    # Check if the line contains 'error' or 'abnormal'
    if ($line -match 'error|abnormal') {
        $logParts = $line -split ' '

        $hour = $logParts[2].Split(':')[0]
        $deviceName = $logParts[3]

        # Get process name and process id from the 6th field
        $processName, $processId = Get-ProcessInfo -logPart $logParts[5]

        $description = ($logParts[6..($logParts.Count - 1)]) -join ' '
        $timeWindow = $hour + '00-' + ([int]$hour + 1).ToString("00") + '00'
        $numberOfOccurrence = 1  # Each line is an error, so occurrence is set to 1 for now.

        $log = [PSCustomObject]@{
            "deviceName" = $deviceName
            "processId" = $processId
            "processName" = $processName
            "description" = $description
            "timeWindow" = $timeWindow
            "numberOfOccurrence" = $numberOfOccurrence
        }

        # Add the log object to the log list
        $logList += $log
    }
}

# Group the logs by processName and description, and calculate occurrence
$aggregatedData = @{}

foreach ($log in $logList) {
    $key = ($log.processName, $log.description)

    if ($aggregatedData.ContainsKey($key)) {
        $aggregatedData[$key].numberOfOccurrence += $log.numberOfOccurrence
    } else {
        $aggregatedData[$key] = $log
    }
}

# Convert the aggregated data to JSON format
$logJson = $aggregatedData.Values | ConvertTo-Json

# Set the URL and headers for the POST request
$url = "https://foo.com/bar"
$headers = @{ "Content-Type" = "application/json" }

# Send the JSON data to the server using Invoke-RestMethod
$response = Invoke-RestMethod -Uri $url -Method Post -Headers $headers -Body $logJson

# Check the response status
if ($response.StatusCode -eq 200) {
    Write-Host "Log data successfully sent to the server."
} else {
    Write-Host "Failed to send log data. Status code: $($response.StatusCode)"
}

