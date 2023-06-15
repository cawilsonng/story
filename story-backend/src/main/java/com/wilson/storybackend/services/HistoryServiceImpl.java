package com.wilson.storybackend.services;

import com.wilson.storybackend.configuration.CachingConfig;
import com.wilson.storybackend.exceptions.TargetNotFoundException;
import com.wilson.storybackend.models.HistoryDto;
import com.wilson.storybackend.models.entity.History;
import com.wilson.storybackend.repositories.HistoryRepository;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    @Cacheable(CachingConfig.SUM_HISTORY)
    public HistoryDto getHistory() {
        return historyRepository.sumHistory();
    }

    @Override
    @Cacheable(CachingConfig.HISTORY)
    public History getHistoryData(long id) throws TargetNotFoundException {
        History history = historyRepository.findHistoryById(id);
        if (history == null) {
            throw new TargetNotFoundException();
        }
        return history;
    }

    @Override
    public boolean viewAck(long id) {
        try {
            historyRepository.ackViewByStoryId(id);
            return true;
        } catch (Exception e) {
            log.warning(e.getCause().getMessage());
            return false;
        }
    }

    @Override
    public boolean downloadAck(long id) {
        try {
            historyRepository.ackDownloadByStoryId(id);
            return true;
        } catch (Exception e) {
            log.warning(e.getCause().getMessage());
            return false;
        }
    }

    @Override
    public History createHistory(long id) {
        History history = History.builder()
                .storyId(id).download(0)
                .view(0).build();
        return historyRepository.save(history);
    }
}
