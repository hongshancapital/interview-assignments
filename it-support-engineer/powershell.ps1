$logFile = '.\interview_data_set1'
$dataDict = @{}
$timeWindow = @{
	"00"="0000-0100";"01"="0100-0200";"02"="0200-0300";"03"="0300-0400";
        "04"="0400-0500";"05"="0500-0600";"06"="0600-0700";"07"="0700-0800";
        "08"="0800-0900";"09"="0900-1000";"10"="1000-1100";"11"="1100-1200";
        "12"="1200-1300";"13"="1300-1400";"14"="1400-1500";"15"="1500-1600";
        "16"="1600-1700";"17"="1700-1800";"18"="1800-1900";"19"="1900-2000";
        "20"="2000-2100";"21"="2100-2200";"22"="2200-2300";"23"="2300-0000";
}


$md5 = new-object -TypeName System.Security.Cryptography.MD5CryptoServiceProvider
$utf8 = new-object -TypeName System.Text.UTF8Encoding


function ReadLog() {
  $loglines = Get-Content $logFile
  foreach ($line in $loglines) {
    if (($line -match 'last message repeated 1 time') -or ($line -notmatch "^May")) {
      continue
  } else {
      analyLog $line
  }

  }
}


function analyLog($line) {
    $reg = ".+\s(\d+:\d+:\d+?)\s(.+?)\s(.+?):\s(.+)$"
    $mat = [regex]::match($line,$reg)
    $deviceName = $mat.groups[2].Value.Trim()
    $description = $mat.groups[4].Value.Trim()
    $processName = $mat.groups[3].Value.split("[")[0].Trim()
    $processId = $mat.groups[3].Value.split("[")[1].split("]")[0].Trim()
    $dateTime = $timeWindow[$mat.groups[1].Value.split(":")[0]]
    $idx_k = @($dateTime,$processName,$processId)
#MD5索引
    $idx = [System.BitConverter]::ToString($md5.ComputeHash($utf8.GetBytes($idx_k)))
#    echo "------------------------------"
#    echo $idx
    if ($dataDict.ContainsKey($idx)) {
        $dataDict[$idx]["numberOfOccurrence"] += 1
    } else {
        $dataDict.Add($idx,@{"deviceName"=$deviceName;"processId"=$processId;"processName"=$processName;"description"=$description;"timeWindow"=$dateTime;":numberOfOccurrence"=1})
    }
#    echo $dataDict.values|ConvertTo-Json
}

Readlog
#Post 数据
Invoke-WebRequest -UseBasicParsing -SkipCertificateCheck -Uri https://foo.com/bar -Method POST -Body $dataDict.values|ConvertTo-Json 
