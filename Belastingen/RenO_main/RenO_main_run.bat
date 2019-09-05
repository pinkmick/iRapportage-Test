%~d0
cd %~dp0
java -Xms256M -Xmx1024M -cp .;../lib/routines.jar;../lib/dom4j-1.6.1.jar;../lib/json-20160810.jar;../lib/log4j-1.2.16.jar;../lib/postgresql-8.3-603.jdbc3.jar;../lib/postgresql-9.4-1201.jdbc41.jar;reno_main_0_1.jar;contextreader_2_0.jar;reno_kws_cases_0_1.jar;reno_kws_cases_open_0_1.jar; reno.reno_main_0_1.RenO_main --context=Default %* 