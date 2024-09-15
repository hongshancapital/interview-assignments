$url = "https://foo.com/bar"

$filePath = "G:\download\data\data"
$fileData = Get-Content $filePath
[System.Collections.ArrayList]$dataList = @()
[System.Collections.ArrayList]$dataListUpload = @()

for($i=0; $i -lt $fileData.Length;$i++){
    $splits = $fileData[$i] -split ": ",2
    #脏数据剔除
    if($splits.Count -lt 2){ 
        continue
    }
    $strFirstHarf = $splits[0]
    #description分离
    $strSecondHarf = $splits[1]
    $strFirstHarfSplits = $strFirstHarf -split " ",6
    $deviceName = $strFirstHarfSplits[3]
    #只匹配第一个ID和name
    $processName = (([regex]"(\D+)(?=\[(.*)\])").Matches($strFirstHarfSplits[4]).Value |Out-String).Trim(" .-`t`n`r") 
    $processID = (([regex]"(?<=\D+\[)(\d+)(?=\])").Matches($strFirstHarfSplits[4]).Value |Out-String).Trim(" .-`t`n`r") 
    $description = $strSecondHarf
    $timeWindowInt = ($strFirstHarfSplits[2] -split ":")[0] -as [int]
    #大于23小时的数据处理
    if($timeWindowInt -gt 23){
        $timeWindow = "{0:d2}00-0000" -f $timeWindowInt
    }else{
        $timeWindow = "{0:d2}00-{1:d2}00" -f $timeWindowInt,($timeWindowInt+1)
    }
    #先设置默认值，方便分组统计
    $numberOfOccurrence = 1
    #构建select-object
    $data = "myData"| Select-Object -Property deviceName,processId,processName,description,timeWindow,numberOfOccurrence
    $data.deviceName = $deviceName
    $data.processId = $processId
    $data.processName = $processName
    $data.description = $description
    $data.timeWindow = $timeWindow
    $data.numberOfOccurrence = $numberOfOccurrence
    
    $dataList.Add($data)
}
#分组操作
$myGroup = $dataList | Group-Object -Property deviceName,processId,processName,description,timeWindow
foreach($item in $myGroup){
   $dataTmp = @{"deviceName" = $item.Group[0].deviceName;"processId" = $item.Group[0].processId;"processName" = $item.Group[0].processName;"description" = $item.Group[0].description;"timeWindow" = $item.Group[0].timeWindow;"numberOfOccurrence" = $item.Count}
   $dataListUpload.Add($dataTmp)
}
#转json
$dataJson = $dataListUpload | ConvertTo-Json
#上传
Invoke-WebRequest -UseBasicParsing $url -ContentType "application/json" -Method POST -Body $dataJson

