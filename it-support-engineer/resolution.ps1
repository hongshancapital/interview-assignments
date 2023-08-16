$filePath = "interview_data_set.gz"
$lines = @()
$pattern = '^(?<date>[a-zA-Z]{3} [0-9]{1,2}) (?<time>\d{2}):\d{2}:\d{2} (?<device>\S+) (?<pname>[a-zA-Z.]+)\[(?<pid>\d+).*[\]\)]: (?<desc>.*)$'
$result = @{}
try {
    $fileStream = [System.IO.File]::OpenRead($filePath)
    $gzipStream = New-Object IO.Compression.GzipStream($fileStream, [IO.Compression.CompressionMode]::Decompress)
    $streamReader = New-Object IO.StreamReader($gzipStream)
    while (($line = $streamReader.ReadLine()) -ne $null) {
        if (-not $line.StartsWith("May")) {
            $lines[-1] += $line
        }
        elseif ($line -like "*--- last message repeated*") {
            $lines += ($line.Split()[0..2] + $lines[-1].Split()[3..($lines[-1].Split().Count-1)]) -join ' '
        }
        else {
            $lines += $line
        }
    }
    foreach ($line in $lines) {
        $matchedSearch = $line | Select-String -Pattern $pattern -AllMatches
        if ($matchedSearch.Matches.Count -gt 0) {
            $resultKey = $matchedSearch.Matches[0].Groups["date"].Value
            $result[$resultKey] = @{
                "time" = $matchedSearch.Matches[0].Groups["time"].Value
                "device" = $matchedSearch.Matches[0].Groups["device"].Value
                "pname" = $matchedSearch.Matches[0].Groups["pname"].Value
                "pid" = $matchedSearch.Matches[0].Groups["pid"].Value
                "desc" = $matchedSearch.Matches[0].Groups["desc"].Value
            }
        }
    }

    $jsonPayload = $result | ConvertTo-Json
    # $url = "https://httpbin.org/post"
    $url = "https://foo.com/bar"
    $response = Invoke-RestMethod -Method Post -Uri $url -Body $jsonPayload -ContentType "application/json"
    Write-Host "Log data successfully sent to the server."
} catch {
    Write-Host "Error occurred: $_"
}
