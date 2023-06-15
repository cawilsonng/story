package com.wilson.storybackend.mapper;

import com.wilson.storybackend.models.StoryDto;
import com.wilson.storybackend.models.entity.Story;
import org.springframework.stereotype.Component;

@Component
public class StoryMapper {
    public StoryDto toStoryDto(Story story) {
        return StoryDto.builder()
                .id(story.getId())
                .title(story.getTitle())
                .description(story.getDescription())
                .content(story.getContent())
                .createDate(story.getCreateDate())
                .build();
    }

}
