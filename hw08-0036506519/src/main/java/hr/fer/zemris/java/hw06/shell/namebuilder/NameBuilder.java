package hr.fer.zemris.java.hw06.shell.namebuilder;

import hr.fer.zemris.java.hw06.shell.commands.FilterResult;

import java.util.Objects;

/**
 * Interface represents name builder which is used to generate
 * name of the file, and append it at some string builder.
 *
 * @author Stjepan Kovačić
 */
public interface NameBuilder {

    /**
     * Generate new name of the file and append it at given string builder.
     *
     * @param result filter result with file whose new name should be generated
     * @param sb     string builder on which to append new name
     */
    void execute(FilterResult result, StringBuilder sb);

    /**
     * Creates binary composite name builder of this and other name builder.
     *
     * @param other other name builder
     * @return composite name builder
     */
    default NameBuilder then(NameBuilder other) {
        Objects.requireNonNull(other);
        return (res, sb) -> {
            this.execute(res, sb);
            other.execute(res, sb);
        };
    }
}
