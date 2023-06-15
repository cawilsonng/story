package com.wilson.storybackend.services;

import com.wilson.storybackend.exceptions.ParameterException;
import com.wilson.storybackend.exceptions.TargetNotFoundException;
import com.wilson.storybackend.models.FetchStoriesDto;
import com.wilson.storybackend.models.StoryDto;
import com.wilson.storybackend.models.entity.Story;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;

public interface StoryService {
    /**
     * Retrieves the story with the given ID from the cache or the database.
     *
     * @param id the ID of the story to retrieve
     * @return the {@link Story} object representing the story with the specified ID
     * @throws TargetNotFoundException if the story with the specified ID is not found
     */
    @Cacheable("story")
    Story getStory(long id) throws TargetNotFoundException;

    /**
     * Acknowledges a view for the story identified by the given ID and retrieves the story.
     *
     * @param id the ID of the story for which the view is acknowledged
     * @return the {@link Story} object representing the updated story with the specified ID
     * @throws TargetNotFoundException if the story with the specified ID is not found
     */
    Story ackViewAndGetStory(long id) throws TargetNotFoundException;

    /**
     * Retrieves a paginated list of stories based on the page index and page size parameters.
     *
     * @param index the index of the page to retrieve
     * @param size  the number of stories per page
     * @return the {@link FetchStoriesDto} object containing the paginated list of stories
     * @throws ParameterException if the index or size parameters are invalid
     */
    FetchStoriesDto getPagedStoryList(Integer index, Integer size) throws ParameterException;

    /**
     * Save a new story based on the provided story DTO.
     *
     * @param storyDto the {@link StoryDto} object representing the story to be saved
     * @return the {@link Story} object representing the saved story
     */
    Story saveStory(StoryDto storyDto);

    /**
     * Delete the story with the given ID.
     *
     * @param id the ID of the story to delete
     * @return {@code true} if the deletion was successful, {@code false} otherwise
     */
    boolean deleteStory(long id);

    /**
     * Acknowledges a download for the story identified by the given ID and generates the associated image.
     *
     * @param id the ID of the story for which the download is acknowledged and image is generated
     * @return a byte array representing the generated image
     * @throws IOException             if an I/O error occurs during the image generation
     * @throws TargetNotFoundException if the story with the specified ID is not found
     */
    byte[] ackDownloadAndGenerateImage(long id) throws IOException, TargetNotFoundException;
}
