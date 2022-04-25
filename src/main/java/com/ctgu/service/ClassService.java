package com.ctgu.service;

import com.ctgu.model.Account;
import com.ctgu.model.Class;

import java.util.List;
import java.util.Map;

/**
 * @author aoboxia
 * @date 2019/12/18 19:50
 */
public interface ClassService {
    int addClass(Class _class);

    boolean updateClass(Class _class);

    Class getClassById(int id);

    List<Class> getClasses();

    boolean deleteClassById(int id);

    Map<String, Object> getClassesBySubjectId(int page, int classPageSize, int id);

    Map<String, Object> getStudentByClassId(int page, int accountPageSize, int classId);

    List<Integer> getSubjectIdsByAccountId(int teacherId);

    boolean addStudentByClassId(List<Account> accountList, int classId);
}
