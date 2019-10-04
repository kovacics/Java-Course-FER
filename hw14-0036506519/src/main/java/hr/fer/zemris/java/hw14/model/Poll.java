package hr.fer.zemris.java.hw14.model;

/**
 * Class represents one row in the polls table.
 *
 * @author Stjepan Kovačić
 */
public class Poll {

    /**
     * Poll id.
     */
    private String id;

    /**
     * Poll title.
     */
    private String title;

    /**
     * Poll message.
     */
    private String message;

    /**
     * Constructs poll with specified id, title and message.
     *
     * @param id      poll id
     * @param title   poll title
     * @param message poll message
     */
    public Poll(String id, String title, String message) {
        this.id = id;
        this.title = title;
        this.message = message;
    }

    /**
     * Getter of the poll id.
     *
     * @return poll id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter of the poll title.
     *
     * @return poll title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter of the poll message.
     *
     * @return poll message
     */
    public String getMessage() {
        return message;
    }
}
