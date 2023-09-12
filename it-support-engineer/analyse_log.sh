
#!/bin/bash
#判断脚本执行参数的日志文件interview_data_set是否存在
if [ $# == 0 ];then
    echo "Usage: sh ./analysis_log.sh log_file"
    exit 1
elif [ ! -f $1 ];then
    echo "log_File does not exist!"
    exit 1
fi

#!/bin/bash
#---------------------------------------------------------------------------------------
# 分析系统日志，由于没有给出关键信息的定义，所以下面对日志所有信息进行分析处理
#---------------------------------------------------------------------------------------
analysis_upto(){
# 分析处理日志文件，用awk命令分析处理，
#以timeWindow|deviceName|processName|processId|description 作为数组来统计在小时级别内发生的次数
    jsonDatas=$(cat $1 \
    | sed 's/\"//g' `# 除去双引号，便于下面格式化JSON格式数据` \
    | sed "s/'//g" `# 除去单引号，便于下面格式化JSON格式数据` \
    | sed 'N;s/\n\t/ /' `# 合并tab键开头的信息到上一行` \
    | sed 'N;s/\n\t/ /' `# 再执行一次以上命令`\
    | sed '/---/d' `# 删除日志提示信息` \
    | awk -F' ' '{split($3,time_window,":"); timeWindow=time_window[1];\
    split($5,process_name,"["); processName=process_name[1]; \
    split(process_name[2],process_id,"]"); processId=process_id[1]; description=""; \
    for(i=6;i<=NF;i++)description=description$i; \
    device_log_msg[timeWindow"|"$4"|"processName"|"processId"|"description]++} \
    END {for(i in device_log_msg) {print i"|"device_log_msg[i]}}' \
    | sort `# 按发生时间（小时级）timeWindow升序排序` \
    `# 下面的awk命令用于生成JSON格式的数据` \
    | awk -F'|' 'BEGIN {print "["} \
    {start_hour=$1"00"; \
    end_hour=$1+1; \
    if(end_hour<10){end_hour="0"end_hour"00"} \
    else{end_hour=end_hour"00"}; \
    jsonData=jsonData"\t{\"deviceName\" : \""$2"\", \
    \"processId\" : \""$4"\", \
    \"processName\" : \""$3"\", \
    \"description\" : \""$5"\", \
    \"timeWindow\" : \""start_hour"-"end_hour"\", \
    \"numberOfOccurrence\" : \""$6"\"},\n" } \
    END {print substr(jsonData,1,length(jsonData)-2)"\n]"}')
    echo $jsonDatas
    response=$(curl -s -k -H 'Content-Type':'application/json' -d $jsonDatas -X POST https://foo.com/bar)
    if [ "$response" = "200" ]; then
    echo "Upload successful"
    else
    echo "Upload failed $response"
    fi
}

#执行分析日志函数以及post上传函数
analysis_upto $1
