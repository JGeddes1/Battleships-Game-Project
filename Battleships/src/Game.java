import javax.swing.*;
import java.awt.*;
import java.io.Console;
import java.util.Scanner;

public class Game  {
	
	private static boolean gameover = false;

    public static void main(String[] args) {
        // Get board size from user
        int[] boardSize = GetBoardSize();
        
        // Create player board
        Board playerBoard = new Board(boardSize[0], boardSize[1]);
//        placeShips(playerBoard); // Place ships on player board

        // Create computer board
        Board computerBoard = new Board(boardSize[0], boardSize[1]);
        // Implement logic to place computer ships (not shown)

        // Create a frame to contain both player and computer boards
        JFrame frame = new JFrame("Battleships Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2)); // Split the frame into two columns
        // Create gap between player and computer boards
        JPanel gapPanel = new JPanel();
        gapPanel.setPreferredSize(new Dimension(20, 20)); // Adjust gap size
        frame.add(playerBoard);
        frame.add(gapPanel, BorderLayout.CENTER);
        frame.add(computerBoard);

        frame.pack();
        frame.setVisible(true);
        
        
        placePlayerShips(playerBoard);
        
        // Create the computer player
        ComputerPlayer computerPlayer = new ComputerPlayer(computerBoard, playerBoard);

        // Place ships randomly on the computer board
        computerPlayer.placeShipsRandomly();
        
        
        Player personPlaying = new Player(playerBoard, computerBoard);
        
        while (!gameover) {
			 int playerShootPositionX = personPlaying.PlayerShootRow();
			 int playerShootPositionY = personPlaying.PlayerShootColumn();
			 personPlaying.fireAtComputerBoard(playerShootPositionX,playerShootPositionY);
			 
	//        personPlaying.fireAtComputerBoard(1, 1);
			 
//			 COMPUTER SHOOTING
			 

			 computerPlayer.fireAtPlayerBoard(computerPlayer.getRandomRow(),computerPlayer.getRandomColumn());
			 

		}
       

    }
    
    public static boolean setGameOverStatus() {
    	return gameover = true;
    }

    // Method to get board size from user
    private static int[] GetBoardSize() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter board size (rows cols): ");
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();

        return new int[]{rows, cols};
    }
    
 

    // Method to place ships on the player board
    private static void placePlayerShips(Board board) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to place each ship individually
        System.out.println("Place your ships on the board.");

        // Iterate over each ship type
        for (Ship_Types shipType : Ship_Types.values()) {
            Ships ship = new Ships(shipType);

            // Keep prompting the user until a valid position is entered
            boolean shipPlaced = false;
            while (!shipPlaced) {
                // Prompt the user for ship placement
                System.out.println("Placing " + shipType + "...");
                System.out.print("Enter row and column for the starting position (row col): ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                

                // Prompt the user to specify the orientation
                System.out.print("Enter 'h' for horizontal or 'v' for vertical placement: ");
                char orientation = scanner.next().charAt(0);
                boolean horizontal = (orientation == 'h' || orientation == 'H');

                // Check if the ship can be placed at the specified position
                if (board.canPlaceShip(row, col, ship, horizontal)) {
                    // Place the ship on the board
                    board.placeShip(row, col, ship, horizontal); 
                    shipPlaced = true; // Set flag to indicate ship has been placed
                } else {
                    // Display error message and prompt the user to try again
                    System.out.println("Cannot place the ship at the specified position. Please try again.");
                }
            }
        }
    }
	
    
    
    
    
    
// OLD CODE CAN REMOVE BUT LEFT FOR REFERENCE
    // Method to place ships on the player board
//	private static void placeComputerShips(Board board) {
//	       Scanner scanner = new Scanner(System.in);
//
//	        // Prompt the user to place each ship individually
//	        System.out.println("Place your ships on the board.");
//
//	        // Iterate over each ship type
//	        for (Ship_Types shipType : Ship_Types.values()) {
//	            Ships ship = new Ships(shipType);
//
//	            // Prompt the user for ship placement
//	            System.out.println("Placing " + shipType + "...");
//	            System.out.print("Enter row and column for the starting position (row col): ");
//	            int row = scanner.nextInt();
//	            int col = scanner.nextInt();
//
//	            // Place the ship on the board
//	            board.placeShip(row, col, ship, true); // Example placement, adjust as needed
//		
//		
//		//	        // Implement ship placement logic on the player board
////	        Ships destroyer = new Ships(Ship_Types.DESTROYER);
////	        Ships battleship = new Ships(Ship_Types.BATTLESHIP);
////	        Ships carrier = new Ships(Ship_Types.CARRIER);
////	        
////	        
////	        board.placeShip(0, 0, destroyer, true);
////	        board.placeShip(3, 3, battleship, true);
////	        board.placeShip(4, 1, carrier, true);// Example placement, adjust as needed
////	        // Place other ships as needed
//	    }
//	
//	}
	
}
