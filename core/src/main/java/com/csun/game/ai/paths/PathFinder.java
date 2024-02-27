package com.csun.game.ai.paths;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.map.GameMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PathFinder {

    public static PathResult findPath(Vector2 start, Vector2 end, GameMap map) {
        TiledMap currentMap = map.getCurrentMap();
        List<Vector2> path = new ArrayList<>();
        List<Vector2> waypoints = new ArrayList<>();

        if (!map.getTiledMapGrid().containsKey(currentMap)) {
            return new PathResult(path, waypoints);
        }

        int rows = (int) map.getCurrentMapHeight();
        int cols = (int) map.getCurrentMapWidth();
        boolean[][] visited = new boolean[rows][cols];
        Integer[][] grid = map.getTiledMapGrid().get(currentMap);
        int[] dr = {0, 0, 1, -1};
        int[] dc = {1, -1, 0, 0};
        int[][] parent = new int[rows][cols];

        Queue<Cell> queue = new LinkedList<>();
        queue.add(new Cell((int) start.x, (int) start.y));
        visited[(int) start.x][(int) start.y] = true;

        boolean reachedEnd = false;

        while (!queue.isEmpty() && !reachedEnd) {
            Cell currentCell = queue.poll();
            int i = currentCell.row;
            int j = currentCell.col;

            for (int k = 0; k < 4; k++) {
                int ni = i + dr[k];
                int nj = j + dc[k];

                if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && !visited[ni][nj] && grid[ni][nj] != -1) {
                    queue.add(new Cell(ni, nj));
                    visited[ni][nj] = true;
                    parent[ni][nj] = i * cols + j;

                    if (ni == end.x && nj == end.y) {
                        reachedEnd = true;
                        break;
                    }
                }
            }
        }

        if (reachedEnd) {
            int i = (int) end.x;
            int j = (int) end.y;
            Vector2 prevDirection = null;
            while (i != (int) start.x || j != (int) start.y) {
                Vector2 currentPoint = new Vector2(i, j);
                path.add(0, currentPoint);

                int pi = parent[i][j] / cols;
                int pj = parent[i][j] % cols;

                Vector2 direction = new Vector2(i - pi, j - pj);
                if (!direction.equals(prevDirection)) {
                    waypoints.add(0, currentPoint);
                }
                prevDirection = direction;

                i = pi;
                j = pj;
            }
            path.add(0, start);
        }

        return new PathResult(path, waypoints);
    }

    record Cell(int row, int col) {}
}
