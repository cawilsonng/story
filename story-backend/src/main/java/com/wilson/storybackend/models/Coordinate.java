package com.wilson.storybackend.models;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {
    private int x;
    private int y;
}
