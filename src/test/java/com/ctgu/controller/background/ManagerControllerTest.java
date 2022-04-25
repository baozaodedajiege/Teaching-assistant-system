package com.ctgu.controller.background;

import com.ctgu.controller.ManageController;
import com.ctgu.model.Account;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest


//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional

@WebAppConfiguration

public class ManagerControllerTest {
    private static Log LOG = LogFactory.getLog(ManageController.class);
    @Autowired
    ManageController manageController;

    protected MockMvc mockMvc;


    @Autowired
    protected WebApplicationContext wac;

    @Before()  //这个方法在每个方法执行之前都会执行一遍
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
    }

    @Test
    public void login() {

    }

    @Test
    public void accountList() {
    }

    @Test
    public void accountLevelList() throws Exception{
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("level",1);
//        map.put("page",1);
        String jsonStr= JSONObject.fromObject(map).toString();
//        String jsonStr= JSON.toJSONString(map);
        RequestBuilder request = MockMvcRequestBuilders.post("/account/level/list")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonStr);
//        RequestBuilder request = MockMvcRequestBuilders.get("/account/{level}/list")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("level","1")
//                .param("page","1");

        String responseString = mockMvc.perform(request)     //添加参数
                .andExpect(status().isOk())    //返回的状态是200
                .andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();  //将相应的数据转换为字符串
        System.out.println("--------返回的json = " + responseString);

    }

    @Test
    public void contestList() throws Exception {
        Account current_account = new Account();
        current_account.setId(2);
        current_account.setUsername("yq");
        current_account.setPassword("123456");
        current_account.setLevel(1);
        RequestBuilder request = MockMvcRequestBuilders
                .get("http://localhost:8080/manage/contest/list");
//                .sessionAttr("current_account",current_account);
        String responseString = mockMvc.perform(request)     //添加参数
                .andExpect(status().isOk())    //返回的状态是200
                .andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();  //将相应的数据转换为字符串
        System.out.println("--------返回的json = " + responseString);

    }

    @Test
    public void contestProblemList() {
    }

    @Test
    public void questionList() {
    }

    @Test
    public void resultContestList() {
    }

    @Test
    public void resultStudentList() {
    }

    @Test
    public void subjectList() {
    }

    @Test
    public void classList() {
    }

    @Test
    public void classStudentList() {
    }

    @Test
    public void postList() {
    }

    @Test
    public void getSyllabus() {
    }

    @Test
    public void commentList() {
    }

    @Test
    public void getQuestionnaire() {
    }

    @Test
    public void index() {
    }

    @Test
    public void getQuestionnaireById() {
    }

    @Test
    public void getQuestionnaireReplyById() {
    }

    @Test
    public void testGetQuestionnaireReplyById() {
    }
}