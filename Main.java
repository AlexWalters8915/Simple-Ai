import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    private static final int gridSize = 15;


    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> closedList = new ArrayList<>();
    Node[][] grid;
    Node currentLocation;
    Node endPoint;
    //leftover from intial attempt
    //Node parent ;
    Node startPoint;
    int moves = 0 ;
// this.something
    //common occurance intaial version had issues with variable names

    public static void print(Object obj) {
        System.out.println(obj);
    }
    public void AStarPathfinding() {
        grid = new Node[gridSize][gridSize];
        grid = generateWorld(grid);
//no longer works after cross over to new attempt
        /*if (moves>50){
    print("greater then 50 moves used now ending");
    System.exit(0);
}*/
        //basically lining it up
        while (currentLocation.getRow() != endPoint.getRow() || currentLocation.getCol() != endPoint.getCol()) {

            explore(currentLocation);
            displayGrid();
            //displayPath();
            print("-------------");
            //current grid location
            print(currentLocation.getCol()  +":"+ currentLocation.getRow());
            print("-------------");



        }
    }






    //create visual of all the board
    private Node[][] generateWorld(Node[][] grid) {
        //scanner for input
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Fill the grid array with nodes and set type to 0
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = new Node(i, j, 0);
            }
        }
        print("A* Ai");
        print("Guide");
        print("2 indicates starting location goal ");
        print("9 indicates current path");
        print("1 indicates some kind of obstacle for the agent");
        print("0 indicates open space");


        // Randomly set type  for inputed number of nodes  to create obs

        print("Please enter the number of impassable grids you would like");
        print("Keep  in mind having more may lead to failed attempts");
        int count = 0 ;
        int bombs = scanner.nextInt();
        //places the obstacles in teh grid checking against the type that they are

        while (count < bombs) {
            int row = random.nextInt(gridSize);
            int col = random.nextInt(gridSize);
            if (grid[row][col].getType() == 0) {
                grid[row][col].setType(1);
                count++;
            }
        }
//user input for the positions and bomb quantity

        System.out.println("Enter the row and column for the start node (separated by a space):");
        int startRow = scanner.nextInt();
        int startCol = scanner.nextInt();
        //decoare node that will move about

        currentLocation = grid[startRow][startCol];
        //mark moving node

        currentLocation.setType(2);
        //declare start node

        startPoint = currentLocation;
        openList.add(startPoint);
        System.out.println("Enter the row and column for the goal node (separated by a space):");
        int endRow = scanner.nextInt();
        int endCol = scanner.nextInt();
        //palce the end point

        endPoint = grid[endRow][endCol];
        endPoint.setType(2);

        print("Initial Layout");
        displayGrid();
        // print(goalNode.getCol());
        print("-------------------");




        //This is the hueristic calculates it and pases it in to nodes useing the manhatten
        //calculate the f value for all nodes in the grid before letting agent determine its move
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                Node node = grid[r][c];

                //current node in loop          current - target + current-targer
                node.setH(Math.abs(node.getRow() - endPoint.getRow()) + Math.abs(node.getCol() - endPoint.getCol()));


//assign g values increimentaly
                node.setG(startPoint.getG() + 1);

//calculate f
                node.setF();



            }
        }



        return grid;
    }
//deub statement to check that values are being assigned correctly by printing a large number of nodes there locations and the values in them

    public void debug(){
        for (int r = 0; r < this.grid.length; r++) {
            for (int c = 0; c < this.grid[r].length; c++) {
                Node node = this.grid[r][c];

                System.out.println("Node at row " + r + ", column " + c + " h " + node.getH()+"f"+node.getF()+"g"+node.getG());
            }
        }
    }
    //prints the grid after a movement occurs

    public void displayGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Node node = this.grid[i][j];
                System.out.print(node.getType() + " ");
            }
            System.out.println();
        }
    }



    //lets the agen move about and compare F values and add tehm to the open list as needed
    public void explore(Node currentNode) {
        int row = currentNode.getRow();
        int col = currentNode.getCol();

        // up
        if (row - 1 >= 0 && grid[row - 1][col].getType() != 1) {
            Node possibleMoves = grid[row - 1][col];
            //check if already visted on a prior move

            if (closedList.contains(possibleMoves)==false) {
                int fValue = possibleMoves.getF();


                // Add the possibleMoves to the open list if necessary
                if (openList.contains(possibleMoves)==false) {
                    openList.add(possibleMoves);
                    moves++;

                }}
        }

        // left
        if (col - 1 >= 0 && grid[row][col - 1].getType() != 1) {
            Node possibleMoves = grid[row][col - 1];
            if (closedList.contains(possibleMoves)==false) {
                int fValue = possibleMoves.getF();


                // Add the possibleMoves to the open list if necessary

                if (openList.contains(possibleMoves)==false) {
                    openList.add(possibleMoves);
                    moves++;

                }}
        }

        // right
        if (col + 1 < grid[row].length && grid[row][col + 1].getType() != 1) {
            Node possibleMoves = grid[row][col + 1];
            if (closedList.contains(possibleMoves)==false) {
                int fValue = possibleMoves.getF();

                // Add the possibleMoves to the open list if necessary

                if (openList.contains(possibleMoves)==false) {
                    openList.add(possibleMoves);
                    moves++;

                }}
        }

        // down
        if (row + 1 < grid.length && grid[row + 1][col].getType() != 1) {
            Node possibleMoves = grid[row + 1][col];
            if (closedList.contains(possibleMoves)==false) {
                int fValue = possibleMoves.getF();


                // Add the possibleMoves to the open list if necessary

                if (openList.contains(possibleMoves)==false) {
                    openList.add(possibleMoves);
                    moves++;

                }}

        }

        // moves++;
        //call to update the the grid
        updatePath();
        //final determiniation


    }




    //intial attempt did not make correct use of A* still was viable for finding paths semi regular so scrapped and turned into more correct version of A*
/*
public void heuristicScore(Node currentLocation) {
        int row = currentLocation.getRow();
        int col = currentLocation.getCol();
        int lowestDistanceUp = Integer.MAX_VALUE;
        int lowestDistanceRight = Integer.MAX_VALUE;
        int lowestDistanceLeft = Integer.MAX_VALUE;
        int lowestDistanceDown = Integer.MAX_VALUE;
        int lowestRowUp = -1;
        int lowestColRight = -1;
        int lowestColLeft = -1;
        int lowestRowDown = -1;

        // up
        for (int r = row - 1; r >= 0; r--) {
            Node node = grid[r][col];
            if (node.getType() == 1) {
                break;
            }
            int distance = Math.abs(r - endPoint.getRow()) + Math.abs(col - endPoint.getCol());
            if (distance < lowestDistanceUp) {
                lowestDistanceUp = distance;
                lowestRowUp = r;
            }
        }

        // right
        for (int c = col + 1; c < grid_SIZE; c++) {
            Node node = grid[row][c];
            if (node.getType() == 1) {
                break;
            }
            int distance = Math.abs(row - endPoint.getRow()) + Math.abs(c - endPoint.getCol());
            if (distance < lowestDistanceRight) {
                lowestDistanceRight = distance;
                lowestColRight = c;
            }
        }

        // left
        for (int c = col - 1; c >= 0; c--) {
            Node node = grid[row][c];
            if (node.getType() == 1) {
                break;
            }
            int distance = Math.abs(row - endPoint.getRow()) + Math.abs(c - endPoint.getCol());
            if (distance < lowestDistanceLeft) {
                lowestDistanceLeft = distance;
                lowestColLeft = c;
            }
        }

        // dowmn
        for (int r = row + 1; r < grid_SIZE; r++) {
            Node node = grid[r][col];
            if (node.getType() == 1) {
                break;
            }
            int distance = Math.abs(r - endPoint.getRow()) + Math.abs(col - endPoint.getCol());
            if (distance < lowestDistanceDown) {
                lowestDistanceDown = distance;
                lowestRowDown = r;
            }
        }

        this.currentLocation = findPath(lowestDistanceUp, lowestDistanceDown, lowestDistanceLeft, lowestDistanceRight,
                lowestRowUp, lowestRowDown, lowestColLeft, lowestColRight, this.currentLocation, grid);
    }



    public Node findPath(int lowestDistanceUp, int lowestDistanceDown, int lowestDistanceLeft, int lowestDistanceRight,
                         int lowestRowUp, int lowestRowDown, int lowestColLeft, int lowestColRight, Node currentLocation, Node[][] grid) {
        // change current node based on least distance from search
        if (lowestDistanceUp < lowestDistanceDown && lowestDistanceUp < lowestDistanceLeft && lowestDistanceUp < lowestDistanceRight) {
            this.currentLocation = this.grid[lowestRowUp][currentLocation.getCol()];

        } else if (lowestDistanceDown < lowestDistanceUp && lowestDistanceDown < lowestDistanceLeft && lowestDistanceDown < lowestDistanceRight) {
            this.currentLocation = grid[lowestRowDown][currentLocation.getCol()];
        } else if (lowestDistanceLeft < lowestDistanceUp && lowestDistanceLeft < lowestDistanceDown && lowestDistanceLeft < lowestDistanceRight) {
            this.currentLocation = grid[currentLocation.getRow()][lowestColLeft];
        } else if (lowestDistanceRight < lowestDistanceUp && lowestDistanceRight < lowestDistanceDown && lowestDistanceRight < lowestDistanceLeft) {
            this.currentLocation = grid[currentLocation.getRow()][lowestColRight];
        }

        // Update the grid array with the new currentLocation position
        if (parent != null && !parent.equals(this.currentLocation)) {
            parent.setType(9);
        }

        parent = this.currentLocation;


        this.currentLocation.setType(2);
}



        if (this.currentLocation == endPoint) {
            displayPath(); // Display the found path
            print("Solution found");
            print("Total number of moves needed to reach goal:"+moves);
        }


        return this.currentLocation;
    }
    */
    public void updatePath() {
        Node fLow = null;

        // Find the node with the lowest F value in the open list
        for (int i = 0; i < openList.size(); i++) {
            Node node = openList.get(i);
            if (fLow == null || node.getF() < fLow.getF()) {
                fLow = node;
            }
        }

        if (fLow == null) {
            // No path found
            System.out.println("No path found.");
            return;
        }


        currentLocation = fLow;

        if (currentLocation == endPoint) {
            // Path to the goal found
            print("------------------------------------");
            System.out.println("Solution found.");
            System.out.println("Total number of moves needed to reach the goal: " + moves);
            displayGrid(); // Display the final grid with the path
            print("-------------------------------");
            System.exit(0);
            return;
        }

        if (openList.isEmpty()) {
            System.out.println("No path found"); // Print desired message
            System.exit(0); // Terminate the program
        }if(currentLocation.getType() != 2 ) {
            currentLocation.setType(9);
            print("-------------------------------");
            displayGrid();
            print("-------------------------------");
        }
        // Move the current node from the open list to the closed list
        openList.remove(currentLocation);
        closedList.add(currentLocation);
//print(openList);
        // Explore the adjacent nodes
        explore(currentLocation); // Call your existing explore method with the current location
        currentLocation = openList.get(0);

    }


    /*
        public void displayPath() {
            for (int row = 0; row < grid; row++) {
                for (int col = 0; col < grid; col++) {
                    System.out.print(world[row][col].getType() + " ");
                }
                System.out.println();
            }
            System.out.println("-------------");
        }
    */
    public static void main(String[] args) {

        //System.out.println(6);

        Main pathfinding = new Main();

//generate and place start and end goals

        pathfinding.AStarPathfinding();


    }}
class Node {
    private int row, col, f, g, h, type;
    private Node parent;

    public Node(int r, int c, int t) {
        row = r;
        col = c;
        type = t;
        parent = null;
        // type 0 is traverseable, 1 is not
    }

    // mutator methods to set values
    public int getType() {
        return type;
    }

    public void setType(int t) {
        type = t;
    }

    public void setF() {
        f = g + h;
    }

    public void setG(int value) {
        g = value;
    }

    public void setH(int value) {
        h = value;
    }

    public void setParent(Node n) {
        parent = n;
    }

    // accessor methods to get values
    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public Node getParent() {
        return parent;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int t) {
        row = t;
    }


    public int getCol() {
        return col;
    }
    public void setCol(int t) {
        col = t;
    }
    public boolean equals(Object in) {
        // typecast to Node
        Node n = (Node) in;
        return row == n.getRow() && col == n.getCol();
    }

    public String toString() {
        return "Node: " + row + "_" + col;
    }

    public void someMethod() {
        System.out.println("This is a method in the Node class.");
    }
}