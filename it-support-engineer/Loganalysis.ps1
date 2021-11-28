$Result = @()
$Record = @{}
$Uri = “https://foo.com/bar”
#Filter the error events in the log
$log = Get-Content .\interview_data_set | ? {($_ -like "*error*")} |
Select @{name="Time"; expr={$_.Substring(7, 2)}},
       @{name="Process"; expr={$_.Substring(($_.IndexOf("(") +1),($_.lastIndexOf(")") - 1 - $_.IndexOf("(")))}},
       @{name="ProcessID";expr={""}},
       @{name="Description"; expr={$_.Substring((($_.lastIndexOf("): ")) + 3))}}
$log = $log | Where-Object {$_.Process -notlike ""}
#Pick up the session IDs
foreach ($event in $log)
 {
  $event.Time = [int]$event.Time
  $event.Process = $event.Process -replace "\]", ""
  $event.ProcessID = $event.Process.Substring($event.Process.length - 5,5)
  $event.Process = $event.Process.Substring(0,$event.Process.Length - 6)
  } 
#Create a new object array for the report
 For ($Time=0;$Time -lt 24;$Time=$Time+1)
{
  $Reports = $log | Where-Object {$_.Time -eq $Time}|Select-Object | Group-Object Process
  Foreach ($Report in $Reports)
   {
    $Record = @{
              timeWindow=$Time;
              deviceName="BBAOMACBOOKAIR2";
              processName=$Report.Name;
              processID=$Report.Group.ProcessID;
              description=$Report.Group.Description |Sort -Unique;
              numberOfOccurrence=$Report.Count
             }
   $RecordObject = [pscustomobject]$Record
   $Result += $RecordObject
   }
}
$Final = $Result | Select-Object -Property timeWindow,deviceName,processName,processID,numberOfOccurrence,description
$FinalJson = $Final | ConvertTo-Json -Depth 4
curl -uri $Uri -Method POST -Body $FinalJson