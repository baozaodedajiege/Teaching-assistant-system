package com.ctgu.dao;

import com.ctgu.model.Contest;
import com.ctgu.model.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SubjectMapper {

    int insertSubject(@Param("subject") Subject subject);

    int updateSubjectById(@Param("subject") Subject subject);

    Subject getSubjectById(@Param("id") int id);

    int getCount();

    List<Subject> getSubjects();

    int deleteSubjectById(@Param("id") int id);

    String getSyllabusBySubjectId(int subjectId);

    int updateSubjectQuestionNum();
}
