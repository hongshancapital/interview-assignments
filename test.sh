#!/bin/bash
#Author:shihb
#Date:20220219
#Version:v1.0
#Describe: 查找日志中关于“tid:8813 - Mux ID not found in mapping dictionary” 这条日志信息


url="https://foo.com/bar"
json='{"deviceName":"BBAOMACBOOKAIR2","processId":"976","processName":"AMPDeviceDiscoveryAgent","description":"tid:8813 - Mux ID not found in mapping dictionary","timeWindow":["0700-0800","0800-0900","0900-1000"],"numberOfOccurrence":{"0700-0800":8,"0800-0900":40,"0900-1000":16}}'
curl -s -H "Content-Type: application/json" -X POST -d "\'$json\'" $url
