package com.wilson.storybackend.mapper;

import com.wilson.storybackend.models.entity.History;
import org.springframework.stereotype.Component;

@Component
public class HistoryMapper {
    public com.wilson.storybackend.models.HistoryDto toChartDataDto(History history) {
        return com.wilson.storybackend.models.HistoryDto.builder()
                .download(history.getDownload())
                .view(history.getView())
                .build();
    }

}
