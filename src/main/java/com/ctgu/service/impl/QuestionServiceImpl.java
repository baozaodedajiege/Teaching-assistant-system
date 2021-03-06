package com.ctgu.service.impl;

import com.ctgu.dao.Contest_QuestionMapper;
import com.ctgu.dao.SubjectMapper;
import com.ctgu.model.Contest_Question;
import com.ctgu.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.ctgu.dao.ContestMapper;
import com.ctgu.dao.QuestionMapper;
import com.ctgu.model.Contest;
import com.ctgu.model.Question;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

    private static Log LOG = LogFactory.getLog(QuestionServiceImpl.class);

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ContestMapper contestMapper;
    @Autowired
    private Contest_QuestionMapper contest_questionMapper;
    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public int addQuestion(Question question) {
        if (question.getContestId() == 0) {
            subjectMapper.updateSubjectQuestionNum();
            question.setState(1);
        } else {
            question.setState(0);
            Contest contest = contestMapper.getContestById(question.getContestId());
            contest.setTotalScore(contest.getTotalScore()+question.getScore());
            contestMapper.updateContestById(contest);
            questionMapper.addContestQuestion(contest.getId(), question.getId());
        }
        return questionMapper.insertQuestion(question);
    }

    @Override
    public boolean updateQuestion(Question question) {
        if (question.getContestId() != 0) {
            Contest contest = contestMapper.getContestById(question.getContestId());
            Question sourceQuestion = questionMapper.getQuestionById(question.getId());
            contest.setTotalScore(contest.getTotalScore()-sourceQuestion.getScore()
                    +question.getScore());
            contestMapper.updateContestById(contest);
        }
        return questionMapper.updateQuestionById(question) > 0;
    }

    @Override
    public List<Question> getQuestionsByContestId(int contestId) {
        List<Integer> questionIdList = contest_questionMapper.selectQuestionByContestId(contestId);
        List<Question> questionList = new ArrayList<>();
        for(Integer id : questionIdList) {
            Question tmp = questionMapper.getQuestionById(id);
            if(tmp != null) {
                questionList.add(tmp);
            }
        }
        return questionList;
    }

    @Override
    public Map<String, Object> getQuestionsByContent(int pageNum, int pageSize, String content) {
        Map<String, Object> data = new HashMap<>();
        int count = questionMapper.getCountByContent(content);
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("questionsSize", 0);
            data.put("questions", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("questionsSize", 0);
            data.put("questions", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.getQuestionsByContent(content);
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("questionsSize", questions.size());
        data.put("questions", questions);
        return data;
    }

    @Override
    public boolean deleteQuestion(int id) {
        return questionMapper.deleteQuestion(id) > 0;
    }

    @Override
    public Question getQuestionById(int id) {
        return questionMapper.getQuestionById(id);
    }

    @Override
    public Map<String, Object> getQuestionsByProblemsetIdAndContentAndDiffculty(int pageNum, int pageSize, int problemsetId, String content, int difficulty) {
        Map<String, Object> data = new HashMap<>();
        int count = questionMapper.getCountByProblemsetIdAndContentAndDiffculty(problemsetId,
                content, difficulty);
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("questionsSize", 0);
            data.put("problemsetId", problemsetId);
            data.put("questions", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("questionsSize", 0);
            data.put("problemsetId", problemsetId);
            data.put("questions", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.getQuestionsByProblemsetIdAndContentAndDiffculty(
                problemsetId, content, difficulty);
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("questionsSize", questions.size());
        data.put("problemsetId", problemsetId);
        data.put("questions", questions);
        return data;
    }

    @Override
    public boolean updateQuestionsStateByContestId(int contestId, int state) {
        return questionMapper.updateQuestionsStateByContestId(contestId, state) > 0;
    }

    @Override
    public List<Question> getQuestionsBySubjectId(int subjectId) {
        return questionMapper.getQuestionsBySubjectId(subjectId);
    }

    @Override
    public List<Question> searchQuestionsByContent(String content) {
        return questionMapper.getQuestionsByContent(content);
    }

    @Override
    public boolean addContestQuestion(Question question) {
        Contest contest = contestMapper.getContestById(question.getContestId());
        contest.setTotalScore(contest.getTotalScore()+question.getScore());
        contestMapper.updateContestById(contest);
        return questionMapper.addContestQuestion(question.getContestId(), question.getId()) > 0;
    }

    @Override
    public List<Question> getOtherQuestionsByContestId(int contestId) {
        return questionMapper.getOtherQuestionsByContestId(contestId);
    }

    @Override
    public boolean deleteContestQuestion(Question question) {
        return contest_questionMapper.deleteByContestIdAndQuestionId(question.getContestId(), question.getId()) > 0;
    }
}
