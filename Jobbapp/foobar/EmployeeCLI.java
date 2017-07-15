/**
 * @author Erik Vesterlund
 */

package foobar;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeCLI {

	public static final Scanner glbScan = new Scanner(System.in);

	/**
	 * Check if name is a valid name
	 *
	 * @param name Name to be checked
	 * @return true if name is valid, false otherwise
	 */
	public static boolean checkName(String name) {
		int nAlphabetic = 0;
		int c;
		for (int i=0; i<name.length(); i++) {
			c = (int)name.charAt(i);
			if ((64 < c && c < 91) || (96 < c && c < 123))
				nAlphabetic++;
		}
		return nAlphabetic >= 2;
	}

	/**
	 * Check if competence string is valid
	 *
	 * @param comp Competencies to check
	 * @return true if comp is valid, false otherwise
	 */
	public static boolean checkComp(String comp) {
		int len = 0;
		int c;
		for (int i=0; i<comp.length(); i++) {
			c = (int)comp.charAt(i);
			if ((64 < c && c < 91) || (96 < c && c < 123))
				len++;
			else if (c == 32)
				;
			else
				return false;
		}

		return len > 0;
	}

	/**
	 * Format a competence string that has been validated by
	 * {@link #checkComp}. Returns an uppercase string of
	 * alphabetic characters.
	 *
	 * @param comp The string to be formatted
	 * @return fmt The formatted string
	 */
	public static String formatComp(String comp) {
		String fmt = "";
		for (int i=0; i<comp.length(); i++) {
			if ((int)comp.charAt(i) != 32)
				fmt += comp.charAt(i);
		}
		return fmt.toUpperCase();
	}

	public static ArrayList<Employee>
	loadShift()
	throws FileNotFoundException, IOException
	{
		ArrayList<Employee> list = new ArrayList<Employee>();
		String empName = "", empComp = "";

		Scanner fileScanner = new Scanner(new File("data.dzn"));
		String line = "";
		while (fileScanner.hasNextLine()) {
			line = fileScanner.nextLine();
			if (line.charAt(0) == '%') {
				line = line.substring(1);
				Scanner fieldScanner = new Scanner(line).useDelimiter(",");
				empName = fieldScanner.next();
				empComp = fieldScanner.next();
				empComp = empComp.substring(1);
				Employee emp = new Employee(empName, empComp);
				list.add(emp);
			}
		}

		return list;
	}

	/**
	 * Create a new list of employees...
	 *
	 * @return A list of employees
	 */
	public static ArrayList<Employee> newShift() {
		int nEmployees = 0;
		System.out.print("Number of employees to enter: ");
		if (glbScan.hasNextInt())
			nEmployees = glbScan.nextInt();
		else {
			System.err.println("Bad format");
			return null;
		}

		System.out.println("\nEnter the names of the "+nEmployees+
			" employees and their competencies.\nIf a worker has no"+
			" competencies, write 'n' in the competence field.");

		Scanner fieldScanner;
		String empName = "";
		String empComp = "";
		boolean nameOK = false;
		boolean compOK = false;
		ArrayList<Employee> empList = new ArrayList<Employee>();
		Employee emp;

		String line = glbScan.nextLine();
		for (int i=0; i<nEmployees; i++) {
			System.out.print(i+1+": ");
			line = glbScan.nextLine();
			fieldScanner = new Scanner(line).useDelimiter(",");

			/* Scan for the name */
			if (fieldScanner.hasNext())
				empName = fieldScanner.next();
			else {
				System.err.println("wth?");
				return null;
			}

			/* Scan for competencies */
			if (fieldScanner.hasNext())
				empComp = fieldScanner.next();
			else {
				System.err.println("comp field is mandatory");
				return null;
			}

			/* More than two entries is not allowed */
			if (fieldScanner.hasNext()) {
				System.err.println("Too many fields.");
				return null;
			}
			else {
				if (!checkName(empName)) {
					System.err.println("Invalid name: '"+empName+"'");
					return null;
				}

				if (!checkComp(empComp)) {
					System.err.println("Invalid comp: '"+empComp+"'");
					return null;
				}
				else
					empComp = formatComp(empComp);
			}

			emp = new Employee(empName, empComp);
			if (emp.canDrive())
				empList.add(0, emp);
			else
				empList.add(emp);
		}

		return empList;
	}

	/**
	 * Add preferences...
	 */
	public static void
	addPrefs(ArrayList<Employee> list)
	throws IOException
	{
		FileWriter fw = new FileWriter("data.dzn", true); /* open to append */
		fw.write("P = [");
		int pref = -1;
		for (int i=0; i<list.size(); i++) {
			fw.write("| ");
			for (int j=0; j<list.size(); j++) {
				if (i == j) {
					fw.write("0");
				}
				else {
					System.out.print(list.get(i).getName()+"'s preference for ");
					System.out.print(list.get(j).toString()+": ");
					pref = glbScan.nextInt();
					if (pref < 1)
						pref = 1;
					else if (pref > list.size()-1)
						pref = list.size()-1;
					fw.write(String.valueOf(pref));
				}

				if (j == list.size()-1) {
					if (i == list.size()-1) {
						fw.write(" |");
					}
					else {
						fw.write("\n");
					}
				}
				else {
					fw.write(", ");
				}

			}
		}
		fw.write("];\n");
		fw.close();
	}

	/**
	 * Gernerate shift...
	 */
	public static void
	generate()
	throws IOException, InterruptedException
	{
		String appDir = "/home/erik/Dropbox/Master_folder/School/MSc/ht16/m4co/MiniZinc/MiniZincIDE-2.0.14-bundle-linux-x86_64/";
		String dataDir = "/home/erik/Dropbox/Master_folder/Misc_code/Java/Jobbapp/";
		String fzn = "flatzinc";
		String app ="mzn2fzn";
		String model = "solve.mzn";
		String data = "data.dzn";

		//$appdir$app $model $datadir$data
		//$appdir$fzn "solve.fzn"

		ProcessBuilder ps2 = new ProcessBuilder(appDir+app, dataDir+"solver/"+model, dataDir+data);
    Process pr2 = ps2.start();
    pr2.waitFor();

		ProcessBuilder ps = new ProcessBuilder(appDir+fzn, dataDir+"solver/solve.fzn");
    ps.redirectErrorStream(true);
    Process pr = ps.start();
    StringBuilder processOutput = new StringBuilder();
    try (BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(pr.getInputStream()));)
    {
    	String readLine;
    	while ((readLine = processOutputReader.readLine()) != null) {
    		processOutput.append(readLine+System.lineSeparator());
    	}
    	pr.waitFor();
    }
    System.out.println(processOutput);
    FileWriter fw = new FileWriter("data.dzn", true);
    fw.write("%"+processOutput.toString());
    fw.close();
	}

	/**
	 * Save list of employees to .dzn file.
	 *
	 * @param list The list of employees
	 * @throws IOException
	 * @throws InterruptedException
	 * @see #addPrefs
	 */
	public static void
	saveToFile(ArrayList<Employee> list)
	throws IOException, InterruptedException
	{
		int n = list.size();
		int d = 0;
		for (int i=0; i<n; i++) {
			if (list.get(i).canDrive())
				d++;
		}

		FileWriter fw = new FileWriter("data.dzn", true);
		fw.write("n = "+n+";\n");
		fw.write("d = "+d+";\n");
		for (int i=0; i<n; i++) {
			fw.write("%"+list.get(i).toString()+"\n");
		}
		fw.close();
	}

	public static void init()
	throws IOException, InterruptedException
	{
		System.out.print("Welcome to the employee CLI. ");
		System.out.println("To quit, press Ctrl+C.\n");

		System.out.println("To configure a new shift, type 'new'.");
		System.out.println("To load an existing shift, type 'load'.");
		ArrayList<Employee> empList = new ArrayList<Employee>();
		String cmd = glbScan.next();
		String sessionCmd = cmd;
		
		if (cmd.compareTo("new") == 0) {
			empList = newShift();
		}
		else if (cmd.compareTo("load") == 0) {
			empList = loadShift();
		}
		else {
			System.err.println("Invalid command.");
			return;
		}

		if (sessionCmd.compareTo("new") == 0) {
			System.out.print("\nSave shift to file? (y/n) ");
			cmd = glbScan.next();
			if (cmd.compareTo("y") == 0)
				saveToFile(empList);
		}
		
		System.out.print("\nAdd preferences? (y/n) ");
		cmd = glbScan.next();
		if (cmd.compareTo("y") == 0)
			addPrefs(empList);

		System.out.print("\nGenerate teams? (y/n) ");
		cmd = glbScan.next();
		if (cmd.compareTo("y") == 0)
			generate();

		System.out.println("\nExiting...");
	}
}
