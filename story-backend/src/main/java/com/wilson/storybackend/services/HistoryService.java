package com.wilson.storybackend.services;

import com.wilson.storybackend.exceptions.TargetNotFoundException;
import com.wilson.storybackend.models.HistoryDto;
import com.wilson.storybackend.models.entity.History;

public interface HistoryService {
    /**
     * Retrieves the overall history statistic.
     *
     * @return the {@link HistoryDto} object representing the history data
     */
    HistoryDto getHistory();

    /**
     * Retrieves the history statistic for a specific ID.
     *
     * @param id the ID of the history statistic to retrieve
     * @return the {@link History} object representing the history statistic with the specified ID
     * @throws TargetNotFoundException if the history data with the specified ID is not found
     */
    History getHistoryData(long id) throws TargetNotFoundException;

    /**
     * Acknowledges a view for the history data identified by the given ID.
     *
     * @param id the ID of the history data for which the view is acknowledged
     * @return {@code true} if the view acknowledgement was successful, {@code false} otherwise
     */
    boolean viewAck(long id);

    /**
     * Acknowledges a download for the history data identified by the given ID.
     *
     * @param id the ID of the history data for which the download is acknowledged
     * @return {@code true} if the download acknowledgement was successful, {@code false} otherwise
     */
    boolean downloadAck(long id);

    /**
     * Creates a new history data entry for the specified ID.
     *
     * @param id the ID for which to create the history data
     * @return the created {@link History} object representing the new history data entry
     */
    History createHistory(long id);
}
