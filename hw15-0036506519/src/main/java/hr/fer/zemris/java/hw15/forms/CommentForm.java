package hr.fer.zemris.java.hw15.forms;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Class represents comment creation form.
 *
 * @author Stjepan Kovačić
 */
public class CommentForm extends Form {

    /**
     * Comment message.
     */
    private String message;

    /**
     * Comment author email.
     */
    private String email;

    @Override
    public void validate() {
        if (message.isBlank()) {
            errors.put("message", "Message field cannot be empty.");
        }
        if (email != null && email.isBlank()) {
            errors.put("email", "Email field cannot be empty.");
        }
    }

    @Override
    public void fillFromRequest(HttpServletRequest req) {
        message = req.getParameter("message");
        email = req.getParameter("email");
        if (email == null) {
            String nick = (String) req.getSession().getAttribute("current.user.nick");
            email = DAOProvider.getDAO().getBlogUser(nick).getEmail();
        }
    }

    /**
     * Method fills given comment with form data.
     *
     * @param comment comment to fill
     * @param entry   blog entry for which comment is intended for
     */
    public void fillComment(BlogComment comment, BlogEntry entry) {
        comment.setMessage(message);
        comment.setPostedOn(new Date());
        comment.setUsersEMail(email);
        comment.setBlogEntry(entry);
    }
}
