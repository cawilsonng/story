package com.wilson.storybackend.models;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FetchStoriesDto {
    private Integer totalPage;
    private Integer index;
    private Integer size;
    private List<StoryDto> stories;
}
