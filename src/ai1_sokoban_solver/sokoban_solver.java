package ai1_sokoban_solver;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class sokoban_solver {

    public LinkedList solve_astar(map map2) {
        String goal_state = map2.solution();
        state_comp comparator = new state_comp();
        PriorityQueue<state> open = new PriorityQueue<state>(10, comparator);
        HashSet<String> closed = new HashSet<String>();
        ArrayList<state> next_states = null;
        state current_state = map2.get_init_state();
        open.add(current_state);
        while (!open.isEmpty()) {
            current_state = (state) open.poll();
            while (closed.contains(current_state.toString())) {
                current_state = (state) open.poll();
            }
            closed.add(current_state.toString());
            if (current_state.toString().substring(4).equals(goal_state)) {
                System.out.println("Solution Found");
                System.out.println("Nodes expanded: " + closed.size());
                LinkedList<state> path = new LinkedList<state>();
                while (current_state.get_parent() != null) {
                    path.addFirst(current_state);
                    current_state = current_state.get_parent();
                }
                System.out.println("Solution path: " + path.size());
                path.addFirst(map2.get_init_state());
                return path;
            }
            next_states = map2.generate_next_state(current_state);
            for (int i = 0; i < next_states.size(); ++i) {
                if (closed.contains(next_states.get(i).toString())) {
                    continue;
                }
                open.add(next_states.get(i));
            }
        }
        System.out.println("No Solution Found");
        System.out.println("Nodes expanded: " + closed.size());
        return null;
    }

    public LinkedList solve_breadth_first(map map2) {
        String goal_state = map2.solution();
        LinkedList<state> open = new LinkedList<state>();
        HashSet<String> closed = new HashSet<String>();
        ArrayList<state> next_states = null;
        state current_state = map2.get_init_state();
        open.add(current_state);
        boolean count = false;
        while (!open.isEmpty()) {
            current_state = (state) open.removeFirst();
            while (closed.contains(current_state.toString())) {
                current_state = (state) open.removeFirst();
            }
            closed.add(current_state.toString());
            if (current_state.toString().substring(4).equals(goal_state)) {
                System.out.println("Solution Found");
                System.out.println("Nodes expanded: " + closed.size());
                LinkedList<state> path = new LinkedList<state>();
                while (current_state.get_parent() != null) {
                    path.addFirst(current_state);
                    current_state = current_state.get_parent();
                }
                System.out.println("Solution path: " + path.size());
                path.addFirst(map2.get_init_state());
                return path;
            }
            next_states = map2.generate_next_state(current_state);
            for (int i = 0; i < next_states.size(); ++i) {
                if (closed.contains(next_states.get(i).toString())) {
                    continue;
                }
                open.addLast(next_states.get(i));
            }
        }
        System.out.println("No Solution Found");
        System.out.println("Nodes expanded: " + closed.size());
        return null;
    }

    public LinkedList solve_depth_first(map map2) {
        String goal_state = map2.solution();
        LinkedList<state> open = new LinkedList<state>();
        HashSet<String> closed = new HashSet<String>();
        ArrayList<state> next_states = null;
        state current_state = map2.get_init_state();
        open.add(current_state);
        boolean count = false;
        while (!open.isEmpty()) {
            current_state = (state) open.removeLast();
            while (closed.contains(current_state.toString())) {
                current_state = (state) open.removeLast();
            }
            closed.add(current_state.toString());
            if (current_state.toString().substring(4).equals(goal_state)) {
                System.out.println("Solution Found");
                System.out.println("Nodes expanded: " + closed.size());
                LinkedList<state> path = new LinkedList<state>();
                while (current_state.get_parent() != null) {
                    path.addFirst(current_state);
                    current_state = current_state.get_parent();
                }
                System.out.println("Solution path: " + path.size());
                path.addFirst(map2.get_init_state());
                return path;
            }
            next_states = map2.generate_next_state(current_state);
            for (int i = 0; i < next_states.size(); ++i) {
                if (closed.contains(next_states.get(i).toString())) {
                    continue;
                }
                open.addLast(next_states.get(i));
            }
        }
        System.out.println("No Solution Found");
        System.out.println("Nodes expanded: " + closed.size());
        return null;
    }
}
