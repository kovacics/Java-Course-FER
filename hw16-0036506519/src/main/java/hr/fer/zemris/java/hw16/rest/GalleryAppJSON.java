package hr.fer.zemris.java.hw16.rest;

import hr.fer.zemris.java.hw16.imageDB.Image;
import hr.fer.zemris.java.hw16.imageDB.ImageDB;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.TreeSet;

/**
 * Jersey servlet used for getting gallery app data such as photos and list of tags.
 *
 * @author Stjepan Kovačić
 */
@Path("/gallery")
public class GalleryAppJSON {

    @Context
    HttpServletRequest request;

    /**
     * Method gets all tags of the gallery photos.
     *
     * @return all tags in json format
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tags")
    public Response getTags() {
        List<Image> images = ImageDB.getImages();
        TreeSet<String> sortedTags = new TreeSet<>();

        for (Image img : images) {
            sortedTags.addAll(img.getTags());
        }

        JSONArray tags = new JSONArray(sortedTags);
        return Response.status(Response.Status.OK).entity(tags.toString()).build();
    }

    /**
     * Method returns all image data for images with given tag.
     *
     * @param tag tag for getting images
     * @return data of images with given tag in json format
     */
    @Path("tags/{tag}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImagesForTag(@PathParam("tag") String tag) {
        List<Image> images = ImageDB.getImages();

        JSONArray array = new JSONArray();
        for (Image img : images) {
            JSONObject result = new JSONObject();

            for (String currentTag : img.getTags()) {
                if (currentTag.trim().equals(tag)) {
                    result.put("name", img.getName());
                    result.put("info", img.getInfo());
                    result.put("tags", img.getTags());
                    array.put(result);
                    break;
                }
            }
        }

        return Response.status(Response.Status.OK).entity(array.toString()).build();
    }

    /**
     * Method returns data of image with given name.
     *
     * @param name name of the image
     * @return image data in json format
     */
    @Path("{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByName(@PathParam("name") String name) {
        List<Image> images = ImageDB.getImages();
        JSONObject result = new JSONObject();

        for (Image img : images) {
            if (img.getName().equals(name)) {
                result.put("name", name);
                result.put("info", img.getInfo());
                result.put("tags", img.getTags());
                break;
            }
        }
        return Response.status(Response.Status.OK).entity(result.toString()).build();
    }
}




