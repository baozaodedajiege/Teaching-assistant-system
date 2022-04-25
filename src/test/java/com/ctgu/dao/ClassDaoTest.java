package com.ctgu.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * ClassName: ClassDaoTest
 * Description:
 * date: 2019/12/23 17:05
 *
 * @author crwen
 * @create 2019-12-23-17:05
 * @since JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClassDaoTest {

	@Autowired
	private ClassMapper classMapper;

	@Test
	public void getAccountIdsBySubjectId() {
		List<Integer> ids = classMapper.getSubjectIdsByAccountId(6);
		for (Integer id : ids) {
			System.out.println(id);
		}
	}
}