package com.ctgu.service;

import com.ctgu.model.Subject;
import com.ctgu.model.Syllabus;

import java.util.List;
import java.util.Map;

public interface SubjectService {

    int addSubject(Subject subject);

    boolean updateSubject(Subject subject);

    Subject getSubjectById(int id);

    Map<String, Object> getSubjects(int pageNum, int pageSize);

    Map<String, Object> getSubjects(int pageNum, int pageSize, int id);

    List<Subject> getSubjects();

    boolean deleteSubjectById(int id);

    String getSyllabusBySubjectId(int subjectId);

    boolean updateSyllabusUrlBySubjectId(Syllabus syllabus);
}
