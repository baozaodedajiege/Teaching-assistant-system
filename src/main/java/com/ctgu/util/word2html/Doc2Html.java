package com.ctgu.util.word2html;

import com.ctgu.util.word2html.bean.DocHtmlDto;

import java.io.IOException;

public interface Doc2Html {
	public DocHtmlDto doc2Html(String filePath) throws IOException;
}