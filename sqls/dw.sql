
-- CDH集群
drop table if exists app_log_json;
create table ods.app_log_json(
log string
)
partitioned by (dt string)
stored as textfile;


-- 非CDH集群
drop table if exists app_log_json;
create table app_log_json (
 account        string,
 appId          string,
 appVersion     string,
 carrier        string,
 deviceId       string,
 deviceType     string,
 eventId        string,
 eventInfo      map<string,string>,
 ip             string,
 latitude       string,
 longitude      string,
 netType        string,
 osName         string,
 osVersion      string,
 releaseChannel string,
 resolution     string,
 sessionId      string,
 `timeStamp`      bigint
)
partitioned by (dt string)
row format serde 'org.openx.data.jsonserde.JsonSerDe'
stored as textfile
;

load data local inpath '/root/logs/doit.mall.access.log.12' into table app_log_json partition(dt='20200712');
load data local inpath '/root/logs/doit.mall.access.log.13' into table app_log_json partition(dt='20200713');
load data local inpath '/root/logs/doit.mall.access.log.14' into table app_log_json partition(dt='20200714');
load data local inpath '/root/logs/doit.mall.access.log.15' into table app_log_json partition(dt='20200715');

-- idm层建库
create database dim;
use dim;

-- id映射维表
drop table if exists dim_idmp;
create table dim_idmp(deviceid string,account string)
partitioned by (dt string)
stored as parquet
;
insert into table dim_idmp partition(dt='20200712') values('','');

-- geohash地理维表
drop table  if exists  dim_geo;
create table dim_geo(
geo_hash string,
province string,
city string,
district string
)
stored as parquet;
insert into table dim_geo values('','','','');


-- dwd
drop table if exists app_log_dtl;
create table app_log_dtl(
account        string               ,
isnew          int                  ,
appId          string               ,
appVersion     string               ,
carrier        string               ,
deviceId       string               ,
deviceType     string               ,
eventId        string               ,
eventInfo      map<string,string>   ,
ip             string               ,
latitude       double               ,
longitude      double               ,
netType        string               ,
osName         string               ,
osVersion      string               ,
releaseChannel string               ,
resolution     string               ,
sessionId      string               ,
province       string               ,
city           string               ,
district       string               ,
ts             bigint               ,
datestr        string               ,
year           string               ,
month          string               ,
day            string
)
    partitioned by (dt string)
    stored as parquet
;


