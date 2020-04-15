package top.yxf.mybatisplus.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import top.yxf.mybatisplus.domain.User;

import java.util.List;


/**
 * @author DELL
 */
@Repository
public interface UserMapper extends BaseMapper<User> {


    /**
     * 查询所有的用户
     * @return
     */
    @DS(value = "slave")
    List<User> listUser();


    /**
     * 查询所有的用户
     * @return
     */
    @DS(value = "master")
    List<User> listUser(User user);

}
