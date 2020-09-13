Get-Content -Path c:\1\Helpdesk_interview_data_set |Format-List |Select-Object deviceName, processId, processName, description, timewindows, numberOf Occurrence| select-string 'error', 'abnormal', 'Failed'  | Sort-Object |Get-Unique| ConvertTo-Json |out-file err.json
curl -d err.json -u user:password https://foo.com/bar
