import java.util.Scanner;


public class BattleShip {

    // Grid size for the game
    static final int GRID_SIZE = 10;

    // Player 1's main grid containing their ships
    static char[][] player1Grid = new char[GRID_SIZE][GRID_SIZE];

    // Player 2's main grid containing their ships
    static char[][] player2Grid = new char[GRID_SIZE][GRID_SIZE];

    // Player 1's tracking grid to show their hits and misses
    static char[][] player1TrackingGrid = new char[GRID_SIZE][GRID_SIZE];

    // Player 2's tracking grid to show their hits and misses
    static char[][] player2TrackingGrid = new char[GRID_SIZE][GRID_SIZE];

    // Scanner object for user input
    static Scanner scanner = new Scanner(System.in);

    
    public static void main(String[] args) {
        // Initialize grids for both players
        initializeGrid(player1Grid);
        initializeGrid(player2Grid);
        initializeGrid(player1TrackingGrid);
        initializeGrid(player2TrackingGrid);

        // Place ships randomly on each player's grid
        placeShips(player1Grid);
        placeShips(player2Grid);

        // Variable to track whose turn it is
        boolean player1Turn = true;

        // Main game loop, runs until one player's ships are all sunk
        while (!isGameOver()) {
            if (player1Turn) {
                System.out.println("Player 1's turn:");
                printGrid(player1TrackingGrid);
                playerTurn(player2Grid, player1TrackingGrid);
            } else {
                System.out.println("Player 2's turn:");
                printGrid(player2TrackingGrid);
                playerTurn(player1Grid, player2TrackingGrid);
            }
            player1Turn = !player1Turn;
        }

        System.out.println("Game Over!");
    }


    static void initializeGrid(char[][] grid) {
        for(int i =0 ; i <GRID_SIZE ;i++ )
            for(int j =0 ; j <GRID_SIZE ;j++ )
            grid[i][j]='~';
    }


    static void placeShips(char[][] grid) {
int[]  shipSizes = {5,4,3,3,2};
for( int size : shipSizes){
    boolean placed = false;
    while (!placed){
        int row= (int) (Math.random() * GRID_SIZE);
        int col= (int) (Math.random() * GRID_SIZE);
boolean horizontal= Math.random() <0.5;
        if (canPlaceShip(grid, row, col, size, horizontal)) {
            for(int i =0 ; i< size ; i++){
                if (horizontal) {
                    grid[row][col + i] ='S';}
                else{
                    grid[col][row + i] ='S';}
                placed = true;

                }
            }
        }
    }
    }


    static boolean canPlaceShip(char[][] grid, int row, int col, int size, boolean horizontal) {
       if(horizontal){
           if( size + col > GRID_SIZE)return false;
           for(int i =0 ; i < size ; i++)
               if (grid[row][col + i] != '~') return false;
       }
     else {

        if (row + size > GRID_SIZE) return false;

        for (int i = 0; i < size; i++) {
            if (grid[row + i][col] != '~') return false;
        }
    }
    return true;
       }

    static void playerTurn(char[][] opponentGrid, char[][] trackingGrid) {
        System.out.print("Enter coordinates (e.g., A5): ");

        String input = scanner.nextLine().toUpperCase();

        while (!isValidInput(input)) {
            System.out.print("Invalid input. Enter coordinates (e.g., A5): ");
            input = scanner.nextLine().toUpperCase();
        }


        int row = input.charAt(0) - 'A';

        int col = Integer.parseInt(input.substring(1)) - 1;


        if (opponentGrid[row][col] == 'S') {
            System.out.println("Hit!");
            opponentGrid[row][col] = 'X';
            trackingGrid[row][col] = 'X';
        } else {
            System.out.println("Miss!");
            opponentGrid[row][col] = 'O';
            trackingGrid[row][col] = 'O';
        }
    }


    static boolean isGameOver() {
        return allShipsSunk(player1Grid) || allShipsSunk(player2Grid);


    }


    static boolean allShipsSunk(char[][] grid) {

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == 'S') {
                    return false;
                }
            }
        }
        return true;

    }


    static boolean isValidInput(String input) {
        if (input.length() < 2 || input.length() > 3) return false;

        char row = input.charAt(0);
        if (row < 'A' || row > 'J') return false;

        int col;
        try {
            col = Integer.parseInt(input.substring(1));
        } catch (NumberFormatException e) {
            return false;
        }
        return col >= 1 && col <= 10;


    }


    static void printGrid(char[][] grid) {
        System.out.print("  ");
        for (int i = 1; i <= GRID_SIZE; i++) {
            System.out.print(i + " ");}
        System.out.println();

        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}



