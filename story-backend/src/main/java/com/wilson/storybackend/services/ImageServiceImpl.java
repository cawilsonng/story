package com.wilson.storybackend.services;

import com.wilson.storybackend.models.Coordinate;
import com.wilson.storybackend.models.StoryImage;
import com.wilson.storybackend.models.entity.Story;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {
    private Coordinate fillTitleToImage(Graphics2D graphics, int imageWidth, Coordinate startCoordinate, String title) {
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.PLAIN, 24));

        FontMetrics fontMetrics = graphics.getFontMetrics();
        int titleWidth = fontMetrics.stringWidth(title);
        int x = imageWidth / 2 - (titleWidth / 2);
        int y = startCoordinate.getY() + 40;
        graphics.drawString(title, x, y);
        return Coordinate.builder().x(x).y(y).build();
    }

    private Coordinate fillDescriptionToImage(Graphics2D graphics, int imageWidth, Coordinate startCoordinate, String description) {
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.PLAIN, 12));
        startCoordinate.setX(20);
        startCoordinate.setY(startCoordinate.getY() + 40);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        return drawWrapString(graphics, imageWidth, startCoordinate, fontMetrics, "Description: " + description);
    }

    private Coordinate fillContentToImage(Graphics2D graphics, int imageWidth, Coordinate startCoordinate, String content) {
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.PLAIN, 12));
        startCoordinate.setX(20);
        startCoordinate.setY(startCoordinate.getY() + 30);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        return drawWrapString(graphics, imageWidth, startCoordinate, fontMetrics, content);
    }

    private Coordinate drawWrapString(Graphics2D graphics, int imageWidth, Coordinate coordinate, FontMetrics fontMetrics, String text) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        int maxWidth = imageWidth - 40;
        String[] lines = text.split("\n");
        for (String line : lines) {
            String[] words = line.split("\\s+");
            StringBuilder currentLine = new StringBuilder();
            for (String word : words) {
                if (fontMetrics.stringWidth(currentLine + " " + word) < maxWidth) {
                    if (!currentLine.isEmpty()) {
                        currentLine.append(" ");
                    }
                    currentLine.append(word);
                } else {
                    graphics.drawString(currentLine.toString(), x, y);
                    y += fontMetrics.getHeight();
                    currentLine = new StringBuilder(word);
                }
            }
            graphics.drawString(currentLine.toString(), x, y);
            y += fontMetrics.getHeight();
        }
        return Coordinate.builder().x(x).y(y).build();
    }

    private StoryImage generateStoryImage(BufferedImage image, int imageWidth, int imageHeight, Story story) {
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        Coordinate drawingCoordinate = Coordinate.builder().x(0).y(0).build();
        drawingCoordinate = fillTitleToImage(graphics, image.getWidth(), drawingCoordinate, story.getTitle());
        drawingCoordinate = fillDescriptionToImage(graphics, image.getWidth(), drawingCoordinate, story.getDescription());
        drawingCoordinate = fillContentToImage(graphics, image.getWidth(), drawingCoordinate, story.getContent());
        return StoryImage.builder().bufferedImage(image).coordinate(drawingCoordinate).build();
    }

    @Override
    public byte[] generateStoryImage(Story story) throws IOException {
        int imageWidth = 800;
        int imageHeight = 600;
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //TODO calculate the image height before generate image and remove generate twice time logic;
        StoryImage storyImage = generateStoryImage(image, imageWidth, imageHeight, story);
        imageHeight = storyImage.getCoordinate().getY() + 20;
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        generateStoryImage(image, imageWidth, imageHeight, story);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        return imageBytes;
    }
}
