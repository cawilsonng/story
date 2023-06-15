package com.wilson.storybackend.repositories;

import com.wilson.storybackend.models.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends CrudRepository<Story, Long> {
    /**
     * Retrieves a story by its ID.
     *
     * @param id the ID of the story to retrieve
     * @return the {@link Story} object representing the story with the specified ID, or {@code null} if not found
     */
    Story getStoryById(long id);

    /**
     * Retrieves a page of stories with pagination.
     *
     * @param pageable the pagination information
     * @return a {@link org.springframework.data.domain.Page} containing the stories
     */
    @Query(value = "SELECT S.id,S.title,S.description, IF(CHAR_LENGTH(S.content) > 33, CONCAT(LEFT(S.content, 33),'...'),S.content) AS 'content',S.create_date FROM story S", nativeQuery = true)
    Page<Story> getStories(Pageable pageable);

}
