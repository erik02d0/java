import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeWindow
    extends JFrame
    implements ActionListener
{
    
    private JPanel firstPanel = new JPanel(new GridLayout(7,1));
    private JPanel secondPanel = new JPanel();
    private JPanel thirdPanel = new JPanel();
    private JTextArea ta = new JTextArea(30,15);
    private JTextArea teamTextArea = new JTextArea(30,15);
    private JLabel nameLab = new JLabel("Namn: ");
    private JTextField nameField = new JTextField("", 10);
    private JCheckBox driveBox = new JCheckBox("Körtillstånd", false);
    private JButton addEmp = new JButton("Lägg till");
    private JButton createTeams = new JButton("Skapa lag");
    private JButton saveList = new JButton("Spara lista");
    private JButton openList = new JButton("Öppna lista");
    private ArrayList<Employee> listOfEmployees = new ArrayList<Employee>();
    
    public EmployeeWindow() {
        
        firstPanel.setPreferredSize(new Dimension(50,50));
        addEmp.setPreferredSize(new Dimension(30,15));
        
        setLayout(new GridLayout(1,3));
        add(firstPanel);
        add(secondPanel);
        add(thirdPanel);
        
        firstPanel.add(nameLab);
        firstPanel.add(nameField);
        firstPanel.add(driveBox);
        firstPanel.add(addEmp);
        firstPanel.add(createTeams);
        firstPanel.add(saveList);
        firstPanel.add(openList);
        
        addEmp.addActionListener(this);
        createTeams.addActionListener(this);
        saveList.addActionListener(this);
        openList.addActionListener(this);
        
        ta.setEditable(false);
        teamTextArea.setEditable(false);
        secondPanel.add(ta);
        thirdPanel.add(teamTextArea);
        thirdPanel.add(new JScrollPane(teamTextArea,
                                       JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        
        pack();
        setVisible(true);
        System.out.println(addEmp.getPreferredSize());
    }
    
    public void actionPerformed(ActionEvent e) {
        
        Scanner scName = new Scanner(nameField.getText());
        
        if (e.getSource() == addEmp) {
            if (scName.hasNext()) {
                Employee t_emp = new Employee(scName.next(), driveBox.isSelected());
                listOfEmployees.add(t_emp);
                ta.append(listOfEmployees.size()+". "+t_emp+"\n");
            }
            nameField.setText("");
            driveBox.setSelected(false);
            System.out.println(Employee.printList(listOfEmployees));
        }
        
        if (e.getSource() == createTeams) {
            if (Employee.numberOfDrivers(listOfEmployees) < Employee.numberOfTeams(listOfEmployees)) {
                dispose();
                JOptionPane.showMessageDialog(null, "För få förare.");
            }
            else {
                teamTextArea.setText("");
                Employee[] teams = Employee.createTeams(listOfEmployees);
                teamTextArea.append(Employee.printTeams(teams));
            }
            System.out.println(Employee.printList(listOfEmployees));
        }
        
        if (e.getSource() == saveList) {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showSaveDialog(EmployeeWindow.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    PrintWriter output = new PrintWriter(file);
                    output.println(Employee.printList(listOfEmployees));
                    output.close();
                } catch (IOException f) {
                    //f.printStackTrace();
                }
                
            }
        }
        
        if (e.getSource() == openList) {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(EmployeeWindow.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    Scanner sc = new Scanner(file);
                    while (sc.hasNextLine()) {
                        String token = sc.nextLine();
                        Employee newEmp;
                        if (token.endsWith(", K")) {
                            int len = token.length()-3;
                            newEmp = new Employee(token.substring(0,len), true);
                        }
                        else
                            newEmp = new Employee(token, false);
                        listOfEmployees.add(newEmp);
                    }
                    ta.append(Employee.printList(listOfEmployees));
                    System.out.println(Employee.printList(listOfEmployees));
                } catch (FileNotFoundException f) {
                    // exception handling
                }
            }
        }
    }
    
    public static void main(String[] args) {
        EmployeeWindow my_ew = new EmployeeWindow();
    }
}