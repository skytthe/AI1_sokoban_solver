package ai1_sokoban_solver;

import java.util.Comparator;

public class state_comp
        implements Comparator<state> {

    @Override
    public int compare(state x, state y) {
        if (x.get_heuristic() < y.get_heuristic()) {
            return -1;
        }
        if (x.get_heuristic() > y.get_heuristic()) {
            return 1;
        }
        return 0;
    }
}
