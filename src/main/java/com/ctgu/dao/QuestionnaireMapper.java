package com.ctgu.dao;

import com.ctgu.model.Questionnaire;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName: Questionnaire
 * Description:
 * date: 2019/12/22 19:29
 *
 * @author crwen
 * @create 2019-12-22-19:29
 * @since JDK 1.8
 */
@Component
@Mapper
public interface QuestionnaireMapper {
	int insertQuestionnaire(@Param("questionnaire") Questionnaire questionnaire);

	int getCountBySubjectId(@Param("subjectId") int subjectId);

 	List<Questionnaire> getQuestionnaireBySubjectId(@Param("subjectId") int subjectId);

	Questionnaire getQuestionnaireById(int id);

	boolean deleteQuestionnaireById(int id);

	List<Questionnaire> getQuestionnaireByAccountId(@Param("accountId") int accountId);
}
