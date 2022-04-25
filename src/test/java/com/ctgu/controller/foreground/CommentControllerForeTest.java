package com.ctgu.controller.foreground;

import com.ctgu.controller.CommentController;
import com.ctgu.dto.AjaxResult;
import com.ctgu.model.Comment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/*
 *@Author： 李菊
 *@Date： 2019/12/25
 *@Description：对于评论的测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class CommentControllerForeTest {

    @Autowired
    private CommentController commentController;
    /*
     *@Param：
     *@return：
     *@Description：
     **/
    @Before
    public void setUp() {
    }

    @Test
    public void addCommentTest() {
        Comment comment = new Comment();
        comment.setId(100);
        comment.setUserId(2);
        comment.setContent("whnb!");
        comment.setPostId(11);
        AjaxResult ajaxResult = commentController.addComment(comment);
        Assert.assertNotNull(ajaxResult);
        System.out.println(ajaxResult);
    }

    @Test
    public void deleteCommentTest() {
        AjaxResult ajaxResult = commentController.deleteComment(23);
        Assert.assertNotNull(ajaxResult);
        System.out.println(ajaxResult);
    }
}
