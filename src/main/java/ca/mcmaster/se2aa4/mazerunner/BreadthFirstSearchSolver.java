// package ca.mcmaster.se2aa4.mazerunner;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.LinkedList;
// import java.util.List;
// import java.util.Queue;

// public class BreadthFirstSearchSolver implements MazeSolver {

//     // private MazeGraph graph;
//     private Maze maze;
//     private Position currentPosition;

//     Queue<Position> queue = new LinkedList<Position>();
//     private List<List<Boolean>> marked;

//     private List<Direction> directionCheck = Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT,
//             Direction.RIGHT);

//     @Override
//     public Path solve(Maze maze) {
//         // graph = new MazeGraph(maze);
//         currentPosition = maze.getStart();
//         queue.add(currentPosition);
//         initializeMarked();

//         while (!queue.isEmpty()) {
//             currentPosition = queue.remove();
//             int currentX = currentPosition.getX();
//             int currentY = currentPosition.getY();

//             for (Direction direction : directionCheck) {
//                 Position checkPosition = currentPosition.move(direction);

//                 if (maze.isWall(checkPosition) && marked.get(currentX).get(currentY) == false) {
//                     queue.add(checkPosition);
//                     marked.get(currentX).set(currentY, true);
//                 }
//             }
//         }

//         return null;
//     }

//     private void initializeMarked() {
//         for (int i = 0; i < maze.getSizeX(); i++) {
//             List<Boolean> row = new ArrayList<Boolean>();

//             for (int j = 0; j < maze.getSizeY(); j++) {
//                 row.add(false);
//             }

//             marked.add(row);
//         }
//     }

//     // public void getPath() {

//     // }

//     // private void getInstruction() {

//     // }

// }