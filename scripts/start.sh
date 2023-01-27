#!/bin/bash

PROJECT_ROOT=/home/ec2-user/auto_deploy/app
BUILD_ROOT=/home/ec2-user/auto_deploy/build
BUILD_JAR_FILE=$BUILD_ROOT/spring-webapp.jar

APP_LOG="$BUILD_ROOT/application.log"
ERROR_LOG="$BUILD_ROOT/error.log"
DEPLOY_LOG="$BUILD_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# build 파일 복사
echo "$BUILD_JAR_FILE 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/build/libs/*.jar $BUILD_JAR_FILE

# jar file 실행
echo "> 새 애플리케이션 배포"
echo "$TIME_NOW > $BUILD_JAR_FILE 파일 실행" >> $DEPLOY_LOG
nohup java -jar -Dspring.config.location=classpath:/application.yml,$BUILD_ROOT/application-prod.yml $BUILD_JAR_FILE > $APP_LOG 2> $ERROR_LOG &

# 실행된 프로세스 확인
CURRENT_PID=$(pgrep -f $BUILD_JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG