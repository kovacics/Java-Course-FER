package hr.fer.zemris.java.hw16.imageDB;

import java.util.Collection;

/**
 * Class represents image data.
 *
 * @author Stjepan Kovačić
 */
public class Image {

    /**
     * Image name.
     */
    private String name;

    /**
     * Image info.
     */
    private String info;

    /**
     * Image tags.
     */
    private Collection<String> tags;

    /**
     * Constructs image with name, info and tags.
     *
     * @param name image name
     * @param info image info
     * @param tags image tags
     */
    public Image(String name, String info, Collection<String> tags) {
        this.name = name;
        this.info = info;
        this.tags = tags;
    }

    /**
     * Getter of the name.
     *
     * @return image name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of the info.
     *
     * @return image info
     */
    public String getInfo() {
        return info;
    }

    /**
     * Getter of the tags list.
     *
     * @return list of tags
     */
    public Collection<String> getTags() {
        return tags;
    }
}
