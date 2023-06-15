#!/bin/bash

# 定义日志文件路径
logFilePath="path/to/interview_data_set.gz"

# 解压缩日志文件
uncompressedFile="interview_data_set"
gunzip -c "$logFilePath" > "$uncompressedFile"

# 解析日志文件并提取关键信息
while IFS= read -r line; do
  # 提取关键信息
  deviceName=$(echo "$line" | grep -oP '(?<=BBAOMACBOOKAIR2 ).*?(?= com)')
  processId=$(echo "$line" | grep -oP '(?<=\(com.apple.mdworker.bundles\[)[0-9]+(?=\])')
  processName=$(echo "$line" | grep -oP '(?<=\()(.*?)(?=\[)')
  description=$(echo "$line" | grep -oP '(?<=ASL Sender Statistics$).+')
  timeWindow=$(echo "$line" | grep -oP '(?<=May [0-9]+ )[0-9]{2}:[0-9]{2}:[0-9]{2}')
  numberOfOccurrence=1

  # 构建关键信息的JSON对象
  json=$(jq -n \
    --arg deviceName "$deviceName" \
    --arg processId "$processId" \
    --arg processName "$processName" \
    --arg description "$description" \
    --arg timeWindow "$timeWindow" \
    --arg numberOfOccurrence "$numberOfOccurrence" \
    '{ deviceName: $deviceName, processId: $processId, processName: $processName, description: $description, timeWindow: $timeWindow, numberOfOccurrence: $numberOfOccurrence }')

  # 发送POST请求将JSON数据上传到服务器
  curl -X POST -H "Content-Type: application/json" -d "$json" https://foo.com/bar
done <"$uncompressedFile"

# 删除临时文件
rm "$uncompressedFile"
