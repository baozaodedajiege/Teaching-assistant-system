package com.ctgu.dao;

import com.ctgu.model.Questionnaire;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ClassName: QuestionnaireTest
 * Description:
 * date: 2019/12/22 19:30
 *
 * @author crwen
 * @create 2019-12-22-19:30
 * @since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionnaireDaoTest {

	@Autowired
	private QuestionnaireMapper questionnaireMapper;

	@Test
	public void insertQuestionnaireTest() {
		Questionnaire questionnaire = new Questionnaire();
		questionnaire.setAuthorId(1);
		questionnaire.setSubjectId(1);
		questionnaire.setTitle("test");
		questionnaire.setContent("just a test");
		int res = questionnaireMapper.insertQuestionnaire(questionnaire);
		//Assert.assertNotEquals(0, res);
		//System.out.println(res);
	}

	@Test
	public void getQuestionnaireBySubjectIdTest() {
		List<Questionnaire> questionnaires = questionnaireMapper.getQuestionnaireBySubjectId(1);
		Assert.assertNotNull(questionnaires);
		for (Questionnaire questionnaire : questionnaires) {
			System.out.println(questionnaire);
		}

	}

	@Test
	public void getCountBySubjectIdTest() {
		int count = questionnaireMapper.getCountBySubjectId(1);
		System.out.println(count);
	}
}