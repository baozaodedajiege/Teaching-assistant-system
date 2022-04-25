package com.ctgu.service;

import com.ctgu.model.SubjectComment;

import java.util.List;

public interface SubjectCommentService {

    public List<SubjectComment> getSubjectComments();

    public List<SubjectComment> selectBySubjectId(Integer subject_id);

    public Integer getCountBySubjectId(Integer subject_id);

    public void insert(SubjectComment subjectComment);

    public void insertSelective(SubjectComment subjectComment);
}
