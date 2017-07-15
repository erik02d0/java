import java.awt.*;
import java.awt.Component;
import javax.swing.*;
import static javax.swing.GroupLayout.Alignment.*;

public class PanelModel extends JFrame {
    
    public PanelModel() {
        
        setLayout(new GridLayout(1,3));
        
        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.Y_AXIS));
        JButton firstButton = new JButton("SDFJSHDGSKDF");
        JButton secondButton = new JButton("text");
        JTextField tf = new JTextField("", 10);
        tf.setPreferredSize(new Dimension(15,15));
        firstPanel.add(tf);
        firstPanel.add(firstButton);
        firstPanel.add(secondButton);
        
        JPanel secondPanel = new JPanel();
        JTextArea ta = new JTextArea(30,15);
        secondPanel.add(ta);
        
        add(firstPanel);
        add(secondPanel);
        
        pack();
        setVisible(true);
    }
    
    public static void main(String[] args) {
        
        PanelModel pm = new PanelModel();
        
    }
}