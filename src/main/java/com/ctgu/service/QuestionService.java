package com.ctgu.service;

import com.ctgu.model.Question;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    int addQuestion(Question question);

    boolean updateQuestion(Question question);

    List<Question> getQuestionsByContestId(int contestId);

    Map<String, Object> getQuestionsByContent(int pageNum, int pageSize, String content);

    Map<String, Object> getQuestionsByProblemsetIdAndContentAndDiffculty(int pageNum, int pageSize,
                                                                         int problemsetId,
                                                                         String content,
                                                                         int diffcult);

    boolean deleteQuestion(int id);

    Question getQuestionById(int id);

    boolean updateQuestionsStateByContestId(int contestId, int state);

    List<Question> searchQuestionsByContent(String content);

    boolean addContestQuestion(Question question);

    List<Question> getOtherQuestionsByContestId(int contestId);

    boolean deleteContestQuestion(Question question);

    List<Question> getQuestionsBySubjectId(int subjectId);
}
