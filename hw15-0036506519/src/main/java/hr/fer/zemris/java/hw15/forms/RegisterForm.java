package hr.fer.zemris.java.hw15.forms;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.hash.SHA1Util;
import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Class represents registration form.
 *
 * @author Stjepan Kovačić
 */
public class RegisterForm extends Form {

    /**
     * User first name.
     */
    private String firstName;

    /**
     * User last name.
     */
    private String lastName;

    /**
     * User email.
     */
    private String email;

    /**
     * User nick.
     */
    private String nick;

    /**
     * User password.
     */
    private String password;

    /**
     * Empty field error message.
     */
    private final static String EMPTY_FIELD_MESSAGE = "This field cannot be empty.";


    @Override
    public void validate() {
        if (firstName.isBlank()) {
            errors.put("firstName", EMPTY_FIELD_MESSAGE);
        }
        if (lastName.isBlank()) {
            errors.put("lastName", EMPTY_FIELD_MESSAGE);
        }
        if (email.isBlank()) {
            errors.put("email", EMPTY_FIELD_MESSAGE);
        }
        if (nick.isBlank()) {
            errors.put("nick", EMPTY_FIELD_MESSAGE);
        } else {
            BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
            if (user != null) {
                errors.put("nick", "Nickname is already taken, choose some other.");
            }
        }
        if (password.isBlank()) {
            errors.put("password", EMPTY_FIELD_MESSAGE);
        } else if (password.length() < 8) {
            errors.put("password", "Password is too weak, it should have at least 8 characters.");
        }
    }

    @Override
    public void fillFromRequest(HttpServletRequest req) {
        firstName = req.getParameter("firstName");
        lastName = req.getParameter("lastName");
        email = req.getParameter("email");
        nick = req.getParameter("nick");
        password = req.getParameter("password");
    }

    /**
     * Method fills given user with form data.
     *
     * @param user user to fill
     */
    public void fillUser(BlogUser user) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setNick(nick);
        user.setPasswordHash(SHA1Util.getHash(password));
    }
}
