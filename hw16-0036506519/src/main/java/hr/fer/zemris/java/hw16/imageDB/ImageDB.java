package hr.fer.zemris.java.hw16.imageDB;


import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents image database.
 *
 * @author Stjepan Kovačić
 */
public class ImageDB {

    /**
     * List of all images.
     */
    private static List<Image> images = new ArrayList<>();

    /**
     * Static method that creates database and fills it with data.
     *
     * @param context servlet context
     */
    public static void createDB(ServletContext context) {
        java.nio.file.Path opisnikPath = Paths.get(context.getRealPath("/WEB-INF/opisnik.txt"));

        try {
            List<String> lines = Files.readAllLines(opisnikPath);
            for (int i = 0; i < lines.size() - 3; i += 3) {
                String name = lines.get(i);
                String info = lines.get(i + 1);
                String[] tags = lines.get(i + 2).split("\\s*,\\s*");

                Image img = new Image(name, info, Arrays.asList(tags));
                images.add(img);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method returns list of all images.
     * @return list of images
     */
    public static List<Image> getImages() {
        return images;
    }
}



