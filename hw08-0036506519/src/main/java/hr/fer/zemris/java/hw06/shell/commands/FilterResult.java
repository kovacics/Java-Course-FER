package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represent one file that passed filtering filename with some mask(pattern).
 *
 * @author Stjepan Kovačić
 */
public class FilterResult {

    /**
     * File whose name matches pattern.
     */
    private File file;

    /**
     * Pattern of the filename.
     */
    private Pattern pattern;

    /**
     * Constructs filter result with specified pair of file and pattern.
     *
     * @param file    file
     * @param pattern pattern of the file name
     */
    public FilterResult(File file, String pattern) {
        this.file = file;
        this.pattern = Pattern.compile(pattern, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
    }

    /**
     * Returns name of the file.
     *
     * @return name of the file
     */
    @Override
    public String toString() {
        return file.getName();
    }


    /**
     * Returns number of groups specified in the pattern.
     *
     * @return number of groups
     */
    public int numberOfGroups() {
        Matcher m = pattern.matcher(this.toString());
        return m.groupCount();
    }

    /**
     * Returns group of the filename at the specified index in the
     * array of the all groups.
     *
     * @param index which group to return
     * @return group
     */
    public String group(int index) {
        Matcher m = pattern.matcher(this.toString());
        if (m.find()) {
            return m.group(index);
        }
        return null;
    }

    public File getFile() {
        return file;
    }
}
