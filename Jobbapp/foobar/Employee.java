/**
 * @author Erik Vesterlund
 */

package foobar;

import java.util.ArrayList;

/**
 * This class is intended to replace Employee.java
 */
public class Employee {

	/**
	 * Employee's name
	 */
	private String name;

	/**
	 * String of employee's competencies. Each letter
	 * represents a competency.
	 */
	private String competencies;

	/**
	 * Constructs an employee with name name and
	 * competencies comp.
	 *
	 * @param name Name of the employee
	 * @param comp A string of one-letter competencies
	 */
	public Employee(String name, String comp) {
		this.name = name;
		this.competencies = comp;
	}

	/**
	 * Gets the name of this employee
	 *
	 * @return The name of this employee
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the competencies of this employee
	 *
	 * @return The competencies of this employee
	 */
	public String getCompetencies() {
		return competencies;
	}

	/**
	 * Determine if this employee can drive, i.e. if
	 * his list of competencies includes the letter 'K'
	 *
	 * @return True if this employee can drive, false otherwise
	 */
	public boolean canDrive() {
		boolean ret = false;
		for (int i=0; i<getCompetencies().length(); i++) {
			if (getCompetencies().charAt(i) == 'K') {
				ret = true;
				break;
			}
		}
		return ret;
	}

	/**
	 * Returns a string representation of this employee.
	 */
	public String toString() {
		return name+", "+competencies;
	}
}