package com.ctgu.dao;

import com.ctgu.model.SubjectComment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SubjectCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SubjectComment record);

    int insertSelective(SubjectComment record);

    SubjectComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SubjectComment record);

    int updateByPrimaryKey(SubjectComment record);

    List<SubjectComment> getSubjectComments();

    List<SubjectComment> selectBySubjectId(Integer subject_id);

    Integer getCountBySubjectId(Integer subject_id);
}