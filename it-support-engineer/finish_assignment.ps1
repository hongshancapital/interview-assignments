param($filename)


####################################################
# Read logfile, make necessary fields 
# and get statis group by multi-fields
####################################################
$groupBy = @{}
Get-Content $filename |  where {$_ -match "^[^\t]"} | % {
    $line = $_ -replace '"','QUOTA' # Remove double quota mark
    $line = $line -replace "'","" # Remove single quosta mark

    $line_a =  $line -split " "  

    $hour = ($line_a[2] -split ":")[0]
    $deviceName = $line_a[3]
    $processName = ($line_a[4] -split "\[")[0]
    $processId = (($line_a[4] -split "\[")[1] -split "\]")[0]
    
    $description = ""
    For ($i = 5; $i -lt $line_a.Length; $i++) {
        $description += $line_a[$i]
    }

    $output = $hour + "|"
    $output += $deviceName + "|"
    $output += $processName + "|"
    $output += $processId + "|"
    $output += $description

    $groupBy[$output] = $groupBy[$output] + 1
}

###################################################
#   Prepare Json records as Arraylist
###################################################
$records_array = New-Object -TypeName System.Collections.ArrayList

foreach($i in $groupBy.Keys){
    $rec_array = $i -split '\|'
    $rec = '{' 
    $rec += '"deviceName" : "' + $rec_array[1] + '", '
    $rec += '"processId" : "' + $rec_array[3] + '", '
    $rec += '"processName" : "' + $rec_array[2] + '", '
    $rec += '"description" : "' + $rec_array[4] + '", '
    $rec += '"timeWindow" : "' + ('{0:d2}' -f $rec_array[0]) + "00-" + ('{0:d2}' -f ([System.Convert]::ToInt16($rec_array[0]) + 1)) + '00", '
    $rec += '"numberOfOccurrence" : "' + $groupBy[$i]
    $rec += '"}'

    $tmp = $records_array.Add($rec)
}

###################################################
#   Make Json data
###################################################
$jsontable = ('{}' | ConvertFrom-Json )
$jsontable | Add-Member "records" ($records_array | ConvertFrom-Json)

$json_text = ($jsontable | ConvertTo-Json)

###################################################
#   Post the Json data
###################################################
$url = "https://foo.com/bar"

Invoke-WebRequest -UseBasicParsing $url -SkipCertificateCheck -ContentType "application/json" -Method POST -Body $json_text
