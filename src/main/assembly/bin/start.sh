#!/bin/bash

# 只输出错误信息到日志文件
# nohup java -Dloader.path=.,lib -jar ratel-boot-test-1.0-SNAPSHOT.jar org.ratel.AppRun >/dev/null 2>ratel-boot-test-error.log &

nohup java -Dloader.path=.,lib -jar ../ratel-boot-test-1.0-SNAPSHOT.jar org.ratel.AppRun >/dev/null 2>&1 &