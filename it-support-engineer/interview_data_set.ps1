# Set-ExecutionPolicy Unrestricted
# $PSDefaultParameterValues['Out-File:Encoding'] = 'utf8'
$logPath = '.\interview_data_set'

$json_list = New-Object System.Collections.ArrayList
$json_str_list = New-Object System.Collections.ArrayList
$count_list = New-Object System.Collections.ArrayList

$url = "https://foo.com/bar"

function openFile($lp) {
    $f = Get-Content $lp
    $content_all = ''

    foreach ($item in $f){ 
        # echo $item
        $content = $item
        if($content -match 'last message repeated 1 time'){
            $content = $content_all
        }
        if($content -match '^May'){
            pushJson $content_all
            $content_all = ''
        }
        $content_all += $content    
        }  
    pushJson $content_all
}

function pushJson($ca) {
    if ($ca -ne '') {
        $pattern = '(.*?)(\d+:\d+:\d+)(\s\w+\s)(.+\[\d+\].*?:)'
        $result = [regex]::matches($ca , $pattern)
        # echo $result.groups
        $deviceName = $result.groups[3].value.Trim(" ")
        $processId = $result.groups[4].value.split("[")[1].split(']')[0].Trim(" ")
        $processName = $result.groups[4].value.split('[')[0].Trim(" ")
        $description = [regex]::split($ca , $pattern)[-1].Trim(" ")
        $hour_1 = $result.groups[2].value.split(':')[0]

        if ([int]($result.groups[2].value.split(':')[0]) -lt 9) {
            $hour_2 = '0'+[string]([int]($result.groups[2].value.split(':')[0]) + 1)
        }elseif ([int]($result.groups[2].value.split(':')[0]) -lt 23) {
            $hour_2 = [string]([int]($result.groups[2].value.split(':')[0]) + 1)
        }else {
            $hour_2 = '24'
        }
        $json_dict = [PSCustomObject][Ordered]@{deviceName=$deviceName; processId=$processId; processName=$processName; description=$description; timeWindow=$hour_1 + '00-' + $hour_2 + '00'; numberOfOccurrence=0}
        # echo $json_dict
        $json_dict_str = $json_dict | Out-String

        if ($json_dict_str -in $json_str_list) {
            $index = [array]::IndexOf($json_str_list,$json_dict_str)
            $count_list[$index] +=1
        }else {
            $json_str_list.Add($json_dict_str)
            $index = [array]::IndexOf($json_str_list,$json_dict_str)
            $count_list.insert($index,1)
            $json_list.insert($index,$json_dict)
        }
    }
}
    
function writeJson($jl,$cl) {
    for($x=0; $x -lt $jl.count; $x++) {
        $jl[$x].numberOfOccurrence = $cl[$x]
        $jl[$x].description.Replace("'","\u0027") 
        if ($jl[$x].description -match 'Can') {echo $jl[$x]}
    }
    # $jl | ConvertTo-Json | %{$_.Replace("\u0027","'") } | out-file .\interview_data_set1.json
    $body = $jl | ConvertTo-Json | %{$_.Replace("\u0027","'") }
    $Response=Invoke-WebRequest -Uri $url -Method POST -ContentType "application/json" -Body $body
    echo $Response
}

openFile $logPath
writeJson $json_list $count_list