package com.ctgu.dto;

import com.ctgu.model.Subject;

import java.util.Date;

/**
 * ClassName: SubjectDTO
 * Description:
 * date: 2019/12/23 17:25
 *
 * @author crwen
 * @create 2019-12-23-17:25
 * @since JDK 1.8
 */
public class SubjectDTO {

	private int id;
	private String name;
	private Date createTime;
	private Date updateTime;
	private int questionNum;

	private int acccountId;

	public SubjectDTO(){}

	public SubjectDTO(Subject subject, int acccountId) {
		this.id = subject.getId();
		this.name = subject.getName();
		this.createTime = subject.getCreateTime();
		this.updateTime = subject.getUpdateTime();
		this.questionNum = subject.getQuestionNum();
		this.acccountId = acccountId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}

	public int getAcccountId() {
		return acccountId;
	}

	public void setAcccountId(int acccountId) {
		this.acccountId = acccountId;
	}

	@Override
	public String toString() {
		return "SubjectDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", questionNum=" + questionNum +
				", acccountId=" + acccountId +
				'}';
	}
}
