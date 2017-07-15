import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class EmployeeGUI
    extends JFrame
    implements ActionListener
{
    /** The actual window */
    private EmployeeWindow empWin;

    /** The window has two buttons */
    private JButton chooseEmps = new JButton("Ny lagindelning");
    private JButton openTeamsFile = new JButton("Öppna lagindelningslista");
    
    public EmployeeGUI() {
        super("Lagindelning"); // window title
        
        this.setLayout(new GridLayout(2,1));
        
        this.add(chooseEmps);
        chooseEmps.addActionListener(this);
        
        this.add(openTeamsFile);
        openTeamsFile.addActionListener(this);
        
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == chooseEmps) {
            //dispose();
            empWin = new EmployeeWindow();
        }
        else if (e.getSource() == openTeamsFile) {
            System.err.println("Not yet implemented.");
        }
    }

    /**
     * This static method calls the EmployeeGUI constructor.
     *
     * @return 0 on error, 1 on success
     */
    public static int init() {
        if (1==0) {
            EmployeeGUI empgui = new EmployeeGUI();
            return 1;
        }
        else {
            System.err.println("Graphics have been disabled.");
            return 0;
        }
    }
}