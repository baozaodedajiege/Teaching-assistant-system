package com.ctgu.util.word2html.bean;

import java.util.List;

public abstract class DocHtmlDto {

	private String html;
	private List<String> imageList;
	private String filePath;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	public void setFilePath(String s) {
		this.filePath = s;
	}
	public String getFilePath() {
		return this.filePath;
	}
}