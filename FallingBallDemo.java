// going to be lazy about imports in these examples...
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   A program to demonstrate a simple animation of a ball that falls to
   the ground after being released from somewhere.

   @author Jim Teresco
   @version Spring 2020
*/

public class FallingBallDemo extends MouseAdapter implements Runnable {

    // list of FallingBall objects currently on the screen
    private java.util.List<FallingBall> list;
    
    private JPanel panel;

    /**
       The run method to set up the graphical user interface
    */
    @Override
    public void run() {
	
	// set up the GUI "look and feel" which should match
	// the OS on which we are running
	JFrame.setDefaultLookAndFeelDecorated(true);
	
	// create a JFrame in which we will build our very
	// tiny GUI, and give the window a name
	JFrame frame = new JFrame("FallingBall");
	frame.setPreferredSize(new Dimension(500,500));
	
	// tell the JFrame that when someone closes the
	// window, the application should terminate
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// JPanel with a paintComponent method
	panel = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
		    
		    // first, we should call the paintComponent method we are
		    // overriding in JPanel
		    super.paintComponent(g);

		    // redraw each ball at its current position, in reverse
		    // so we can remove the ones that are done
		    int i = list.size() - 1;
		    while (i >= 0 && !list.isEmpty()) {
			FallingBall b = list.get(i);
			if (b.done()) {
			    list.remove(i);
			}
			else {
			    b.paint(g);
			    i--;
			}
		    }
		}
	    };
	frame.add(panel);
	panel.addMouseListener(this);

	// construct the list
	list = new ArrayList<FallingBall>();
	
	// display the window we've created
	frame.pack();
	frame.setVisible(true);
    }

    /**
       Mouse press event handler to create a new FallingBall with its top
       centered at the press point.

       @param e mouse event info
    */
    @Override
    public void mousePressed(MouseEvent e) {

	FallingBall newBall = new FallingBall(e.getPoint(), panel);
	list.add(newBall);

	// calling start on the object that extends Thread results in
	// its run method being called once the operating system and
	// JVM have set up the thread
	newBall.start();
	panel.repaint();
    }

    public static void main(String args[]) {

	// The main method is responsible for creating a thread (more
	// about those later) that will construct and show the graphical
	// user interface.
	javax.swing.SwingUtilities.invokeLater(new FallingBallDemo());
    }
}
   
