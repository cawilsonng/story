package com.wilson.storybackend.controllers;

import com.wilson.storybackend.exceptions.ParameterException;
import com.wilson.storybackend.exceptions.TargetNotFoundException;
import com.wilson.storybackend.mapper.StoryMapper;
import com.wilson.storybackend.models.CommonResponse;
import com.wilson.storybackend.models.FetchStoriesDto;
import com.wilson.storybackend.models.StoryDto;
import com.wilson.storybackend.models.entity.Story;
import com.wilson.storybackend.services.StoryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/stories")
public class StoryController {
    private final StoryService storyService;
    private final StoryMapper storyMapper;

    public StoryController(StoryService storyService, StoryMapper storyMapper) {
        this.storyService = storyService;
        this.storyMapper = storyMapper;
    }

    @GetMapping("/{id}")
    public CommonResponse<StoryDto> getStory(@PathVariable long id) throws TargetNotFoundException {
        Story story = storyService.ackViewAndGetStory(id);
        StoryDto storeDto = storyMapper.toStoryDto(story);
        return CommonResponse.<StoryDto>builder().payload(storeDto).code(200).message("success").build();
    }

    @GetMapping(value = "/{id}/download", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] exportStory(@PathVariable long id) throws IOException, TargetNotFoundException {
        return storyService.ackDownloadAndGenerateImage(id);
    }

    @PostMapping
    public CommonResponse<StoryDto> submitStory(@RequestBody StoryDto storyDto) {
        Story story = storyService.saveStory(storyDto);
        StoryDto storeDto = storyMapper.toStoryDto(story);
        return CommonResponse.<StoryDto>builder().payload(storeDto).code(200).message("success").build();
    }

    @GetMapping("all")
    public CommonResponse<FetchStoriesDto> listStories(@RequestParam Integer index, @RequestParam Integer size) throws ParameterException {
        FetchStoriesDto resultDto = storyService.getPagedStoryList(index, size);
        return CommonResponse.<FetchStoriesDto>builder().payload(resultDto).code(200).message("success").build();
    }
}
