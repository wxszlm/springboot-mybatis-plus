package top.yxf.mybatisplus;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.yxf.mybatisplus.domain.User;
import top.yxf.mybatisplus.mapper.UserMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisPlusApplicationTests {

    @Autowired
    UserMapper userMapper;


    @Test
    public void testSelect() {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("name","一乡风");
        // 查询全部用户
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);

    }

    @Test
    public void testInsert() {

        User user = new User();
        user.setAge(23);
        user.setName("一乡风");
        user.setEmail("123465232777@qq.com");
        final int result = userMapper.insert(user);

        // 1250308475397873667
        // 1250308475397873668
        System.out.println("当前新增的用户编号是---->" + user.getId());

        System.out.println("受影响的行数 -------->" + result);
    }

    @Test
    public void testUpdate() {

        User user = new User();
        user.setAge(23);
        user.setName("一乡风");
        user.setEmail("941908967@qq.com");
        user.setId(6L);
        final int result = userMapper.updateById(user);

        // 1250308475397873667
        // 1250308475397873668
        System.out.println("当前新增的用户编号是---->" + user.getId());

        System.out.println("受影响的行数 -------->" + result);
    }

    /**
     * 测试乐观锁
     */
    @Test
    public void testOptimisticLocker() {

        final User user = userMapper.selectById(6L);
        user.setName("是码农你就来");

        userMapper.updateById(user);

    }

    /**
     * 测试乐观锁失败！多线程下
     */
    @Test
    public void testOptimisticLocker2() {

        // 线程 1
        User user = userMapper.selectById(1L);
        user.setName("yixiangfeng1111");
        user.setEmail("941908967@qq.com");

        // 模拟另外一个线程执行了插队操作
        User user2 = userMapper.selectById(1L);
        user2.setName("yixiangfeng2222");
        user2.setEmail("941908967@qq.com");

        // 自旋锁来多次尝试提交！
        userMapper.updateById(user2);
        // 如果没有乐观锁就会覆盖插队线程的值!
        userMapper.updateById(user);

    }

    /**
     * 测试查询
     */
    @Test
    public void testSelectById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    /**
     * 测试批量查询
     */
    @Test
    public void testSelectByBatchId() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    /**
     * 按条件查询之一使用map操作
     */
    @Test
    public void testSelectByBatchIds() {
        // 自定义要查询
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "一乡风java");
        map.put("age", 3);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    /**
     * 测试分页
     */
    @Test
    public void testPage() {
        // 参数一：当前页
        // 参数二：页面大小
        // 使用了分页插件之后，所有的分页操作也变得简单的！
        Page<User> page = new Page<>(2, 5);
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
    }

    /**
     * 删除
     */
    @Test
    public void testDeleteById() {
        userMapper.deleteById(1L);
    }

    /**
     * 通过id批量删除
     */
    @Test
    public void testDeleteBatchId() {
        userMapper.deleteBatchIds(Arrays.asList(1240620674645544961L, 1240620674645544962L));
    }

    /**
     * 通过map删除
     */
    @Test
    public void testDeleteMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "一乡风Java");
        userMapper.deleteByMap(map);
    }

    /**
     * 查询name不为空的用户，并且邮箱不为空的用户，年龄大于等于12
     */
    @Test
    public void testQueryNameAndEmailNotNullAndAge() {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name").isNotNull("email").ge("age", 12);
        userMapper.selectList(wrapper).forEach(System.out::println);
    }

    @Test
    public void test2() {
        // 查询名字狂神说
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "一乡风");
        User user = userMapper.selectOne(wrapper);
        // 查询一个数据，出现多个结果使用List 或者 Map
        System.out.println(user);
    }

    @Test
    public void test3() {
        // 查询年龄在 20 ~ 30 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age", 20, 30);
        // 区间
        Integer count = userMapper.selectCount(wrapper);
        // 查询结果数
        System.out.println(count);
    }

    // 模糊查询
    @Test
    public void test4() {
        // 查询年龄在 20 ~ 30 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 左和右 t%
        wrapper.notLike("name", "e").likeRight("email", "t");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);

    }

    // 模糊查询
    @Test
    public void test5() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // id 在子查询中查出来
        wrapper.inSql("id", "select id from user where id<3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    @Test
    public void test6() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 通过id进行排序
        wrapper.orderByAsc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * 测试动态数据源
     */
    @Test
    public void testDynamicDataSourceSlave(){
        final List<User> users = userMapper.listUser();
        users.forEach(System.out::println);
    }

    /**
     * 测试动态数据源
     */
    @Test
    public void testDynamicDataSourceMaster(){
        final List<User> users = userMapper.listUser(null);
        users.forEach(System.out::println);
    }

}
