package com.ctgu.service.impl;

import com.ctgu.dao.AccountMapper;
import com.ctgu.dao.QuestionnaireMapper;
import com.ctgu.dao.QuestionnaireReplyMapper;
import com.ctgu.dao.SubjectMapper;
import com.ctgu.dto.QuestionnaireDTO;
import com.ctgu.model.Account;
import com.ctgu.model.Questionnaire;
import com.ctgu.model.QuestionnaireReply;
import com.ctgu.model.Subject;
import com.ctgu.service.QuestionnaireService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: QuestionnaireServiceImpl
 * Description:
 * date: 2019/12/22 20:07
 *
 * @author crwen
 * @create 2019-12-22-20:07
 * @since JDK 1.8
 */
@Service("questionnaireService")
public class QuestionnaireServiceImpl implements QuestionnaireService {

	@Autowired
	private QuestionnaireMapper questionnaireMapper;
	@Autowired
	private SubjectMapper subjectMapper;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	QuestionnaireReplyMapper questionnaireReplyMapper;

	@Override
	public int addQuestionnaire(Questionnaire questionnaire) {
		return questionnaireMapper.insertQuestionnaire(questionnaire);
	}

	@Override
	public Map<String, Object> getQuestionnaireBySubjectId(int pageNum, int pageSize, int subjectId) {
		Map<String, Object> data = new HashMap<>();
		int count = questionnaireMapper.getCountBySubjectId(subjectId);
		if (count == 0) {
			data.put("pageNum", 0);
			data.put("pageSize", 0);
			data.put("totalPageNum", 1);
			data.put("totalPageSize", 0);
			data.put("questionnaire", new ArrayList<>());
			return data;
		}
		int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		if (pageNum > totalPageNum) {
			data.put("pageNum", 0);
			data.put("pageSize", 0);
			data.put("totalPageNum", totalPageNum);
			data.put("totalPageSize", 0);
			data.put("questionnaire", new ArrayList<>());
			return data;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Questionnaire> questionnaires = questionnaireMapper.getQuestionnaireBySubjectId(subjectId);
		List<QuestionnaireDTO> questionnaireDTOS = new ArrayList<>();
		for(Questionnaire q : questionnaires) {
			Subject subject = subjectMapper.getSubjectById(q.getSubjectId());
			Account account = accountMapper.getAccountById(q.getAuthorId());
			//List<Integer> students = class_studentMapper.getStudentsByClassId(c.getId());
			if (account == null || subject == null) {
				continue;
			}
			questionnaireDTOS.add(new QuestionnaireDTO(q, subject, account));
		}
		data.put("pageNum", pageNum);
		data.put("pageSize", pageSize);
		data.put("totalPageNum", totalPageNum);
		data.put("totalPageSize", count);
		data.put("questionnaires", questionnaireDTOS);
		return data;
	}

	@Override
	public List<Questionnaire> getQuestionnaireBySubjectId(int subjectId) {
		return questionnaireMapper.getQuestionnaireBySubjectId(subjectId);
	}

	@Override
	public Questionnaire getQuestionnaireById(int id) {
		return questionnaireMapper.getQuestionnaireById(id);
	}

	@Override
	public boolean deleteQuestionnaireById(int id) {
		return questionnaireMapper.deleteQuestionnaireById(id);
	}

	@Override
	public int submitQuestionnaire(QuestionnaireReply questionnaireReply) {
		return questionnaireReplyMapper.submitQuestionnaire(questionnaireReply);
	}

	//@Override
	//public List<Questionnaire> getQuestionnaireByAccountId(int accountId) {
	//	return questionnaireMapper.getQuestionnaireByAccountId(accountId);
	//}

	public Map<String, Object> getQuestionnaireReplyById(int pageNum, int pageSize, int id) {
		Map<String, Object> data = new HashMap<>();
		int count = questionnaireReplyMapper.getCountByQuestId(id);
		if (count == 0) {
			data.put("pageNum", 0);
			data.put("pageSize", 0);
			data.put("totalPageNum", 1);
			data.put("totalPageSize", 0);
			data.put("questionnaireReply", new ArrayList<>());
			return data;
		}
		int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
		if (pageNum > totalPageNum) {
			data.put("pageNum", 0);
			data.put("pageSize", 0);
			data.put("totalPageNum", totalPageNum);
			data.put("totalPageSize", 0);
			data.put("questionnaireReply", new ArrayList<>());
			return data;
		}
		PageHelper.startPage(pageNum, pageSize);
		List<QuestionnaireReply> questionnaireReplys = questionnaireReplyMapper.getQuestionnairesByQuestId(id);
		Questionnaire questionnaire = questionnaireMapper.getQuestionnaireById(id);
		Account manager = accountMapper.getAccountById(questionnaire.getAuthorId());
		Subject subject = subjectMapper.getSubjectById(questionnaire.getSubjectId());
		List<QuestionnaireDTO> questionnaireDTOS = new ArrayList<>();
		for(QuestionnaireReply q : questionnaireReplys) {
			QuestionnaireDTO questionnaireDTO = new QuestionnaireDTO(q,subject);
			questionnaireDTO.setManagerName(manager.getName());
			Account replyer = accountMapper.getAccountById(q.getSubmitId());
			questionnaireDTO.setReplyName(replyer.getName());
			questionnaireDTOS.add(questionnaireDTO);
		}
		data.put("pageNum", pageNum);
		data.put("pageSize", pageSize);
		data.put("totalPageNum", totalPageNum);
		data.put("totalPageSize", count);
		data.put("questionnaireReply", questionnaireDTOS);
		return data;
	}

	@Override
	public QuestionnaireReply getQuestionnaireReplyById(int id) {
		return questionnaireReplyMapper.getQuestionnaireById(id);
	}
}
