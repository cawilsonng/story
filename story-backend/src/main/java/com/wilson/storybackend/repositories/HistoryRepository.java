package com.wilson.storybackend.repositories;

import com.wilson.storybackend.models.HistoryDto;
import com.wilson.storybackend.models.entity.History;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends CrudRepository<History, Long> {
    History findHistoryById(long id);

    /**
     * Retrieves the sum of views and downloads from the history table.
     *
     * @return {@link com.wilson.storybackend.models.HistoryDto} representing the sum of views and downloads.
     */
    @Query("SELECT new com.wilson.storybackend.models.HistoryDto(COALESCE(SUM(h.view),0), COALESCE(SUM(h.download),0)) from History h")
    HistoryDto sumHistory();

    /**
     * Acknowledges a view for a story identified by the given story ID.
     *
     * @param storyId the ID of the story for which the view is acknowledged
     */
    @Modifying
    @Query("UPDATE History h set h.view = h.view + 1 where h.storyId = :storyId")
    void ackViewByStoryId(@Param("storyId") long storyId);

    /**
     * Acknowledges a download for a story identified by the given story ID.
     *
     * @param storyId the ID of the story for which the download is acknowledged
     */
    @Modifying
    @Query("UPDATE History h set h.download = h.download + 1 where h.storyId = :storyId")
    void ackDownloadByStoryId(@Param("storyId") long storyId);
}
