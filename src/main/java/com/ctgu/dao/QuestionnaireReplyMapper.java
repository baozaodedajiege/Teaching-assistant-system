package com.ctgu.dao;

import com.ctgu.model.QuestionnaireReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ClassName: QuestionnaireReply
 * Description:
 * date: 2019/12/23 20:31
 *
 * @author crwen
 * @create 2019-12-23-20:31
 * @since JDK 1.8
 */
@Component
@Mapper
public interface QuestionnaireReplyMapper {

	int submitQuestionnaire(@Param("questionnaireReply") QuestionnaireReply questionnaireReply);

	List<QuestionnaireReply> getQuestionnairesBySubmitId(@Param("submitId") int submitId);

	List<QuestionnaireReply> getQuestionnairesByQuestId(@Param("questId") int questId);

	QuestionnaireReply getQuestionnaireById(@Param("id") int id);

	int getCountByQuestId(@Param("questId") int questId);
}
