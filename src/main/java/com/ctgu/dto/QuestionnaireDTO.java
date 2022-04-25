package com.ctgu.dto;

import com.ctgu.model.Account;
import com.ctgu.model.Questionnaire;
import com.ctgu.model.QuestionnaireReply;
import com.ctgu.model.Subject;

/**
 * ClassName: QuestionnaireDTO
 * Description:
 * date: 2019/12/22 21:32
 *
 * @author crwen
 * @create 2019-12-22-21:32
 * @since JDK 1.8
 */
public class QuestionnaireDTO {
	private Integer id;

	private String managerName;

	private String subjectName;

	private String replyName;

	private Integer subjectId;

	private String title;

	private Integer replyNum;

	public QuestionnaireDTO() {
	}

	public QuestionnaireDTO(Questionnaire questionnaire, Subject subject, Account account) {
		this.id = questionnaire.getId();
		this.title = questionnaire.getTitle();
		this.managerName = account.getName();
		this.subjectName = subject.getName();
		this.subjectId = subject.getId();
	}

	public QuestionnaireDTO(QuestionnaireReply questionnaireReply, Subject subject) {
		this.id = questionnaireReply.getId();
		this.title = questionnaireReply.getTitle();
		//this.managerName = account.getName();
		this.subjectName = subject.getName();
		//this.replyName =
		this.subjectId = subject.getId();
	}

	public QuestionnaireDTO(Questionnaire questionnaire, Subject subject, Account account, int num) {
		this.id = questionnaire.getId();
		this.managerName = account.getName();
		this.subjectName = subject.getName();
		this.replyNum = num;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;

	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
}
