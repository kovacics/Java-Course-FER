package hr.fer.zemris.java.hw14.model;

/**
 * Class represents one row in poll options table.
 *
 * @author Stjepan Kovačić
 */
public class PollOption {
    /**
     * Poll option id.
     */
    private String id;

    /**
     * Poll option title.
     */
    private String optionTitle;

    /**
     * Poll option link.
     */
    private String optionLink;

    /**
     * ID of the poll whose option is this poll option.
     */
    private String pollID;

    /**
     * Votes count of this poll option.
     */
    private int votesCount;

    /**
     * Constructs poll option with specified attributes.
     *
     * @param id          poll option id
     * @param optionTitle option title
     * @param optionLink  option link
     * @param pollID      poll id
     * @param votesCount  votes count of the option
     */
    public PollOption(String id, String optionTitle, String optionLink, String pollID, int votesCount) {
        this.id = id;
        this.optionTitle = optionTitle;
        this.optionLink = optionLink;
        this.pollID = pollID;
        this.votesCount = votesCount;
    }

    /**
     * Getter of the poll option id.
     *
     * @return option id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter of the poll option title.
     *
     * @return option title
     */
    public String getOptionTitle() {
        return optionTitle;
    }

    /**
     * Getter of the poll option link.
     *
     * @return option link
     */
    public String getOptionLink() {
        return optionLink;
    }

    /**
     * Getter of the poll id.
     *
     * @return poll id
     */
    public String getPollID() {
        return pollID;
    }

    /**
     * Getter of the votes count of the option.
     *
     * @return option votes count
     */
    public int getVotesCount() {
        return votesCount;
    }
}
