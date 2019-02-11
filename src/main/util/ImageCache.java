package main.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * The Class ImageCache.
 *
 * @author Sanchez
 */
public class ImageCache {

    private static ImageCache instance;
    private static Map<String, Image> images;

    /**
     * Instantiates a new image cache.
     */
    private ImageCache() {
        images = new HashMap<>();
    }

    /**
     * Gets the single instance of ImageCache.
     *
     * @return single instance of ImageCache
     */
    public static ImageCache getInstance() {
        if (instance == null)
            instance = new ImageCache();

        return instance;
    }

    /**
     * (Partial javadoc).
     *
     * @param path the path
     * This will throw an exception if the image is used in an ImagePattern before it's finished loading.
     * @return Image
     */
    public synchronized Image fetch(String path) {
        Image image = images.get(path);
        if(image == null)
        {
            InputStream file = this.getClass().getClassLoader().getResourceAsStream(path);
            image = new Image(file);
            images.put(path, image);
        }
        return image;
    }


}