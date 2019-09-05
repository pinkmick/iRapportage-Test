#!/bin/sh
cd `dirname $0`
ROOT_PATH=`pwd`
java -Xms256M -Xmx1024M -cp .:$ROOT_PATH:$ROOT_PATH/../lib/routines.jar:$ROOT_PATH/../lib/dom4j-1.6.1.jar:$ROOT_PATH/../lib/json-20160810.jar:$ROOT_PATH/../lib/log4j-1.2.16.jar:$ROOT_PATH/../lib/postgresql-8.3-603.jdbc3.jar:$ROOT_PATH/../lib/postgresql-9.4-1201.jdbc41.jar:$ROOT_PATH/reno_main_0_1.jar:$ROOT_PATH/contextreader_2_0.jar:$ROOT_PATH/reno_kws_cases_0_1.jar:$ROOT_PATH/reno_kws_cases_open_0_1.jar: reno.reno_main_0_1.RenO_main --context=Default "$@" 