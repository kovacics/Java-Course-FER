package hr.fer.zemris.java.hw13.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Util class with methods used for voting web app.
 *
 * @author Stjepan Kovačić
 */
public class GlasanjeUtil {

    public static List<BandRecord> getAllBands(String fileName) throws IOException {
        ArrayList<BandRecord> bands = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        for (String line : lines) {
            String[] parts = line.split("\\t");
            bands.add(new BandRecord(parts[0], parts[1], parts[2]));
        }

        return bands;
    }


    /**
     * Helping class for getting results map.
     *
     * @param fileName filename
     * @return results map
     * @throws IOException if io error happens
     */
    private static Map<String, Integer> getResultsMap(String fileName) throws IOException {
        Map<String, Integer> results = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        for (String line : lines) {
            String[] parts = line.split("\\t");
            results.put(parts[0], Integer.parseInt(parts[1]));
        }
        return results;
    }

    /**
     * Helping class for getting list of all band records.
     *
     * @param resultsFileName results path
     * @param bandsFileName   bands path
     * @return list of records
     * @throws IOException if io error happens
     */
    public static List<VotingResult> getVotingResults(String resultsFileName, String bandsFileName) throws IOException {
        Map<String, Integer> results = getResultsMap(resultsFileName);
        List<BandRecord> bands = getAllBands(bandsFileName);

        ArrayList<VotingResult> records = new ArrayList<>();

        for (BandRecord band : bands) {
            Integer votes = results.get(band.id);
            records.add(new VotingResult(band, votes == null ? 0 : votes));
        }

        records.sort((first, second) -> Integer.compare(second.votes, first.votes));
        return records;
    }

    /**
     * Returns list of winners.
     *
     * @param records records
     * @return list
     */
    public static List<BandRecord> getAllWinners(List<VotingResult> records) {
        ArrayList<BandRecord> winners = new ArrayList<>(1);
        var winner = records.get(0);
        int winnerVotes = winner.votes;
        winners.add(winner.band);

        for (int i = 1; i < records.size(); i++) {
            if (records.get(i).votes == winnerVotes) {
                winners.add(records.get(i).band);
            }
        }
        return winners;
    }

    /**
     * Static method for updating result for the band with given id.
     *
     * @param id              id of the band
     * @param resultsFileName file with current band results
     * @throws IOException if io error happens
     */
    public synchronized static void updateVotingResults(String id, String resultsFileName) throws IOException {
        Map<String, Integer> resultsMap = getResultsMap(resultsFileName);
        resultsMap.merge(id, 1, (oldValue, value) -> oldValue + 1);

        StringBuilder sb = new StringBuilder();
        resultsMap.forEach((key, value) ->
                sb.append(key).append('\t').append(value).append('\n'));

        Files.writeString(Paths.get(resultsFileName), sb.toString());
    }


    /**
     * Helping static class representing band record.
     */
    public static class BandRecord {

        /**
         * Band id.
         */
        private String id;

        /**
         * Band name.
         */
        private String name;

        /**
         * Band song.
         */
        private String song;

        /**
         * Constructs band record with specified id, name and song.
         *
         * @param id   band id
         * @param name band name
         * @param song band song
         */
        public BandRecord(String id, String name, String song) {
            this.id = id;
            this.name = name;
            this.song = song;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSong() {
            return song;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BandRecord that = (BandRecord) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

    /**
     * Helping class representing voting result with band and
     * number of votes for the band.
     */
    public static class VotingResult {

        /**
         * Band record.
         */
        private BandRecord band;

        /**
         * Number of votes.
         */
        private int votes;

        /**
         * Constructs voting result with given band and votes.
         *
         * @param band  band
         * @param votes votes number
         */
        public VotingResult(BandRecord band, int votes) {
            this.band = band;
            this.votes = votes;
        }

        public BandRecord getBand() {
            return band;
        }

        public int getVotes() {
            return votes;
        }
    }
}
