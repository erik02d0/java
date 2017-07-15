import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class EmployeeList
    extends JFrame
    implements ActionListener
{
    
    private ArrayList<Employee> list;
    private JTextArea empArea = new JTextArea(30,20);
    private JButton teamsGenerate = new JButton("Skapa lagindelning");
    private JButton saveList = new JButton("Spara lista");
    private TeamsWindow teamWin;
    
    public EmployeeList(ArrayList<Employee> list) {
        
        this.setLayout(new GridLayout(3,1));
        teamsGenerate.addActionListener(this);
        this.list = list;
        empArea.setEditable(false);
        
        this.add(empArea);
        this.add(teamsGenerate);
        this.add(saveList);
        
        for (int i=0; i<list.size(); i++) {
            empArea.append(list.get(i)+"\n");
        }
        
        this.pack();
        this.setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == teamsGenerate) {
            if (Employee.numberOfDrivers(list) < Employee.numberOfTeams(list)) {
                dispose();
                JOptionPane.showMessageDialog(null, "För få förare.");
            }
            else {
                Employee[] teams = Employee.createTeams(list);
                teamWin = new TeamsWindow(teams);
            }
        }
    }
    
    public void addToList(Employee emp) {
        empArea.append(emp+"\n");
    }
    
}