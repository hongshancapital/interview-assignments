#!/bin/bash

function log_to_console(){
    if [[ $1 -eq 0 ]]
    then
        echo $2 succeed
    else
        echo $2 failed
    fi
}

function create_metric_filter(){
    aws logs put-metric-filter \
        --log-group-name $log_group_name \
        --filter-name $metric_filter_name \
        --filter-pattern $metric_filter_pattern \
        --metric-transformations metricName=$metric_name,metricNamespace=$metric_namespace,metricValue=$metric_value \
        $aws_cli_args

    log_to_console $? create_metric_filter
}

function create_sns_topic(){
    sns_topic_arn=$(aws sns create-topic --name $sns_topic_name $aws_cli_args | jq '.TopicArn' | sed 's/"//g')
    log_to_console $? create_sns_topic
  
    aws sns subscribe \
        --topic-arn $sns_topic_arn \
        --protocol email \
        --notification-endpoint $email \
        $aws_cli_args
  
    log_to_console $? create_sns_subscribe
}

function create_metric_alarm(){
    aws cloudwatch put-metric-alarm \
        --alarm-name $alarm_name \
        --metric-name $metric_name \
        --namespace $metric_namespace \
        --statistic $alarm_statistic \
        --comparison-operator GreaterThanOrEqualToThreshold \
        --evaluation-periods $alarm_evaluation_periods \
        --period $alarm_period \
        --threshold $alarm_threshold \
        --actions-enabled \
        --alarm-actions $sns_topic_arn \
        $aws_cli_args
  
    log_to_console $? create_metric_alarm
}

function rollback(){
    aws cloudwatch delete-alarms --alarm-names $alarm_name $aws_cli_args

    sns_topic_arn=$(aws sns list-topics $aws_cli_args | jq '.Topics[].TopicArn' | fgrep scdt-err | sed 's/"//g')
    aws sns delete-topic --topic-arn $sns_topic_arn $aws_cli_args

    aws logs delete-metric-filter --log-group-name $log_group_name --filter-name $metric_filter_name $aws_cli_args
}


# Main
aws_profile=default
aws_output=json
aws_cli_args="--profile $aws_profile --output json --no-cli-pager"

log_group_name=scdt-service-scdt
metric_filter_name=scdt-log-err
metric_filter_pattern=ERROR
metric_name=scdt-err-metric
metric_namespace=scdt
metric_value=1

sns_topic_name=scdt-err
email=yaoluoye@hotmail.com

alarm_name=scdt-err-alarm
alarm_evaluation_periods=1
alarm_statistic=Sum
alarm_period=300
alarm_threshold=10

create_metric_filter
create_sns_topic
create_metric_alarm

# rollback