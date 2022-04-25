package com.ctgu.dao;

import com.ctgu.model.QuestionnaireReply;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ClassName: QuestionnaireReplyDaoTest
 * Description:
 * date: 2019/12/23 21:30
 *
 * @author crwen
 * @create 2019-12-23-21:30
 * @since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionnaireReplyDaoTest {

	@Autowired
	private QuestionnaireReplyMapper questionnaireReplyMapper;

	@Test
	public void getQuestionnairesBySubmitId() {
		List<QuestionnaireReply> questList = questionnaireReplyMapper.getQuestionnairesBySubmitId(2);
		System.out.println(questList);
	}

	@Test
	public void save() {
		QuestionnaireReply reply = new QuestionnaireReply();
		reply.setQuestId(15);
		reply.setSubmitId(2);
		reply.setHtmlContent("test");
		reply.setTitle("test");
		int res = questionnaireReplyMapper.submitQuestionnaire(reply);
		Assert.assertNotEquals(0, res);

	}

	@Test
	public void getCountByQuestId() {
		int count = questionnaireReplyMapper.getCountByQuestId(15);
		System.out.println(count);
	}
}