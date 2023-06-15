package com.wilson.storybackend.models;

import lombok.*;

import java.util.Date;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StoryDto {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Date createDate;
}
