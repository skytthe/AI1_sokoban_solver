package ai1_sokoban_solver;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class map {

    private state init;
    private state goal;
    private int x;
    private int y;
    private tile[][] tile_map;

    public map(int x, int y, tile[][] tile_map, state init, state goal) {
        this.x = x;
        this.y = y;
        this.tile_map = tile_map;
        this.init = init;
        this.goal = goal;
        tile_map = new tile[x][y];
        this.generate_neighbour_tile();
    }

    private void generate_neighbour_tile() {
        int itr_x;
        int itr_y;
        for (itr_y = 0; itr_y < this.y; ++itr_y) {
            for (itr_x = 0; itr_x < this.x; ++itr_x) {
                if (itr_y == 0) {
                    this.tile_map[itr_x][itr_y].set_neighbour_tile(null, 'u');
                    continue;
                }
                this.tile_map[itr_x][itr_y].set_neighbour_tile(this.tile_map[itr_x][itr_y - 1], 'u');
            }
        }
        for (itr_y = 0; itr_y < this.y; ++itr_y) {
            for (itr_x = 0; itr_x < this.x; ++itr_x) {
                if (itr_y == this.y - 1) {
                    this.tile_map[itr_x][itr_y].set_neighbour_tile(null, 'd');
                    continue;
                }
                this.tile_map[itr_x][itr_y].set_neighbour_tile(this.tile_map[itr_x][itr_y + 1], 'd');
            }
        }
        for (itr_y = 0; itr_y < this.y; ++itr_y) {
            for (itr_x = 0; itr_x < this.x; ++itr_x) {
                if (itr_x == this.x - 1) {
                    this.tile_map[itr_x][itr_y].set_neighbour_tile(null, 'r');
                    continue;
                }
                this.tile_map[itr_x][itr_y].set_neighbour_tile(this.tile_map[itr_x + 1][itr_y], 'r');
            }
        }
        for (itr_y = 0; itr_y < this.y; ++itr_y) {
            for (itr_x = 0; itr_x < this.x; ++itr_x) {
                if (itr_x == 0) {
                    this.tile_map[itr_x][itr_y].set_neighbour_tile(null, 'l');
                    continue;
                }
                this.tile_map[itr_x][itr_y].set_neighbour_tile(this.tile_map[itr_x - 1][itr_y], 'l');
            }
        }
    }

    public void add_tile(tile t, int x, int y) {
        this.tile_map[x][y] = t;
    }

    public tile get_tile(int x, int y) {
        return this.tile_map[x][y];
    }

    public void print_map(state s) {
        boolean diamond_count = false;
        for (int i = 0; i < this.y; ++i) {
            for (int j = 0; j < this.x; ++j) {
                if (s.get_man_pos()[0] == j && s.get_man_pos()[1] == i) {
                    System.out.print("M");
                    continue;
                }
                if (s.get_diamond_pos(this.generate_pos_string(j, i))) {
                    System.out.print("D");
                    continue;
                }
                System.out.print(this.tile_map[j][i].toString());
            }
            System.out.println("");
        }
    }

    public state get_init_state() {
        return this.init;
    }

    public void print_map() {
        this.print_map(this.init);
    }

    public String solution() {
        return this.goal.toString().substring(4);
    }

    public ArrayList<state> generate_next_state(state s) {
        ArrayList<state> next_states = new ArrayList<state>();
        state temp = this.move(s, 'u');
        if (temp != null) {
            next_states.add(temp);
        }
        if ((temp = this.move(s, 'd')) != null) {
            next_states.add(temp);
        }
        if ((temp = this.move(s, 'r')) != null) {
            next_states.add(temp);
        }
        if ((temp = this.move(s, 'l')) != null) {
            next_states.add(temp);
        }
        return next_states;
    }

    private state move(state s, char direction) {
        tile tile_man = this.tile_map[s.get_man_pos()[0]][s.get_man_pos()[1]].get_neighbour_tile(direction);
        tile tile_diamond = tile_man.get_neighbour_tile(direction);
        if (s.get_diamond_pos(this.generate_pos_string(tile_man.get_coordinates()[0], tile_man.get_coordinates()[1]))) {
            if (tile_diamond != null) {
                if (tile_diamond.get_move_char_diamond() == 'f' && !s.get_diamond_pos(this.generate_pos_string(tile_diamond.get_coordinates()[0], tile_diamond.get_coordinates()[1]))) {
                    HashSet<String> temp = new HashSet<String>();
                    Iterator<String> itr = s.get_diamond_pos().iterator();
                    while (itr.hasNext()) {
                        temp.add(itr.next().toString());
                    }
                    temp.remove(this.generate_pos_string(tile_man.get_coordinates()[0], tile_man.get_coordinates()[1]));
                    temp.add(this.generate_pos_string(tile_diamond.get_coordinates()[0], tile_diamond.get_coordinates()[1]));
                    return new state(tile_man.get_coordinates(), temp, s, this.calc_heuristic(temp, s));
                }
                return null;
            }
            return null;
        }
        if (tile_man.get_move_char_man() == 'f') {
            return new state(tile_man.get_coordinates(), s.get_diamond_pos(), s, this.calc_heuristic(s.get_diamond_pos(), s));
        }
        return null;
    }

    private int calc_heuristic(HashSet<String> pos, state s) {
        int temp = 0;
        Iterator<String> itr = pos.iterator();
        while (itr.hasNext()) {
            String line = itr.next().toString();
            temp += this.tile_map[Integer.parseInt(line.substring(2, 4))][Integer.parseInt(line.substring(0, 2))].get_heuristic();
        }
        for (state p = s.get_parent(); p != null; p = p.get_parent()) {
        }
        return temp += s.get_path_lenght();
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
}
