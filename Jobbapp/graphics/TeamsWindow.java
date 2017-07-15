import java.awt.*;
import javax.swing.*;
import java.util.Arrays;

public class TeamsWindow
    extends JFrame
    //implements ActionListener
{
    
    private Employee[] teams;
    private JTextArea listArea = new JTextArea(40,20);
    
    public TeamsWindow(Employee[] teams) {
        
        this.teams = teams;
        listArea.setEditable(false);
        
        this.add(listArea, BorderLayout.NORTH);
        
        listArea.append(Employee.printTeams(teams));
        
        this.pack();
        this.setVisible(true);
        
    }
    
}