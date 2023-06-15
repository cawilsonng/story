package com.wilson.storybackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilson.storybackend.controllers.StoryController;
import com.wilson.storybackend.exceptions.TargetNotFoundException;
import com.wilson.storybackend.mapper.StoryMapper;
import com.wilson.storybackend.models.FetchStoriesDto;
import com.wilson.storybackend.models.StoryDto;
import com.wilson.storybackend.models.entity.Story;
import com.wilson.storybackend.services.StoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class StoryControllerTests {

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Mock
    private StoryService storyService;

    @Mock
    private StoryMapper storyMapper;

    @InjectMocks
    private StoryController storyController;

    private StoryDto storyDto;
    private Story story;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(storyController).build();
        objectMapper = new ObjectMapper();
        storyDto = StoryDto.builder().title("Sample Story")
                .description("This is a sample story.").content("Sample content")
                .build();
        story = Story.builder().title("Sample Story")
                .description("This is a sample story.").content("Sample content")
                .build();
    }

    @Test
    void testGetStory() throws Exception {
        long storyId = 1L;
        when(storyService.ackViewAndGetStory(storyId)).thenReturn(story);
        when(storyMapper.toStoryDto(story)).thenReturn(storyDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stories/{id}", storyId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.title").value(storyDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.description").value(storyDto.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"));
    }

    @Test
    void testExportStory() throws TargetNotFoundException, IOException {
        long storyId = 1L;
        byte[] imageBytes = "Sample Image".getBytes();

        when(storyService.ackDownloadAndGenerateImage(storyId)).thenReturn(imageBytes);

        byte[] response = storyController.exportStory(storyId);

        assertTrue(response.length > 0);
    }

    @Test
    void testSubmitStory() throws Exception {
        when(storyService.saveStory(storyDto)).thenReturn(story);
        when(storyMapper.toStoryDto(story)).thenReturn(storyDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/stories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(storyDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.title").value(storyDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.description").value(storyDto.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
    }

    @Test
    void testListStories() throws Exception {
        int index = 1;
        int size = 10;
        FetchStoriesDto fetchStoriesDto = FetchStoriesDto.builder()
                .index(index).size(size).totalPage(1).build();

        when(storyService.getPagedStoryList(index, size)).thenReturn(fetchStoriesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stories/all")
                        .param("index", String.valueOf(index))
                        .param("size", String.valueOf(size)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.totalPage").value(fetchStoriesDto.getTotalPage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.index").value(fetchStoriesDto.getIndex()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.size").value(fetchStoriesDto.getSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.stories").value(fetchStoriesDto.getStories()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
    }
}
