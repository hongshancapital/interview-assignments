#!/bin/bash

#written by wang.yingxi, on 2023.07.06
#usage: ./upload.sh interview_data_set-1 
#step 1, 数据清洗
n=0
n_file=0
n_occu=0
rm -rf /tmp/file-error*.log
for i in $(cat $1 | grep error | awk '{print NF}' | sort | uniq)
do
     num[n]=$i
     let n++
done	 
echo ${num[0]}
echo ${num[1]}
echo ${num[2]}
echo ${num[3]}
echo ${num[4]}
echo ${n}
cat $1 |grep error > /tmp/file_ref.log  

while read line
do
       j=$(echo "${line}" | awk '{print NF}')
       seq_num=$(seq $n)
       for k in $seq_num
       do
	       l=`expr $k - 1`
               if [ "$j" = "${num[$l]}" ];then
                       echo "${line}">>/tmp/file-error-${k}.log
                       let n_file++
               fi
       done
       
done</tmp/file_ref.log
echo ${n_file}
#step 2, 不同数据文件分别进行信息提取

#针对file文件1分析对应字段

file_num1=0 
deviceN1=()
pid1=()
pname1=()
des1=()
tim1=()
while read line
do
	echo ${line}
	deviceN1[${#deviceN1[*]}]=$(echo "${line}" | awk '{print $4}')
        echo ${line} | awk '{print $4}'
        echo "deviceN1 is ${deviceN1[$file_num1]}"
        pid1[${#pid1[*]}]=$(echo "${line}" | awk '{ print $5}' | awk -F'[' '{print $2}' | awk -F']' '{print $1}' )
        pname1[${#pname1[*]}]=$(echo "${line}" | awk '{ print $5}' | awk -F'[' '{print $1}')
	des1[${#des1[*]}]=$(echo "${line}" | awk -F':' '{print $7,$8}' | sed 's/ /-/g')
        echo "des file num1 is ${des1[file_num1]}"
	echo ${des1[file_num1]}>>/tmp/file-error-des-1.log
	tim1[${#tim1[*]}]=$(echo "${line}" | awk '{print $3}' | awk -F':' '{print $1}')
        echo "${tim1[$file_num1]}"
        let file_num1++
done</tmp/file-error-1.log 

#针对file2
file_num2=0
deviceN2=()
pid2=()
pname2=()
des2=()
tim2=()
while read line
do
        echo ${line} 
        deviceN2[${#deviceN2[*]}]=$(echo "${line}" | awk '{print $4}')
        #echo ${line} | awk '{print $4}'
        #echo "deviceN2 is ${deviceN2[$file_num2]}"
        pid2[${#pid2[*]}]=$(echo "${line}" | awk '{ print $6}' | sed 's/[^0-9]//g' )
        pname2[${#pname2[*]}]=$(echo "${line}" | awk '{ print $6}' | sed 's/[^a-zA-Z\.]//g' )
        des2[${#des2[*]}]=$(echo "${line}" | awk -F':' '{print $4,$5,$6,$7}' | sed 's/ /-/g')
        #echo "des file num2 is ${des2[file_num2]}"
        echo ${des2[file_num2]}>>/tmp/file-error-des-2.log
        tim2[${#tim2[*]}]=$(echo "${line}" | awk '{print $3}' | awk -F':' '{print $1}')
        echo "${tim2[$file_num2]}"
        let file_num2++
done</tmp/file-error-2.log
#update to here on 7.2

#针对file3
file_num3=0
deviceN3=()
pid3=()
pname3=()
des3=()
tim3=()
while read line
do
        echo ${line}
        deviceN3[${#deviceN3[*]}]=$(echo "${line}" | awk '{print $4}')
        #echo ${line} | awk '{print $4}'
        #echo "deviceN3 is ${deviceN13[$file_num3]}"
        pid3[${#pid3[*]}]=$(echo "${line}" | awk '{ print $6}' | sed 's/[^0-9]//g' )
        pname3[${#pname3[*]}]=$(echo "${line}" | awk '{ print $6}' | sed 's/[^a-zA-Z\.]//g')
        des3[${#des3[*]}]=$(echo "${line}" | awk -F':' '{print $7,$8}' | sed 's/ /-/g')
        #echo "des file num3 is ${des3[file_num3]}"
        echo ${des3[file_num3]}>>/tmp/file-error-des-3.log
        tim3[${#tim3[*]}]=$(echo "${line}" | awk '{print $3}' | awk -F':' '{print $1}')
        echo "${tim3[$file_num3]}"
        let file_num3++
done</tmp/file-error-3.log


#针对file4
file_num4=0
deviceN4=()
pid4=()
pname4=()
des4=()
tim4=()
while read line
do
        echo ${line}
        deviceN4[${#deviceN4[*]}]=$(echo "${line}" | awk '{print $4}')
        #echo ${line} | awk '{print $4}'
        #echo "deviceN4 is ${deviceN4[$file_num4]}"
        pid4[${#pid4[*]}]=$(echo "${line}" | awk '{ print $5}' | sed 's/[^0-9]//g' )
        pname4[${#pname4[*]}]=$(echo "${line}" | awk '{ print $5}' | sed 's/[^a-zA-Z]//g')
        des4[${#des4[*]}]=$(echo "${line}" | awk -F':' '{print $7,$8}' | sed 's/ /-/g')
        #echo "des file num4 is ${des4[file_num4]}"
        echo ${des4[file_num4]}>>/tmp/file-error-des-4.log
        tim4[${#tim4[*]}]=$(echo "${line}" | awk '{print $3}' | awk -F':' '{print $1}')
        echo "${tim4[$file_num4]}"
        let file_num4++
done</tmp/file-error-4.log


#针对file5
file_num5=0
deviceN5=()
pid5=()
pname5=()
des5=()
tim5=()
while read line
do
        echo ${line}
        deviceN5[${#deviceN5[*]}]=$(echo "${line}" | awk '{print $4}')
        #echo ${line} | awk '{print $4}'
        #echo "deviceN5 is ${deviceN5[$file_num5]}"
        pid5[${#pid5[*]}]=$(echo "${line}" | awk '{ print $5,$6,$7}' | sed 's/[^0-9]//g' )
        pname5[${#pname5[*]}]=$(echo "${line}" | awk '{ print $5,$6,$7}' | sed 's/[^a-zA-Z\ ]//g')
        des5[${#des5[*]}]=$(echo "${line}" | awk -F':' '{print $7,$8}' | sed 's/ /-/g')
        #echo "des file num5 is ${des5[file_num5]}"
        echo ${des5[file_num5]}>>/tmp/file-error-des-5.log
        tim5[${#tim5[*]}]=$(echo "${line}" | awk '{print $3}' | awk -F':' '{print $1}')
        echo "${tim5[$file_num5]}"
        let file_num5++
done</tmp/file-error-5.log


#step 3聚类分析错误信息
#统计相同错误类型的数量

#分析error1
error_cat1=()
cat /tmp/file-error-des-1.log | sort | uniq > /tmp/file-error-des1sort1.log
tmp_dessort1=/tmp/file-error-des1sort1.log
while read line
do
	echo "${line}"
	error_cat1[${#error_cat1[*]}]=$(echo "$line")
	num_t=${#error_cat1[*]}
        num_t1=`expr $num_t - 1`
	echo "${error_cat1[$num_t1]}"
done<${tmp_dessort1}
num_error_tmp=$(seq $num_t)
for i in $num_error_tmp
do
        i_num=`expr $i - 1`
	num_file_num1=$(seq $file_num1)
	for k in $num_file_num1
	do
		j=`expr $k - 1`	
		echo "des is ${des1[$j]}"
		echo "error cat1 is ${error_cat1[$i_num]}"
		if [ "${des1[$j]}" = "${error_cat1[$i_num]}" ]
		then
                        echo "en loop"
			tmp=`expr ${tim1[$i_num]} + 1`
			echo -e "${tim1[$i_num]}00-${tmp}00 | ${deviceN1[$i_num]} | ${pid1[$i_num]} | ${pname1[$i_num]} | ${des1[$i_num]}  \n">>/tmp/file-error/file-error1-${pid1[$i_num]}.log
			
		fi
	done
done

#分析error2
error_cat2=()
cat /tmp/file-error-des-2.log | sort | uniq > /tmp/file-error-des2sort2.log
tmp_dessort2=/tmp/file-error-des2sort2.log
while read line
do
        echo "${line}"
        error_cat2[${#error_cat2[*]}]=$(echo "$line")
        num_t=${#error_cat2[*]}
        num_t2=`expr $num_t - 1`
        echo "${error_cat1[$num_t2]}"
done<${tmp_dessort2}
num_error_tmp=$(seq $num_t)
for i in $num_error_tmp
do
        i_num=`expr $i - 1`
        num_file_num2=$(seq $file_num2)
        for k in $num_file_num2
        do
                j=`expr $k - 1`
                echo "des2 is ${des2[$j]}"
                echo "error cat2 is ${error_cat2[$i_num]}"
                if [ "${des2[$j]}" = "${error_cat2[$i_num]}" ]
                then
                        echo "en loop"
                        tmp=`expr ${tim2[$i_num]} + 1`
                        echo -e "${tim2[$i_num]}00-${tmp}00 | ${deviceN2[$i_num]} | ${pid2[$i_num]} | ${pname2[$i_num]} | ${des2[$i_num]}  \n">>/tmp/file-error/file-error2-${pid2[$i_num]}.log

                fi
        done
done

#分析error3
error_cat3=()
cat /tmp/file-error-des-3.log | sort | uniq > /tmp/file-error-des3sort3.log
tmp_dessort3=/tmp/file-error-des3sort3.log
while read line
do
        echo "${line}"
        error_cat3[${#error_cat3[*]}]=$(echo "$line")
        num_t=${#error_cat3[*]}
        num_t3=`expr $num_t - 1`
        echo "${error_cat3[$num_t3]}"
done<${tmp_dessort3}
num_error_tmp=$(seq $num_t)
for i in $num_error_tmp
do
        i_num=`expr $i - 1`
        num_file_num3=$(seq $file_num3)
        for k in $num_file_num3
        do
                j=`expr $k - 1`
                echo "des3 is ${des3[$j]}"
                echo "error cat3 is ${error_cat3[$i_num]}"
                if [ "${des3[$j]}" = "${error_cat3[$i_num]}" ]
                then
                        echo "en loop"
                        tmp=`expr ${tim3[$i_num]} + 1`
                        echo -e "${tim3[$i_num]}00-${tmp}00 | ${deviceN3[$i_num]} | ${pid3[$i_num]} | ${pname3[$i_num]} | ${des3[$i_num]}  \n">>/tmp/file-error/file-error3-${pid3[$i_num]}.log

                fi
        done
done

#分析error4
error_cat4=()
cat /tmp/file-error-des-4.log | sort | uniq > /tmp/file-error-des4sort4.log
tmp_dessort4=/tmp/file-error-des4sort4.log
while read line
do
        echo "${line}"
        error_cat4[${#error_cat4[*]}]=$(echo "$line")
        num_t=${#error_cat4[*]}
        num_t4=`expr $num_t - 1`
        echo "${error_cat4[$num_t4]}"
done<${tmp_dessort1}
num_error_tmp=$(seq $num_t)
for i in $num_error_tmp
do
        i_num=`expr $i - 1`
        num_file_num4=$(seq $file_num4)
        for k in $num_file_num4
        do
                j=`expr $k - 1`
                echo "des4 is ${des4[$j]}"
                echo "error cat4 is ${error_cat4[$i_num]}"
                if [ "${des4[$j]}" = "${error_cat4[$i_num]}" ]
                then
                        echo "en loop"
                        tmp=`expr ${tim4[$i_num]} + 1`
                        echo -e "${tim4[$i_num]}00-${tmp}00 | ${deviceN4[$i_num]} | ${pid4[$i_num]} | ${pname4[$i_num]} | ${des4[$i_num]}  \n">>/tmp/file-error/file-error4-${pid4[$i_num]}.log

                fi
        done
done

#分析error5

error_cat5=()
cat /tmp/file-error-des-5.log | sort | uniq > /tmp/file-error-des5sort5.log
tmp_dessort5=/tmp/file-error-des5sort5.log
while read line
do
        echo "${line}"
        error_cat5[${#error_cat5[*]}]=$(echo "$line")
        num_t=${#error_cat5[*]}
        num_t1=`expr $num_t - 1`
        echo "${error_cat5[$num_t1]}"
done<${tmp_dessort1}
num_error_tmp=$(seq $num_t)
for i in $num_error_tmp
do
        i_num=`expr $i - 1`
        num_file_num5=$(seq $file_num5)
        for k in $num_file_num5
        do
                j=`expr $k - 1`
                echo "des5 is ${des5[$j]}"
                echo "error cat5 is ${error_cat5[$i_num]}"
                if [ "${des5[$j]}" = "${error_cat5[$i_num]}" ]
                then
                        echo "en loop"
                        tmp=`expr ${tim5[$i_num]} + 1`
                        echo -e "${tim5[$i_num]}00-${tmp}00 | ${deviceN5[$i_num]} | ${pid5[$i_num]} | ${pname5[$i_num]} | ${des5[$i_num]}  \n">>/tmp/file-error/file-error5-${pid5[$i_num]}.log

                fi
        done
done



#step4, use curl to post to https://foo.com/bar


folder=/tmp/file-error
rm -rf /tmp/file-error/file-upload.txt
rm -rf /tmp/file-error/file-upload-new.txt
for file in $folder/*.log
do 
	cat $file>>/tmp/file-error/file-upload.txt
done

cat /tmp/file-error/file-upload.txt | sed 's/[[:space:]]//g' |sort -t '|' -k 1 -k 2 -k 3 -k 4 -k 5 > /tmp/file-error/file-upload-new.txt
tw=()
devn=()
pid=()
pn=()
des=()
num_tw=1
c=1
occu_tw=()
while read line
do
	tw[${#tw[*]}]=$(echo $line | awk -F'|' '{print $1}')
        devn[${#devn[*]}]=$(echo $line | awk -F'|' '{print $2}')
        pid[${#pid[*]}]=$(echo $line | awk -F'|' '{print $3}')
        pn[${#pn[*]}]=$(echo $line | awk -F'|' '{print $4}')
        des[${#des[*]}]=$(echo $line | awk -F'|' '{print $5}')
        if [ ${#tw[*]} -ge $c ]
	then
		tem1=`expr ${#tw[*]} - 1`
		echo "test conditioni1"
		tem2=`expr ${#tw[*]} - 2`
		if [[ "${tw[$tem2]}" = "${tw[$tem1]}" ]] && [[ "${devn[$tem2]}]" = "${devn[$tem1]}]" ]] && [[ "${pid[$tem2]}]" = "${pid[$tem1]}]" ]] && [[ "${pn[$tem2]}]" = "${pn[$tem1]}]" ]] && [[ "${des[$tem2]}]" = "${des[$tem1]}]" ]] 
		then
			let num_tw++
		else
			occu_tw[${#_occutw[*]}]=$num_tw
			#以json格式上传
			curl -i -k -X POST https://foo.com/bar -H 'Content-Type:application/json' -d'{"deviceName":${devn[$tem1]},"processid":${pid[$tem1]},"processName":${pn[$tem1]},"description":${des[$tem1]},"timeWindow":${tw[tem1]},"numberOfOccurrence":$num_tw}'
		fi
	fi

done</tmp/file-error/file-upload-new.txt
