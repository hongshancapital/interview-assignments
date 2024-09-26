& "C:\Program Files\7-Zip\7z.exe" x C:\powershell\interview_data_set.gz -r -y 
#decompress interview_data_set.gz with 7-zip
$log = New-Object System.IO.StreamReader("C:\powershell\interview_data_set")
function rawdata{
while($linex = $log.ReadLine()){
switch -Regex ("$linex") {
    '^\S' { 
      if ($line) {$line 
      }
       $line = $_
    }
    default { 
      $line += ' ' + $_.TrimStart()
    }}
}} 
#create function rawdata on reformat the interview_data_set log
$lin = rawdata
[array]$Awe=foreach($lin in $lin)
{
    $arr=@(foreach($i in $i+1){$lin.Split(' ',7)})
    $arr_error=@(foreach($a in $a+1){$lin.Split(':',5)})
    if($arr_error[4]){
            $f = [string]::format("{0:d2}",[int]::Parse($arr[2].Split(':')[0])+1)
            $pattern= $arr[4] -match'\d{1,6}'
             $Object=[PSCustomObject]@{
             deviceName = $arr[3]
             processId = $matches.Item(0)
             processName = $arr[4] 
             description = $arr[6]
             d = $arr_error[4]
             timeWindow = $arr[2].Split(':')[0] +"00-" + "$f" + "00"
}}
    else{continue}
    $Object
    }
$result_json= $Awe | Group-Object d,timeWindow|
    ForEach-Object {
        $item = $_.Group[0] 
        $item | Add-Member -NotePropertyName 'numberOfOccurrence' -NotePropertyValue $_.Count
        $item 
    }| Select-Object * -Exclude d | ConvertTo-Json -depth 100 | Out-File "C:\powershell\interview_data.json"
# converting format and output as json 
If ((Get-Content "C:\powershell\interview_data.json") -ne $Null) {
$weburl = "https://foo.com/bar"
Invoke-WebRequest -UseBasicParsing $weburl  -ContentType "application/json" -Method POST -Body $result_json  
} 
#upload the jason file (if no empty) in https://foo.com/bar  
