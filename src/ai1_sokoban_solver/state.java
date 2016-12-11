package ai1_sokoban_solver;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

public class state {

    private final String string_state;
    private int[] man_pos;
    private final state parent;
    private int heuristic;
    private static int path_lenght;
    private HashSet<String> diamond_pos;

    public state(int[] man_pos, HashSet<String> diamond_pos, state parent, int heuristic) {
        this.man_pos = man_pos;
        this.diamond_pos = diamond_pos;
        this.string_state = this.gen_string();
        this.parent = parent;
        this.heuristic = heuristic;
    }

    public int get_heuristic() {
        return this.heuristic;
    }

    public int get_path_lenght() {
        int temp = path_lenght;
        path_lenght = 0;
        return temp;
    }

    public void set_heuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public state get_parent() {
        ++path_lenght;
        return this.parent;
    }

    public int[] get_man_pos() {
        return this.man_pos;
    }

    public boolean get_diamond_pos(String pos) {
        return this.diamond_pos.contains(pos);
    }

    public HashSet<String> get_diamond_pos() {
        return this.diamond_pos;
    }

    public void print_h() {
        System.out.println(this.heuristic);
    }

    private String gen_string() {
        String diamond_string = "";
        PriorityQueue<String> list = this.generate_diamond_pos_list(this.diamond_pos);
        while (!list.isEmpty()) {
            diamond_string = diamond_string + list.poll();
        }
        return this.correct_string_length(Integer.toString(this.man_pos[0])) + this.correct_string_length(Integer.toString(this.man_pos[1])) + diamond_string;
    }

    public PriorityQueue<String> generate_diamond_pos_list(HashSet<String> diamond_pos) {
        Iterator<String> itr = diamond_pos.iterator();
        PriorityQueue<String> list = new PriorityQueue<String>();
        while (itr.hasNext()) {
            list.add(itr.next().toString());
        }
        return list;
    }

    private String correct_string_length(String number) {
        switch (number.length()) {
            case 1: {
                return "0" + number;
            }
            case 2: {
                return number;
            }
        }
        throw new UnsupportedOperationException("ERROR IN STATE OBJECT (or the map is over 99x99 tiles)");
    }

    public String toString_parent() {
        if (this.parent != null) {
            return this.parent.toString();
        }
        return "";
    }

    public String toString() {
        return this.string_state;
    }
}
