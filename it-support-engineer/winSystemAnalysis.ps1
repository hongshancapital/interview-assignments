
####################################################################
#                                                                  #
# System analysis script for Windows.                                #
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

# 设置Out-File:Width，防止跨终端运行时产生意外输出截断，导致信息丢失
$PSDefaultParameterValues['out-file:width'] = 512

# 配置待处理文件名
$FILE_NAME_RAW="interview_data_set.gz"

# 配置关键信息识别字符串
$VAR_KEY_1='error:'
$VAR_KEY_2='error ='

# 初始化程序运行状态
$STATUS_UNZIP_DATA_RAW="BAD"
$STATUS_GATHER_DATA_ERROR="BAD"
$STATUS_MACHINING_DATA_ERROR="BAD"
$STATUS_JSON_DATA_ERROR="BAD"
$STATUS_SERVER_POST="BAD"

$VAR_ACCOUNT_ERRORS=0

# 配置服务器POST地址
$VAR_DOMAIN_SERVICE="https://foo.com/bar"

$DIR_SCRIPT=$PSScriptRoot

##################
# END OF OPTIONS #
##################

#################
# CLI Interface #
#################

$STRING_0="DEVICE:                     "
$STRING_1="DATA RAW UNZIP:             "
$STRING_2="DATA ERROR GATHER:          "
$STRING_3="DATA ERROR MACHINING:       "
$STRING_4="DATA ERROR JSON:            "
$STRING_5="SERVER POST:                "
$STRING_9="INFO REPORT:                "

$PADDING_X="----------------------------------------------------------------------------------------"
$PADDING_M="||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||100%"
$PADDING_A="                                                                TIME PAST: 24h"
$PADDING_B="                                                           ACCOUNT ERRORS:"
$PADDING_Z="***************System Analysis Report Finished at: "+ $(Get-Date) +"******************"

function PRINT-PADDING_X(){
    echo "$PADDING_X"
}
function PRINT-PADDING_M(){
    echo "$PADDING_M"
}

#################

###########
# install #
###########

function MKDIR-DATA(){
    if (!(Test-Path "$PSScriptRoot/data")){
        New-Item -path $PSScriptRoot -name data -type directory 1>null
    }    
}
MKDIR-DATA

###########

######################
# 解压缩系统日志数据 #
######################

#.gz文件解压函数
function ExGZip-File{
    Param(
        $infile,
        $outfile = ($infile -replace '\.gz$','')
        )

    $input = New-Object System.IO.FileStream $inFile, ([IO.FileMode]::Open), ([IO.FileAccess]::Read), ([IO.FileShare]::Read)
    $output = New-Object System.IO.FileStream $outFile, ([IO.FileMode]::Create), ([IO.FileAccess]::Write), ([IO.FileShare]::None)
    $gzipStream = New-Object System.IO.Compression.GzipStream $input, ([IO.Compression.CompressionMode]::Decompress)

    $buffer = New-Object byte[](1024)
    while($true){
        $read = $gzipstream.Read($buffer, 0, 1024)
        if ($read -le 0){break}
        $output.Write($buffer, 0, $read)
        }

    $gzipStream.Close()
    $output.Close()
    $input.Close()
}

function UNZIP-DATA_RAW{
    if((Test-Path "$PSScriptRoot/$FILE_NAME_RAW")){
        ExGZip-File "$DIR_SCRIPT/$FILE_NAME_RAW" "$DIR_SCRIPT/data/ALL_SYSTEM_MAC.log"

        $script:STATUS_UNZIP_DATA_RAW="OK"
        }else{
            echo "WRONG:File Exist Or Not ? PATH:$PSScriptRoot\$FILE_NAME_RAW"
        }
}

function PRINT-STATUS_UNZIP_DATA_RAW(){
    UNZIP-DATA_RAW
    echo "$STRING_1 $STATUS_UNZIP_DATA_RAW"
}

######################

####################
# 汇总错误日志数据 #
####################
 
function GATHER-DATA_ERROR(){
    if( $STATUS_UNZIP_DATA_RAW -eq "OK"){

        # 识别错误关键字，并将结果保存至“ERROR_SYSTEM_MAC.log”
        Get-Content "$DIR_SCRIPT/data/ALL_SYSTEM_MAC.log" | Select-String -Pattern "$VAR_KEY_1", "$VAR_KEY_2" > "$DIR_SCRIPT/data/ERROR_SYSTEM_MAC.log" 
        $script:STATUS_GATHER_DATA_ERROR="OK"
    }
}

function PRINT-STATUS_GATHER_DATA_ERROR(){
    GATHER-DATA_ERROR
    echo "$STRING_2 $STATUS_GATHER_DATA_ERROR"
}
####################

#############################################################
# 加工错误日志数据 & 移除噪声：同进程同错误 发生于 不同线程 #
#############################################################

# 时间窗口函数，用以将HH转为时间窗口HH00-HH00
function CONVERT-HOUR_WINDOW($HOUR){    
    $HOUR_NEXT = [int]$HOUR+1
    if($HOUR_NEXT -lt 10){
        return $HOUR + "00-0" + $HOUR_NEXT.ToString() + "00"
    }else{
        return $HOUR + "00-" + $HOUR_NEXT.ToString() + "00"
    }
}
# 核心函数，用以移除线程噪声，统计错误总数、重复次数
function MACHINING-DATA_ERROR(){
    if($STATUS_GATHER_DATA_ERROR -eq "OK"){
        echo -n "" > "$DIR_SCRIPT/data/MACHINING_ERROR_SYSTEM_MAC.log"

        $ERROR_ALL = Get-Content "$DIR_SCRIPT/data/ERROR_SYSTEM_MAC.log"
        # 逐行扫描数据，如果匹配，则进入数据加工
        ForEach($ERROR_LINE in $ERROR_ALL){
            $part = $ERROR_LINE.split(' ')
            # 匹配XXX[YY] (XXX[YYY]): XXX的错误显示
            if($ERROR_LINE -match '\[(\d.*)\]\s\((.*)\[(\d.*)\]\)\:\s(.*)')
            {
                $TIME_WINDOW=CONVERT-HOUR_WINDOW (($part[2].split(':'))[0])
                $DEVICE_NAME = $part[3]
                $PROCESS_ID = $matches[1]
                $PROCESS_NAME = ($part[4].split('['))[0]
                $DESCRIPTION = $matches[2]+': '+$matches[4]
                ++$Script:VAR_ACCOUNT_ERRORS
                echo "$TIME_WINDOW $DEVICE_NAME $PROCESS_ID $PROCESS_NAME $DESCRIPTION" >> "$DIR_SCRIPT/data/MACHINING_ERROR_SYSTEM_MAC.log"

            }
            # 匹配XXX[YY] (XXX.YYYYY): XXX的错误显示
            elseif($ERROR_LINE -match '\[(\d.*)\]\s\((.*)\.(\d.*)\)\:\s(.*)'){
                $TIME_WINDOW = CONVERT-HOUR_WINDOW (($part[2].split(':'))[0])
                $DEVICE_NAME = $part[3]
                $PROCESS_ID = $matches[1]
                $PROCESS_NAME = ($part[4].split('['))[0]
                $DESCRIPTION = $matches[2]+': '+$matches[4]
                ++$Script:VAR_ACCOUNT_ERRORS
                echo "$TIME_WINDOW $DEVICE_NAME $PROCESS_ID $PROCESS_NAME $DESCRIPTION" >> "$DIR_SCRIPT/data/MACHINING_ERROR_SYSTEM_MAC.log"
            }
            # 匹配XXX[YY]:XXX的错误显示
            elseif($ERROR_LINE -match '\[(\d.*)\]\:\s(.*)'){
                $TIME_WINDOW = CONVERT-HOUR_WINDOW (($part[2].split(':'))[0])
                $DEVICE_NAME = $part[3]
                $PROCESS_ID = $matches[1]
                $PROCESS_NAME = ($part[4].split('['))[0]
                $DESCRIPTION = $matches[2]
                ++$Script:VAR_ACCOUNT_ERRORS
                echo "$TIME_WINDOW $DEVICE_NAME $PROCESS_ID $PROCESS_NAME $DESCRIPTION" >> "$DIR_SCRIPT/data/MACHINING_ERROR_SYSTEM_MAC.log"
            }
        }
        $script:STATUS_MACHINING_DATA_ERROR = "OK"
        # 统计重复次数，JSON物料准备
        Get-Content "$DIR_SCRIPT/data/MACHINING_ERROR_SYSTEM_MAC.log" | ? {$_.trim() -ne "" } | Group-Object -NoElement | Select-Object Count, Name > "$DIR_SCRIPT/data/JSON_ERROR_SYSTEM_MAC.log"
        echo $(Get-Content "$DIR_SCRIPT/data/JSON_ERROR_SYSTEM_MAC.log").Trim() > "$DIR_SCRIPT/data/JSON_ERROR_SYSTEM_MAC.log"
    }
}

function PRINT-STATUS_MACHINING_DATA_ERROR(){
    MACHINING-DATA_ERROR
    echo "$STRING_3 $STATUS_MACHINING_DATA_ERROR"
}

###################################################

##################
# JSON格式化数据 #
##################
function JSON-DATA_ERROR(){
    $JSON_DATA = @{}
    $JSON_DATA = @()

    if ($STATUS_MACHINING_DATA_ERROR -eq "OK"){
        $JSON_ALL = Get-Content "$DIR_SCRIPT/data/JSON_ERROR_SYSTEM_MAC.log"
        # 逐行扫描
        ForEach($JSON_ALL_LINE in $JSON_ALL){
            if($JSON_ALL_LINE -match  '(\d+)\s(\d+\-\d+)\s(\S+)\s(\d+)\s(\S+)\s(.*)'){
                $JSON_DATA += @{
                    "deviceName" =  $matches[3];
                    "processId" = $matches[4];
                    "processName" =  $matches[5];
                    "description" = $matches[6];
                    "timeWindow" = $matches[2];
                    "numberOfOccurrence" = $matches[1];
                }
            }
        }
        $JSON_DATA | ConvertTo-Json >  "$DIR_SCRIPT/winERROR_SYSTEM_MAC.json"
        $script:STATUS_JSON_DATA_ERROR="OK"
    }

}
    
function PRINT-STATUS_JSON_DATA_ERROR(){
    JSON-DATA_ERROR
    echo "$STRING_4 $STATUS_JSON_DATA_ERROR"
}

#################

###############
# POST SERVER #
###############

function POST-SERVER(){
    if($STATUS_JSON_DATA_ERROR -eq "OK"){
        # 调用Invoke-RestMethod方法发送POST请求，同时屏蔽控制台错误输出
        &{Invoke-RestMethod -Uri "$VAR_DOMAIN_SERVICE" -Method POST -ContentType "application/json" -Body $(Get-Content "$DIR_SCRIPT/winERROR_SYSTEM_MAC.json")
            if($? -eq "True"){$Script:STATUS_SERVER_POST = "OK"}
        } *> $null
    }
    echo "$STRING_5 $STATUS_SERVER_POST"
}

###############

################
# 打印信息报告 #
################
function PRINT-ANALYSIS_REPORT(){
    echo "$STRING_9"
    echo "$PADDING_A"
    echo "$PADDING_B $VAR_ACCOUNT_ERRORS"
    echo "$PADDING_Z"
}

################

################
# PRINT OUTPUT #
################

function PRINT-OUTPUT(){
    PRINT-PADDING_X
    # 打印程序处理状态：输出CLI
    PRINT-STATUS_UNZIP_DATA_RAW
    PRINT-STATUS_GATHER_DATA_ERROR
    PRINT-STATUS_MACHINING_DATA_ERROR
    PRINT-STATUS_JSON_DATA_ERROR
    
    # POST SERVER
    POST-SERVER
    
    PRINT-PADDING_M
    # 程序处理结果简单汇总：输出CLI
    PRINT-ANALYSIS_REPORT

    PRINT-PADDING_X
}

PRINT-OUTPUT

################