# kettle-metadata-plugin
kettlt transformation step plugin
自定义kettle transstep组件，实现功能：
1. 读取在数据库中的配置信息表，主要有字段：表名、字段名、元数据id
2. 通过元数据id查询出所有元数据，返回的一个String数组
3. 检验配置信息表中字段的值，是否在String限定的值域内。


