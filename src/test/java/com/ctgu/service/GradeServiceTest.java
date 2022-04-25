package com.ctgu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * ClassName: GradeServiceTest
 * Description:
 * date: 2019/12/18 21:37
 *
 * @author crwen
 * @create 2019-12-18-21:37
 * @since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class GradeServiceTest {

	@Autowired
	private GradeService gradeService;

	@Test
	public void getGradesByStudentIdTest() {
		Map<String, Object> res = gradeService.getGradesByStudentId(0, 10, 1);
		System.out.println(res.size());
		System.out.println(res.get("grades"));
	}
}