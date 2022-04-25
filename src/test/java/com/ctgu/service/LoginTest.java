package com.ctgu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author aoboxia
 * @date 2019/12/17 21:33
 */

/*
 * @Author: 李菊
 * @Date: 2019/12/17
 * @Description: 登录模块测试类
  */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTest {

    /*
    * @param userName, passWord 输入用户名和密码
    * @return true 返回布尔值true
    * */
    @Test
    public boolean login() {
        String userName = "admin";
        String passWord = "admin";
        return true;
    }
}
