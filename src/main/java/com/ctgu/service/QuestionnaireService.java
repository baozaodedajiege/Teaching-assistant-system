package com.ctgu.service;

import com.ctgu.model.Questionnaire;
import com.ctgu.model.QuestionnaireReply;

import java.util.List;
import java.util.Map;

/**
 * ClassName: QuestionnaireService
 * Description:
 * date: 2019/12/22 20:06
 *
 * @author crwen
 * @create 2019-12-22-20:06
 * @since JDK 1.8
 */
public interface QuestionnaireService {

	int addQuestionnaire(Questionnaire questionnaire);

	Map<String, Object> getQuestionnaireBySubjectId(int page, int size, int subjectId);

	List<Questionnaire> getQuestionnaireBySubjectId(int subjectId);

	Questionnaire getQuestionnaireById(int id);

	boolean deleteQuestionnaireById(int id);

	int submitQuestionnaire(QuestionnaireReply questionnaireReply);

	//List<Questionnaire> getQuestionnaireByAccountId(int accountId);

	public Map<String, Object> getQuestionnaireReplyById(int pageNum, int pageSize, int id);

	QuestionnaireReply getQuestionnaireReplyById(int id);
}
