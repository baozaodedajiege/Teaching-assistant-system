package com.ctgu.service.impl;

import com.ctgu.dao.SubjectCommentMapper;
import com.ctgu.model.SubjectComment;
import com.ctgu.service.SubjectCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("subjectCommentService")
public class SubjectCommentServiceImpl implements SubjectCommentService {

    @Autowired
    SubjectCommentMapper subjectCommentMapper;

    @Override
    public List<SubjectComment> getSubjectComments() {
        return subjectCommentMapper.getSubjectComments();
    }

    @Override
    public List<SubjectComment> selectBySubjectId(Integer subject_id) {
        return subjectCommentMapper.selectBySubjectId(subject_id);
    }

    @Override
    public Integer getCountBySubjectId(Integer subject_id) {
        return subjectCommentMapper.getCountBySubjectId(subject_id);
    }

    @Override
    public void insert(SubjectComment subjectComment) {
        subjectCommentMapper.insert(subjectComment);
    }

    @Override
    public void insertSelective(SubjectComment subjectComment) {
        subjectCommentMapper.insertSelective(subjectComment);
    }

}
