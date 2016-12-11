package ai1_sokoban_solver;

public class tile {

    private int x;
    private int y;
    private String type;
    private boolean goal;
    private boolean deadlock;
    private int heuristic;
    private tile tile_u;
    private tile tile_d;
    private tile tile_r;
    private tile tile_l;

    public tile(String type, boolean goal, boolean deadlock, int x, int y) {
        this.type = type;
        this.goal = goal;
        this.deadlock = deadlock;
        this.x = x;
        this.y = y;
    }

    public int get_heuristic() {
        return this.heuristic;
    }

    public void set_heuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public void set_neighbour_tile(tile neighbour_tile, char direction) {
        switch (direction) {
            case 'u': {
                this.tile_u = neighbour_tile;
                break;
            }
            case 'd': {
                this.tile_d = neighbour_tile;
                break;
            }
            case 'r': {
                this.tile_r = neighbour_tile;
                break;
            }
            case 'l': {
                this.tile_l = neighbour_tile;
                break;
            }
            default: {
                throw new UnsupportedOperationException("ERROR: " + direction);
            }
        }
    }

    public int[] get_coordinates() {
        return new int[]{this.x, this.y};
    }

    public tile get_neighbour_tile(char direction) {
        switch (direction) {
            case 'u': {
                return this.tile_u;
            }
            case 'd': {
                return this.tile_d;
            }
            case 'r': {
                return this.tile_r;
            }
            case 'l': {
                return this.tile_l;
            }
        }
        throw new UnsupportedOperationException("ERROR");
    }

    public void calc_heuristic() {
    }

    public String toString() {
        String temp = "";
        switch (this.type.charAt(0)) {
            case 'f': {
                temp = " ";
                break;
            }
            case 'e': {
                temp = " ";
                break;
            }
            case 'w': {
                temp = "X";
                break;
            }
        }
        if (this.goal) {
            temp = "G";
        }
        return temp;
    }

    public char get_move_char_man() {
        return this.type.charAt(0);
    }

    public char get_move_char_diamond() {
        if (this.deadlock) {
            return 'w';
        }
        return this.type.charAt(0);
    }
}
