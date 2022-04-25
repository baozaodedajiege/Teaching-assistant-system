package com.ctgu.dao;

import com.ctgu.model.Class;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Class record);

    int insertSelective(Class record);

    Class selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Class record);

    int updateByPrimaryKey(Class record);

    int getCount();

    Class getClassById(Integer id);

    List<Class> getClasses();

    List<Class> getClassesBySubjectId(int id);

    int getCountBySubjectId(int id);

    List<Integer> getSubjectIdsByAccountId(int teacherId);

    List<Integer> getAccountIdsBySubjectId(int subjectId);
}