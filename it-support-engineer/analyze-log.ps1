# read $data from original log file
$data = Get-Content ./interview_data_set

# format $data before counting
$out = @()
for ( $i=0; $i -lt $data.count; $i++ ) {
    # handle lines not start with datetime
    if($data[$i] -notmatch '^May') {
        $out[-1] += $data[$i]
    } else {     
        # handel repeated lines
        if($data[$i].IndexOF('---') -gt -1) { 
            $out += $data[$i-1] 
        }
        else { $out += $data[$i] }   
    }
}

# create output table and loop capture $data
$outTable = @()
for ( $j=0; $j -lt $out.count; $j++ ) {
    # prepare regex
    $patterns = ([regex]'\s(\d{2}):\S+\s(\w+)\s([^\[]+)\[(\d+)\]:?\s(.+)').Match($out[$j])
    # capture required field
    $windowStart = $patterns.groups[1].value
    $windowEnd = ([string]([int]$windowStart + 1)).PadLeft(2, '0')
    $timeWindow = $windowStart + ':00-' + $windowEnd + ':00'
    $deviceName = $patterns.groups[2].value
    $processName = $patterns.groups[3].value
    $processId = $patterns.groups[4].value
    $description = $patterns.groups[5].value
    # insert entry to output table
    $outTable += [pscustomobject]@{
        timeWindow = $timeWindow;
        deviceName = $deviceName;
        processName = $processName;
        processId = $processId;
        description = $description
    }
}

# prepary query
$c = @{ e = "count"; n='numberOfOccurrence' } 
$t = @{ e={ $_.group[0].timewindow}; n='timeWindow' }
$d = @{ e={ $_.group[0].deviceName}; n='deviceName' }
$p = @{ e={ $_.group[0].processName}; n='processName' }
$prid = @{ e={ $_.group[0].processId}; n='processId' }
$desc = @{ e={ $_.group[0].description}; n='description' }

# count by hour per device per process
$countPerDevice = $outTable | Group timewindow,devicename,processname 
                            | Select $c, $t, $d, $p, $prid, $desc 
                            | Sort-Object @{e='timeWindow'}, @{e='numberOfOccurrence'; descending=$true}

# count by hour
$hourlyCount = $outTable | Group-Object timeWindow | Select-Object name, count

# create final report and convert to Json
$report = [ordered]@{ 
    numberOfOccurrenceHourly=$hourlyCount; 
    numberOfOccurrenceHourlyByDevice=$countPerDevice 
}
$reportJson = $report | convertto-json -depth 5 | Out-File ps-out.json

# send result to https://foo.com/bar, comment out due to fake server
# Invoke-RestMethod -Method 'Post' -Uri 'https://foo.com/bar' -Body $reportJson