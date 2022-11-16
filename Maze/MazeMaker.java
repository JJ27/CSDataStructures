package Maze;

import java.util.*;
public class MazeMaker {
    //using recursive backtracking algorithm
    private final int x, y;
    private static int[][] maze = new int[0][0];

    public MazeMaker(int x, int y) {
        this.x = x;
        this.y = y;
        maze = new int[x][y];
        generateMaze(0, 0);
    }

    public String send() {
        String s = "";
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                s += (maze[j][i] & 1) == 0 ? "+-" : "+ ";
            }
            s += "+" + "\n";
            for (int j = 0; j < x; j++) {
                s += (maze[j][i] & 8) == 0 ? "| " : "  ";
            }
            s += "|" + "\n";
        }
        for (int j = 0; j < x; j++) {
            s += "+-";
        }
        s += "+" + "\n";
        return s;
    }

    private void generateMaze(int cx, int cy) {
        DIRECTION[] dirs = DIRECTION.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIRECTION dir : dirs) {
            int nx = cx + dir.xprime;
            int ny = cy + dir.yprime;
            if (between(nx, x) && between(ny, y)
                    && (maze[nx][ny] == 0)) {
                maze[cx][cy] |= dir.b;
                maze[nx][ny] |= dir.opp.b;
                generateMaze(nx, ny);
            }
        }
    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    private enum DIRECTION {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int b;
        private final int xprime, yprime;
        private DIRECTION opp;

        // use the static initializer to resolve forward references
        static {
            N.opp = S;
            S.opp = N;
            W.opp = E;
            E.opp = W;
        }

        private DIRECTION(int b, int xprime, int yprime) {
            this.b = b;
            this.xprime = xprime;
            this.yprime = yprime;
        }
    }
}