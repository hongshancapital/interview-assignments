@echo off

echo please enter the location of "interview_data_set.gz"
echo eg: c:/users/admin (*note: please DO NOT include the slash in the end)
set /p inputDir=Please input the directory:
cd %inputDir%
mkdir log
pause
copy interview_data_set.gz %inputDir%\log\interview_data_set.gz
cd %inputDir%
echo %inputDir% > input_dir.txt

pause

pip install pandas
pip install re
pip install os
pip install gzip
pip install json
pip install numpy


python log_analysis.py

copy log.json %inputDir%\log\log.json

powershell .\post_powershell.ps1
pause