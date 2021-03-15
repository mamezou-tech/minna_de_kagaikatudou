#!/bin/bash -x
cd /home/ec2-user/cicdSampleApp
java -Dserver.port=80 -Dserver.sockets.0.port=443 -jar cicd-sample-main.jar > /dev/null 2>&1 < /dev/null &
echo "[cicdSampleAppliction]START SEVER"
