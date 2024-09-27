<#
    将输入的原始日志时间格式转换为timeWindow格式，并返回一个timeWindow的字符串
#>
Function ConvertTo-TimeWindow
{
    [CmdletBinding()]
    Param
    (
        [Parameter(Mandatory = $true, Position = 0)]$TimeString
    )
    [int]$Hour = ($TimeString -split":")[0]

    switch ($Hour)
    {
        {$_ -lt 9} {[string]$StartTimeWindow = '0' + $Hour; [string]$EndTimeWindow = '0' + ($Hour + 1);[string]$TimeWindowString = $StartTimeWindow +"00" + "-" + $EndTimeWindow + "00";return $TimeWindowString}
        {$_ -eq 9} {[string]$StartTimeWindow = '0' + $Hour; [string]$TimeWindowString = $StartTimeWindow +"00" + "-" + "1000";return $TimeWindowString}
        {$_ -ge 10} {[string]$StartTimeWindow = $Hour;[string]$EndTimeWindow = $Hour + 1;[string]$TimeWindowString = $StartTimeWindow +"00"+ "-" + $EndTimeWindow + "00";return $TimeWindowString}
    }

}

<#
    将原始日志文件根据条件筛选后进行对应字符串处理，并格式化timeWindow日期，返回一个数组对象
#>
Function ConvertTo-LogFile
{
    [CmdletBinding()]
    Param
    (
        [Parameter(Mandatory = $true, Position = 0)]$LogPath
    )
    $logs = Get-Content $LogPath
    $Type = @()
    $LogCount = $logs.Count
    for ($i = 0; $i -lt $LogCount ; $i++)
    { 
        switch ($logs[$i])
        {
            {($_ -match '.*\[\d.*\]') -and ($_ -notmatch 'Notice\:') -and ($_ -notmatch 'WARNING\:') -and ($_ -notmatch 'failed\:')} {
                $Type += New-Object -TypeName Psobject -Property @{
                    "deviceName" = ($_ -split " ",6)[3]
                    "processId" = ($_ -split " ",6)[4].Split("[")[1].Split("]")[0]
                    "processName" = ($_ -split " ",6)[4].Split("[")[0]
                    "description"=  ($_ -split " ",6)[5]
                    "timeWindow" = ConvertTo-TimeWindow -TimeString ($_ -split " ",4)[2].Split(":")[0]
                    }
                }

            {$_ -match 'Configuration Notice\:'} {
                $Type += New-Object -TypeName Psobject -Property @{
                    "deviceName" = ($_ -split " ",6)[3]
                    "processId" = ($_ -split " ",6)[4].Split("[")[1].Split("]")[0]
                    "processName" = ($_ -split " ",6)[4].Split("[")[0]
                    "description"= ($_ -split ":",4)[3] + $logs[$i+1] + $logs[$i+2]
                    "timeWindow" = ConvertTo-TimeWindow -TimeString ($_ -split " ",4)[2].Split(":")[0]
                    }
                }
        
            {($_ -match 'WARNING\:') -or ($_ -match 'failed\:')} {
                $Type += New-Object -TypeName Psobject -Property @{
                    "deviceName" = ($_ -split " ",6)[3]
                    "processId" = ($_ -split ":",8)[2].Split("[")[1].Split("]")[0]
                    "processName" = ($_ -split " ",5)[4].Split(":")[0].Split("[")[0]
                    "description"= ($_ -split ":",4)[3]
                    "timeWindow" = ConvertTo-TimeWindow -TimeString ($_ -split " ",4)[2].Split(":")[0]
                    }
                }
        }
    }
    return $Type
}

<#
    输入原始日志文件及需要提交的网址，最终生成指定Key的Josn文件并上传到服务器�?
#>

Function Submit-PostRequest
{
    [CmdletBinding()]
    Param
    (
        [Parameter(Mandatory = $true, Position = 0)]$FilePath,
        [Parameter(Mandatory = $true, Position = 1)]$Url,
        [switch]$LogCsv 
    )
# 将原始数据按条件进行字符串处理
    $LogFile = ConvertTo-LogFile -LogPath $FilePath

# 将日志按小时级内发生的次数进行处理（去除重复日志信息）
    $JosnReport = @()
    $LogFile | Group-Object -Property  timeWindow, deviceName, processId, processName, description | ForEach-Object{
            $JosnReport += New-Object -TypeName PsObject -Property @{
            "deviceName"  = $_.Group[0].deviceName
            "processId"   = $_.Group[0].processId
            "processName" = $_.Group[0].processName
            "description" = $_.Group[0].description
            "timeWindow"  = $_.Group[0].timeWindow
            "numberOfOccurrence" = $_.Count
        }
    }
# 将数组对象转换为Json格式
    $SubmitJson = $JosnReport | Select-Object -Property deviceName,processId,processName,description,timeWindow,numberOfOccurrence | ConvertTo-Json

# 如果有LogCsv开关参数打开，输出CSV格式日志到本地
if ($LogCsv) {
    $JosnReport | Export-Csv -Path .\Interview.csv -NoTypeInformation -Encoding UTF8
}

# Windows10、11 默认Powershell版本5.1，用于不受信任证书的运行环境

add-type @"
using System.Net;
using System.Security.Cryptography.X509Certificates;
public class TrustAllCertsPolicy : ICertificatePolicy {
    public bool CheckValidationResult(
        ServicePoint srvPoint, X509Certificate certificate,
        WebRequest request, int certificateProblem) {
            return true;
        }
 }
"@
[System.Net.ServicePointManager]::CertificatePolicy = New-Object TrustAllCertsPolicy

    Invoke-WebRequest -ContentType "application/json" -Method POST -Body $SubmitJson -Uri $url
}


<#
    执行主函数，输入原始日志文件及需要提交的网址,需要本地输出可以添加“-Logcsv”开关参数
#>
Submit-PostRequest -FilePath "D:\interview_data_set" -Url "https://foo.com/bar"
