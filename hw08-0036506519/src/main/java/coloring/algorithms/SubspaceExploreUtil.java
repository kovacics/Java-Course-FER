package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Util with methods of subspace exploring algorithms.
 *
 * @author Stjepan Kovačić
 */
public class SubspaceExploreUtil {


    /**
     * Breadth-First Search algorithm.
     *
     * @param s0         initial state
     * @param process    consumer for processing work on state
     * @param succ       function for getting succeeding states
     * @param acceptable predicate for checking if state is acceptable
     * @param <S>        type
     */
    public static <S> void bfs(Supplier<S> s0, Consumer<S> process,
                               Function<S, List<S>> succ,
                               Predicate<S> acceptable) {
        LinkedList<S> toExplore = new LinkedList<>();
        toExplore.add(s0.get());

        while (!toExplore.isEmpty()) {
            S si = toExplore.remove();
            if (!acceptable.test(si)) continue;
            process.accept(si);
            toExplore.addAll(succ.apply(si));
        }
    }

    /**
     * Breadth-First Search algorithm with additional memory of already visited
     * states (already planned to visit).
     * This is more advanced algorithm (gives better performance).
     *
     * @param s0         initial state
     * @param process    consumer for processing work on state
     * @param succ       function for getting succeeding states
     * @param acceptable predicate for checking if state is acceptable
     * @param <S>        type
     */
    public static <S> void bfsv(Supplier<S> s0, Consumer<S> process,
                                Function<S, List<S>> succ,
                                Predicate<S> acceptable) {
        LinkedList<S> toExplore = new LinkedList<>();
        toExplore.add(s0.get());

        Set<S> visited = new HashSet<>();
        visited.add(s0.get());

        while (!toExplore.isEmpty()) {
            S si = toExplore.remove();
            if (!acceptable.test(si)) continue;
            process.accept(si);

            List<S> children = succ.apply(si);

            children.removeIf(visited::contains);

            toExplore.addAll(children);
            visited.addAll(children);
        }
    }

    /**
     * Depth-First Search algorithm.
     *
     * @param s0         initial state
     * @param process    consumer for processing work on state
     * @param succ       function for getting succeeding states
     * @param acceptable predicate for checking if state is acceptable
     * @param <S>        type
     */
    public static <S> void dfs(Supplier<S> s0, Consumer<S> process,
                               Function<S, List<S>> succ,
                               Predicate<S> acceptable) {
        LinkedList<S> toExplore = new LinkedList<>();
        toExplore.add(s0.get());

        while (!toExplore.isEmpty()) {
            S si = toExplore.remove();
            if (!acceptable.test(si)) continue;
            process.accept(si);
            succ.apply(si).forEach(toExplore::addFirst);
        }
    }
}
