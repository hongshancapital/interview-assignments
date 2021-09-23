$log = New-Object System.IO.StreamReader("C:\Users\YZY\Desktop\interview_data_set")
function x{
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
$lin = x
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
$result= $Awe | Group-Object d,timeWindow|
    ForEach-Object {
        $item = $_.Group[0] 
        $item | Add-Member -NotePropertyName 'numberOfOccurrence' -NotePropertyValue $_.Count
        $item 
    }| Select-Object * -Exclude d | ConvertTo-Json
#write-host $result
$url = 'https://foo.com/bar'
curl -uri $url -Method POST -Body $result
