package com.programming.techie.springredditclone.repository;

import com.programming.techie.springredditclone.model.Post;
import com.programming.techie.springredditclone.model.Subreddit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertAll;


@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SubredditRepository subredditRepository;

    @Test
    public void shouldFindPostsBySubreddit(){
        Subreddit subreddit = Subreddit.builder()
                .id(1L)
                .name("Subreddit Name")
                .description("Test Description")
                .createdDate(Instant.now())
                .build();

        subredditRepository.save(subreddit);

        Post post1 = Post.builder()
                .postId(1L)
                .postName("Post Name 1")
                .description("Post Description 2")
                .subreddit(subreddit)
                .build();

        Post post2 = Post.builder()
                .postId(2L)
                .postName("Post Name 2")
                .description("Post Description 2")
                .build();

        postRepository.saveAll(List.of(post1,post2));

        List<Post> posts = postRepository.findAllBySubreddit(subreddit);

        assertAll( () -> {
            assertEquals(1, posts.size());
            assertFalse(posts.stream().anyMatch(post -> post.getPostName().equals("Post Name 2")));
        });

    }

    @AfterEach
    public void tearDown() {
        subredditRepository.deleteAll();
        postRepository.deleteAll();
    }

}
