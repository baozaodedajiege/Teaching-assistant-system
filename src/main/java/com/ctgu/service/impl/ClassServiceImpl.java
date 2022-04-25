package com.ctgu.service.impl;

import com.ctgu.dao.AccountMapper;
import com.ctgu.dao.ClassMapper;
import com.ctgu.dao.Class_StudentMapper;
import com.ctgu.dao.SubjectMapper;
import com.ctgu.dto.ClassDTO;
import com.ctgu.model.Account;
import com.ctgu.model.Class_Student;
import com.ctgu.model.Subject;
import com.ctgu.service.ClassService;
import com.ctgu.model.Class;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aoboxia
 * @date 2019/12/18 20:09
 */
@Service("classService")
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private SubjectMapper subjectMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private Class_StudentMapper class_studentMapper;

    @Override
    public int addClass(Class _class) {
        if(_class != null) {
            return classMapper.insert(_class);
        }
        return 0;
    }

    @Override
    public boolean updateClass(Class _class) {
        return classMapper.updateByPrimaryKey(_class) > 0;
    }

    @Override
    public Class getClassById(int id) {
        return classMapper.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> getClassesBySubjectId(int pageNum, int pageSize, int id) {
        Map<String, Object> data = new HashMap<>();
        int count = classMapper.getCountBySubjectId(id);
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("classes", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("classes", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Class> classes = classMapper.getClassesBySubjectId(id);
        List<ClassDTO> classDTOS = new ArrayList<>();
        for(Class c : classes) {
            Subject subject = subjectMapper.getSubjectById(c.getSubjectId());
            Account account = accountMapper.getAccountById(c.getTeacherId());
            List<Integer> students = class_studentMapper.getStudentsByClassId(c.getId());
            classDTOS.add(new ClassDTO(c, subject, account, students.size()));
        }
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("classes", classDTOS);
        return data;
    }

    @Override
    public Map<String, Object> getStudentByClassId(int pageNum, int pageSize, int classId) {
        Map<String, Object> data = new HashMap<>();
//        int count = classMapper.getCountBySubjectId(classId);
        Class c = classMapper.getClassById(classId);
        if(c != null) {
            data.put("className", c.getName());
        } else {
            data.put("className", "");
        }
        List<Integer> studentIdList = class_studentMapper.getStudentsByClassId(classId);
        int count = studentIdList.size();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("students", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("students", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Account> studentList = new ArrayList<>();
        for(Integer id : studentIdList) {
            Account account = accountMapper.getAccountById(id);
            studentList.add(account);
        }
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("students", studentList);
        return data;
    }

    @Override
    public List<Integer> getSubjectIdsByAccountId(int teacherId) {
        return classMapper.getSubjectIdsByAccountId(teacherId);
    }

    @Override
    public boolean addStudentByClassId(List<Account> accountList, int classId) {
        for(Account account : accountList) {
            if(account.getQq() != null && account.getName() != null) {
                System.out.println(account.getQq() + ": " + account.getName());
                Account tmp = accountMapper.getAccountByNameAndStudentId(account.getQq(), account.getName());
                if(tmp != null) {
                    class_studentMapper.insertSelective(new Class_Student(classId, tmp.getId()));
                }
            }
        }
        return false;
    }

    @Override
    public List<Class> getClasses() {
        return classMapper.getClasses();
    }

    @Override
    public boolean deleteClassById(int id) {
        return classMapper.deleteByPrimaryKey(id) > 0;
    }

}
