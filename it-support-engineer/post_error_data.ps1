<#
����powershell����ʮ����Ϥ����ʹ����pythonд�˽ű����ʼ��ڲ��ظ��������ԭ����python�ű�
�н�errorд���ļ���powershellֱ�Ӷ�ȡ�ļ�����post��ָ��url
#>
$error_json = Get-Content .\error.json
Invoke-WebRequest -UseBasicParsing https://foo.com/bar -ContentType "application/json" -Method POST -Body $error_json -SkipCertificateCheck True