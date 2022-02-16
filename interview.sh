#!/bin/bash
DEVICENAME=`grep -iE '(error|failed)' interview_data_set | awk '{print $4}' | uniq`

for JINCHENG1 in `grep -iE '(error|failed)' interview_data_set | awk '{ if ($6 ~ /.*[0-9].*/) print $6 ; else print $5 }' | sort | sed -E 's/(\(?)(.*)(\[|\.)([0-9])(.*)/\2/' | uniq | grep '^com'` 
do  
	PROCESSNAME=$JINCHENG1
	DESCRIPTION=`grep -iE '(error|failed)' interview_data_set | grep $JINCHENG1  | awk '{ for(i=7;i<=NF;i++){printf $i" "}; print ""}' |sort -u`
	STARTTIME=`grep -iE '(error|failed)' interview_data_set | grep $JINCHENG1  | head -n1 | awk -F[:\ ] '{print $3$4}'`
	ENDTIME=`grep -iE '(error|failed)' interview_data_set | grep $JINCHENG1 | tail -n1 | awk -F[:\ ] '{print $3$4}'`
	
	H1=`grep -iE '(error|failed)' interview_data_set | grep $JINCHENG1  | head -n1 | awk -F[:\ ] '{print $3}'`
	H2=`grep -iE '(error|failed)' interview_data_set | grep $JINCHENG1 | tail -n1 | awk -F[:\ ] '{print $3}'`
	HOURS=`expr $H2 - $H1`
	NUMS=`grep -iE '(error|failed)' interview_data_set | grep $JINCHENG1  | wc -l`

	NUMOFOCC=`expr $NUMS / $HOURS`
	
        curl  -H "Content-Type: application/json" -d "{\"deviceName\":\"$DEVICENAME\",\"processId\",\"\",\"processName\":\"$PROCESSNAME\",\"description\":\"$DESCRIPTION\",\"timeWindow\":\"$STARTTIME-$ENDTIME\",\"numberOfOccurrence\":\"$NUMOFOCC\"}" -X POST https://foo.com/bar 
done



NUMS2=`grep -iE '(error|failed)' interview_data_set | sed -E 's/(.*)(BBAOMACBOOKAIR2)(.*)([\[\.])([0-9]+)(\])(.*)/\3/' | grep -v 'com' | sort -u  | wc -l`

for (( i=1 ; i<=$NUMS2 ; i++ ))
do
	PROCESSNAME2=`grep -iE '(error|failed)' interview_data_set | sed -E 's/(.*)(BBAOMACBOOKAIR2)(.*)([\[\.])([0-9]+)(\])(.*)/\3/' | grep -v 'com' | sort -u  | sed -n "${i}p"`
	DESCRIPTION2=`grep -iE '(error|failed)' interview_data_set | grep "$PROCESSNAME2"  | awk '{ for(i=7;i<=NF;i++){printf $i" "}; print ""}' |sort -u`
	PROCESSID2=`grep -iE '(error|failed)' interview_data_set | grep "$PROCESSNAME2"  | awk -F\[ '{print $2}' | awk -F\] '{print $1}'`
	STARTTIME2=`grep -iE '(error|failed)' interview_data_set | grep "$PROCESSNAME2"  | head -n1 | awk -F[:\ ] '{print $3$4}'`
	ENDTIME2=`grep -iE '(error|failed)' interview_data_set | grep "$PROCESSNAME2" | tail -n1 | awk -F[:\ ] '{print $3$4}'`
	
	H12=`grep -iE '(error|failed)' interview_data_set | grep "$PROCESSNAME2"  | head -n1 | awk -F[:\ ] '{print $3}'`
	H22=`grep -iE '(error|failed)' interview_data_set | grep "$PROCESSNAME2" | tail -n1 | awk -F[:\ ] '{print $3}'`
	HOURS2=`expr $H2 - $H1`
	NUMS2=`grep -iE '(error|failed)' interview_data_set | grep "$PROCESSNAME2"  | wc -l`

	NUMOFOCC2=`expr $NUMS / $HOURS`
	
        curl  -H "Content-Type: application/json" -d "{\"deviceName\":\"$DEVICENAME\",\"processId\",\"$PROCESSID2\",\"processName\":\"$PROCESSNAME2\",\"description\":\"$DESCRIPTION2\",\"timeWindow\":\"$STARTTIME2-$ENDTIME2\",\"numberOfOccurrence\":\"$NUMOFOCC2\"}" -X POST https://foo.com/bar 
done

