1. Set config.properties for connection details .
2. Set StartDate and EndDate for search query . To list all the expired contents use blank values 





in unix

3. 


. ./classpath


4. To get expired content :


java -classpath $CLASSPATH GetExpired

Content.txt will have the expired date 

5. To update the expired Date :

java -classpath $CLASSPATH UpdateExpiredDate


in windows

3. call classpath.bat

4. java -classpath %CLASSPATH% GetExpired

5 . java -classpath %CLASSPATH% UpdateExpiredDate


OR



java -classpath .oracle.ucm.ridc.jar;. GetExpired


java -classpath .oracle.ucm.ridc.jar;. UpdateExpiredDate 



Sample run:

[sterin@sterinlap expiredContent]$ . ./classpath
[sterin@sterinlap expiredContent]$ java GetExpired 
ContentID is : STJACOBPC1IDCO003530 3131
ContentID is : STJACOBPC1IDCO003522 3123
ContentID is : STJACOBPC1IDCO003527 3128
[sterin@sterinlap expiredContent]$ java UpdateExpiredDate
Updating STJACOBPC1IDCO003530


@Properties LocalData
UserDateFormat=iso8601
IdcService=UPDATE_DOCINFO
dDocName=STJACOBPC1IDCO003530
UserTimeZone=UTC
dOutDate=2017-04-29 08:59:00
dID=3131
@end
Updating STJACOBPC1IDCO003522


@Properties LocalData
UserDateFormat=iso8601
IdcService=UPDATE_DOCINFO
dDocName=STJACOBPC1IDCO003522
UserTimeZone=UTC
dOutDate=2017-04-29 08:59:00
dID=3123
@end
Updating STJACOBPC1IDCO003527


@Properties LocalData
UserDateFormat=iso8601
IdcService=UPDATE_DOCINFO
dDocName=STJACOBPC1IDCO003527
UserTimeZone=UTC
dOutDate=2017-04-29 08:59:00
dID=3128
@end
[sterin@sterinlap expiredContent]$ java GetExpired 
[sterin@sterinlap expiredContent]$ 

Script to Set new dOutDate for Expired Content Using RIDC (Doc ID 2139331.1)

