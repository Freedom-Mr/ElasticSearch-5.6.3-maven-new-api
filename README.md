ElasticSearch-5.6.3-maven-new-api
=====
基于ElasticSearch-5.6.3开发的自定义API接口
=====


5.6.3.1.9
-----
>> * 1、增加返回检索状态
>> * 2、增加创建索引返回类型，返回结果包含新增、覆盖、失败以及失败信息等详情

5.6.3.1.10
------
>> * 1、增加聚合统计类型，数字范围统计
>> * 2、修复executeQueryInfo查询,聚合结果无返回bug
>> *  3、增加聚合统计类型，IP范围统计
>> * 4、增加聚合统计类型，地理网格聚合


5.6.3.1.11 
------
>> * 1、修复日期聚合，时间段0文档时，无法返回对应的日期结果
>> * 2、增加聚合统计类型，矩阵统计
