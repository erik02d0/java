/**
 * Class for testing IO frontend
 */

import java.io.*;
import java.util.Scanner;

public class init {

	public static final Scanner scan = new Scanner(System.in);

	public static void loadShift() {
		System.err.println("Not yet implemented");
	}

	public static void newShift() {
		int nEmployees = 0;
		System.out.println("Number of employees to enter: ");
		if (scan.hasNextInt()) {
			nEmployees = scan.nextInt();
		}
		else {
			System.err.println("Bad format");
			return;
		}

		System.out.println("Enter names of employees...");
		String line = scan.nextLine();
		for (int i=0; i<nEmployees; i++) {
			line = scan.nextLine();
			System.out.println("You entered: "+line);
		}
	}

	public static void main(String[] args) {

		/* Welcome and intro */
		System.out.println("Welcome to the employee CLI. ");
		System.out.println("To quit, press Ctrl+C.\n");

		System.out.println("Type 'new' to create a new shift, ");
		System.out.println("or type 'load' to load an existing one.");

		String mrCmd = ""; /* most recent command */
		boolean validCmd = false;
		while (true) {
			System.out.print("\ncmd: ");
		
			String cmd = scan.next();
			mrCmd = cmd;
			if (cmd.compareTo("new") == 0) {
				validCmd = true;
				newShift();
			}
			else if (cmd.compareTo("load") == 0) {
				validCmd = true;
				loadShift();
			}
			else {
				System.err.println("Invalid command.");
				continue; /* skip remaining code in loop */
			}

			System.out.print("Add preferences? (y/n) ");
			cmd = scan.next();
			if (cmd.compareTo("y") == 0)
				addPrefs();

			/* etc etc */
		}
	}
}
