package com.ctgu.service.impl;

import com.ctgu.common.Const;
import com.ctgu.dao.ClassMapper;
import com.ctgu.dao.SubjectMapper;
import com.ctgu.dao.SyllabusMapper;
import com.ctgu.model.Subject;
import com.ctgu.model.Syllabus;
import com.ctgu.service.SubjectService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {

    private static Log LOG = LogFactory.getLog(SubjectServiceImpl.class);

    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private SyllabusMapper syllabusMapper;
    @Autowired
    private ClassMapper classMapper;


    @Override
    public int addSubject(Subject subject) {
        if (subject != null && StringUtils.isEmpty(subject.getImgUrl())) {
            subject.setImgUrl(Const.DEFAULT_SUBJECT_IMG_URL);
        }
        subject.setQuestionNum(0);
        return subjectMapper.insertSubject(subject);
    }

    @Override
    public boolean updateSubject(Subject subject) {
        return subjectMapper.updateSubjectById(subject) > 0;
    }

    @Override
    public Subject getSubjectById(int id) {
        return subjectMapper.getSubjectById(id);
    }

    @Override
    public Map<String, Object> getSubjects(int pageNum, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        int count = subjectMapper.getCount();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("subjects", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("subjects", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Subject> subjects = subjectMapper.getSubjects();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("subjects", subjects);
        return data;
    }

    public Map<String, Object> getSubjects(int pageNum, int pageSize, int id) {
        Map<String, Object> data = new HashMap<>();
        List<Integer> SubjectIds = classMapper.getSubjectIdsByAccountId(id);
        int count = SubjectIds.size();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("subjects", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("subjects", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        //List<Subject> subjects = subjectMapper.getSubjects();

        List<Subject> subjects = new ArrayList<>();
        for (Integer i : SubjectIds) {
            Subject subject = subjectMapper.getSubjectById(i);
            if (subject != null) {
                //System.out.println(subject);
                subjects.add(subject);
            }
        }

        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("subjects", subjects);
        return data;
    }

    @Override
    public List<Subject> getSubjects() {
        return subjectMapper.getSubjects();
    }

    @Override
    public boolean deleteSubjectById(int id) {
        return subjectMapper.deleteSubjectById(id) > 0;
    }

    @Override
    public String getSyllabusBySubjectId(int subjectId) {
        return subjectMapper.getSyllabusBySubjectId(subjectId);
    }

    @Override
    public boolean updateSyllabusUrlBySubjectId(Syllabus syllabus) {
        Syllabus old = syllabusMapper.selectBySubjectId(syllabus.getSubjectId());
        if(old != null) {
            return syllabusMapper.updateBySubjectId(old) > 0;
        }
        return syllabusMapper.insertSelective(syllabus) > 0;
    }
}
