package com.wilson.storybackend.services;

import com.wilson.storybackend.configuration.CachingConfig;
import com.wilson.storybackend.exceptions.ParameterException;
import com.wilson.storybackend.exceptions.TargetNotFoundException;
import com.wilson.storybackend.mapper.StoryMapper;
import com.wilson.storybackend.models.FetchStoriesDto;
import com.wilson.storybackend.models.StoryDto;
import com.wilson.storybackend.models.entity.Story;
import com.wilson.storybackend.repositories.StoryRepository;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Log
public class StoryServiceImpl implements StoryService {
    private final StoryRepository storyRepository;

    private final HistoryService historyService;
    private final ImageService imageService;
    private final StoryMapper storyMapper;

    public StoryServiceImpl(StoryRepository storyRepository, HistoryService historyService, ImageService imageService, StoryMapper storyMapper) {
        this.storyRepository = storyRepository;
        this.historyService = historyService;
        this.imageService = imageService;
        this.storyMapper = storyMapper;
    }

    @Cacheable(CachingConfig.STORY)
    @Override
    public Story getStory(long id) throws TargetNotFoundException {
        Story story = storyRepository.getStoryById(id);
        if (story == null) {
            throw new TargetNotFoundException();
        }
        return story;
    }

    @Override
    public Story ackViewAndGetStory(long id) throws TargetNotFoundException {
        Story story = getStory(id);
        historyService.viewAck(story.getId());
        return story;
    }

    private List<Story> getStoryList(Integer index, Integer size) {
        Pageable paging = PageRequest.of(index, size, Sort.by("create_date").descending());
        return storyRepository.getStories(paging).toList();
    }

    @Cacheable(CachingConfig.STORY_LIST)
    @Override
    public FetchStoriesDto getPagedStoryList(Integer index, Integer size) throws ParameterException {
        if (index == null || size == null || size < 0) {
            throw new ParameterException();
        }
        size = size > 100 ? 100 : size;
        long recordSize = storyRepository.count();
        int estimatedPage = Math.toIntExact(recordSize / size);
        estimatedPage = Math.toIntExact(recordSize % size) > 0 ? estimatedPage + 1 : estimatedPage;
        List<Story> stories = getStoryList(index, size);
        List<StoryDto> storyDtoList = stories.stream().map(story -> storyMapper.toStoryDto(story)).toList();
        FetchStoriesDto fetchStoriesDto = FetchStoriesDto.builder()
                .totalPage(estimatedPage)
                .index(index).size(size)
                .stories(storyDtoList).build();
        return fetchStoriesDto;
    }

    @Override
    public Story saveStory(StoryDto storyDto) {
        Story storyEntity = Story.builder()
                .title(storyDto.getTitle())
                .content(storyDto.getContent())
                .description(storyDto.getDescription())
                .createDate(new Date())
                .build();
        Story story = storyRepository.save(storyEntity);
        historyService.createHistory(story.getId());
        return story;
    }

    @Override
    public boolean deleteStory(long id) {
        try {
            storyRepository.deleteById(id);
        } catch (Exception e) {
            log.warning(e.getCause().getMessage());
            return false;
        }
        return true;
    }


    @Override
    public byte[] ackDownloadAndGenerateImage(long id) throws IOException, TargetNotFoundException {
        Story story = storyRepository.getStoryById(id);
        if (story == null) {
            throw new TargetNotFoundException();
        }
        historyService.downloadAck(story.getId());
        return imageService.generateStoryImage(story);
    }
}
