package com.ctgu.service.impl;

import com.ctgu.dao.Class_StudentMapper;
import com.ctgu.service.Class_StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("class_StudentService")
public class Class_StudentServiceImpl implements Class_StudentService {
    @Autowired
    Class_StudentMapper class_studentMapper;
    @Override
    public List<Integer> selectByStudentId(Integer studentId) {
        return class_studentMapper.selectByStudentId(studentId);
    }
}
