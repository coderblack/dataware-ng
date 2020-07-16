drop table if exists app_log_json;
create external table app_log_json (
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

load data local inpath '/root/logs/doit.mall.access.log.12' into table app_log_json partition(dt='2020-07-12');
load data local inpath '/root/logs/doit.mall.access.log.13' into table app_log_json partition(dt='2020-07-13');
load data local inpath '/root/logs/doit.mall.access.log.14' into table app_log_json partition(dt='2020-07-14');
load data local inpath '/root/logs/doit.mall.access.log.15' into table app_log_json partition(dt='2020-07-15');

-- dwd
drop table if exists dwd_app_log;
create table dwd_app_log(
                            account        string,
                            isnew          int,
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
                            province       string,
                            city           string,
                            district       string,
                            ts             bigint,
                            year           string,
                            month          string,
                            day            string,
                            timestr        string
)
    partitioned by (dt string)
    stored as orc
;

insert into table dwd_app_log partition(dt)
select

    account                 ,
    appId                   ,
    appVersion              ,
    carrier                 ,
    deviceId                ,
    deviceType              ,
    eventId                 ,
    eventInfo               ,
    ip                      ,
    latitude                ,
    longitude               ,
    netType                 ,
    osName                  ,
    osVersion               ,
    releaseChannel          ,
    resolution              ,
    sessionId               ,
    `timeStamp`             ,
    year(to_date(from_unixtime(cast(1594369376698/1000 as int)))) as year,
    month(to_date(from_unixtime(cast(1594369376698/1000 as int)))) as month,
    day(to_date(from_unixtime(cast(1594369376698/1000 as int)))) as day,
    dt

from app_log_json;

