package hr.fer.zemris.java.hw14.dao;


import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

import java.util.List;

/**
 * Data access object interface with all needed methods for
 * communication between application and data persistence layer.
 */
public interface DAO {

    /**
     * Method returns list of all available polls.
     *
     * @return list of all polls
     * @throws DAOException if error happens in the data persistence layer
     */
    List<Poll> getPolls() throws DAOException;

    /**
     * Method returns poll with given poll id.
     *
     * @param id id of the poll
     * @return poll with given id
     * @throws DAOException if error happens in the data persistence layer
     */
    Poll getPoll(String id) throws DAOException;

    /**
     * Method returns list of all poll options for given poll id.
     *
     * @param pollId poll id
     * @return list of all poll options for given poll id
     * @throws DAOException if error happens in the data persistence layer
     */
    List<PollOption> getPollOptions(String pollId) throws DAOException;

    /**
     * Method returns poll id for given option.
     *
     * @param pollOptionID poll option id
     * @return poll id
     * @throws DAOException if error happens in the data persistence layer
     */
    String getPollID(String pollOptionID) throws DAOException;

    /**
     * Method updates(increment) vote count for given poll option id.
     *
     * @param pollOptionId id of the poll option
     * @throws DAOException if error happens in the data persistence layer
     */
    void addVote(String pollOptionId) throws DAOException;

    /**
     * Method returns list of all winners of the poll with given id.
     *
     * @param pollID poll id
     * @return list of winners
     * @throws DAOException if error happens in the data persistence layer
     */
    List<PollOption> getPollWinners(String pollID) throws DAOException;
}