package hr.fer.zemris.java.hw15.forms;


import hr.fer.zemris.java.hw15.model.BlogEntry;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Class represents blog entry creation/modification form.
 *
 * @author Stjepan Kovačić
 */
public class BlogEntryForm extends Form {

    /**
     * Blog entry title.
     */
    private String title;

    /**
     * Blog entry text.
     */
    private String text;


    @Override
    public void validate() {
        if (title.isBlank()) {
            errors.put("title", "This field cannot be empty.");
        }
        if (text.isBlank()) {
            errors.put("text", "This field cannot be empty.");
        }
    }

    @Override
    public void fillFromRequest(HttpServletRequest req) {
        this.title = req.getParameter("title");
        this.text = req.getParameter("text");
    }

    /**
     * Method fills given entry with form data.
     *
     * @param entry entry to fill
     */
    public void fillEntry(BlogEntry entry) {
        entry.setTitle(title);
        entry.setText(text);
        if (entry.getCreatedAt() == null) {
            entry.setCreatedAt(new Date());
        }
        entry.setLastModifiedAt(new Date());
    }

    /**
     * Method fills form data from given blog entry.
     *
     * @param entry entry with form data
     */
    public void fillFromEntry(BlogEntry entry) {
        this.title = entry.getTitle();
        this.text = entry.getText();
    }

    /**
     * Getter of the form blog entry title.
     *
     * @return blog entry title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title of the form.
     *
     * @param title title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter of the form blog entry text.
     *
     * @return form text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text of the form.
     *
     * @param text text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}
