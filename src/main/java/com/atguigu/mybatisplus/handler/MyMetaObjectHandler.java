package com.atguigu.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.javassist.tools.reflect.Metaobject;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * @Project: mybatis-plus
 * @Describe: 描述
 * @Author: Jan
 * @Date: 2020-09-01 16:16
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyMetaObjectHandler.class);

    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("***Jan*** start insert fill...");
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        // 设置乐观锁版本号初始值
        this.setFieldValByName("version", 1, metaObject);
        // 设置逻辑删除初始值
        this.setFieldValByName("deleted", 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LOGGER.info("***Jan*** start update fill...");
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }


}
