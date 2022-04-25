package com.ctgu.service;

import com.ctgu.model.Question;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceTest {
    private static Log LOG = LogFactory.getLog(QuestionService.class);

    @Autowired
    private QuestionService questionService;

    @Test
    public void addQuestion() {
        Question question = new Question();
        question.setTitle("emmmmmmm");
        question.setContent("emmmmmmmm");
        question.setContestId(4);
        question.setSubjectId(8);
        int result = questionService.addQuestion(question);
        LOG.info("result = " +result);
    }

    @Test
    public void updateQuestion() {
        Question question = new Question();
        question.setId(16);
        question.setTitle("hhhhh");
        boolean result = questionService.updateQuestion(question);
        LOG.info("result = " +result);
    }

    @Test
    public void getQuestionsByContestId() {
        List<Question> questions = questionService.getQuestionsByContestId(4);
        for(Question question : questions){
            LOG.info("question = " + question);
        }
    }

    @Test
    public void getQuestionsByContent() {
        Map<String, Object> data = new HashMap<>();
        data = questionService.getQuestionsByContent(1,5,"数据库");
        List<Question> questions = (List<Question>) data.getOrDefault("questions", new ArrayList<>());
        for (Question question : questions) {
            LOG.info("question = " + question);
        }
    }

    @Test
    public void getQuestionsByProblemsetIdAndContentAndDiffculty() {
        Map<String, Object> data = new HashMap<>();
        data = questionService.getQuestionsByProblemsetIdAndContentAndDiffculty(1,5,8,"数据库",1);
        List<Question> questions = (List<Question>) data.getOrDefault("questions", new ArrayList<>());
        for (Question question : questions) {
            LOG.info("question = " + question);
        };
    }

    @Test
    public void deleteQuestion() {
        boolean result = questionService.deleteQuestion(16);
        LOG.info("result = " +result);
    }

    @Test
    public void getQuestionById() {
        Question question = questionService.getQuestionById(14);
        LOG.info("question = " + question);
    }

    @Test
    public void updateQuestionsStateByContestId() {
        boolean result = questionService.updateQuestionsStateByContestId(0,0);
        LOG.info("result = " + result);
    }
}
