package com.csun.game.ai.paths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.csun.game.map.GameMap;
import com.github.tommyettinger.gand.Int2UndirectedGraph;
import com.github.tommyettinger.gand.Path;
import com.github.tommyettinger.gand.points.PointF2;
import com.github.tommyettinger.gand.points.PointI2;
import com.github.tommyettinger.gand.smoothing.Ortho2DRaycastCollisionDetector;
import com.github.tommyettinger.gand.smoothing.PathSmoother;

import java.util.*;

public class PathFinder {

    public static final Map<TiledMap, PathCache> CACHE = new HashMap<>();

    public record PathCache(PathSmoother<PointI2> pathSmoother, Int2UndirectedGraph graph) { }

    public static Path<PointI2> findPathAStar(Vector2 start, Vector2 end, GameMap gameMap) {
        if(CACHE.containsKey(gameMap.getCurrentMap())) {
            PathCache cache = CACHE.get(gameMap.getCurrentMap());
            Int2UndirectedGraph graph = cache.graph;
            PathSmoother<PointI2> pathSmoother = cache.pathSmoother;

            PointI2 startPoint = new PointI2(MathUtils.floor(start.x), MathUtils.floor(start.y));
            PointI2 endPoint = new PointI2(MathUtils.floor(end.x), MathUtils.floor(end.y));

            if(graph.getVertices().contains(startPoint) && graph.getVertices().contains(endPoint)) {
                Path<PointI2> path = graph.algorithms().findShortestPath(
                    startPoint,
                    endPoint);
                pathSmoother.smoothPath(path);
                return path;
            } else {
                Gdx.app.log("Not on path", start + " " + end);
            }
            return new Path<>();
        }

        boolean[][] validGrid = gameMap.getTiledMapGrid().get(gameMap.getCurrentMap());

        Int2UndirectedGraph graph = new Int2UndirectedGraph(validGrid, 1f);
        graph.connectAdjacent(null, false);

        PointI2 startPoint = new PointI2(MathUtils.floor(start.x), MathUtils.floor(start.y));
        PointI2 endPoint = new PointI2(MathUtils.floor(end.x), MathUtils.floor(end.y));

        PathSmoother<PointI2> pathSmoother = new PathSmoother(new Ortho2DRaycastCollisionDetector<PointF2>((x, y) -> x >= 0 && x < validGrid.length && y >= 0 && y < validGrid[x].length && validGrid[x][y]));


        Path<PointI2> path = graph.algorithms().findShortestPath(startPoint, endPoint);
        pathSmoother.smoothPath(path);

        CACHE.put(gameMap.getCurrentMap(), new PathCache(pathSmoother, graph));
        return path;
    }


    public static PathResult findPath(int mySizeX, int mySizeY, Vector2 start, Vector2 end, GameMap map) {
        List<Vector2> path = new ArrayList<>();
        List<Vector2> waypoints = new ArrayList<>();

        int rows = (int) map.getCurrentMapHeight();
        int cols = (int) map.getCurrentMapWidth();
        boolean[][] visited = new boolean[rows][cols];
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

                if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && !visited[ni][nj]) {
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

    static final char[][] DUNGEON = new char[][]{
        "#########################".toCharArray(),
        "#.......###.......#..####".toCharArray(),
        "#...............###..####".toCharArray(),
        "#....................####".toCharArray(),
        "#.##..##.............####".toCharArray(),
        "#.#....#......##....#####".toCharArray(),
        "#.#....#....####....#####".toCharArray(),
        "#.#....#########....#####".toCharArray(),
        "###......#######..#######".toCharArray(),
        "#........#######..#######".toCharArray(),
        "#........###......#######".toCharArray(),
        "###########........######".toCharArray(),
        "##.................######".toCharArray(),
        "#..................######".toCharArray(),
        "#...##...########.......#".toCharArray(),
        "#...#...#########.......#".toCharArray(),
        "#......####....##########".toCharArray(),
        "#.....#####....#######..#".toCharArray(),
        "##...######........###..#".toCharArray(),
        "##..#######........###..#".toCharArray(),
        "###.....###........###..#".toCharArray(),
        "###.....###........##...#".toCharArray(),
        "#.#......##.............#".toCharArray(),
        "#.##....................#".toCharArray(),
        "#..##..........#....#...#".toCharArray(),
        "#...##.........#....##..#".toCharArray(),
        "#....####...........###.#".toCharArray(),
        "#...................#####".toCharArray(),
        "#..................######".toCharArray(),
        "######..####..#..########".toCharArray(),
        "#####..#####..###########".toCharArray(),
        "#.........##..#....####.#".toCharArray(),
        "#.........##..#....###..#".toCharArray(),
        "#####..#..##..#....##...#".toCharArray(),
        "#...#..#..##..#....##...#".toCharArray(),
        "#...#..#..##..#....##...#".toCharArray(),
        "#...####..##............#".toCharArray(),
        "#.........##............#".toCharArray(),
        "#.........##.......##...#".toCharArray(),
        "#########################".toCharArray(),
    };
}
