package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Util class with searching algorithm methods.
 *
 * @author Stjepan Kovačić
 */
public class SearchUtil {


    /**
     * Breadth-First Search algorithm.
     *
     * @param s0   initial state
     * @param succ function for getting succeeding states
     * @param goal predicate for testing goal (acceptable) state
     * @param <S>  type
     * @return goal state node with smallest possible cost
     */
    public static <S> Node<S> bfs(Supplier<S> s0,
                                  Function<S, List<Transition<S>>> succ,
                                  Predicate<S> goal) {
        LinkedList<Node<S>> toExplore = new LinkedList<>();
        toExplore.add(new Node<>(null, s0.get(), 0));

        while (!toExplore.isEmpty()) {
            Node<S> ni = toExplore.remove();
            if (goal.test(ni.getState())) return ni;
            succ.apply(ni.getState()).forEach((tr) ->
                    toExplore.add(new Node<>(ni, tr.getState(), tr.getCost() + ni.getCost())));
        }

        return null;
    }

    /**
     * Advanced Breadth-First Search algorithm (memories already visited states, which
     * results in better performance)
     *
     * @param s0   initial state
     * @param succ function for getting succeeding states
     * @param goal predicate for testing goal (acceptable) state
     * @param <S>  type
     * @return goal state node with smallest possible cost
     */
    public static <S> Node<S> bfsv(Supplier<S> s0,
                                   Function<S, List<Transition<S>>> succ,
                                   Predicate<S> goal) {

        LinkedList<Node<S>> toExplore = new LinkedList<>();
        toExplore.add(new Node<>(null, s0.get(), 0));

        Set<S> visited = new HashSet<>();
        visited.add(s0.get());

        while (!toExplore.isEmpty()) {
            Node<S> ni = toExplore.remove();
            if (goal.test(ni.getState())) return ni;

            succ.apply(ni.getState()).forEach((tr) -> {
                if (!visited.contains(tr.getState())) {
                    toExplore.add(new Node<>(ni, tr.getState(), tr.getCost() + ni.getCost()));
                    visited.add(tr.getState());
                }
            });
        }

        return null;
    }
}
