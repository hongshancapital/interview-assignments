#!/bin/bash
gunzip interview_data_set.gz
cat interview_data_set | grep -E "error|invalid|failed" | grep com > mac-service.log
cat interview_data_set | grep -E "error|invalid|failed" | grep -v com > no-mac-service.log

#微软相关产品日志
cat no-mac-service.log | grep Microsoft > no-mac-service-MS.log
sed -i '/Microsoft/d' no-mac-service.log

cat no-mac-service.log | grep Teams >> no-mac-service-MS.log
sed -i '/Teams/d' no-mac-service.log


Mac_Service_processName_Second=($(cat mac-service.log | awk '{print $6}' | tr -d "[0-9]" | cut -d ")" -f1 | cut -d "(" -f2 | sort -n | uniq))
for ((i=0;i<${#Mac_Service_processName_Second[*]};i++))
do
        #获取设备名字，因为管道符的关系，采用了读取文件的方式传递参数,以下同理
        cat mac-service.log | grep ${Mac_Service_processName_Second[$i]} | awk '{print $4}' | sort -n | uniq > Mac_Service_deviceName
        h=0;while read a;do Mac_Service_deviceName[$h]=$a;((h++));done < Mac_Service_deviceName
        rm -rf  Mac_Service_deviceName

        cat mac-service.log | grep ${Mac_Service_processName_Second[$i]} | awk '{print $5}' | sort -n | cut -d "[" -f1 |sort -n| uniq > Mac_Service_processName_First
        h=0;while read a;do Mac_Service_processName_First[$h]=$a;((h++));done < Mac_Service_processName_First
        rm -rf Mac_Service_processName_First

        IFS=$'\n'
        cat mac-service.log | grep ${Mac_Service_processName_Second[$i]} | awk '{print $5}' | sort -n | cut -d "[" -f2 | cut -d "]" -f1 | sort -n| uniq > Mac_Service_processId
        h=0;while read a;do Mac_Service_processId[$h]=$a;((h++));done < Mac_Service_processId
        rm -rf Mac_Service_processId

        IFS=$'\n'
        cat mac-service.log | grep ${Mac_Service_processName_Second[$i]} | awk '{for (n=7;n<30;n=n+1)printf $n" ";printf "\n"}'|sed  -e 's/[ ]*$//g'| sort -n | uniq > Mac_Service_Description
        h=0;while read a;do Mac_Service_Description[$h]=$a;((h++));done < Mac_Service_Description
        rm -rf Mac_Service_Description

        echo " {" >> json.${Mac_Service_processName_Second[$i]}
        echo "    \"deviceName\": \"${Mac_Service_deviceName[*]}\"," >> json.${Mac_Service_processName_Second[$i]}
        echo "    \"processId\": \"${Mac_Service_processId[*]}\"," >> json.${Mac_Service_processName_Second[$i]}
        echo "    \"processName\": \"${Mac_Service_processName_First[*]}---${Mac_Service_processName_Second[$i]}\"," >> json.${Mac_Service_processName_Second[$i]}
        echo "    \"description\": \"${Mac_Service_Description[*]}\"," >> json.${Mac_Service_processName_Second[$i]}
        echo "    \"time\": [" >> json.${Mac_Service_processName_Second[$i]}
        for ((hour=0;hour<24;hour++))
        do
                str1="00"
                str2="59"
                str3="0"
                count_list=()
                if [ $hour -le 9 ];then
                        cat mac-service.log | grep ${Mac_Service_processName_Second[$i]} | awk '{print $3}' |grep -E  0$hour:[0-5][0-9]:[0-5][0-9] | uniq  > time_list
                        m=0;while read a;do time_list[$m]=$a;((m++));done<time_list
                else
                        cat mac-service.log | grep ${Mac_Service_processName_Second[$i]} | awk '{print $3}' |grep -E  $hour:[0-5][0-9]:[0-5][0-9] | uniq  > time_list
                        m=0;while read a;do time_list[$m]=$a;((m++));done<time_list
                fi
                rm -rf time_list
                count[$hour]=${#time_list[*]}
                count_list[$hour]=$count
                ##其实这个地方我还是感觉有问题的 因为计算出现次数不能单纯的统计日志中的值
                ##原本我的想法是取一个固定值，大于这个值记为1次，小于这个值可以看做是本身app的重试机制
                ##只是作为一场作业，我没法这么武断决定
                #if [ ${#time_list[*]} -eq 0 ];then
                #       let count=$count
                #       count[$hour]=$count
                        #count[$hour]=1
                        #time_one[$hour]=$hour
                #elif [ ${#time_list[*]} -eq 0 ];then
                                #       let count=$count
                #       count[$hour]=$count
                        #count[$hour]=1
                        #time_one[$hour]=$hour
                #elif [ ${#time_list[*]} -eq 0 ];then
                #       count[$hour]=0
                #else
                #       #time_more[$hour]=$hour
                #       while [ ${#time_list[*]} -gt 1 ]
                #       do
                #               t1=`date -d "${time_list[${#time_list[*]}-1]}" +%s`
                #               t2=`date -d "${time_list[${#time_list[*]}-2]}" +%s`
                #               time=`expr $t1 - $t2`
                #               unset time_list[${#time_list[*]}-1]
                #               if [ $time -ge 120 ];then
                #                       let count=$count+1
                #                       count[$hour]=$count
                #               else
                #                       count[$hour]=$count
                #               fi
                #       done
                echo "          {" >>json.${Mac_Service_processName_Second[$i]}
                if [ $hour -le 9 ];then
                        echo "                  \"timeWindow\" :\"$str3$hour$str1-$str3$hour$str2\"," >> json.${Mac_Service_processName_Second[$i]}
                else
                        echo "                  \"timeWindow\" :\"$hour$str1-$hour$str2\"," >> json.${Mac_Service_processName_Second[$i]}
                fi
                echo "                  \"numberOfOccurrence\": \"${count[$hour]}\"" >> json.${Mac_Service_processName_Second[$i]}
                echo "          }," >> json.${Mac_Service_processName_Second[$i]}
                time_list=()
        done
        unset IFS
        sed -i '$d' json.${Mac_Service_processName_Second[$i]}
        echo "           }" >> json.${Mac_Service_processName_Second[$i]}
        echo "      }" >> json.${Mac_Service_processName_Second[$i]}
        echo " ]" >>  json.${Mac_Service_processName_Second[$i]}
done

No_Mac_Service_processName=($(cat no-mac-service.log  | awk '{print $5,$6,$7} '| cut -d "[" -f1 | sort -n | uniq))
for ((j=0;j<${#No_Mac_Service_processName[*]};j++))
do
        cat no-mac-service.log | grep ${No_Mac_Service_processName[$j]} | awk '{print $4}' | sort -n | uniq > No_Mac_Service_deviceName_Temp
        cat No_Mac_Service_deviceName_Temp |  tr "\n" " "| xargs -0 echo > No_Mac_Service_deviceName
        h=0;while read a;do No_Mac_Service_deviceName[$h]=$a;((h++));done < No_Mac_Service_deviceName
        rm -rf No_Mac_Service_deviceName No_Mac_Service_deviceName_Temp

        IFS=' '
        cat no-mac-service.log | grep ${No_Mac_Service_processName[$j]} | cut -d "]" -f1 | cut -d "[" -f2 | sort -n | uniq  > No_Mac_Service_processId_Temp
        cat No_Mac_Service_processId_Temp |  tr "\n" " "| xargs -0 echo > No_Mac_Service_processId
        h=0;while read a;do No_Mac_Service_processId[$h]=$a;((h++));done < No_Mac_Service_processId
        rm -rf No_Mac_Service_processId No_Mac_Service_processId_Temp

        IFS=$'\n'
        cat no-mac-service.log | grep ${No_Mac_Service_processName[$j]} | awk '{for (n=5;n<30;n=n+1)printf $n" ";printf "\n"}'  | cut -d "]" -f2 | sed 's/^://g' | sed 's/^ //g' | sed  -e 's/[ ]*$//g' | sort -n | uniq> No_Mac_Service_Description_Temp
        cat No_Mac_Service_Description_Temp |  tr "\n" " "| xargs -0 echo > No_Mac_Service_Description
        h=0;while read a;do No_Mac_Service_Description[$h]=$a;((h++));done < No_Mac_Service_Description
        rm -rf No_Mac_Service_Description No_Mac_Service_Description_Temp

        echo " {" >> json.${No_Mac_Service_processName[$j]}
        echo "    \"deviceName\": \"${No_Mac_Service_deviceName[*]}\"," >> json.${No_Mac_Service_processName[$j]}
        echo "    \"processId\": \"${No_Mac_Service_processId[*]}\"," >> json.${No_Mac_Service_processName[$j]}
        echo "    \"processName\": \"${No_Mac_Service_processName[$j]}\"," >> json.${No_Mac_Service_processName[$j]}
        echo "    \"description\": \"${No_Mac_Service_Description[*]}\"," >> json.${No_Mac_Service_processName[$j]}
        echo "    \"time\": [" >> json.${No_Mac_Service_processName[$j]}
                for ((hour=0;hour<24;hour++))
        do
                str1="00"
                str2="59"
                str3="0"
                count_list=()
                if [ $hour -le 9 ];then
                        cat no-mac-service.log | grep ${No_Mac_Service_processName[$j]} | awk '{print $3}' |grep -E  0$hour:[0-5][0-9]:[0-6][0-9] | sort -n | uniq> time_list
                        n=0;while read a;do time_list[$n]=$a;((n++));done<time_list
                else
                        cat no-mac-service.log | grep ${No_Mac_Service_processName[$j]} | awk '{print $3}' |grep -E  $hour:[0-5][0-9]:[0-6][0-9] | sort -n | uniq > time_list
                        n=0;while read a;do time_list[$n]=$a;((n++));done<time_list
                fi
                count[$hour]=${#time_list[*]}
                count_list[$hour]=$count
                rm -rf time_list
                echo "          {" >>json.${No_Mac_Service_processName[$j]}
                if [ $hour -le 9 ];then
                        echo "          \"timeWindow\" :\"$str3$hour$str1-$str3$hour$str2\"," >> json.${No_Mac_Service_processName[$j]}

                else
                        echo "          \"timeWindos\": \"$hour$str1-$hour$str2\"," >> json.${No_Mac_Service_processName[$j]}
                fi
                echo "          \"numberOfOccurrence\": \"${count[$hour]}\"" >> json.${No_Mac_Service_processName[$j]}
                echo "          }," >> json.${No_Mac_Service_processName[$j]}
                time_list=()
        done
        unset IFS
        sed -i '$d' json.${No_Mac_Service_processName[$j]}
        echo "          }" >> json.${No_Mac_Service_processName[$j]}
        echo "      }" >> json.${No_Mac_Service_processName[$j]}
        echo " ]" >>  json.${No_Mac_Service_processName[$j]}
done
IFS=':'
No_Mac_MS_Service_processName=($(cat no-mac-service-MS.log  | awk '{print $5,$6,$7} '| cut -d "[" -f1 | sed ':label;N;s/\n/:/;b label' | sort -n | uniq))
for ((k=0;k<${#No_Mac_MS_Service_processName[*]};k++))
do
        cat no-mac-service-MS.log | grep "${No_Mac_MS_Service_processName[$k]}" | awk '{print $4}' | sort -n | uniq > No_Mac_MS_Service_deviceName
        h=0;while read a;do No_Mac_MS_Service_deviceName[$h]=$a;((h++));done < No_Mac_MS_Service_deviceName
        rm -rf No_Mac_MS_Service_deviceName

        cat no-mac-service-MS.log | grep "${No_Mac_MS_Service_processName[$k]}" | cut -d "]" -f1 | cut -d "[" -f2 | sort -n | uniq  > No_Mac_MS_Service_processId
        No_Mac_MS_Service_processId=($(while read line;do line=$line; echo $line; done < No_Mac_MS_Service_processId))
        rm -rf No_Mac_MS_Service_processId

        IFS=$'\n'
        cat no-mac-service-MS.log | grep "${No_Mac_MS_Service_processName[$k]}" | awk '{for (n=5;n<100;n=n+1)printf $n" ";printf "\n"}' | sed 's/:/;/' | cut -d ";" -f2 | sed 's/^ //g' | sed  -e 's/[ ]*$//g' |sort -n | uniq > No_Mac_MS_Service_Description
        h=0;while read a;do No_Mac_MS_Service_Description[$h]=$a;((h++));done < No_Mac_MS_Service_Description
        rm -rf No_Mac_MS_Service_Description

        echo " {" >> json."${No_Mac_MS_Service_processName[$k]}"
        echo "    \"deviceName\": \"${No_Mac_MS_Service_deviceName[*]}\"," >> json."${No_Mac_MS_Service_processName[$k]}"
        echo "    \"processId\": \"${No_Mac_MS_Service_processId[*]}\"," >> json."${No_Mac_MS_Service_processName[$k]}"
        echo "    \"processName\": \"${No_Mac_MS_Service_processName[$k]}\"," >> json."${No_Mac_MS_Service_processName[$k]}"
        echo "    \"description\": \"${No_Mac_MS_Service_Description[*]}\"," >> json."${No_Mac_MS_Service_processName[$k]}"
        echo "    \"time\": [" >>  json."${No_Mac_MS_Service_processName[$k]}"
        for ((hour=0;hour<24;hour++))
        do
                str1="00"
                str2="59"
                str3="0"
                count_list=()
                if [ $hour -le 9 ];then
                        cat no-mac-service-MS.log | grep ${No_Mac_MS_Service_processName[$k]} | awk '{print $3}' |grep -E  0$hour:[0-5][0-9]:[0-6][0-9] | sort -n | uniq> time_list
                                                n=0;while read a;do time_list[$n]=$a;((n++));done<time_list

                else
                        cat no-mac-service-MS.log | grep ${No_Mac_MS_Service_processName[$k]} | awk '{print $3}' |grep -E  $hour:[0-5][0-9]:[0-6][0-9] | sort -n | uniq > time_list
                        n=0;while read a;do time_list[$n]=$a;((n++));done<time_list
                fi
                rm -rf time_list
                count[$hour]=${#time_list[*]}
                count_list[$hour]=$count
                echo "          {" >>json.${No_Mac_MS_Service_processName[$k]}
                if [ $hour -le 9 ];then
                        #if [ ${count_list[$hour]} -eq 0 ] ;then
                        #       :
                        #else
                        echo "          \"timeWindow\" :\"$str3$hour$str1-$str3$hour$str2\"," >>json.${No_Mac_MS_Service_processName[$k]}
                        #fi
                        #echo "         \"timeWindow\" :\"$str3$hour$str1-$str3$hour$str2\"," >>json.${No_Mac_MS_Service_processName[$k]}
                else
                        #if [ ${count_list[$hour]} -eq 0 ] ;then
                        #       :
                        #else
                        echo "          \"timeWindow\" :\"$hour$str1-$hour$str2\"," >>json.${No_Mac_MS_Service_processName[$k]}
                        #echo "         \"timeWindow\" :\"$hour$str1-$hour$str2\"," >>json.${No_Mac_MS_Service_processName[$k]}

                fi
                echo "                  \"numberOfOccurrence\" : \"${count[$hour]}\"" >>json.${No_Mac_MS_Service_processName[$k]}
                echo "          }," >>json.${No_Mac_MS_Service_processName[$k]}
                time_list=()
        done
        unset IFS
        sed -i '$d' json."${No_Mac_MS_Service_processName[$k]}"
        echo "        }" >> json."${No_Mac_MS_Service_processName[$k]}"
        echo "    }" >> json."${No_Mac_MS_Service_processName[$k]}"
        echo " ]" >>  json."${No_Mac_MS_Service_processName[$k]}"
done
cat json.* > analysis.json
rm -rf json* *.log
#curl -k -H "Content-Type:application/json" -X POST -d @analysis-mac.json https://foo.com/bar
rm -rf analysis-mac.json
