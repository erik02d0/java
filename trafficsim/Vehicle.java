/**
 * Represents a vehicle with its born time (the time when it enters the system)
 * and its destination.
 * <p>
 * <b>Note:</b> The constructor gets the current time from <code>Simulation.getTime()</code>.
 */

public class Vehicle {

    private int  bornTime;
    private char destination;

    /**
     * Constructs a Vehicle 
     * @param destination the destination
     */
    
    public Vehicle(char destination) {
        this.bornTime = Simulation.getTime();
        this.destination = destination;
    }

    /**
     * Get method for the born time
     * @return the time when this vehicle was born 
     */
    public int getTime() {
        return this.bornTime;
    }

    /**
     * Get method for the destination
     * @return the destination
     */
    public char getDestination() {
        return this.destination;
    }

    /**
     * Return a string representation indicating the destination
     * @return the string representation
     */
    public String toString() {
        return ""+this.destination;
    }
    
    public static void main(String[] args) {
        Vehicle bmw = null;
        System.out.println(bmw);
        System.out.println(bmw.getDestination());
    }
    
}

