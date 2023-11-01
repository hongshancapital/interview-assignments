1. 设备名称: (deviceName)
共2个：
bogon
BBAOMACBOOKAIR2

		awk '{print $4}' interview_data_set  | sort | uniq -c

1. 错误的进程号码: (processId)
共150个:
12523,12680,12736,12809,12862,13012,13053,13095,24374,39523,52359,52389,52473,52534,52548,52624,52710,52834,52954,53047,53053,53138,53150,53157,53328,53342,53375,53560,53945,54131,54153,54165,54171,54178,54187,54266,54273,54284,54285,54286,54287,54288,54289,54290,54292,54293,54297,54309,54317,54464,54524,54529,54532,54533,54534,54535,54536,54537,54538,54539,54553,54559,54560,54563,54564,54639,54653,54672,54755,54762,54779,54781,54783,54785,54787,54802,54840,54847,54848,54916,55072,55172,55188,55227,55233,55241,55261,55321,55336,55367,55452,55454,55460,55461,55462,55463,55464,55465,55468,55469,55470,55471,55472,55565,55620,55621,55627,55628,55629,55630,55631,55632,55633,55634,55635,55636,55642,55673,55675,55706,55714,55720,55743,55751,55754,55762,55770,55777,55780,55794,55797,55800,55802,55836,55896,56059,56060,56061,56062,56063,56064,56065,56068,56079,56080,56081,56149,56246,56260,56311

    grep -r pid interview_data_set | grep error | grep -oP '(?<=\().*(?=\))' | awk -F'.' '{print $NF}' | sort | uniq

1. 进程/服务名称: (processName)
共15个
activateSettings
DesktopServicesHelper
diskutil
docker-credenti
fdesetup
fud
installer
mdmclient
networksetup
photoanalysisd
remindd
security_authtr
signpost_reporter
system_profiler
walletAvailabil

	    grep -r pid interview_data_set | grep error | grep -oP '(?<=\().*(?=\))' | awk -F'.' '{print $(NF-1)}' | sort | uniq 

1. 错误的原因（描述）(description)

服务的错误原因：

activateSettings : Failed to bootstrap path: path = 		/System/Library/PrivateFrameworks/SystemAdministration.framework/Versions/A/Resources/activateSettings, error = 2: No such file or directory
DesktopServicesHelper : Failed to bootstrap path: path = 
....


	    grep -r pid interview_data_set | grep -oP '(?<=\().*' | awk -F')' '{split($1,a,".");print a[7], $2}'  | sort  | uniq


5. 发生的时间（小时级），例如 0100-0200，0300-0400, (timeWindow)

	    grep -r pid interview_data_set | grep error | sort -k 3 -n


1. 在小时级别内发生的次数 (numberOfOccurrence)

发生错误计时：
'May 13 00-01' 3
'May 13 01-02' 2
'May 13 02-03' 5
'May 13 03-04' 2
'May 13 04-05' 3
'May 13 05-07' 2
'May 13 07-08' 1
'May 13 08-09' 4
'May 13 09-11' 5
'May 13 11-12' 1
'May 13 12-13' 1
'May 13 13-14' 3
'May 13 14-15' 17
'May 13 15-16' 19
'May 13 16-17' 12
'May 13 17-18' 3
'May 13 18-20' 4
'May 13 20-21' 1
'May 13 21-22' 1
'May 13 22-23' 57
'May 13 23-00' 4
  
    grep -r pid interview_data_set | grep error | sed 's/:[0-9]\{2,\}.*(/ /g' | sort  | uniq  | awk '{sum=0;time[$1" "$2" "$3]+=1;next}END{for (i in time) print  i, time[i]}' | sort -k 3 -n 
