package com.ctgu.dao;

import com.ctgu.model.Grade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ClassName: GradeMapperTest
 * Description:
 * date: 2019/12/18 21:52
 *
 * @author crwen
 * @create 2019-12-18-21:52
 * @since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GradeDaoTest {

	@Autowired
	private GradeMapper gradeMapper;

	@Test
	public void getGradesByStudentIdTest() {
		List<Grade> grades = gradeMapper.getGradesByStudentId(2);
		Assert.assertNotNull(grades);
		System.out.println(grades);
	}

	@Test
	public void getCountByStudentIdTest() {
		gradeMapper.getCountByStudentId(2);
	}
}