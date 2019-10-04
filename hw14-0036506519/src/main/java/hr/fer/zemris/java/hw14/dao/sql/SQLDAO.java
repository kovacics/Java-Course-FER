package hr.fer.zemris.java.hw14.dao.sql;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * SQL implementation of the web app's DAO.
 */
public class SQLDAO implements DAO {

    @Override
    public List<Poll> getPolls() throws DAOException {
        List<Poll> polls = new ArrayList<>();

        try {
            PreparedStatement ps = SQLConnectionProvider.getConnection().prepareStatement("SELECT * FROM Polls");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                polls.add(new Poll(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (SQLException e) {
            throw new DAOException("Error happened while getting list of polls.");
        }

        return polls;
    }

    @Override
    public List<PollOption> getPollOptions(String pollId) throws DAOException {
        List<PollOption> pollOptions = new ArrayList<>();

        try {
            PreparedStatement ps = SQLConnectionProvider.getConnection()
                    .prepareStatement("SELECT * FROM PollOptions WHERE pollID = " + pollId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                pollOptions.add(new PollOption(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5)
                ));
            }
        } catch (SQLException e) {
            throw new DAOException("Couldn't get options for specified poll.");
        }
        return pollOptions;
    }

    @Override
    public Poll getPoll(String id) throws DAOException {
        try {
            PreparedStatement ps = SQLConnectionProvider.getConnection()
                    .prepareStatement("SELECT * FROM Polls WHERE id = " + id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            return new Poll(rs.getString(1), rs.getString(2), rs.getString(3));
        } catch (SQLException e) {
            throw new DAOException("Couldn't get poll with specified id.");
        }
    }

    @Override
    public void addVote(String pollOptionId) throws DAOException {
        try {
            PreparedStatement st = SQLConnectionProvider.getConnection()
                    .prepareStatement(
                            "UPDATE PollOptions\n" +
                                    "SET votesCount = votesCount + 1 \n" +
                                    "WHERE id = " + pollOptionId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error while updating votes count.");
        }
    }

    @Override
    public List<PollOption> getPollWinners(String pollID) throws DAOException {
        try {
            PreparedStatement ps = SQLConnectionProvider.getConnection().prepareStatement(
                    "SELECT * FROM PollOptions " +
                            "WHERE pollID = " + pollID + " AND " +
                            "votesCount = (SELECT MAX(votesCount)" +
                            "FROM PollOptions WHERE PollID = " + pollID + ")");

            List<PollOption> winners = new ArrayList<>();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                winners.add(new PollOption(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5)));
            }
            return winners;
        } catch (SQLException e) {
            throw new DAOException("Couldn't get winners of specified poll.");
        }
    }

    @Override
    public String getPollID(String pollOptionID) throws DAOException {
        try {
            PreparedStatement ps = SQLConnectionProvider.getConnection().prepareStatement(
                    "SELECT pollID FROM PollOptions WHERE id = " + pollOptionID
            );
            ResultSet rs = ps.executeQuery();
            rs.next();
            return String.valueOf(rs.getLong(1));
        } catch (SQLException e) {
            throw new DAOException("Couldn't get poll id for given pollOption.");
        }
    }
}