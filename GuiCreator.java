import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GuiCreator extends JFrame
{
    public GuiCreator()
    {
        super("Seats");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.add(new SeatingPanel());

        pack();    

        setVisible(true);
    }

    public static void main(String[] args) {
        new GuiCreator();
    }
}

class SeatingPanel extends JPanel
{
    public SeatingPanel()
    {
        super(new BorderLayout());

        JPanel panel4seating = new JPanel();//creating a grid panel
        panel4seating.setLayout(new GridLayout(4, 10));//setting the layout of the grid panel

        JButton [] seats = new JButton [40]; //creating a pointer to the buttonsArray
        for (int i = 0; i < 40; i++)
        {
            seats[i] = new JButton();//creating the buttons
            //better to set the preferred size of the button
            seats[i].setPreferredSize(new Dimension(50,25));
            panel4seating.add(seats[i]);
        }

        add(panel4seating, BorderLayout.CENTER);
    }
}