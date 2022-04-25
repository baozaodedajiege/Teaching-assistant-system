package com.ctgu.dao;

import com.ctgu.model.Contest_Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface Contest_QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Contest_Question record);

    int insertSelective(Contest_Question record);

    Contest_Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Contest_Question record);

    int updateByPrimaryKey(Contest_Question record);

    List<Integer> selectQuestionByContestId(Integer contestId);

    int deleteByContestIdAndQuestionId(int contestId, int questionId);
}