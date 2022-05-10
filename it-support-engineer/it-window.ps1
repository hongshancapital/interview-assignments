
$content = Get-Content -Path "interview_data_set" | select-string error
foreach($line in $content) {
    
             $str1= $line -split " ", 7
             $str2= $line -split '[][]'
             $str3= $str1[5] -split '[.\[]'                       
             $pname=$str3[3]                              #进程名称
            
             if ( !${$pname} )
                  {  ${$pname}++                        
                    $firsttime=$str1[2]                         #记录第一次发生错误的时间
                    $latesttime=$str1[2] 
                  }
             else  
                 {   ${$pname}++
                    $latesttime=$str1[2]                     #记录最后一次发生错误的时间
                                         
                 }
        
            $duration= New-TimeSpan -Start $firsttime -End  $latesttime  #第一次发生错误到最后一次的时间间隔
             $t=$duration -split ":"
             $tt=[int]$t[0]
             [float]$numberOfOccurrence=${$pname}/ ($tt+1)                 #平均每小时发生错误的次数
         
            write-host '{'
            write-host '"deviceName":'    '"'$str1[3]'",'
            write-host '"processId":'          '"'$str2[3]'",'
            write-host '"processName":'    '"'$str3[3]'",'
             write-host '"description:"'        '"'$str1[6]'",'
             write-host '"timeWindow":'     '"'$str1[2]'",'
             write-host '"numberOfOccurrence":'    '"'$numberOfOccurrence  '",'    
            write-host '}' 
           
 }

 