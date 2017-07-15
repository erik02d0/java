/**
 * Represents a series of measurements.
 */

public class Measurements {
  private double[] data;
  private int counter; // keeps track of #(entered values)
  
  /**
   * Constructs a series of user-specified length
   */
  public Measurements(int max) {
    this.data = new double[max];
    this.counter = 0;
  }
  
  /**
   * Constructs series containing entries equal
   * to those in a user-specified array.
   */
  public Measurements(double[] values) {
    this(values.length);
    for (int i=0; i<values.length; i++) {
      this.add(values[i]);
    }
  }
  
  /*
   * Allows a neat representation of a Measurements object,
   * @return data inside <>, or empty if empty
   */
  public String toString() {
    if (counter == 0) {
      return "empty";
    }
    else {
      String temp_str = "<";
      for (int i=0; i<counter-1; i++) {
        temp_str = temp_str+this.get(i)+", ";
      }
      return temp_str+this.get(counter-1)+"> \n";
    }
  }
  
  /*
   * Allows user to enter data. Creates double-length series
   * if series is full.
   * @param value the data to be saved into series
   */
  public void add(double value) {
    if (this.counter < this.data.length) {
      this.data[counter]=value;
      counter++;
    }
    
    else { // expand object's original array if full:
      double[] emptyArr = new double[2*this.data.length];
      for (int i=0; i < this.data.length; i++) { // copy this.data into emptyArr;
        emptyArr[i] = this.data[i];
      }
      this.data = emptyArr; // assign emptyArr as this object data
      this.add(value); // now add the value. (counter remains unchanged to this point)
    }
  }
  
  /**
   * Returns to user the no. of entries made so far
   * @return value of counter
   */
  public int stored() {
    return this.counter;
  }
  
  /**
   * Returns data stored in user-specified place.
   * @param index what data the user wants
   * @return the desired value
   */
  public double get(int index) {
    return this.data[index];
  }
  
  /*
   * Calculates the mean of the entered values.
   * @return the mean
   */
  public double mean() {
    if (this.counter == 0) {
      System.out.println("No data entered! \n");
      return 0;
    }
    else {
      double sum=0;
      for (int i=0; i<counter; i++) {
        sum = sum + this.get(i);
      }
      return sum/counter;
    }
  }
  
  /*
   * Determines the smallest value entered so far.
   * @return the minimum
   */
  public double min() {
    double min = this.get(0);
    for (int i=1; i<counter; i++) {
      if (this.get(i) < min) {
        min = this.get(i);
      }
    }
    return min;
  }
  
  /*
   * Determines the largest value entered.
   * @return the maximum
   */
  public double max() {
    double max = this.get(0);
    for (int i=0; i<counter; i++) {
      if (this.get(i) > max) {
        max = this.get(i);
      }
    }
    return max;
  }
  
  /*
   * calculates the standard deviation of the series
   * @return the standard deviation
   */
  public double stdDev() {
    double avg = this.mean();
    
    double sumsq = 0;
    for (int i=0; i<counter; i++) {
      sumsq = sumsq+Math.pow(this.get(i)-avg,2);
    }
    return Math.sqrt(sumsq/counter);
  }
  
  /*
   * Creates an array from the values entered by user.
   * @return array
   */
  public double[] get() {
    double[] arr = new double[this.counter];
    for (int i=0; i<this.counter; i++) {
      arr[i] = this.get(i);
    }
    return arr;
  }
  
  // create new object whose array is smoothed,
  // meaning entries in new array are averages
  // of those in called object's array
  /*
   * Creates new series with smoothed entries;
   * entries are re-defined as the average of the
   * surrounding values.
   * @return the smoothed series.
   */
  public Measurements smooth() {
    Measurements mSmooth = new Measurements(this.data.length);
    
    mSmooth.add(this.data[0]);
    
    for (int i=1; i<this.data.length-1; i++) {
      mSmooth.add((this.data[i-1]+this.data[i]+this.data[i+1])/3);
    }
    
    mSmooth.add(this.data[this.data.length-1]);
    
    return mSmooth;
  }
}