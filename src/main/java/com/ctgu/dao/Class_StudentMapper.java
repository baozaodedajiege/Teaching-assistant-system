package com.ctgu.dao;

import com.ctgu.model.Class_Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.net.Inet4Address;
import java.util.List;

@Component
@Mapper
public interface Class_StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Class_Student record);

    int insertSelective(Class_Student record);

    Class_Student selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Class_Student record);

    int updateByPrimaryKey(Class_Student record);

    List<Integer> getStudentsByClassId(Integer classId);

    List<Integer> selectByStudentId(Integer studentId);

}