import java.awt.Color;

public class Ships {
    private final Ship_Types type;
    private int length;
    private int hits;
    
    // Define colors for each ship type
    private static final Color DESTROYER_COLOR = Color.ORANGE;
    private static final Color BATTLESHIP_COLOR = Color.DARK_GRAY;
    private static final Color CARRIER_COLOR = Color.PINK;
    private static final Color CRUISER_COLOR = Color.GRAY;
    private static final Color SUBMARINE_COLOR = Color.MAGENTA;

    public Ships(Ship_Types type) {
        this.type = type;
        this.hits = 0;
        
    }

    // Getter method for ship type
    public Ship_Types getType() {
        return type;
    }

    // Getter method for ship length
    public int getShipLength() {
        return getLength(type);
    }
    
    
    
    // Method to get the length of a ship based on its type
    private int getLength(Ship_Types type) {
        switch (type) {
            case CARRIER:
                return 5;
            case BATTLESHIP:
                return 4;
            case CRUISER:
            case SUBMARINE:
                return 3;
            case DESTROYER:
                return 2;
            default:
                return 0; // Default length if type is unknown
        }
    }
    
    
    // Method to get color for a ship type
    public Color getShipColor() {
        switch (type) {
            case DESTROYER:
                return DESTROYER_COLOR;
            case BATTLESHIP:
                return BATTLESHIP_COLOR;
            case CARRIER:
                return CARRIER_COLOR;
            case SUBMARINE:
                return SUBMARINE_COLOR;
            case CRUISER:
                return CRUISER_COLOR;
            default:
                return Color.GRAY; // Default color for unknown ship types
        }
    }
    
    
    public void registerHit() {
    	hits++;
    	int shipHitsLeft = getLength(type) - hits;
    	System.out.println("Hit! " + shipHitsLeft + " hits left on " + type );
        if (isSunk()) {
            System.out.println("Ship " + type + " has been sunk!");
            
        }
    }

    public boolean isSunk() {
    	
        return hits == getLength(type);
    }
}