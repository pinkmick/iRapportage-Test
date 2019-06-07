#!/bin/sh
# current location of this script
IBZPATH="$(cd "$(dirname "$0")" && pwd -P)"
# get date for loggile
MYDATE="$(date +"%Y-%m-%d")"
# go to the base location of circoo
cd ../../../..
CIRBASE="$(cd "$(dirname "$0")" && pwd -P)"
cd ${IBZPATH}
# define the logfile and delete an older version
LOGFILE=${CIRBASE}/CirCooNodejs/log/iRapportage.log.${MYDATE}
if [ -e $LOGFILE ]
then rm $LOGFILE 
fi
# call circoo script for the correct javahome
source ${CIRBASE}/CirCooNodejs/res/setJavaHome.sh
echo iRapportage: ${IBZPATH}
echo CIRBASE: ${CIRBASE}
cd ${IBZPATH}
ERRCODE=0

echo `date '+%Y-%m-%d %H:%M:%S'`  >> $LOGFILE
# if configfiles exist copy them to iRapportage and run the job
if [[ -f ${CIRBASE}/iRapContext/concipers.txt ]]
then
	cp -f ${CIRBASE}/iRapContext/concipers.txt ${IBZPATH}/concipers.txt
	cp -f ${CIRBASE}/iRapContext/conmks.txt ${IBZPATH}/conmks.txt

	echo [JAVA_HOME] set to: ${JAVA_HOME}
	echo [JAVA_HOME] set to: ${JAVA_HOME}  >> $LOGFILE

	echo Running the ibz_cipers_ldf_run.sh file from: ${IBZPATH}
	echo Running the ibz_cipers_ldf_run.sh file from: ${IBZPATH} >> $LOGFILE
	echo [Module iNzicht] iRapportage: 
	echo [Module iNzicht] iRapportage:  >> $LOGFILE
	${IBZPATH}/ibz_cipers_ldf_run.sh 2>> $LOGFILE
# if errorlevel <> 0
	if [ $? != 0 ]
	then
		ERRCODE=1
		echo ERROR: ${ERRCODE}
		echo ERROR: ${ERRCODE}  >> $LOGFILE
	fi
else
	ERRCODE=2
	echo Configuratie contextfiles nog niet aanwezig!
	echo Configuratie contextfiles nog niet aanwezig! >> $LOGFILE
	echo ERROR: ${ERRCODE}
	echo ERROR: ${ERRCODE}  >> $LOGFILE

fi

echo Done
echo Done >> $LOGFILE

# exit the script with ERRCODE content as result
exit ${ERRCODE}