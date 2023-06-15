package com.wilson.storybackend.services;

import com.wilson.storybackend.models.entity.Story;

import java.io.IOException;

public interface ImageService {
    /**
     * Generates the image for the given story.
     *
     * @param story the {@link Story} object for which to generate the image
     * @return a byte array representing the generated image
     * @throws IOException if an I/O error occurs during the image generation
     */
    byte[] generateStoryImage(Story story) throws IOException;
}
