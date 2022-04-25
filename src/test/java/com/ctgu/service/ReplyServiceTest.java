package com.ctgu.service;

import com.ctgu.model.Reply;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReplyServiceTest {
    private static Log LOG = LogFactory.getLog(QuestionService.class);

    @Autowired
    private ReplyService replyService;
    @Test
    public void addReply() {
        Reply reply = new Reply();
        reply.setCommentId(8);
        reply.setUserId(4);
        reply.setPostId(8);
        reply.setAtuserId(2);
        reply.setContent("tql");
        int result = replyService.addReply(reply);
        LOG.info("result = " + result);
    }

    @Test
    public void getReliesByPostId() {
        List<Reply> replies = replyService.getReliesByPostId(8);
        for(Reply reply : replies){
            LOG.info("replay = " + reply);
        }
    }
}
