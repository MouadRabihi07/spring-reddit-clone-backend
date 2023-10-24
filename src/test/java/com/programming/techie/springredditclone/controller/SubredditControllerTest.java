package com.programming.techie.springredditclone.controller;

import com.programming.techie.springredditclone.dto.SubredditDto;
import com.programming.techie.springredditclone.model.Subreddit;
import com.programming.techie.springredditclone.model.User;
import com.programming.techie.springredditclone.service.SubredditService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SubredditController.class)
class SubredditControllerTest {

    @MockBean
    private SubredditService subredditService;

    @Autowired
    private MockMvc mockMvc;

    Subreddit subreddit1;
    Subreddit subreddit2;
    @BeforeEach
    void setupSubredditData() {
        subreddit1 = Subreddit.builder()
                .id(1L)
                .name("Subreddit Name 1")
                .description("Description 1")
                .createdDate(Instant.now())
                .user(User.builder().username("User 1").build())
                .build();

        subreddit2 = Subreddit.builder()
                .id(2L)
                .name("Subreddit Name 2")
                .description("Description 2")
                .createdDate(Instant.now())
                .user(User.builder().username("User 2").build())
                .build();
    }
    @Test
    @DisplayName("Should return all the subreddits when making a get request")
    @WithMockUser(username = "test")
    void shouldGetAllSubreddits() throws Exception {
        Mockito.when(subredditService.getAll()).thenReturn(Stream.of(subreddit1,subreddit2)
                .map(subreddit -> {
                    return SubredditDto.builder().name(subreddit.getName())
                            .description(subreddit.getDescription())
                            .build();
                })
                .collect(toList()));
        mockMvc.perform(get("/api/subreddit"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Subreddit Name 1")));
    }

}
