package com.ctgu.model;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: Questionnaire
 * Description:
 * date: 2019/12/22 19:17
 *
 * @author crwen
 * @create 2019-12-22-19:17
 * @since JDK 1.8
 */
public class Questionnaire implements Serializable {

	private int id;
	private int authorId;
	private int subjectId;
	private String content;
	private Date createTime;
	private Date updateTime;
	private String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Questionnaire{" +
				"id=" + id +
				", authorId=" + authorId +
				", subjectId=" + subjectId +
				", content='" + content + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", title='" + title + '\'' +
				'}';
	}
}
