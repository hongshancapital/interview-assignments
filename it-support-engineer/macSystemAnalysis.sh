#!/bin/bash
####################################################################
#                                                                  #
# System analysis script for MacOS.                                #
# Created by FanJialin, 2022                                       #
#                                                                  #
# This script may be freely used, copied, modified and distributed #
# under the sole condition that credits to the original author     #
# remain intact.                                                   #
#                                                                  #
# This script comes without any warranty, use it at your own risk. #
#                                                                  #
####################################################################

###############################################
# CHANGE THESE OPTIONS TO MATCH YOUR SYSTEM ! #
###############################################
LANG=en_US.UTF-8

# 配置待处理文件名
FILE_NAME_RAW="interview_data_set.gz"

# 配置关键信息识别字符串
VAR_KEY_1='error:'
VAR_KEY_2='error ='

# 初始化程序运行状态
STATUS_UNZIP_DATA_RAW=BAD
STATUS_GATHER_DATA_ERROR=BAD
STATUS_MACHINING_DATA_ERROR=BAD
STATUS_JSON_DATA_ERROR=BAD
STATUS_SERVER_POST=BAD

VAR_ACCOUNT_ERRORS=0

# 配置服务器POST地址
VAR_DOMAIN_SERVICE="https://foo.com/bar"

DIR_SCRIPT=$(cd "$(dirname "${BASH_SOURCE[0]}")" || exit; pwd)

##################
# END OF OPTIONS #
##################

#################
# CLI Interface #
#################

COLOR_RED="\033[1;31m"
COLOR_YELLOW="\033[1;33m"
COLOR_BLUE="\033[1;34m"
COLOR_RESET="\033[0m"

STRING_0="DEVICE:                     "
STRING_1="DATA RAW UNZIP:             "
STRING_2="DATA ERROR GATHER:          "
STRING_3="DATA ERROR MACHINING:       "
STRING_4="DATA ERROR JSON:            "
STRING_5="SERVER POST:                "
STRING_9="INFO REPORT:                "

PADDING_X="----------------------------------------------------------------------------------------"
PADDING_M="||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||100%"
PADDING_A="                                                                TIME PAST: 24h"
PADDING_B="                                                           ACCOUNT ERRORS:"
PADDING_Z="*********$COLOR_YELLOW System Analysis Report Finished at: $(date "+%a %d %b %Y %I:%M:%S %p %Z") $COLOR_RESET**********"

PRINT_PADDING_X(){
    echo "$PADDING_X"
}
PRINT_PADDING_M(){
    echo "$PADDING_M"
}

#################

###########
# install #
###########

MKDIR_DATA(){
    if [[ ! -d "$DIR_SCRIPT/data" ]];then
        mkdir "$DIR_SCRIPT/data"
    fi
}
MKDIR_DATA

###########

###################
# 解压缩系统日志数据 #
###################

UNZIP_DATA_RAW(){
    cp "$DIR_SCRIPT/$FILE_NAME_RAW" "$DIR_SCRIPT/data/ALL_SYSTEM_MAC.log.gz" && gzip -df "$DIR_SCRIPT/data/ALL_SYSTEM_MAC.log.gz"
    if [ $? -eq 0 ]; then
        STATUS_UNZIP_DATA_RAW=OK
    fi
}

PRINT_STATUS_UNZIP_DATA_RAW(){
    COLOR_STATUS_UNZIP_DATA_RAW=$COLOR_RED

    UNZIP_DATA_RAW
    if [ $STATUS_UNZIP_DATA_RAW == "OK" ]; then
        COLOR_STATUS_UNZIP_DATA_RAW=$COLOR_BLUE
    fi
    echo -e "$STRING_1 $COLOR_STATUS_UNZIP_DATA_RAW $STATUS_UNZIP_DATA_RAW $COLOR_RESET"
}

###################

##################
# 汇总错误日志数据 #
##################

GATHER_DATA_ERROR(){
    if [ $STATUS_UNZIP_DATA_RAW == "OK" ]; then
        echo -n "" > "$DIR_SCRIPT/data/ERROR_SYSTEM_MAC.log"
        while read line
        do
            if [[ "$line" == *"$VAR_KEY_1"* || "$line" == *"$VAR_KEY_2"* ]]; then
                echo $line >> "$DIR_SCRIPT/data/ERROR_SYSTEM_MAC.log"
                ((++VAR_ACCOUNT_ERRORS))
            fi
        done < "$DIR_SCRIPT/data/ALL_SYSTEM_MAC.log"
        STATUS_GATHER_DATA_ERROR=OK
    fi
}

PRINT_STATUS_GATHER_DATA_ERROR(){
    COLOR_STATUS_GATHER_DATA_ERROR=$COLOR_RED

    GATHER_DATA_ERROR
    if [ $STATUS_GATHER_DATA_ERROR == "OK" ]; then
        COLOR_STATUS_GATHER_DATA_ERROR=$COLOR_BLUE
    fi
    echo -e "$STRING_2 $COLOR_STATUS_UNZIP_DATA_RAW $STATUS_UNZIP_DATA_RAW $COLOR_RESET"
}

##################

###################################################
# 加工错误日志数据，移除噪声：同进程同错误 发生于 不同线程 #
###################################################

MACHINING_DATA_ERROR(){
    if [ $STATUS_GATHER_DATA_ERROR == "OK" ]; then
        # 数据加工
        awk '{
            $1=""; $2=""; 
            split($3,a,":"); $3=a[1];
                if ($3 < 9) $3=a[1]"00-0"++$3"00"; else $3=a[1]"00-"++$3"00";
            sub(/\[/ , " ", $5); sub(/\]:/ , "", $5); sub(/\]/ , "", $5);
            sub(/\(/ , "", $6); sub(/\[+.*/, "", $6); sub(/\.+[0-9]+.*/, "", $6);
            sub("  " , ""); 
        print}' "$DIR_SCRIPT/data/ERROR_SYSTEM_MAC.log" > "$DIR_SCRIPT/data/MACHINING_ERROR_SYSTEM_MAC.log"

        # 数据统计,按时间升序：JSON物料准备。
        awk '{ARRAY_AWK[$0]++}END{for(i in ARRAY_AWK)print ARRAY_AWK[i],i}' "$DIR_SCRIPT/data/MACHINING_ERROR_SYSTEM_MAC.log" |sort -n -k 2 > "$DIR_SCRIPT/data/JSON_ERROR_SYSTEM_MAC.log"

        STATUS_MACHINING_DATA_ERROR=OK
    fi
}

PRINT_STATUS_MACHINING_DATA_ERROR(){
    COLOR_STATUS_MACHINING_DATA_ERROR=$COLOR_RED

    MACHINING_DATA_ERROR
    if [ $STATUS_MACHINING_DATA_ERROR == "OK" ]; then
        COLOR_STATUS_MACHINING_DATA_ERROR=$COLOR_BLUE
    fi
    echo -e "$STRING_3 $COLOR_STATUS_MACHINING_DATA_ERROR $STATUS_MACHINING_DATA_ERROR $COLOR_RESET"
}

###################################################

#################
# JSON格式化数据 #
#################

JSON_DATA_ERROR(){
    echo '[' > "$DIR_SCRIPT/ERROR_SYSTEM_MAC.json"
    if [ $STATUS_MACHINING_DATA_ERROR == "OK" ]; then

        awk '{

            DEVICE_NAME="\{\"deviceName\":\""$3"\",";
            PROCESS_ID="\"processId\":\""$5"\",";
            PROCESS_NAME="\"processName\":\""$4"\",";
            DESCRIPTION_MID=$6;
                for(i=7;i<=NF;i++){
                    DESCRIPTION_MID=DESCRIPTION_MID" "$i
                    };
            DESCRIPTION="\"description\":\""DESCRIPTION_MID"\",";
            TIME_WINDOWS="\"timeWindow\":\""$2"\",";
            NUMBER_OF_OCCURRENCE="\"numberOfOccurrence\":"$1"\},";

            print DEVICE_NAME PROCESS_ID PROCESS_NAME DESCRIPTION TIME_WINDOWS NUMBER_OF_OCCURRENCE;

            }' "$DIR_SCRIPT/data/JSON_ERROR_SYSTEM_MAC.log" | sed '$ s/.$//' >> "$DIR_SCRIPT/ERROR_SYSTEM_MAC.json"
    fi
    echo ']' >> "$DIR_SCRIPT/ERROR_SYSTEM_MAC.json"
    STATUS_JSON_DATA_ERROR=OK
}

PRINT_STATUS_JSON_DATA_ERROR(){
    COLOR_STATUS_JSON_DATA_ERROR=$COLOR_RED

    JSON_DATA_ERROR
    if [ $STATUS_JSON_DATA_ERROR == "OK" ]; then
        COLOR_STATUS_JSON_DATA_ERROR=$COLOR_BLUE
    fi
    echo -e "$STRING_4 $COLOR_STATUS_JSON_DATA_ERROR $STATUS_JSON_DATA_ERROR $COLOR_RESET"
}

#################

###############
# POST SERVER #
###############

SERCER_POST(){
    COLOR_STATUS_SERVER_POST=$COLOR_RED
    curl -H "Content-Type: application/json" -X POST -d '@ERROR_SYSTEM_MAC.json' -k $VAR_DOMAIN_SERVICE > /dev/null && STATUS_SERVER_POST=OK
    if [ $STATUS_SERVER_POST == "OK" ]; then
        COLOR_STATUS_SERVER_POST=$COLOR_BLUE
    fi
    echo -e "$STRING_5 $COLOR_STATUS_SERVER_POST $STATUS_SERVER_POST $COLOR_RESET"
}

###############



PRINT_ANALYSIS_REPORT(){
    echo -e $COLOR_YELLOW"$STRING_9"$COLOR_RESET
    echo -e "$PADDING_A"
    echo -e "$PADDING_B" $COLOR_RED"$VAR_ACCOUNT_ERRORS"$COLOR_RESET
    echo -e "$PADDING_Z"
}



################
# PRINT OUTPUT #
################

PRINT_OUTPUT(){
    PRINT_PADDING_X
    # 打印程序处理状态：输出CLI
    PRINT_STATUS_UNZIP_DATA_RAW
    PRINT_STATUS_GATHER_DATA_ERROR
    PRINT_STATUS_MACHINING_DATA_ERROR
    PRINT_STATUS_JSON_DATA_ERROR
    
    # POST SERVER
    SERCER_POST
    
    PRINT_PADDING_M
    # 程序处理结果简单汇总：输出CLI
    PRINT_ANALYSIS_REPORT

    PRINT_PADDING_X
}

PRINT_OUTPUT

################
