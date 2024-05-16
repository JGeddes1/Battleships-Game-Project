import java.util.Scanner;

public class Player {
    private Board playerBoard;
    private Board computerBoard;

    public Player(Board playerBoard, Board computerBoard) {
        this.playerBoard = playerBoard;
        this.computerBoard = computerBoard;
    }

    public void fireAtComputerBoard(int row, int col) {
        // Check if the position has already been fired upon
        if (computerBoard.hasBeenFiredUpon(row, col)) {
            System.out.println("You've already fired at this position. Choose a different position.");
            return;
        }
        
        if(row > computerBoard.getRow()) {
        	System.out.println("You've tried to fire outside of this grid. Choose a different position.");
        	PlayerShootRow();
        }
        if(col > computerBoard.getColumn()) {
        	System.out.println("You've tried to fire outside of this grid. Choose a different position.");
        }

        // Mark the position as fired upon
        computerBoard.markPositionAsFiredUpon(row, col);

        // Check if the fired position contains a ship
        if (computerBoard.hasShipAt(row, col)) {
            System.out.println("Hit!");
            
            // Handle hit logic (e.g., mark the ship as hit)
        } else {
            System.out.println("Miss!");
            // Handle miss logic
        }
    }

	int PlayerShootRow() {
		Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your shooting location (row): ");
        int rows = scanner.nextInt();
        

        return  rows;
		
	}
	
	int PlayerShootColumn() {
		Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your shooting location (column): ");
        int column = scanner.nextInt();
        

        return  column;
		
	}
}
