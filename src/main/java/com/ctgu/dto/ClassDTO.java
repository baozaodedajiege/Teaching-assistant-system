package com.ctgu.dto;

import com.ctgu.model.Account;
import com.ctgu.model.Class;
import com.ctgu.model.Subject;

/**
 * @author aoboxia
 * @date 2019/12/18 20:36
 */
public class ClassDTO {
    private Integer id;

    private String className;

    private String subjectName;

    private String teacherName;

    private Integer classNum;

    public ClassDTO() {
    }

    public ClassDTO(Class _class, Subject subject, Account account, int num) {
        this.id = _class.getId();
        this.className = _class.getName();
        this.subjectName = subject.getName();
        this.teacherName = account.getName();
        this.classNum = num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }
}
