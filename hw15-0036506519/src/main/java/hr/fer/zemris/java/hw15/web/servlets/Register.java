package hr.fer.zemris.java.hw15.web.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.forms.RegisterForm;
import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class represents servlet used for user registration process.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/servleti/register")
public class Register extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Method that process request.
     *
     * @param req  http request
     * @param resp http response
     * @throws ServletException if servlet error happens
     * @throws IOException      if i/o error happens
     */
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // user didn't came from the form, so no validation, just redirect
        if (req.getParameter("register") == null) {
            req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
            return;
        }

        RegisterForm form = new RegisterForm();
        form.fillFromRequest(req);
        form.validate();

        if (form.hasErrors()) {
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
            return;
        }

        BlogUser user = new BlogUser();
        form.fillUser(user);
        DAOProvider.getDAO().saveNewBlogUser(user);

        // after registration, automatically log in user
        req.getSession().setAttribute("current.user.id", user.getId());
        req.getSession().setAttribute("current.user.fn", user.getFirstName());
        req.getSession().setAttribute("current.user.ln", user.getLastName());
        req.getSession().setAttribute("current.user.nick", user.getNick());

        resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
    }
}
