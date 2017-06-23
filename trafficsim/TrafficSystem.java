import java.util.ArrayList;
import java.util.Properties;
import java.io.*;

/**
 * Defines the components and behaviour of s specific traffic system
 */

public class TrafficSystem {
    
  private Properties props = new Properties();
  private Lane lane;
  private Lane laneWest;
  private Lane laneSouth;
  private Light lightWest;
  private Light lightSouth;
  private int laneLength;
  private int laneWSLength;
  private VehicleGenerator vg;
  private ArrayList<Vehicle> queue;
  private Measurements statWest;
  private Measurements statSouth;
  private double sinksBlocked;
  private double queueBlocked;
  
  public TrafficSystem() throws IOException {
      this.loadProperties("properties.txt");
      this.vg = new VehicleGenerator("probabilities.txt");
      this.queue = new ArrayList<Vehicle>();
      this.statWest = new Measurements(100);
      this.statSouth = new Measurements(100);
  }
  
  /**
   * Advances the whole traffic system one timestep. Makes use
   * of components' step methods
   */
  public void step() {
      // vehicle passes signal if green
      if (this.laneWest.getFirst() != null) {
          if (this.lightWest.isGreen()) {
              statWest.add(Simulation.getTime()-this.laneWest.getFirst().getTime());
              this.laneWest.removeFirst();
              }
      }
      
      if (this.laneSouth.getFirst() != null) {
          if (this.lightSouth.isGreen()) {
              statSouth.add(Simulation.getTime()-this.laneSouth.getFirst().getTime());
              this.laneSouth.removeFirst();
              }
      }
      // vehicles in laneWest, laneSouth advance one step
      this.laneWest.step();
      this.laneSouth.step();
      
      // first vehicle in lane moves depending on destination
      if (this.lane.getFirst() != null) {
          if (this.lane.getFirst().getDestination() == 'W') {
              if (this.laneWest.lastFree()) {
                  this.laneWest.putLast(this.lane.getFirst());
                  this.lane.removeFirst();
              }
              else sinksBlocked++;
          }
          else {
              if (this.laneSouth.lastFree()) {
                  this.laneSouth.putLast(this.lane.getFirst());
                  this.lane.removeFirst();
              }
              else sinksBlocked++;
          }
      }
      this.lane.step();
      
      // vehicle arrives to queue
      Vehicle arrival = this.vg.step();
      
      // if last position in lane is empty, arrival goes to lane
      if (arrival != null) {
          this.queue.add(arrival);
      }
      if (!(this.queue.isEmpty())) {
          if (this.lane.lastFree()) {
              this.lane.putLast(this.queue.remove(0));
          }
          else queueBlocked++;
      }
      
      // one or both signals shift color
      this.lightWest.step();
      this.lightSouth.step();
      
  }

  
  /**
   * Prints currently collected statistics
   */  
  public void printStatistics() {
      System.out.println("\nStatistics after "+Simulation.getTime()+" time steps");
      System.out.println("\n Exist West");
      System.out.println("Number: "+statWest.stored());
      System.out.println("Mean: "+statWest.mean());
      System.out.println("Max: "+statWest.max());
      
      System.out.println("\n Exist South");
      System.out.println("Number: "+statSouth.stored());
      System.out.println("Mean: "+statSouth.mean());
      System.out.println("Max: "+statSouth.max());
      
      System.out.println("\n Percent time step with block: "+100*(sinksBlocked/(double)(Simulation.getTime())));
      System.out.println("Percent time step with queue: "+100*(queueBlocked/(double)(Simulation.getTime())));
  }
 
  
  /**
   * Prints the current situation using toString-methods in 
   * lights and lanes
   */
  public void print() {
      System.out.println("\n"+this.lightWest+" "+this.laneWest+" "+this.lane+" Queue: "+this.queue);
      System.out.println(this.lightSouth+" "+this.laneSouth);
  }
  
  
  /**
   * Prints the simulation parameters and arrival probabilities used in 
   * this run
   */
  public void printSetup() throws IOException, FileNotFoundException {
      BufferedReader br1 = new BufferedReader(new FileReader("properties.txt"));
      String line1 = null;
      BufferedReader br2 = new BufferedReader(new FileReader("probabilities.txt"));
      String line2 = null;
      
      System.out.println("\nSimulation parameters:");
      while ((line1 = br1.readLine()) != null) {
          System.out.println(line1);
      }
      System.out.println("\nTraffic periods and probabilities:");
      while ((line2 = br2.readLine()) != null) {
          System.out.println(line2);
      }
  }
  
  
  /** 
   * Reads the lane lengths and the properties of the lights from a 
   * property file
   * 
   * @param filename The file containing the properties
   * 
   * 
   * The property file should define 
   * <ul>
   * <li> length of the first lane</li>
   * <li> length of the lanes in front of the traffic lights</li>
   * <li> traffic light period (same for both lights)</li>
   * <li> green light period for each of the lights</li> 
   * </ul>
   * <p>
   * <b>Example of file contents:</b>
   * <pre>
   *    laneLength      : 10
   *    laneWSLength    :  8
   *    lightPeriod     : 14
   *    lightWestGreen  :  6
   *    lightSouthGreen :  4
   * </pre>
   * 
   */
  public void loadProperties(String filename) throws IOException {
      this.props.load(new FileReader(filename));
      this.lane = new Lane(Integer.parseInt(props.getProperty("laneLength")));
      this.laneWest = new Lane(Integer.parseInt(props.getProperty("laneWSLength")));
      this.laneSouth = new Lane(Integer.parseInt(props.getProperty("laneWSLength")));
      this.lightWest = new Light(Integer.parseInt(props.getProperty("lightPeriod")),
                                 Integer.parseInt(props.getProperty("lightWestGreen")));
      this.lightSouth = new Light(Integer.parseInt(props.getProperty("lightPeriod")),
                                  Integer.parseInt(props.getProperty("lightSouthGreen")));
  }
  
  public static void main(String[] args) throws IOException {
      TrafficSystem ts = new TrafficSystem();
      ts.print();
      ts.printSetup();
      for (int i=0; i<100; i++) {
          ts.step();
          ts.print();
      }
      ts.printStatistics();
  }
}