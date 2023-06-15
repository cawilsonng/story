package com.wilson.storybackend.models;

import lombok.*;

import java.awt.image.BufferedImage;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StoryImage {
    private BufferedImage bufferedImage;
    private Coordinate coordinate;
}
