package com.ctgu.controller;

import com.ctgu.dto.AjaxResult;
import com.ctgu.model.Comment;
import com.ctgu.service.CommentService;
import com.ctgu.service.PostService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    private static Log LOG = LogFactory.getLog(CommentController.class);

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;

    //添加评论
    @RequestMapping(value="/api/addComment", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult addComment(@RequestBody Comment comment) {
        System.out.println(comment);
        AjaxResult ajaxResult = new AjaxResult();
        postService.updateReplyNumById(comment.getPostId());
        int commentId = commentService.addComment(comment);
        return new AjaxResult().setData(commentId);
    }

    //删除评论
    @DeleteMapping("/api/deleteComment/{id}")
    public AjaxResult deleteComment(@PathVariable int id) {
        AjaxResult ajaxResult = new AjaxResult();
        boolean result = commentService.deleteCommentById(id);
        return new AjaxResult().setData(result);
    }
}
