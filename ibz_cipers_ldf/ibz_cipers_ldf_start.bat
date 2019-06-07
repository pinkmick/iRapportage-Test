echo off
rem get the correct path from the called program
SET ibzpath=%~dp0
rem define a logfile and date-stamp
for /F "tokens=2" %%i in ('date /t') do set mydate=%%i
SET LogFile=%ibzpath%..\..\..\..\CirCooNodejs\log\iRapportage.log.%mydate%
if exist %LogFile% (
	del %LogFile%
)
rem switch to the drive of the program
%~d0
rem first start in the path of the program since the rest depends on this
cd "%ibzpath%"
cd ..\..\..\..\CirCooNodejs\res
rem now we call the setJavaHome.bat of circoo. This sets the java and circoo base
call setJavaHome.bat
rem go back to the location of the program
cd "%ibzpath%"
echo iRapportage: %ibzpath%
echo cirbase: %CIR_BASE%
echo javahome: %JAVA_HOME%
SET /a ERRCODE=0
rem start the log with the date and time of running
@echo %date% %time% >> %LogFile%
if exist %CIR_BASE%/iRapContext/concipers.txt goto configured
set /a ERRCODE=%ERRCODE%+2
echo Configuratie contextfiles nog niet aanwezig!
echo Configuratie contextfiles nog niet aanwezig! >> %LogFile%
echo ERROR: %ERRCODE%  >> %LogFile%
goto end

:configured
copy /Y %CIR_BASE%\iRapContext\concipers.txt %ibzpath%\concipers.txt
copy /Y %CIR_BASE%\iRapContext\conmks.txt %ibzpath%\conmks.txt

@echo [JAVA_HOME] set to: %JAVA_HOME%
@echo [JAVA_HOME] set to: %JAVA_HOME% >> %LogFile%

@echo Running the ibz_cipers_ldf_run.bat file from: %ibzpath:~0,-1%
@echo Running the ibz_cipers_ldf_run.bat file from: %ibzpath:~0,-1% >> %LogFile%
@echo [Module iNzicht] iRapportage: 
@echo [Module iNzicht] iRapportage: >> %LogFile%
call %ibzpath%/ibz_cipers_ldf_run.bat 2>> %LogFile% 
IF %ERRORLEVEL% NEQ 0 (
    set /a ERRCODE=%ERRCODE%+1
	echo ERROR: %ERRCODE%  >> %LogFile%
)

:end
@echo Done
@echo Done  >> %LogFile%

