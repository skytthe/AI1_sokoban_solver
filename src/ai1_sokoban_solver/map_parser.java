package ai1_sokoban_solver;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Scanner;

public class map_parser {

    private Exception Exception;

    public map generate_map_from_txt(String path) {
        map map2 = null;
        int x = 0;
        int y = 0;
        int diamonds = 0;
        int diamond_count = 0;
        int man_count = 0;
        int goal_count = 0;
        state init_state = null;
        HashSet<String> diamond_pos_init = null;
        HashSet<String> diamond_pos_goal = null;
        int[] man_pos = null;
        state goal_state = null;
        tile[][] tile_map = null;
        try {
            Scanner file_scanner = new Scanner(new BufferedInputStream(new FileInputStream(path)));
            x = file_scanner.nextInt();
            y = file_scanner.nextInt();
            diamonds = file_scanner.nextInt();
            String start_dir = file_scanner.next();
            System.out.println("bredde: " + x);
            System.out.println("h\u00f8jde: " + y);
            System.out.println("antal diamander: " + diamonds);
            System.out.println("start retning: " + start_dir);
            file_scanner.nextLine();
            diamond_pos_init = new HashSet<String>(diamonds);
            diamond_pos_goal = new HashSet<String>(diamonds);
            man_pos = new int[2];
            tile_map = new tile[x][y];
            int line_number = 0;
            while (file_scanner.hasNextLine()) {
                String line = file_scanner.nextLine();
                block12:
                for (int i = 0; i < line.length(); ++i) {
                    switch (line.charAt(i)) {
                        case 'X': {
                            System.out.print("x");
                            tile_map[i][line_number] = new tile("wall", false, false, i, line_number);
                            continue block12;
                        }
                        case 'J': {
                            System.out.print("j");
                            tile_map[i][line_number] = new tile("free", false, false, i, line_number);
                            diamond_pos_init.add(this.generate_pos_string(i, line_number));
                            ++diamond_count;
                            continue block12;
                        }
                        case 'G': {
                            System.out.print("g");
                            tile_map[i][line_number] = new tile("free", true, false, i, line_number);
                            diamond_pos_goal.add(this.generate_pos_string(i, line_number));
                            ++goal_count;
                            continue block12;
                        }
                        case '.': {
                            System.out.print(",");
                            tile_map[i][line_number] = new tile("free", false, false, i, line_number);
                            continue block12;
                        }
                        case 'D': {
                            System.out.print(",");
                            tile_map[i][line_number] = new tile("free", false, true, i, line_number);
                            continue block12;
                        }
                        case 'M': {
                            System.out.print("M");
                            tile_map[i][line_number] = new tile("free", false, false, i, line_number);
                            man_pos[0] = i;
                            man_pos[1] = line_number;
                            ++man_count;
                            continue block12;
                        }
                        case ' ': {
                            System.out.print("_");
                            tile_map[i][line_number] = new tile("empty", false, false, i, line_number);
                            continue block12;
                        }
                        default: {
                            throw new UnsupportedOperationException("ERROR IN MAP TXT FILE - illegal char ignored");
                        }
                    }
                }
                System.out.println(" : line " + line_number);
                ++line_number;
            }
        } catch (FileNotFoundException ex) {
            // empty catch block
        }
        System.out.println("" + man_count + " - " + diamond_count + " - " + diamonds + " - " + goal_count);
        if (man_count != 1 || diamond_count != diamonds || diamonds != goal_count) {
            map2 = null;
        } else {
            init_state = new state(man_pos, diamond_pos_init, null, 0);
            goal_state = new state(man_pos, diamond_pos_goal, null, 0);
            map2 = new map(x, y, tile_map, init_state, goal_state);
        }
        System.out.println(init_state.toString());
        System.out.println(goal_state.toString());
        System.out.println(map2.solution());
        return map2;
    }

    private String generate_pos_string(int x, int y) {
        return this.correct_string_length(Integer.toString(y)) + this.correct_string_length(Integer.toString(x));
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

    public void gen_heuristic(map map2) {
        int[][] heuristic_map = new int[][]{{99, 99, 99, 99, 99, 99, 99, 99, 99, 99}, {99, 99, 99, 99, 99, 99, 5, 4, 5, 99}, {99, 3, 2, 3, 4, 5, 4, 3, 4, 99}, {99, 2, 1, 2, 99, 99, 99, 2, 99, 99}, {99, 99, 0, 1, 0, 1, 0, 1, 0, 99}, {99, 99, 1, 2, 99, 2, 1, 2, 1, 99}, {99, 99, 2, 3, 99, 99, 99, 99, 99, 99}, {99, 99, 3, 4, 99, 99, 99, 99, 99, 99}, {99, 99, 99, 99, 99, 99, 99, 99, 99, 99}};
        for (int y = 0; y < 9; ++y) {
            for (int x = 0; x < 10; ++x) {
                map2.get_tile(x, y).set_heuristic(heuristic_map[y][x]);
            }
        }
    }
}
