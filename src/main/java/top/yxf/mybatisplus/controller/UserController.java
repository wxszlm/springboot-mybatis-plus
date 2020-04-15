package top.yxf.mybatisplus.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import top.yxf.mybatisplus.common.ResponseVo;
import top.yxf.mybatisplus.service.UserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 一乡风
 * @since 2020-04-15
 */
@RestController
@RequestMapping("/mybatisplus/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "list")
    public ResponseVo listUser(){
        return ResponseVo.success(userService.listUser());
    }


}

