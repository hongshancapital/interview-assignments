#!/bin/bash

#echo please enter the location of "interview_data_set.gz"
#echo eg: c:/users/admin (*note: please DO NOT include the slash in the end)
#IFS= read -rp "Please input the directory: " inputDir
#UnixPath="${inputDir//\\//}"
#echo $inputDir
mkdir log
#sleep
cp interview_data_set.gz ./log/interview_data_set.gz

#echo $inputDir > input_dir.txt

#sleep
pip install pandas
pip install re
pip install os
pip install gzip
pip install json
pip install numpy


python log_analysis_bash.py

#copy log.json $inputDir/log/log.json



curl -X POST -H "Content-Type: application/json" -d @log.json http://www.foo.com/bar

sleep