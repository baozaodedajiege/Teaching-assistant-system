package com.ctgu.controller;

import com.ctgu.common.Const;
import com.ctgu.dto.AjaxResult;
import com.ctgu.model.Account;
import com.ctgu.model.Question;
import com.ctgu.model.Subject;
import com.ctgu.model.Syllabus;
import com.ctgu.service.QuestionService;
import com.ctgu.service.SubjectService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    private static Log LOG = LogFactory.getLog(QuestionController.class);

    @Autowired
    private QuestionService questionService;
    @Autowired
    private SubjectService subjectService;

    //获取所有题目
    @RequestMapping(value = "/api/questionList/{contestId}", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getOtherQuestionList(HttpServletRequest request, @PathVariable int contestId) {
        AjaxResult ajaxResult = new AjaxResult();
        Account currentAccount = (Account) request.getSession().getAttribute(Const.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            ajaxResult.setMessage("用户尚未登录");
        } else {
            List<Question> questions = questionService.getOtherQuestionsByContestId(contestId);
            ajaxResult.setData(questions);
        }
        return ajaxResult;
    }

    //添加考试题目
    @RequestMapping(value = "/api/addContestQuestion", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addContestQuestion(@RequestBody Question question) {
        boolean res = questionService.addContestQuestion(question);
        return new AjaxResult().setData(res);
    }

    //添加题目
    @RequestMapping(value = "/api/addQuestion", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addQuestion(@RequestBody Question question) {
        int questionId = questionService.addQuestion(question);
        return new AjaxResult().setData(questionId);
    }

    //更新题目信息
    @RequestMapping(value = "/api/updateQuestion", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateQuestion(@RequestBody Question question) {
        boolean result = questionService.updateQuestion(question);
        return new AjaxResult().setData(result);
    }

    //删除题目信息
    @DeleteMapping("/api/deleteQuestion/{id}")
    public AjaxResult deleteContest(@PathVariable int id) {
        boolean result = questionService.deleteQuestion(id);
        return new AjaxResult().setData(result);
    }

    //删除题目信息
    @ResponseBody
    @RequestMapping(value = "/api/deleteContestQuestion", method = RequestMethod.POST)
    public AjaxResult deleteContestQuestion(@RequestBody Question question) {
        boolean result = questionService.deleteContestQuestion(question);
        return new AjaxResult().setData(result);
    }
}
