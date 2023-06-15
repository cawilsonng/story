package com.wilson.storybackend.controllers;

import com.wilson.storybackend.exceptions.TargetNotFoundException;
import com.wilson.storybackend.mapper.HistoryMapper;
import com.wilson.storybackend.models.CommonResponse;
import com.wilson.storybackend.models.HistoryDto;
import com.wilson.storybackend.models.entity.History;
import com.wilson.storybackend.services.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    private final HistoryService historyService;
    private final HistoryMapper historyMapper;

    public HistoryController(HistoryService historyService, HistoryMapper historyMapper) {
        this.historyService = historyService;
        this.historyMapper = historyMapper;
    }

    @GetMapping
    public ResponseEntity<CommonResponse<HistoryDto>> showStatistics() {
        HistoryDto historyDto = historyService.getHistory();
        return ResponseEntity.ok(CommonResponse.<HistoryDto>builder().payload(historyDto).code(200).message("success").build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<HistoryDto>> showStoryStatistics(@PathVariable long id) throws TargetNotFoundException {
        History history = historyService.getHistoryData(id);
        com.wilson.storybackend.models.HistoryDto historyDto = historyMapper.toChartDataDto(history);
        return ResponseEntity.ok(CommonResponse.<HistoryDto>builder().payload(historyDto).code(200).message("success").build());
    }
}
