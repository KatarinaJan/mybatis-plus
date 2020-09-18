package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.entity.User;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
class MybatisPlusApplicationTests {

    // userMapper处报错，因为找不到注入的对象，
    // 因为类是动态创建的，但是程序可以正确的执行。
    // 为了避免报错，可以在dao层的接口上添加@Repository注解
    @Autowired
    private  UserMapper userMapper;

    @Test
    void testSelectList() {
        System.out.println("----- selectAll method test -----");
        // UserMapper中的selectList()方法的参数为MP内置的条件封装器 Wrapper
        // 这里可以填写为无任何条件
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("Helen");
        user.setAge(18);
        user.setEmail("55317332@qq.com");

        int result = userMapper.insert(user);
        System.out.println("影响行数：" + result);
        System.out.println(user);   //id自动回调
    }

    @Test
    public void testUpdateById() {
        User user = new User();
        user.setId(1L);
        user.setAge(28);

        int result = userMapper.updateById(user);
        System.out.println("影响行数：" + result);
    }

    /**
     * 测试乐观锁
     */
    @Test
    public void testOptimisticLocker() {
        // 查询
        User user = userMapper.selectById(1L);
        // 修改数据
        user.setName("Helen Yao");
        user.setEmail("helen@qq.com");
        // 执行更新
        userMapper.updateById(user);
    }

    /**
     * 测试乐观锁失败
     */
    @Test
    public void testOptimisticLockerFail() {
        // 查询
        User user = userMapper.selectById(1L);
        // 修改数据
        user.setName("Helen Yao1");
        user.setEmail("helen1@qq.com");
        // 模拟取出数据后，数据库version实际数据比取出的值大，即已被其他线程修改并更新了version
        user.setVersion(user.getVersion() - 1);
        // 执行更新
        userMapper.updateById(user);
    }

    @Test
    public void testSelectById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    @Test
    public void testSelectBatchIds() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectByMap() {
        HashMap<String, Object> map = new HashMap<>();
        // map中的key对应的是数据库中的列名
        map.put("name", "Helen");
        map.put("age",18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    /**
     * 分页查询
     * User(id=2, name=Jack, age=20, email=test2@baomidou.com, createTime=null, updateTime=null, version=null)
     */
    @Test
    public void testSelectPage() {
        Page<User> page = new Page<>(1,5);
        IPage<User> userIPage = userMapper.selectPage(page, null);

        page.getRecords().forEach(System.out::println);
        System.out.println("当前页：" + page.getCurrent());
        System.out.println("总页数：" + page.getPages());
        System.out.println("页容量：" + page.getSize());
        System.out.println("总条数：" + page.getTotal());
        System.out.println("是否有下一页：" + page.hasNext());
        System.out.println("是否有上一页：" + page.hasPrevious());
        System.out.println("当前页记录：" + page.getRecords());
    }

    /**
     * 分页查询
     * 结果集为key-value格式
     * {name=Jack, id=2, age=20, email=test2@baomidou.com}
     */
    @Test
    public void testSelectMapsPage() {
        Page<User> page = new Page<>(1, 5);
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page, null);

        // 此行必须使用mapIpage获取记录数，否则会有数据类型转换错误
        mapIPage.getRecords().forEach(System.out::println);
        System.out.println("当前页：" + page.getCurrent());
        System.out.println("总页数：" + page.getPages());
        System.out.println("页容量：" + page.getSize());
        System.out.println("总条数：" + page.getTotal());
        System.out.println("是否有下一页：" + page.hasNext());
        System.out.println("是否有上一页：" + page.hasPrevious());
        System.out.println("当前页记录：" + page.getRecords());
    }

    @Test
    public void testDeleteById() {
        int result = userMapper.deleteById(8L);
        System.out.println("影响行数：" + result);
    }

    @Test
    public void testDeleteBatchIds() {
        int result = userMapper.deleteBatchIds(Arrays.asList(8, 9, 10));
        System.out.println("影响行数：" + result);
    }

    @Test
    public void testDeleteByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Helen");
        map.put("age", 18);

        int result = userMapper.deleteByMap(map);
        System.out.println("影响行数：" + result);
    }

    /**
     * 测试逻辑删除
     * 被删除数据的deleted字段的值必须是 0，才能被选取出来执行逻辑删除的操作
     */
    @Test
    public void testLogicDelete() {
        int result = userMapper.deleteById(1L);
        System.out.println("影响行数：" + result);
    }

    /**
     * 测试 逻辑删除后的查询：
     * 不包括被逻辑删除的记录
     */
    @Test
    public void testLogicDeleteSelect() {
        User user = new User();
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    /**
     * 测试 性能分析插件
     */
    @Test
    public void testPerformance() {
        User user = new User();
        user.setName("我是Helen");
        user.setEmail("helen@sina.com");
        user.setAge(18);
        userMapper.insert(user);
    }

}
