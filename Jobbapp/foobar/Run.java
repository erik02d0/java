/**
 * @author Erik Vesterlund
 */

package foobar;

import java.io.*;

/**
 * Class containing only a main method which starts the app.
 */
public class Run {

	/**
	 * Main method only used to call an initializer
	 *
	 * @param args Input arguments (have no effect)
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void
	main(String[] args)
	throws IOException, InterruptedException
	{
		for (int i=0; i<70; i++)
			System.out.print("*");
		System.out.println("\n");

		EmployeeCLI.init();
	}

}
