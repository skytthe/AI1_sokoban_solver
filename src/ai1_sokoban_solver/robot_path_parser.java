package ai1_sokoban_solver;

import java.io.PrintStream;
import java.util.LinkedList;

public class robot_path_parser {

    map map;
    LinkedList<state> path;

    public robot_path_parser(map map_competition, LinkedList<state> path) {
        this.path = path;
    }

    public void print_path_terminal_robot() {
        System.out.println("Robot path string");
        System.out.println(this.get_path_sim());
    }

    public void print_path_terminal_sim() {
        String sim_path;
        System.out.println("Simulator Robot path string");
        String robot_path = sim_path = this.get_path_sim();
        System.out.println(robot_path);
    }

    private String get_path_sim() {
        String robot_path = "";
        for (int i = 0; i < this.path.size() - 1; ++i) {
            robot_path = robot_path + this.calc_move_char(this.path.get(i).toString().substring(0, 4), this.path.get(i + 1).toString().substring(0, 4));
        }
        return robot_path;
    }

    private String calc_move_char(String current, String next) {
        int current_x = Integer.parseInt(current.substring(0, 2));
        int current_y = Integer.parseInt(current.substring(2, 4));
        int next_x = Integer.parseInt(next.substring(0, 2));
        int next_y = Integer.parseInt(next.substring(2, 4));
        if (current_x == next_x && current_y > next_y) {
            return "u";
        }
        if (current_x == next_x && current_y < next_y) {
            return "d";
        }
        if (current_x < next_x && current_y == next_y) {
            return "r";
        }
        if (current_x > next_x && current_y == next_y) {
            return "l";
        }
        return "";
    }
}
