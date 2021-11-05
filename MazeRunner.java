package edu.yqian;

import java.util.Arrays;
import java.util.Stack;

class Position {
    int x;
    int y;
    int direction;

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getDirection() {
        return direction;
    }

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    void setDirection(int direction) {
        this.direction = direction;
    }
}

public class MazeRunner {
    char[][] matrix = new char[6][10];
    boolean[][] visitedMatrix = new boolean[6][10];
    Stack<Position> path = new Stack<>();

    public void initMaze() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = '#';
            }
        }
        matrix[0][8] = '@';
        Arrays.fill(matrix[1], '.');
        for (int i = 2; i < matrix.length - 1; i++) {
            matrix[i][8] = '.';
        }
        matrix[3][7] = '.';
        matrix[3][9] = '.';
        matrix[4][7] = '.';
        matrix[5][7] = '$';

        for (char[] chars : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(chars[j]);
            }
            System.out.println();
        }
    }

    public Position findStartPoint() {
        Position startPoint = new Position();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '@') {
                    startPoint.setX(i);
                    startPoint.setY(j);
                }
            }
        }
        return startPoint;
    }

    public boolean isPath(int x, int y) { return matrix[x][y] != '#'; }

    public boolean canMoveUp(int x) { return x > 0; }

    public boolean canMoveDown(int x) {
        return x < matrix.length;
    }

    public boolean canMoveLeft(int y) {
        return y > 0;
    }

    public boolean canMoveRight(int y) {
        return y < matrix[0].length;
    }

    public boolean isExit(int x, int y) {
        return matrix[x][y] == '$';
    }

    public void walkThrough() {

        Position startPoint = findStartPoint();
        path.push(startPoint);

        while (!path.empty()) {
            startPoint = path.pop();
            int direction = startPoint.getDirection();

            startPoint.setDirection(startPoint.getDirection() + 1);
            path.push(startPoint);

            if (isExit(startPoint.getX(), startPoint.getY())) {
               break;
            }

            if (direction == 0) {
                if (canMoveLeft(startPoint.getY() - 1) && isPath(startPoint.getX(), startPoint.getY() - 1) && !visitedMatrix[startPoint.getX()][startPoint.getY() - 1]) {
                    Position currentPoint = new Position();
                    currentPoint.setX(startPoint.getX());
                    currentPoint.setY(startPoint.getY() - 1);
                    visitedMatrix[startPoint.getX()][startPoint.getY() - 1] = true;
                    path.push(currentPoint);
                }
            } else if (direction == 1) {
                if (canMoveUp(startPoint.getX() - 1) && isPath(startPoint.getX() - 1, startPoint.getY()) && !visitedMatrix[startPoint.getX() - 1][startPoint.getY()]) {
                    Position currentPoint = new Position();
                    currentPoint.setX(startPoint.getX() - 1);
                    currentPoint.setY(startPoint.getY());
                    visitedMatrix[startPoint.getX() - 1][startPoint.getY()] = true;
                    path.push(currentPoint);
                }
            } else if (direction == 2) {
                if (canMoveRight(startPoint.getY() + 1) && isPath(startPoint.getX(), startPoint.getY() + 1) && !visitedMatrix[startPoint.getX()][startPoint.getY() + 1]) {
                    Position currentPoint = new Position();
                    currentPoint.setX(startPoint.getX());
                    currentPoint.setY(startPoint.getY() + 1);
                    visitedMatrix[startPoint.getX()][startPoint.getY() + 1] = true;
                    path.push(currentPoint);
                }
            } else if (direction == 3) {
                if (canMoveDown(startPoint.getX() + 1) && isPath(startPoint.getX() + 1, startPoint.getY()) && !visitedMatrix[startPoint.getX() + 1][startPoint.getY()]) {
                    Position currentPoint = new Position();
                    currentPoint.setX(startPoint.getX() + 1);
                    currentPoint.setY(startPoint.getY());
                    visitedMatrix[startPoint.getX() + 1][startPoint.getY()] = true;
                    path.push(currentPoint);
                }
            } else {
                visitedMatrix[startPoint.getX()][startPoint.getY()] = false;
                path.pop();
            }
        }
    }

    public static void main(String[] args) {
        MazeRunner mazeRunner = new MazeRunner();
        mazeRunner.initMaze();
        mazeRunner.walkThrough();
        while(!mazeRunner.path.isEmpty()) {
         Position position = mazeRunner.path.pop();
         System.out.println(position.getX() + ", " + position.getY());
        }
    }
}
