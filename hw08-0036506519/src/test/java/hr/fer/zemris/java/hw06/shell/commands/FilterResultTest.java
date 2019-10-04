package hr.fer.zemris.java.hw06.shell.commands;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

/**
 * @author Stjepan Kovačić
 */
class FilterResultTest {


    @Test
    void groupTest() {
        FilterResult filterResult = new FilterResult(Paths.get("slika1-zagreb.jpg").toFile(), "slika(\\d+)-([^.]+)\\.jpg");
        System.out.println(filterResult.toString());
        System.out.println(filterResult.numberOfGroups());
        System.out.println(filterResult.group(0));
        System.out.println(filterResult.group(1));
        System.out.println(filterResult.group(2));
    }
}