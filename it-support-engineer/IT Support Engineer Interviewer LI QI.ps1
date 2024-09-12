[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
$json = @"
{
    "deviceName" : "BBAOMACBOOKAIR2",
    "processId(for each time)" : {
        "12680",
        "53047",
        "54563",
        "55565"
        },
    "processName" : "remindd"
    "description" : {
        "Class REMCDChangeTrackingState is implemented in both /System/Library/PrivateFrameworks/ReminderKit.framework/Versions/A/ReminderKit and /usr/libexec/remindd. One of the two will be used. Which one is undefined.",
        "Class REMDAAccountInfo is implemented in both /System/Library/PrivateFrameworks/CDDataAccess.framework/Frameworks/DACalDAV.framework/Versions/A/DACalDAV (0x7fff922f17d8) and /System/Library/PrivateFrameworks/CDDataAccess.framework/Frameworks/DACalDAV.framework/DADaemonCalDAV.bundle/Contents/MacOS/DADaemonCalDAV (0x1042c9930). One of the two will be used. Which one is undefined.",
        "Failed to bootstrap path: path = /usr/libexec/remindd, error = 108: Invalid path"
        },
    "timeWindow" : {
        "0000-0100",
        "0800-0900",
        "1500-1600",
        "2200-2300"
        },
    "numberOfOccurrence" : "6 hours one time"
}
"@ | ConvertTo-Json
Invoke-WebRequest -UseBasicParsing -Uri https://foo.com/bar -ContentType "application/json" -body $json -Method Post