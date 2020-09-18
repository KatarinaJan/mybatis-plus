该项目参照MyBatis-plus官网做了部分练习，
主要包含几个插件的使用以及MetaObjectHandler
插件：MybatisPlusConfig类
	乐观锁插件
	分页插件
	逻辑删除插件
	SQL性能分析插件
元对象：MyMetaObjectHandler
	创建时间和修改时间设置默认值
	版本号设置默认值
	删除标记设置默认值
日志：使用slf4j
	# 引入
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	# 定义
	private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);
	# 使用
	LOGGER.info("内容");
测试类：MybatisPlusApplicationTests
	做了关于baseMapper的一些基本增删改查的操作
条件类：QueryWrapperTests
	做了一些构造条件的操作(where子句)
	
最后附上本人参考的MyBatis-plus文档官网：https://baomidou.com/guide/crud-interface.html
作者：Jan