import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
public class Board extends JPanel {
    private int rows;
    private int cols;
    private JButton[][] buttons;
    private boolean[][] firedPositions;
    private Ships[][] shipgrid;
    public List<Ships> remainingShips;
    

    

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        buttons = new JButton[rows][cols];
        this.shipgrid = new Ships[rows][cols];
        remainingShips = new ArrayList<>();

        firedPositions = new boolean[rows][cols];
        setLayout(new GridLayout(rows, cols));

        // Initialize the grid of buttons
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(25, 25));
                buttons[i][j].setBackground(Color.BLUE);
                // Adjust button size
                add(buttons[i][j]);
            }
        }
        
    }

    // Getter for the number of rows
    public int getRow() {
        return rows;
    }

    // Getter for the number of columns
    public int getColumn() {
        return cols;
    }
    
    
    
    
    // Method to place a ship on the board
    public void placeShip(int row, int col, Ships ship, boolean horizontal) {
        int length = ship.getShipLength();
        Color color = Color.BLACK; // Change color as needed
        Color shipColor = ship.getShipColor();
        remainingShips.add(ship);
        // Check if there is enough space to place the ship horizontally
        if (horizontal && col + length > cols) {
            System.out.println("Not enough space to place the ship horizontally from the specified position.");
            return;
        }

        // Check if there is enough space to place the ship vertically
        if (!horizontal && row + length > rows) {
            System.out.println("Not enough space to place the ship vertically from the specified position.");
            return;
        }

        // Place the ship on the board
        if (horizontal) {
            for (int i = 0; i < length; i++) {
                if (col + i < cols) { // Check if index is within bounds
                    shipgrid[row][col + i] = ship;
                    buttons[row][col + i].setBackground(shipColor); // Display ship type on button
                } else {
                    System.out.println("Attempting to place ship out of bounds horizontally.");
                    return;
                }
            }
        } else { // vertical placement
            for (int i = 0; i < length; i++) {
                if (row + i < rows) { // Check if index is within bounds
                    shipgrid[row + i][col] = ship;
                    buttons[row + i][col].setBackground(shipColor); // Display ship type on button
                } else {
                    System.out.println("Attempting to place ship out of bounds vertically.");
                    return;
                }
            }
        }
    }
    
    public boolean canPlaceShip(int startRow, int startCol, Ships ship, boolean horizontal) {
        int length = ship.getShipLength();

        // Check if the ship will fit within the boundaries of the board
        if (horizontal && startCol + length > cols) {
            return false; // Not enough horizontal space
        }
        if (!horizontal && startRow + length > rows) {
            return false; // Not enough vertical space
        }

        // Check if any cells are already occupied by other ships
        if (horizontal) {
            for (int i = 0; i < length; i++) {
                if (buttons[startRow][startCol + i].getBackground() != Color.BLUE) {
                    return false; // Ship overlap
                }
            }
        } else {
            for (int i = 0; i < length; i++) {
                if (buttons[startRow + i][startCol].getBackground() != Color.BLUE) {
                    return false; // Ship overlap
                }
            }
        }

        // The ship can be placed at the specified position
        return true;
    }
    
    
    

	public boolean hasBeenFiredUpon(int row, int col) {
		// TODO Auto-generated method stub
		
		return firedPositions[row][col];
	}

	public void markPositionAsFiredUpon(int row, int col) {
		firedPositions[row][col] = true;
		buttons[row][col].setEnabled(false); // Disable the button to prevent further firing
	    Color originalColor = buttons[row][col].getBackground();
	    Color firedColor = Color.WHITE; 
	    buttons[row][col].setBackground(firedColor); // Change the background color to indicate firing
	    if (hasShipAt(row, col)) {
	        // Handle hit
	        // Update button appearance to indicate a hit
	        updateButtonForHit(row, col);
	    } 
//	    handleFiredPosition(row, col);
		
	}
	
	public void handleFiredPosition(int row, int col) {
	    if (hasShipAt(row, col)) {
	        // Handle hit
	        // Update button appearance to indicate a hit
	        updateButtonForHit(row, col);
	    } 
	}


	public boolean hasShipAt( int row, int col) {
	   
	    firedPositions[row][col] = true;
	    
	    
	    
	    
	    
	    
	 // Check if there is a ship at the specified position
	    // You may need to access the ship placement information stored in your Board class
	    // For example, if you have a 2D array representing the board where each element stores the ship information,
	    // you can check if that element contains a ship or not.
	    
	    // For example, assuming you have a 2D array called shipPositions[][] representing the board:
	    // return shipPositions[row][col] != null;
	    
	    // You would replace shipPositions[][] with the appropriate data structure or mechanism
	    // you are using to represent ship placement on the board.
	    return shipgrid[row][col] != null; // Placeholder, replace with actual logic
	}
	private void updateButtonForHit(int row, int col) {
	    // Change button appearance to indicate a hit (e.g., change color)
	    buttons[row][col].setBackground(Color.RED);
	    Ships ship = shipgrid[row][col];
	    int numberOfShipsRemaining = remainingShips.size();
	    if (ship != null) {
            ship.registerHit();
            System.out.println(numberOfShipsRemaining);
            System.out.println(remainingShips);
            // If the ship is sunk, remove it from the list
            if (ship.isSunk()) {
                remainingShips.remove(ship);
                System.out.println(numberOfShipsRemaining);
                System.out.println(remainingShips);
                // If there are no remaining ships, the player wins
                if (remainingShips.isEmpty()) {
                    endGameWithVictory();
                }
            }
            
            
        
        }
	}
	private void endGameWithVictory() {
	        // Display a message or perform any other end-of-game actions
	        System.out.println("Congratulations! You have sunk all enemy ships. You win!");
	        // You can add more actions here, such as displaying a dialog, stopping the game loop, etc.
	        Game.setGameOverStatus();
	    }


}
