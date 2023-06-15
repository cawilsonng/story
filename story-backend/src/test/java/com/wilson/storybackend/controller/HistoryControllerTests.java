package com.wilson.storybackend.controller;

import com.wilson.storybackend.controllers.HistoryController;
import com.wilson.storybackend.mapper.HistoryMapper;
import com.wilson.storybackend.models.HistoryDto;
import com.wilson.storybackend.models.entity.History;
import com.wilson.storybackend.services.HistoryService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class HistoryControllerTests {

    @Mock
    private HistoryService historyService;

    @Mock
    private HistoryMapper historyMapper;

    @InjectMocks
    private HistoryController historyController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(historyController).build();
    }

    @Test
    void testShowStatistics() throws Exception {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setView(10);
        historyDto.setDownload(5);

        when(historyService.getHistory()).thenReturn(historyDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.view").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.download").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"));
    }

    @Test
    void testShowStoryStatistics() throws Exception {
        long historyId = 1L;
        History history = new History();
        history.setView(5);
        history.setDownload(3);

        HistoryDto historyDto = new HistoryDto();
        historyDto.setView(5);
        historyDto.setDownload(3);

        when(historyService.getHistoryData(anyLong())).thenReturn(history);
        when(historyMapper.toChartDataDto(history)).thenReturn(historyDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/history/{id}", historyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.view").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.payload.download").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"));
    }
}