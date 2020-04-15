package top.yxf.mybatisplus.service;

import top.yxf.mybatisplus.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 一乡风
 * @since 2020-04-15
 */
public interface UserService extends IService<User> {

    /**
     * 查询所有的用户
      * @return
     */
    List<User> listUser();


}
