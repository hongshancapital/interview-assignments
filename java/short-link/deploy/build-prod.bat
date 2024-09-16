@ECHO OFF

echo %cd%

cd /d %cd%

cd ..

echo ********current path*********: %cd%
echo ************start compile ************

cmd /k mvn clean compile package -e  -Dmaven.test.skip=true -P prod

echo ************end************