function GetTime($time_str)
{
 $segment = $time_str -split ":"
 $start = [int]$segment[0]
 $end = $start + 1
 $times = ""
 if($end -lt 10)
 {
     $times = "0" + $start + "00-" + "0" + $end + "00"
 }
    elseif($end -eq 10)
 {
     $times = "0" + $start + "00-" + $end + "00"
 }
 else
 {
     $times = $start.ToString() + "00-" + $end + "00"
 }
 return $times
}

[System.Collections.ArrayList]$list_all =@{}
[System.Collections.Generic.Dictionary[String,Object]]$dict_all = @{}

function proprocess($line)
{
 $curline = $line.Trim(".-`t`n`r")
 $parts = -split $curline
 $time = $parts[2]
 $deviceName = $parts[3]
 $otherinfo = $parts[4..$parts.Length]
 $processinfo = $otherinfo -join(" ")
 $processinfo_tmp = $processinfo.Trim(".-`t`n`r") -split ":"
 $processinfo = -split $processinfo_tmp[0].Trim(".-`t`n`r")
 $processinfo = $processinfo[-1]
 $errorinfo = $processinfo_tmp[1..$processinfo_tmp.Length] -join(" ")
 $processinfo = $processinfo -replace "\(", "" -replace "\)", ""

 $matchObj = $processinfo -match "(.*)\[(\d+)\]"
 if ($matchObj)
 {
  $processName = $Matches[1]
  $processId = $Matches[2]
  $times = GetTime $time
  [System.Collections.Generic.Dictionary[String,Object]]$mDic = @{}
  $mDic["timeWindow"] = $times
  $mDic["deviceName"] = $deviceName
  $mDic["processId"] = $processId
  $mDic["processName"] = $processName
  $mDic["description"] = $errorinfo
  $index=$list_all.Add($mDic)
  if(-not $dict_all[$times])
  {
   $dict_all[$times] = @{$errorinfo=1}
  }
  else
  {
   $errorinfo_dict = $dict_all[$times]
   if(-not $errorinfo_dict[$errorinfo])
   {
    $errorinfo_dict[$errorinfo] = 1
   }
   else
   {
    $errorinfo_dict[$errorinfo] = $errorinfo_dict[$errorinfo]+1
   }
  }

  #$message = "{" + '"timeWindow"'+':'+'"'+$times+'"'+','+'"deviceName"'+':'+'"'+$deviceName+
  #           '"'+','+'processId'+':'+'"'+$processId+','+'"'+','+'processName'+':'+'"'+$processName+','+'"'+
  #     'description'+'"'+':'+'"'+$errorinfo+'"'+'}'
  #
  #Write-Host $message
 }
}

echo "First powershell"
$data = Get-Content $args[0]
Write-Host "$args"
$preLine = ""
for ($i=0; $i -lt $data.Length; $i++ )
{
 $curline = $data[$i].Trim(".-`t`n`r")
    $parts = -split $data[$i]
 if (($parts[-1][-1] -eq ":") -and $parts[0] -eq "May")
 {
  if ($preLine -ne "")
  {proprocess $preLine.Trim(".-`t`n`r")}
  $preLine = $curline
 }
 elseif ($parts[0] -ne "May")
 {
  $preLine = $preLine + " " + $curline
  $preLine = $preLine.Trim(".-`t`n`r")

 }
 elseif(($parts[-1][-1] -ne ":") -and ($parts[0] -eq "May"))
 {
  if ($preLine.Trim(".-`t`n`r") -ne "")
  {
   proprocess $preLine.Trim(".-`t`n`r")
  }
  $preLine=""
  proprocess $curline
 }
}

$url = "https://foo.com/bar"
$headers = @{}
$headers["Content-Type"]="application/json;charset=UTF-8"

foreach($line_dict in $list_all)
{

 $time = $line_dict["timeWindow"]
 $errorinfo = $line_dict["description"]
 $deviceName = $line_dict["deviceName"]
 $processId = $line_dict["processId"]
 $processName = $line_dict["processName"]
 if($dict_all[$time])
 {
  $error_dict_count = $dict_all[$time]
  if($error_dict_count[$errorinfo])
  {
   $num = $error_dict_count[$errorinfo]
   $line_dict["numberOfOccurrence"] = $num
   $message = "{" + '"timeWindow"'+':'+'"'+$time+'"'+','+'"deviceName"'+':'+'"'+$deviceName+
             '"'+','+'processId'+':'+'"'+$processId+','+'"'+','+'processName'+':'+'"'+$processName+','+'"'+
       'description'+'"'+':'+'"'+$errorinfo+'"'+','+'"'+"numberOfOccurrence"+'"'+":"+'"'+$num+'"'+'}'

      Write-Host $message
   $JSON = $line_dict | convertto-json

   $curl_result = curl -URi $url -Method Post -Headers $headers -UseBasicParsing
   Write-Host $curl_result["status_code"]
   Write-Host $curl_result["text"]
  }
 }
}
