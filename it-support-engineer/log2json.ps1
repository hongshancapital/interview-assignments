# ����ϵͳ��־
$logFilePath = "H:\AI\Json\data"  # ��־�ļ�·��
$logData = Get-Content -Path $logFilePath -Raw

# ��ʼ��ͳ������
$logs = $logData -split "`n" | ForEach-Object {
    $logEntry = $_ -split " "
    $description = if ($logEntry.Length -gt 6) { $logEntry[6..($logEntry.Length - 1)] -join " " } else { "" }
    [PSCustomObject]@{
        'Timestamp'    = $logEntry[0] + " " + $logEntry[1]
        'DeviceName'   = $logEntry[3]
        'ProcessId'    = if ($logEntry[4]) { $logEntry[4].Trim('[]') } else { "" }
        'ProcessName'  = if ($logEntry[5]) { $logEntry[5].Trim('():') } else { "" }
        'Description'  = $description
        'TimeWindow'   = $logEntry[2]
    }
}

# ͳ�Ʒ�������
$logStats = $logs | Group-Object -Property 'Timestamp', 'DeviceName', 'ProcessId', 'ProcessName', 'Description' | ForEach-Object {
    [PSCustomObject]@{
       # 'Timestamp'           = $_.Group[0].Timestamp
        'DeviceName'          = $_.Group[0].DeviceName
        'ProcessId'           = $_.Group[0].ProcessId
        'ProcessName'         = $_.Group[0].ProcessName
        'Description'         = $_.Group[0].Description
        'TimeWindow'          = $_.Group[0].TimeWindow
        'NumberOfOccurrence'  = $_.Count
       # 'TimeWindow'          = $_.Group[0].Timestamp.Split(' ')[2] + "-" + $_.Group[0].Timestamp.Split(' ')[3]
        
    }
}

# ת��ΪJSON��ʽ
$jsonData = $logStats | ConvertTo-Json

# ����JSON�ļ�
$jsonFile = "logs.json"
$jsonData | Out-File -FilePath $jsonFile


# ���� POST �����ϴ�����
$url = "https://foo.com/bar"  # ������ URL
$headers = @{ "Content-Type" = "application/json" }
$response = Invoke-RestMethod -Uri $url -Method Post -Body $jsonData -Headers $headers

# �����Ӧ״̬��
if ($response.StatusCode -eq 200) {
    Write-Host "�ϴ��ɹ���"
} else {
    Write-Host "�ϴ�ʧ�ܣ�" $response.StatusCode
}
#>

Write-Host "��־��������ѷ��ͣ�������ΪjsonFile"