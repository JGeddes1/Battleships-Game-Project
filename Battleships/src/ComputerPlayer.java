import java.util.Random;

public class ComputerPlayer {
    private Board _computerBoard;
    private Random random_placement;
    private Board _playerBoard;
 // Declare the previousPosition variable
    private int previousRow;
    private int previousCol;
    public int hitstreak = 0;
    private Direction currentDirection;
    private boolean targetingMode;

    public ComputerPlayer(Board computerBoard,Board playerBoard) {
        _computerBoard = computerBoard;
        random_placement = new Random();
        _playerBoard = playerBoard;
        previousRow = 0;
        previousCol = 0;
        targetingMode = false;
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
    
    
    
    public void fireAtPlayerBoard() {
        int row, col;
        if (!targetingMode) {
            row = getRandomRow();
            col = getRandomColumn();
        } else {
            int[] target = getNextTarget();
            row = target[0];
            col = target[1];
        }

        // Check if the position has already been fired upon
        if (_playerBoard.hasBeenFiredUpon(row, col)) {
        	System.out.println("Computer Already Fired upon!" + row + " " +col);
        	
            fireAtPlayerBoard(); // Retry with a different position
            return;
        }

        // Mark the position as fired upon
        _playerBoard.markPositionAsFiredUpon(row, col);

        if (_playerBoard.hasShipAt(row, col)) {
            System.out.println("Computer Hit!");
            previousRow = row;
            previousCol = col;
            hitstreak++;
            if (!targetingMode) {
                targetingMode = true;
                currentDirection = Direction.UP; // Start with UP direction
            }
        } else {
            System.out.println("Computer Miss!");
            if (targetingMode) {
                hitstreak = 0;
                currentDirection = getNextDirection();
                if (currentDirection == null) {
                    targetingMode = false;
                }
            }
        }
    }

    private int[] getNextTarget() {
        int row = previousRow, col = previousCol;
        switch (currentDirection) {
            case UP:
                row -= 1;
                break;
            case DOWN:
                row += 1;
                break;
            case LEFT:
                col -= 1;
                break;
            case RIGHT:
                col += 1;
                break;
        }
        if (row < 0 || row >= _playerBoard.getRow() || col < 0 || col >= _playerBoard.getColumn()) {
            currentDirection = getNextDirection();
            return getNextTarget();
        }
        return new int[]{row, col};
    }

    private Direction getNextDirection() {
        switch (currentDirection) {
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.LEFT;
            case LEFT:
                return Direction.RIGHT;
            case RIGHT:
                return null; // All directions tried, reset to hunting mode
        }
        return null;
    }
    
    
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
        
      
}
    
    

