ElasticSearch-5.6.3-maven-new-api
=====
基于ElasticSearch-5.6.3开发的自定义API接口
=====


5.6.3.1.9
-----
> * 1、增加返回检索状态
> * 2、增加创建索引返回类型，返回结果包含新增、覆盖、失败以及失败信息等详情

5.6.3.1.10
------
> * 1、增加聚合统计类型，数字范围统计
> * 2、修复executeQueryInfo查询,聚合结果无返回bug
> * 3、增加聚合统计类型，IP范围统计
> * 4、增加聚合统计类型，地理网格聚合


5.6.3.1.11 
------
> * 1、修复日期聚合，当某时间段为0文档时，缺失返回对应该日期的结果bug
> * 2、增加聚合统计类型，统计多组多字段复杂组合关键词的文档数，详情见：《聚合统计》-《关键词统计》
> * 3、修复修改数据成功后结果返回失败bug
> * 4、增加聚合类型返回结果字段别名参数，以修复之前相同字段名返回结果覆盖现象
> * 5、增加聚合统计类型，矩阵统计，详情见：《聚合统计》-《矩阵统计》
> * 6、打印输入结果增加层级导向线，方便查看层级。


5.6.3.1.12 
------
> * 1、新增：增加索引迁移接口，详情见：《索引管理》-《索引迁移》
> * 2、新增：创建索引别名接口，详情见：《索引管理》-《索引别名》
> * 3、新增：删除索引别名接口，详情见：《索引管理》-《索引别名》
> * 4、新增：查询索引别名接口，详情见：《索引管理》-《索引别名》
> * 5、新增：单独索引名称和类型配置
> * 6、新增：CasiaEsApi 异步关联操作接口

5.6.3.1.13 
------
> * 1、新增：文本分词接口，详解见：《文本分词》-《文本分词》
> * 2、新增：批量修改接口，详解见：《索引管理》-《修改数据》
> * 3、修复：批量删除，删除条件无法重置BUG
> * 4、修复：查询文档数量接口（executeQueryTotal）结果始终为0的BUG
> * 5、新增：分片管理接口，详解见：《索引管理》-《分片副本》

5.6.3.1.14 
------
> * 1、新增：修改新增数据接口，详解见：《索引管理》-《修改数据》
> * 2、新增：xshell 快速操作索引接口，详解见：《Linux操作》