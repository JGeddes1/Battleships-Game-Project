import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Game {
    private static boolean gameover = false;
    private static JFrame frame;

    public static void main(String[] args) {
        // Get board size from user
        int[] boardSize = GetBoardSize();

        // Create player board
        Board playerBoard = new Board(boardSize[0], boardSize[1]);

        // Create computer board
        Board computerBoard = new Board(boardSize[0], boardSize[1]);

        // Create a frame to contain both player and computer boards
        frame = new JFrame("Battleships Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 2)); // Split the frame into two columns
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout());
        JLabel playerHeader = new JLabel("Player Board", SwingConstants.CENTER);
        playerPanel.add(playerHeader, BorderLayout.NORTH);
        playerPanel.add(createBoardWithNumbers(playerBoard), BorderLayout.CENTER);

        // Create computer panel with header
        JPanel computerPanel = new JPanel();
        computerPanel.setLayout(new BorderLayout());
        JLabel computerHeader = new JLabel("Computer Board", SwingConstants.CENTER);
        computerPanel.add(computerHeader, BorderLayout.NORTH);
        computerPanel.add(createBoardWithNumbers(computerBoard), BorderLayout.CENTER);
        JPanel gapPanel = new JPanel();
        gapPanel.setPreferredSize(new Dimension(20, 20)); // Adjust gap size
        // Add panels to frame
        frame.add(playerPanel);
        frame.add(gapPanel, BorderLayout.CENTER);
        frame.add(computerPanel);

        
        
        
       
        
        
 

        frame.pack();
        frame.setVisible(true);

        placePlayerShips(playerBoard);

        // Create the computer player
        ComputerPlayer computerPlayer = new ComputerPlayer(computerBoard, playerBoard);

        // Place ships randomly on the computer board
        computerPlayer.placeShipsRandomly();

        Player personPlaying = new Player(playerBoard, computerBoard);
        
        while (!gameover) {
            playTurn(computerBoard, personPlaying);
            computerPlayer.fireAtPlayerBoard();

        }
    }
    
    private static JPanel createBoardWithNumbers(Board board) {
        int rows = board.getRow();
        int cols = board.getColumn();

        JPanel panel = new JPanel(new BorderLayout());

        // Create row numbers
        JPanel rowNumbers = new JPanel(new GridLayout(rows +0, 1));
       
        for (int i = 0; i < rows; i++) {
            rowNumbers.add(new JLabel(String.valueOf(i), SwingConstants.CENTER));
        }

        // Create column numbers
        JPanel colNumbers = new JPanel(new GridLayout(1, cols + 1));
        colNumbers.add(new JLabel("")); // Empty top-left corner
        for (int i = 0; i < cols; i++) {
            colNumbers.add(new JLabel(String.valueOf(i), SwingConstants.LEFT));
        }

        // Create board panel
        JPanel boardPanel = new JPanel(new GridLayout(rows, cols));
        JButton[][] buttons = board.getButtons();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boardPanel.add(buttons[i][j]);
            }
        }
        // Add row numbers to the left and board panel to the center
        panel.add(rowNumbers, BorderLayout.WEST);
        panel.add(colNumbers, BorderLayout.NORTH);
        panel.add(boardPanel, BorderLayout.CENTER);

        return panel;
    }

    public static void endGameWithVictory(String winner) {
        // Display a message or perform any other end-of-game actions
        String message;
        if (winner.equals("Player")) {
            message = "Congratulations! You have sunk all enemy ships. You win!";
        } else {
            message = "The computer has sunk all your ships. You lose!";
        }

        // Show the message in a dialog box
        JOptionPane.showMessageDialog(frame, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);

//        // Ask if the player wants to play again
//        int response = JOptionPane.showConfirmDialog(frame, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
//
//        if (response == JOptionPane.YES_OPTION) {
//            resetGame();
//        } else {
//            System.exit(0); // Exit the application
//        }
    }

//    private static void resetGame() {
//        // Dispose of the current game frame
//        if (frame != null) {
//            frame.dispose();
//        }
//
//        // Restart the game by calling the main method
//        SwingUtilities.invokeLater(() -> Game.main(null));
//    }

    public static int getValidRow(Board board) {
        int row;
        while (true) {
            System.out.print("Enter row (0 to " + (board.getRow() - 1) + "): ");
            Scanner scanner = new Scanner(System.in);
            try {
                row = scanner.nextInt();
                if (row >= 0 && row < board.getRow()) {
                    break;
                } else {
                    System.out.println("Invalid row. Please enter a valid row.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
        }
        return row;
    }

    public static int getValidColumn(Board board) {
        int col;
        while (true) {
            System.out.print("Enter column (0 to " + (board.getColumn() - 1) + "): ");
            Scanner scanner = new Scanner(System.in);
            try {
                col = scanner.nextInt();
                if (col >= 0 && col < board.getColumn()) {
                    break;
                } else {
                    System.out.println("Invalid column. Please enter a valid column.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
        }
        return col;
    }

    private static int[] GetBoardSize() {
        Scanner scanner = new Scanner(System.in);

        int rows;
        int cols;

        while (true) {
            System.out.print("Please enter board size (rows cols): ");

            rows = scanner.nextInt();
            cols = scanner.nextInt();
            if (cols >= 5 && rows >= 5) {
                break;
            } else {
                System.out.println("Invalid grid size (needs to be at least 5x5 to play). Please enter a valid grid size.");
            }
        }

        return new int[]{rows, cols};
    }

    public static void playTurn(Board board, Player player) {
        int row = getValidRow(board);
        int col = getValidColumn(board);
        // Fire at the player's board
        player.fireAtComputerBoard(row, col);
    }

    public static boolean setGameOverStatus() {
        return gameover = true;
    }

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
                System.out.print("Enter row and column for the starting position (row): ");
                int row = getValidRow(board);
                System.out.print("Enter row and column for the starting position (col): ");
                int col = getValidColumn(board);

                // Prompt the user to specify the orientation
                System.out.print("Enter 'h' for horizontal or 'v' for vertical placement: ");
                char orientation = scanner.next().charAt(0);
                boolean horizontal = (orientation == 'h' || orientation == 'H');

                // Check if the ship can be placed at the specified position
                if (board.canPlaceShip(row, col, ship, horizontal)) {
                    // Place the ship on the board
                    board.placeShip(row, col, ship, horizontal, false);
                    shipPlaced = true; // Set flag to indicate ship has been placed
                } else {
                    // Display error message and prompt the user to try again
                    System.out.println("Cannot place the ship at the specified position. Please try again.");
                }
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
	

