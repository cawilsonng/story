package com.wilson.storybackend.services;

import com.wilson.storybackend.exceptions.TargetNotFoundException;
import com.wilson.storybackend.models.entity.History;
import com.wilson.storybackend.repositories.HistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoryServiceTests {

    @InjectMocks
    private HistoryServiceImpl historyService;

    @Mock
    private HistoryRepository historyRepository;

    @Test
    void testGetHistoryData_WhenValidId() throws TargetNotFoundException {
        long historyId = 1L;
        History expectedHistory = new History();
        when(historyRepository.findHistoryById(historyId)).thenReturn(expectedHistory);
        History actualHistory = historyService.getHistoryData(historyId);
        assertNotNull(actualHistory);
        assertEquals(expectedHistory, actualHistory);
        verify(historyRepository).findHistoryById(historyId);
    }

    @Test
    void testGetHistoryData_WhenInvalidId() {
        long historyId = 1L;
        when(historyRepository.findHistoryById(historyId)).thenReturn(null);
        assertThrows(TargetNotFoundException.class, () -> historyService.getHistoryData(historyId));
        verify(historyRepository).findHistoryById(historyId);
    }

    @Test
    void testViewAck() {
        long historyId = 1L;
        historyService.viewAck(historyId);
        verify(historyRepository).ackViewByStoryId(historyId);
    }

    @Test
    void testDownloadAck() {
        long historyId = 1L;
        historyService.downloadAck(historyId);
        verify(historyRepository).ackDownloadByStoryId(historyId);
    }

    @Test
    void testCreateHistory() {
        long storyId = 1L;
        History expectedHistory = new History();
        expectedHistory.setStoryId(storyId);
        when(historyRepository.save(expectedHistory)).thenReturn(expectedHistory);
        History actualHistory = historyService.createHistory(storyId);
        assertNotNull(actualHistory);
        assertEquals(expectedHistory, actualHistory);
        assertEquals(storyId, actualHistory.getStoryId());
        assertEquals(0, actualHistory.getDownload());
        assertEquals(0, actualHistory.getView());
        verify(historyRepository).save(any(History.class));
    }
}