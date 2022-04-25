package com.ctgu.service;

import com.ctgu.model.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * ClassName: CommentServiceTest
 * Description:
 * date: 2019/10/11 11:07
 *
 * @author crwen
 * @since JDK 1.8
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentServiceTest {

	@Autowired
	private CommentService commentService;

	@Test
	public void getCommentsByPostIdTest() {
		final List<Comment> comments= commentService.getCommentsByPostId(6);
		for (Comment comment : comments) {
			System.out.println(comment.getUser());
		}

	}
}
