package toangLaRo;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;


public class GamePanel extends JPanel implements MouseListener, MouseMotionListener
{
    /* Static variables */
   
    /* This static variable is just to avoid an Eclipse warning.  It serves no other purpose (for us). */
    
    private static final long serialVersionUID = -266426690684141363L;
    
     
    /* Object fields and methods */
   
    private towerDefenseGame enclosingTowerDefenseGame;  	// A reference back to the towerDefenseGame object that created 'this' object.
    public int mouseX;				// Tracks X position of mouse events
    public int mouseY;				// Tracks Y position of mouse events
    public boolean mouseIsPressed;	// Determines if mouse has been clicked or not
    

    public GamePanel (towerDefenseGame enclosingTowerDefenseGame)
    {
        // Keep track of the towerDefenseGame object that created this panel.
        //   That way, we can call methods in the game object when needed.
    	
    	this.addMouseListener(this); 			// Listen to our own mouse events.
    	this.addMouseMotionListener(this);		// Listen to mouse movements
        this.enclosingTowerDefenseGame = enclosingTowerDefenseGame;
    }
    

    public void paintComponent (Graphics g)
    {
        enclosingTowerDefenseGame.draw (g);
    }
    

    public Coordinate getCoordinate()
    {
    	return new Coordinate(mouseX, mouseY);
    }
    
    /* Overridden methods that report the correct panel size when needed. */
    
    public Dimension getMinimumSize ()
    {
        return new Dimension(800,600);
    }
    public Dimension getMaximumSize ()
    {
        return new Dimension(800,600);
    }
    public Dimension getPreferredSize ()
    {
        return new Dimension(800,600);
    }

    /* MouseListener methods */
    
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = true;
		
	}

	/* MouseMotionListener methods */
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{	
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = false;
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = false;

	}
}
