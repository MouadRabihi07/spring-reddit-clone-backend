package com.programming.techie.springredditclone.controller;

import com.programming.techie.springredditclone.dto.CommentsDto;
import com.programming.techie.springredditclone.model.Comment;
import com.programming.techie.springredditclone.model.Post;
import com.programming.techie.springredditclone.model.User;
import com.programming.techie.springredditclone.repository.CommentRepository;
import com.programming.techie.springredditclone.repository.PostRepository;
import com.programming.techie.springredditclone.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentsControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Post post;
    private Comment comment1;
    private Comment comment2;
    private Comment comment3;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("test")
                .password("password")
                .build();
        userRepository.save(user);

        post = Post.builder()
                .postId(1L)
                .postName("Post Name")
                .description("Post Description")
                .build();
        postRepository.save(post);

        comment1 = Comment.builder()
                .text("Comment 1")
                .post(post)
                .createdDate(Instant.now())
                .user(user)
                .build();
        commentRepository.save(comment1);

        comment2 = Comment.builder()
                .text("Comment 2")
                .createdDate(Instant.now())
                .user(user)
                .build();
        commentRepository.save(comment2);

        comment3 = Comment.builder()
                .text("Comment 3")
                .post(post)
                .createdDate(Instant.now())
                .user(user)
                .build();
        commentRepository.save(comment3);
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "test")
    void shouldGetAllCommentsForPost() {
        ResponseEntity<CommentsDto[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/comments?postId=" + post.getPostId(), CommentsDto[].class);

        // Assert the response
        assertEquals(200, response.getStatusCodeValue());
        CommentsDto[] comments = response.getBody();
        assertEquals(2, comments.length);
        assertEquals(post.getPostId(), comments[0].getPostId());
    }
}