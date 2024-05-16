import java.util.Random;

public class ComputerPlayer {
    private Board _computerBoard;
    private Random random_placement;
    private Board _playerBoard;
 // Declare the previousPosition variable
    private int previousRow;
    private int previousCol;
    public int hitstreak = 0;

    public ComputerPlayer(Board computerBoard,Board playerBoard) {
        _computerBoard = computerBoard;
        random_placement = new Random();
        _playerBoard = playerBoard;
        previousRow = 0;
        previousCol = 0;
    }

    public void placeShipsRandomly() {
        for (Ship_Types type : Ship_Types.values()) {
            boolean placed = false;
            while (!placed) {
                int row = random_placement.nextInt(_computerBoard.getRow());
           
                int col = random_placement.nextInt(_computerBoard.getColumn());
                
                
                boolean horizontal = random_placement.nextBoolean(); 
                if (row >=_computerBoard.getRow() || col >= _computerBoard.getColumn()) {
                	 do  {
                    	 row = random_placement.nextInt(_computerBoard.getRow());
                        
                         col = random_placement.nextInt(_computerBoard.getColumn());
    				}while(row >9 || col > 9);
					
				}
               
                
                Ships ship = new Ships(type);
                
                // Check if there's enough space on the board for the ship
                if (_computerBoard.canPlaceShip(row, col, ship, horizontal)) {
                    _computerBoard.placeShip(row, col, ship, horizontal);
                    placed = true;
                }
            }
        }
    }
    
    
    public int getRandomRow() {
    	int row = random_placement.nextInt(_computerBoard.getRow());
    	
    	return row;
    }
    
    public int getRandomColumn() {
    	int col = random_placement.nextInt(_computerBoard.getColumn());
    	
    	return col;
    }
    
    
    
    public void fireAtPlayerBoard(int row, int col) {
        // Check if the position has already been fired upon
        if (_playerBoard.hasBeenFiredUpon(row, col)) {
            System.out.println("You've already fired at this position. Choose a different position.");
            return;
        }
        
        // Check if the position is within the grid bounds
        if (row >= _playerBoard.getRow() || col >= _playerBoard.getColumn()) {
            System.out.println("You've tried to fire outside of the grid. Choose a different position.");
            row = getRandomRow(); // Get a new random row
            col = getRandomColumn(); // Get a new random column
        }
        
        // Check for hit or miss based on the current hit streak
        if (hitstreak == 0) {
            // No hit streak, check the current position
            if (_playerBoard.hasShipAt(row, col)) {
                System.out.println("Computer Hit!");
                // Mark the position as fired upon
                _playerBoard.markPositionAsFiredUpon(row, col);
                previousRow = row;
                previousCol = col;
                hitstreak++;
            } else {
                System.out.println("Computer Miss!");
                // Mark the position as fired upon
                _playerBoard.markPositionAsFiredUpon(row, col);
            }
        } else {
            // Hit streak active, continue targeting the next position
        	int nextRow = previousRow + 1; // Calculate the next row
        	if (nextRow >= _playerBoard.getRow()) {
        		getRandomRow();
			}
        	
            if (nextRow < _playerBoard.getRow()) {
                if (_playerBoard.hasShipAt(nextRow, previousCol)) {
                    System.out.println("Computer Hit!");
                    // Mark the position as fired upon
                    _playerBoard.markPositionAsFiredUpon(nextRow, previousCol);
                    previousRow = nextRow; // Update the previous row
                    hitstreak++;
                } else {
                    System.out.println("Computer Miss!");
                    hitstreak = 0; // Reset the hit streak
                    _playerBoard.markPositionAsFiredUpon(row, col);
                }
            } else {
                System.out.println("Computer Miss!"); // Out of bounds, treat as miss
                hitstreak = 0; // Reset the hit streak
                _playerBoard.markPositionAsFiredUpon(row, col);
            }
        }
    }
    
        
      
}
    
    

