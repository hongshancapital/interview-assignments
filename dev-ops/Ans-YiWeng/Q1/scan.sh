#!/usr/bin/env bash
set -o errexit

input_file="DevOps_interview_data_set"

gunzip -c ./${input_file}.gz > ./${input_file}

# Cleaning log format
cat ${input_file} |\
  sed -e"s/,/+/g" |\
  sed -e ':a' -e 'N' -e '$!ba' -e 's/\n'$'\t/+/g' |\
  sed -e ':a' -e 'N' -e '$!ba' -e 's/\n[ ][ ][ ][ ]*/+/g' |\
  sed -e ':a' -e 'N' -e '$!ba' -e 's/ (/+(/g' > temp

# Convert repeate message
awk '/last message repeated 1 time+/{print a;}{a=$0}' temp >> temp
grep -v 'last message repeated 1 time' temp > temp.tmp

# Split event and description
cut -d' ' -f1,2,3,4 temp.tmp |\
  sed -e"s/ /,/g"> even.csv
cut -d':' -f3 temp.tmp |\
  cut -d' ' -f3- |\
  sed -e"s/ /+/g"> process.csv
cut -d':' -f4- temp.tmp |\
  sed -e"s/ /+/g"> desc.csv

# Put column names to the first line
/bin/echo 'Month,Date,Time,DeviceName,Process,Description' > log.csv

# Merge back content, and clean temporary files
paste -d, $FILE even.csv process.csv desc.csv |\
tr -d "\r">> log.csv
rm -r even.csv process.csv desc.csv temp temp.tmp
