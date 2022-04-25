package com.ctgu.service;

import com.ctgu.model.Post;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {
    private static Log LOG = LogFactory.getLog(PostService.class);

    @Autowired
    private PostService postService;

    @Test
    public void addPost() {
        Post post = new Post();
        post.setAuthorId(4);
        post.setTitle("hahahaha");
        post.setHtmlContent("hahahahah");
        post.setTextContent("hahahahaha");
        int result = postService.addPost(post);
        LOG.info("result = " + result);
    }

    @Test
    public void updatePostById() {
        Post post = new Post();
        post.setId(4);
        post.setHtmlContent("<p>hahahahahooo<p>");
        post.setTextContent("hahahahahaooo");
        boolean result = postService.updatePostById(post);
        LOG.info("result = " + result);
    }

    @Test
    public void deletePostById() {
        boolean result = postService.deletePostById(12);
        LOG.info("result = " + result);

    }

    @Test
    public void getPosts() {
        Map<String, Object> data = new HashMap<>();
        data = postService.getPosts(1,3);
        List<Post> posts = (List<Post>) data.getOrDefault("posts", new ArrayList<>());
        for (Post post : posts) {
            LOG.info("post = " + post);
        }
    }

    @Test
    public void getPostById() {
        Post post = postService.getPostById(11);
        LOG.info("post = " + post);
    }

    @Test
    public void updateReplyNumById() {
        boolean result = postService.updateReplyNumById(11);
        LOG.info("result = " + result);
    }

    @Test
    public void getPostsByAuthorId() {
        Map<String, Object> data = new HashMap<>();
        data = postService.getPostsByAuthorId(1,3,11);
        List<Post> posts = (List<Post>) data.getOrDefault("posts", new ArrayList<>());
        for (Post post : posts) {
            LOG.info("post = " + post);
        }
    }
}
