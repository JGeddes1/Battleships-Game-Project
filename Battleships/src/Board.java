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
    
    
    public void displaySunkShip(Ships ship) {
    	for (int[] coordinate : ship.getCoordinates()) {
            int row = coordinate[0];
            int col = coordinate[1];
            buttons[row][col].setBackground(ship.getShipColor()); // Change color to indicate a sunk ship
        }
    }
    
    
    // Method to place a ship on the board
    public void placeShip(int row, int col, Ships ship, boolean horizontal, boolean ComputerPlacement) {
        int length = ship.getShipLength();
        Color color = Color.BLACK; // Change color as needed
        Color shipColor = ship.getShipColor();
        boolean computerplacement = ComputerPlacement;
        remainingShips.add(ship);
        // Check if there is enough space to place the ship horizontally
        if (horizontal && col + length > cols && computerplacement ==false) {
            System.out.println("Not enough space to place the ship horizontally from the specified position.");
            return;
        }

        // Check if there is enough space to place the ship vertically
        if (!horizontal && row + length > rows && computerplacement ==false) {
            System.out.println("Not enough space to place the ship vertically from the specified position.");
            return;
        }

        // Place the ship on the board
        if (horizontal && computerplacement ==false) {
            for (int i = 0; i < length; i++) {
                if (col + i < cols) { // Check if index is within bounds
                    shipgrid[row][col + i] = ship;
                    ship.addCoordinate(row, col + i);
                    buttons[row][col + i].setBackground(shipColor); // Display ship type on button
                } else {
                    System.out.println("Attempting to place ship out of bounds horizontally.");
                    return;
                }
            }
        } if (computerplacement ==false) { // vertical placement
            for (int i = 0; i < length; i++) {
                if (row + i < rows) { // Check if index is within bounds
                    shipgrid[row + i][col] = ship;
                    ship.addCoordinate(row + i, col);
                    buttons[row + i][col].setBackground(shipColor); // Display ship type on button
                } else {
                    System.out.println("Attempting to place ship out of bounds vertically.");
                    return;
                }
            }
        }
        
        
// IF COMPUTER PLACEMENT  
        
        
        
        if (horizontal && col + length > cols && computerplacement ==true) {
            System.out.println("Not enough space to place the ship horizontally from the specified position.");
            return;
        }

        // Check if there is enough space to place the ship vertically
        if (!horizontal && row + length > rows && ComputerPlacement==true) {
            System.out.println("Not enough space to place the ship vertically from the specified position.");
            return;
        }

        // Place the ship on the board
        if (horizontal && ComputerPlacement==true) {
            for (int i = 0; i < length; i++) {
                if (col + i < cols) { // Check if index is within bounds
                    shipgrid[row][col + i] = ship;
                    ship.addCoordinate(row, col + 1);
              
    
                } else {
                    System.out.println("Attempting to place ship out of bounds horizontally.");
                    return;
                }
            }
        } else { // vertical placement
            for (int i = 0; i < length; i++) {
                if (row + i < rows) { // Check if index is within bounds
                    shipgrid[row + i][col] = ship;
                    ship.addCoordinate(row + i, col);
                   
                    // Display ship type on button
                } else {
                    System.out.println("Attempting to place ship out of bounds vertically computer." + row + col);
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
		ImageIcon hitIcon = new ImageIcon("J:\\eclipse_workspace\\Battleships-Game-Project\\Battleships\\src\\puddle.png"); 
		 int buttonWidth = buttons[row][col].getWidth();
	        int buttonHeight = buttons[row][col].getHeight();

	        // Resize the image to fit the button size
	        Image img = hitIcon.getImage();
	        Image resizedImg = img.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
	        ImageIcon resizedIcon = new ImageIcon(resizedImg);


	    if (hasShipAt(row, col)) {
	        // Handle hit
	        // Update button appearance to indicate a hit
	        updateButtonForHit(row, col);
	        return;
	    } 
//	    handleFiredPosition(row, col);
        // Set the resized image as the button icon
	    buttons[row][col].setIcon(resizedIcon);
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
		ImageIcon hitIcon = new ImageIcon("J:\\eclipse_workspace\\Battleships-Game-Project\\Battleships\\src\\explode.png"); 
		 int buttonWidth = buttons[row][col].getWidth();
	        int buttonHeight = buttons[row][col].getHeight();

	        // Resize the image to fit the button size
	        Image img = hitIcon.getImage();
	        Image resizedImg = img.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
	        ImageIcon resizedIcon = new ImageIcon(resizedImg);

	        // Set the resized image as the button icon
	    buttons[row][col].setIcon(resizedIcon);
	    Ships ship = shipgrid[row][col];
	    int numberOfShipsRemaining = remainingShips.size();
	    if (ship != null) {
            ship.registerHit();
            
//            System.out.println(remainingShips);
            // If the ship is sunk, remove it from the list
            if (ship.isSunk()) {
                remainingShips.remove(ship);
                displaySunkShip(ship);
    
                // If there are no remaining ships, the player wins
                if (remainingShips.isEmpty()) {
//                    endGameWithVictory("Player");
                }
            }
            
            
        
        }
	}




}
