package hr.fer.zemris.java.hw16.rest;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Servlet used for creating and fetching thumbnails and full gallery images.
 *
 * @author Stjepan Kovačić
 */
@WebServlet(urlPatterns = {"/thumbnail", "/fullImage"})
public class ThumbnailServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String action = req.getServletPath().substring(1);
        String name = req.getParameter("name");
        String format = name.split("\\.")[1];

        if (action.equals("thumbnail")) {

            File toThumbnail = new File(req.getServletContext().getRealPath("/WEB-INF/thumbnails/" + name));
            File toFullImage = new File(req.getServletContext().getRealPath("/WEB-INF/slike/" + name));

            if (toThumbnail.exists() && toThumbnail.isFile()) {
                BufferedImage thumbnail = ImageIO.read(toThumbnail);
                ImageIO.write(thumbnail, format, resp.getOutputStream());
                return;
            }
            toThumbnail.getParentFile().mkdir();
            Files.createFile(toThumbnail.toPath());

            BufferedImage img = ImageIO.read(toFullImage);
            BufferedImage resized = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
            Graphics g = resized.createGraphics();
            g.drawImage(img, 0, 0, 150, 150, null);
            g.dispose();

            ImageIO.write(resized, format, toThumbnail);
            ImageIO.write(resized, format, resp.getOutputStream());
        } else {
            File toFullImage = new File(req.getServletContext().getRealPath("/WEB-INF/slike/" + name));
            BufferedImage img = ImageIO.read(toFullImage);

            ImageIO.write(img, format, resp.getOutputStream());
        }
    }
}
