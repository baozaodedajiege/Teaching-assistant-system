package com.ctgu.controller.foreground;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.ctgu.controller.AccountController;
import com.ctgu.dto.AjaxResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/*
 *@Author： 李菊
 *@Date： 2019/12/25
 *@Description：对于登录的测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class AccountControllerForeTest
{
    private static Log LOG = LogFactory.getLog(AccountController.class);
    @Autowired
    private AccountController accountController;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;


    @Before
    public void setUp(){
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();


    }

    /*
     *@Param：正确的username：admin；正确的password：admin
     *@return：用户字典
     *@Description：输入正确的用户名和密码输出结果
     **/

    @Test
    public void testLogin() {
        //创建request对象
        request = new MockHttpServletRequest();
        request.setParameter("username","admin");
        request.setParameter("password","admin");
        //调用controller需要测试的方法
        AjaxResult ajaxResult = accountController.login(request,response);
        LOG.info(String.valueOf(ajaxResult));
    }

    /*
     *@Param：错误的username：123；正确的password：admin
     *@return：3002错误页面
     *@Description：输入错误的用户名和密码输出结果
     **/
    @Test
    public void testErrorName() {
        //创建request对象
        request = new MockHttpServletRequest();
        request.setParameter("username","123");
        request.setParameter("password","admin");
        //调用controller需要测试的方法
        AjaxResult ajaxResult = accountController.login(request,response);
        LOG.info(String.valueOf(ajaxResult));
    }

    /*
     *@Param：正确的username：admin；错误的password：123
     *@return：用户字典
     *@Description：输入正确的用户名和密码输出结果
     **/
    @Test
    public void testErrorPassword() {
        //创建request对象
        request = new MockHttpServletRequest();
        request.setParameter("username","admin");
        request.setParameter("password","123");
        //调用controller需要测试的方法
        AjaxResult ajaxResult = accountController.login(request,response);
        LOG.info(String.valueOf(ajaxResult));
    }

}
