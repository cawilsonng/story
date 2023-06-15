package com.wilson.storybackend.models;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDto {
    private long view;
    private long download;
}
