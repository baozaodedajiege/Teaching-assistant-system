package com.ctgu.model;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: QuestionnaireReply
 * Description:
 * date: 2019/12/23 20:29
 *
 * @author crwen
 * @create 2019-12-23-20:29
 * @since JDK 1.8
 */
public class QuestionnaireReply implements Serializable {
	private int id;

	private int questId;

	private int submitId;

	private String htmlContent;

	private Date createTime;

	private String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestId() {
		return questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}

	public int getSubmitId() {
		return submitId;
	}

	public void setSubmitId(int submitId) {
		this.submitId = submitId;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "QuestionnaireReply{" +
				"id=" + id +
				", questId=" + questId +
				", submitId=" + submitId +
				", htmlContent='" + htmlContent + '\'' +
				", createTime=" + createTime +
				", title='" + title + '\'' +
				'}';
	}
}
