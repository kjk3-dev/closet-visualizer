@echo off
cd /d %~dp0
chcp 65001 > nul
cls
javac -encoding UTF-8 -d bin -cp "lib\*" -sourcepath src src\com\example\closetvisualizer\Main.java
copy /Y src\db.properties bin\db.properties > nul
java -Dfile.encoding=UTF-8 -cp "bin;lib\*" com.example.closetvisualizer.Main
pause