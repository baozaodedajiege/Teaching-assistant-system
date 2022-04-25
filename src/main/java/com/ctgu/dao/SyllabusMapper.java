package com.ctgu.dao;

import com.ctgu.model.Syllabus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface SyllabusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Syllabus record);

    int insertSelective(Syllabus record);

    Syllabus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Syllabus record);

    int updateByPrimaryKey(Syllabus record);

    int updateBySubjectId(@Param("syllabus") Syllabus syllabus);

    Syllabus selectBySubjectId(Integer subjectId);
}