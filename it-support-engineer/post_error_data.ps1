<#
由于powershell不是十分熟悉，先使用了python写了脚本，故鉴于不重复做任务的原则，在python脚本
中降error写入文件，powershell直接读取文件内容post至指定url
#>
$error_json = Get-Content .\error.json
Invoke-WebRequest -UseBasicParsing https://foo.com/bar -ContentType "application/json" -Method POST -Body $error_json -SkipCertificateCheck True