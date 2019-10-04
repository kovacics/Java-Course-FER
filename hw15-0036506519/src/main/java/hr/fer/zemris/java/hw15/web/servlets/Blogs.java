package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.forms.BlogEntryForm;
import hr.fer.zemris.java.hw15.forms.CommentForm;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet used for processing variety of requests such as
 * showing blog entries, editing entry, adding comments etc.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/servleti/author/*")
public class Blogs extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String fullPath = req.getPathInfo().substring(1);
        String[] parts = fullPath.split("/");

        switch (parts.length) {
        case 1:
            requestForAllBlogs(req, resp, fullPath);
            break;

        case 2:
            String author = parts[0];
            String other = parts[1];

            if (other.equals("new")) {
                newBlogEntryRequest(req, resp, author);
            } else {
                blogEntryInfoRequest(req, resp, author, other);
            }
            break;

        case 3:
            editBlogEntryRequest(req, resp, parts);

        default:
            req.setAttribute("errorMessage", "Invalid path.");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }

    /**
     * Method process request for all blogs.
     *
     * @param req      http request
     * @param resp     http response
     * @param fullPath full url
     * @throws ServletException if servlet error happens
     * @throws IOException      if i/o error happens
     */
    private void requestForAllBlogs(HttpServletRequest req, HttpServletResponse resp, String fullPath) throws ServletException, IOException {
        List<BlogEntry> blogs = DAOProvider.getDAO().getAllBlogsOfUser(fullPath);
        req.setAttribute("blogs", blogs);
        req.setAttribute("author", fullPath);
        req.getRequestDispatcher("/WEB-INF/pages/blogs.jsp").forward(req, resp);
    }

    /**
     * Method process request for blog entry editing.
     *
     * @param req   http request
     * @param resp  http response
     * @param parts url parts
     * @throws ServletException if servlet error happens
     * @throws IOException      if i/o error happens
     */
    private void editBlogEntryRequest(HttpServletRequest req, HttpServletResponse resp, String[] parts) throws ServletException, IOException {
        String author = parts[0];
        String other = parts[1];
        String entryId = parts[2];

        if (!other.equals("edit")) {
            req.setAttribute("errorMessage", "Invalid path.");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        if (!author.equals(req.getSession().getAttribute("current.user.nick"))) {
            req.setAttribute("errorMessage", "Forbidden page.");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        Long id = null;
        try {
            id = Long.parseLong(entryId);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Invalid path.");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
        BlogEntryForm form = new BlogEntryForm();

        // user is not coming from form, so just redirect, no validation
        if (req.getParameter("newEntry") == null) {

            form.fillFromEntry(entry);

            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/newEntry.jsp").forward(req, resp);
            return;
        }

        form.fillFromRequest(req);
        form.validate();

        if (form.hasErrors()) {
            form.fillFromEntry(entry);
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/newEntry.jsp").forward(req, resp);
            return;
        }

        // auto updates entry
        form.fillEntry(entry);
        resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + author);
    }

    /**
     * Method process request for blog entry info.
     *
     * @param req    http request
     * @param resp   http response
     * @param author blog author nick
     * @param id     blog entry author id
     * @throws ServletException if servlet error happens
     * @throws IOException      if i/o error happens
     */
    private void blogEntryInfoRequest(HttpServletRequest req, HttpServletResponse resp, String author, String id) throws ServletException, IOException {
        Long entryId = null;

        try {
            entryId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Invalid path.");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }

        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryId);
        req.setAttribute("entry", entry);

        // user didn't try to add new comment so no validation
        if (req.getParameter("comment") == null) {
            req.getRequestDispatcher("/WEB-INF/pages/entryInfo.jsp").forward(req, resp);
            return;
        }

        CommentForm form = new CommentForm();
        form.fillFromRequest(req);
        form.validate();

        if (form.hasErrors()) {
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/entryInfo.jsp").forward(req, resp);
            return;
        }

        BlogComment comment = new BlogComment();
        form.fillComment(comment, entry);
        DAOProvider.getDAO().saveNewComment(comment);

        resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + author + "/" + entryId);
    }


    /**
     * Method that process request for adding new blog entry.
     *
     * @param req    http request
     * @param resp   http response
     * @param author blog entry author nick
     * @throws ServletException if servlet error happens
     * @throws IOException      if i/o error happens
     */
    private void newBlogEntryRequest(HttpServletRequest req, HttpServletResponse resp, String author) throws ServletException, IOException {

        if (!author.equals(req.getSession().getAttribute("current.user.nick"))) {
            req.setAttribute("errorMessage", "Forbidden page.");
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
            return;
        }

        if (req.getParameter("newEntry") == null) {
            req.getRequestDispatcher("/WEB-INF/pages/newEntry.jsp").forward(req, resp);
            return;
        }

        BlogEntryForm form = new BlogEntryForm();
        form.fillFromRequest(req);
        form.validate();

        if (form.hasErrors()) {
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/newEntry.jsp").forward(req, resp);
            return;
        }

        BlogEntry entry = new BlogEntry();
        BlogUser user = DAOProvider.getDAO().getBlogUser(author);

        form.fillEntry(entry);
        entry.setCreator(user);

        DAOProvider.getDAO().saveNewBlogEntry(entry);

        resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/author/" + author);
    }
}
