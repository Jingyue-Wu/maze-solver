# Maze Solver

## Business Logic Specification

This program explores a maze, finding a path from an entry point to an exit one.

- The maze is stored in a text file, with `#` representing walls and `␣` (_empty space_) representing passages.
- The Maze is surrounded by walls on its four borders, except for its entry/exit points.
    - Entry and exit points are always located on the East and West border.
    - The maze is not directed. As such, exit and entry can be interchanged.
- At the beginning of the exploration, we're located on the entry tile, facing the opposite side (e.g., if entering by
  the eastern entry, you're facing West).
- The program generates a sequence of instructions to reach the opposite exit (i.e., a "path"):
    - `F` means 'move forward' according to your current direction
    - `R` means 'turn right' (does not move, just change direction), and `L` means ‘turn left’.
- A canonical path contains only `F`, `R` and `L` symbols
- A factorized path squashes together similar instructions (i.e., `FFF` = `3F`, `LL` = `2L`).
- Spaces are ignored in the instruction sequence (only for readability: `FFLFF` = `FF L FF`)

## How to run this software?

Package with Maven:

```
mosser@azrael A1-Template % mvn -q clean package 
```

Assumes the maze file name is the first argument.

```
mosser@azrael A1-Template % java -jar target/mazerunner.jar ./examples/small.maz.txt
** Starting Maze Runner
**** Reading the maze from file ./examples/small.maz.txt
WALL WALL WALL WALL WALL WALL WALL WALL WALL WALL WALL 
WALL PASS PASS PASS PASS PASS PASS PASS PASS PASS WALL 
WALL WALL WALL PASS WALL WALL WALL PASS WALL WALL WALL 
WALL PASS PASS PASS PASS PASS WALL PASS PASS PASS WALL 
WALL PASS WALL PASS WALL WALL WALL WALL WALL PASS WALL 
WALL PASS WALL PASS PASS PASS PASS PASS WALL PASS PASS 
WALL WALL WALL PASS WALL PASS WALL WALL WALL WALL WALL 
WALL PASS PASS PASS WALL PASS PASS PASS PASS PASS WALL 
PASS PASS WALL PASS WALL PASS WALL WALL WALL PASS WALL 
WALL PASS WALL PASS WALL PASS WALL PASS PASS PASS WALL 
WALL WALL WALL WALL WALL WALL WALL WALL WALL WALL WALL 
**** Computing path
PATH NOT COMPUTED
** End of MazeRunner
```

When called on a non-existing file, it prints an error message

```
mosser@azrael A1-Template % java -jar target/mazerunner.jar ./examples/small.maz.txtd
** Starting Maze Runner
**** Reading the maze from file ./examples/small.maz.txtd
/!\ An error has occured /!\
**** Computing path
PATH NOT COMPUTED
** End of MazeRunner
```

#### Command line arguments

The delivered program uses the following flags:

- `-i MAZE_FILE`: specifies the filename to be used;
- `-p "PATH_SEQUENCE"`: activates the path verification mode to validate that PATH_SEQUENCE is correct for the maze. (quotes,`" "`, are required for paths containing spaces)
- `-method {tremaux, righthand, bfs}`: specifies which path computation method to use. (default is right hand)
- `-baseline {tremaux, righthand, bfs}`: activates benchmark mode when used with `-method` and specifies the baseline method to compare with the selected method.

#### Examples

When benchmark mode is activated, the program will only print the time to load maze from file, the explore time for both methods and the speedup. All times are in milliseconds.

```
~/2aa4/a3-maze-runner-take-two-Jingyue-Wu$ java -jar target/mazerunner.jar -i ./examples/rectangle.maz.txt  -method bfs -baseline tremaux
Time to load maze from file: 2 milliseconds 
Explore time for selected method: 44 milliseconds 
Explore time for Baseline: 8 milliseconds 
Speedup: 1.00 times faster

```

When no logs are activated, the programs only print the computed path on the standard output.

```
mosser@azrael A1-Template % java -jar target/mazerunner.jar -i ./examples/straight.maz.txt
4F
mosser@azrael A1-Template %
```

If a given path is correct, the program prints the message `correct path` on the standard output.

```
mosser@azrael A1-Template % java -jar target/mazerunner.jar -i ./examples/straight.maz.txt -p 4F
correct path
mosser@azrael A1-Template %
```

If a given path is incorrect, the program prints the message `incorrect path` on the standard output.





```
mosser@azrael A1-Template % java -jar target/mazerunner.jar -i ./examples/straight.maz.txt -p 3F
inccorrect path
mosser@azrael A1-Template %
```



## Citations

[1]	J. Hellings, SFWRENG 2C03 Class Lecture, Topic: "(9) Graphs: Elementary Graph Algorithms”, McMaster University, 2024. 

[2]	A. Lachance, SFWRENG 2AA4 A1 Sample Solution, Topic: "Assignment A1 - Maze Runner”, McMaster University, 2024. 

[3]	R. Li, SFWRENG 2AA4 Tutorial #12, Topic: "Double Dispatch in practice”, McMaster University, 2024. 

[4]	“Visitor Design Pattern in Java | DigitalOcean,” https://www.digitalocean.com/community/tutorials/visitor-design-pattern-java

[5]	“Visitor and Double Dispatch,” refactoring.guru. https://refactoring.guru/design-patterns/visitor-double-dispatch


