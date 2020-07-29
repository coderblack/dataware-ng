# DOIT用户行为数据模拟生产系统

## 系统功能
v1.0 全自动全时段模拟各（泛电商）行业app,wx,h5,web端所形成的用户行为日志  
v2.0 全自动全时段模拟各（泛电商）行业所产生的业务数据表数据（待开发）  
系统所产生的的日志数据，可以生成为本地日志文件，也可以直接写入kafka集群

## 系统部属
- 准备好一个mysql，并建库：realtimedw
- 将initdata下的area_dict.sql/doit_mall.sql/realtimedw.sql 导入上一步建好的库
- 修改配置文件
    - kafka.properties 仅当需要把行为数据写入kafka时需要正确配置
    - log4j.properties 仅当需要把行为数据写入日志文件时需要正确配置
    - other.properties 主配置文件，可以在此配置mysql连接参数，日志输出方式等
    
