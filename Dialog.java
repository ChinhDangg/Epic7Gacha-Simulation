import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
public class Dialog extends JDialog implements WindowFocusListener
{
    public Dialog(JLabel content, JPanel main)
    {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(false);
        addWindowFocusListener(this);
        getContentPane().add(content);
        setResizable(false);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(main);
        setVisible(true);
    }
    
    public Dialog(JPanel content)
    {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setModal(false);
        addWindowFocusListener(this);
        getContentPane().add(content);
        setResizable(true);
        pack();
        setVisible(true);
    }
    
    public Dialog(JScrollPane content)
    {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setModal(false);
        addWindowFocusListener(this);
        getContentPane().add(content);
        setResizable(true);
        pack();
        setVisible(true);
    }
    
    public void windowLostFocus(WindowEvent e) 
    {
        this.setVisible(false);
    }
    
    public void windowGainedFocus(WindowEvent e)
    {
    }

    
}
