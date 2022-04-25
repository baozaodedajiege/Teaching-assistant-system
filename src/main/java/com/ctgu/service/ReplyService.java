package com.ctgu.service;

import com.ctgu.model.Reply;

import java.util.List;

public interface ReplyService {

    int addReply(Reply reply);

    List<Reply> getReliesByPostId(int postId);

    List<Reply> getRelies();

    List<Reply> getReliesOrderByCreateTime();
}
