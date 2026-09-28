@echo off
cd /d %~dp0
chcp 65001 > nul
cls
java -Dfile.encoding=UTF-8 -cp "bin;lib\*" com.example.closetvisualizer.Main
pause