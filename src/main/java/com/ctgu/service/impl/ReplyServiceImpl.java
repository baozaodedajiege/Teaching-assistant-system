package com.ctgu.service.impl;

import com.ctgu.service.ReplyService;
import com.ctgu.dao.ReplyMapper;
import com.ctgu.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("replyService")
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public int addReply(Reply reply) {
        return replyMapper.insertReply(reply);
    }

    @Override
    public List<Reply> getReliesByPostId(int postId) {
        return replyMapper.getReliesByPostId(postId);
    }

    public List<Reply>  getRelies(){
        return replyMapper.getRelies();
    }

    @Override
    public List<Reply> getReliesOrderByCreateTime() {
        return replyMapper.getReliesOrderByCreateTime();
    }
}
