package ai1_sokoban_solver;

import java.util.LinkedList;

public class AI1_sokoban_solver {

    public static void main(String[] args) {
        map_parser map_parser2 = new map_parser();
        map map_competition = map_parser2.generate_map_from_txt("maps/map_competition.txt");
        map_parser2.gen_heuristic(map_competition);
        map map_competition_deadlock = map_parser2.generate_map_from_txt("maps/map_competition_deadlock.txt");
        map_parser2.gen_heuristic(map_competition_deadlock);
        sokoban_solver solver = new sokoban_solver();
        long time1 = System.currentTimeMillis();
        LinkedList path = solver.solve_breadth_first(map_competition_deadlock);
        long time2 = System.currentTimeMillis();
        System.out.println("runtime :" + (time2 - time1));
        System.out.println(path.peek() + " - " + map_competition.solution());
        robot_path_parser robot_parser = new robot_path_parser(map_competition, path);
        robot_parser.print_path_terminal_sim();
        robot_parser.print_path_terminal_robot();
        map_competition.print_map((state) path.peekFirst());
        map_competition.print_map((state) path.peekLast());
        time1 = System.currentTimeMillis();
        path = solver.solve_astar(map_competition_deadlock);
        time2 = System.currentTimeMillis();
        System.out.println("runtime :" + (time2 - time1));
        System.out.println(path.peek() + " - " + map_competition_deadlock.solution());
        robot_parser = new robot_path_parser(map_competition_deadlock, path);
        robot_parser.print_path_terminal_sim();
        robot_parser.print_path_terminal_robot();
        map_competition_deadlock.print_map((state) path.peekFirst());
        map_competition_deadlock.print_map((state) path.peekLast());
    }

}
