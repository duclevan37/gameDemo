package toangLaRo;

import java.awt.*;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


enum GameState { SETUP, UPDATE, DRAW, WAIT, END }


public class towerDefenseGame implements Runnable
{
    /* Static methods */
    

    public static void main (String[] args)
    {
        // Just create a game object.  The game constructor
        //   will do the rest of the work.
        
        new towerDefenseGame();
          
        // Main exits, but our other thread of execution
        //   will keep going.  We could do other work here if
        //   needed.
    }
    
    /* Object fields and methods */
    private Image backdrop;				// background star image		
    private createRoad line;			// path coordinates
    
    private GamePanel gamePanel;		// gamePanel object 
    private GameState state;	   		// The current game state
    
    private int frameCounter;			// keeps track of frame updates
    private long lastTime;				// keeps track of time
    
    private boolean placingTower1;	// true if tower is being placed
    private Tower newTower1; 		// variable to hold new tower objects
    private double elapsedTime;			// time trackers
    
    private boolean placingTower2;			// true if tower is being placed
    private Tower newTower2; 				// variable to hold new tower objects
    private boolean gameIsOver;			// indicates if game is lost
    private boolean gameIsWon;			// indicates if game is won
    
    int livesCounter; 					// counter for lives left
    int scoreCounter;					// points the user earns
    int killsCounter;					// number of enemies destroyed
    
    /* create enemies */
    List<Enemy> enemies;				// list of enemy objects
    
    /* create towers */
    List<Tower> towers;					// list of tower objects
    
    /* create effects */
    List<Effect> effects;				// list of effect objects
    
    // You will declare other variables here.  These variables will last for
    //   the lifetime of the game, so don't store temporary values or loop counters
    //   here.


    public towerDefenseGame()
    {
        // The game starts in the SETUP state.
        
        state = GameState.SETUP;
        gamePanel = new GamePanel(this);
        // Create a thread of execution and run it.
        
        Thread t = new Thread(this);
        t.start();  // Our run method is now executing!!!
    }

    public void run ()
    {
        // Loop forever, or until the user closes the game window,
        //   whichever comes first.  ;)
        
        while (true)
        {
            // Test our game state, and do the appropriate action.
            
            if (state == GameState.SETUP)
            {
                doSetupStuff();
            }
            
            else if (state == GameState.UPDATE)
            {
                doUpdateTasks();
            }
            
            else if (state == GameState.DRAW)
            {
                // We don't actually force the drawing to happen.
                //   Instead, we 'request' it of the panel.
                
                gamePanel.repaint();  // redraw screen
                
                // We must wait for the drawing.  It will happen at some time in the near future.
                //   Since we are in an infinite loop, we could just loop until we leave the draw
                //   state.  This would waste battery life on a low power device, so instead
                //   I choose to sleep the current thread for a very short while (so that it
                //   will be briefly inactive).
                
                try { Thread.sleep(5); } catch (Exception e) {}
                
                // Do not advance the state here.  The 'draw' method will advance the state after it draws.
            }
            
            else if (state == GameState.WAIT)
            {
                // Wait 1/10th a second.  This code is not ideal, we'll explore a better way soon.
                
                try { Thread.sleep(100); } catch (Exception e) {}
                
                // Drawing is complete, waiting is complete.  It is time to move
                //   the objects in the game again.  Re-enter the UPDATE state.
                
                state = GameState.UPDATE;
            }
            
            else if (state == GameState.END)
            {
                // Do cleanup if any.  (We don't need to do anything here yet.)
            }
        }
    }
    

    private void doSetupStuff ()
    {
        // Do setup tasks
        // Create the JFrame and the JPanel
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.setTitle("oop-demo");
        f.setContentPane(gamePanel);
        f.pack();
        f.setVisible(true);

    	// creates a new ImageLoader object and loads the background image
		ImageLoader loader = ImageLoader.getLoader();
        backdrop = loader.getImage("resources/star1.jpg");
        
        JOptionPane.showMessageDialog(null,  "Hello:\n" +
        		"1. This is demo-towerDefense.\n" +
        		"2. NguyenPhuongHieu - LeVanDuc - DangHuuTien.\n" +
        		"3. We need more time :< .\n" +
                "---thanks---"
        		);
        
        // fill counters
        livesCounter = 10;		// gives the player 10 lives
        scoreCounter = 500;		// give the user 500 points to begin
        killsCounter = 0;		// begin with 0 kills
        
        // Reset the frame counter and time 
        frameCounter = 0;
        lastTime = System.currentTimeMillis();
        
        // Use the loader to build a scanner on the path data text file, then build the 
        // path points object from the data in the file.
		ClassLoader myLoader = this.getClass().getClassLoader();
        InputStream pointStream = myLoader.getResourceAsStream("resources/path_1.txt");
        Scanner s = new Scanner (pointStream);
        line  = new createRoad(s);

        // Fill enemy list with new LinkedList
        enemies = new LinkedList<Enemy>();
        
        // Fill tower list with new LinkedList
        towers = new LinkedList<Tower>();
        
        // Fill effects list with new LinkedList
        effects = new LinkedList<Effect>();
        
        // initialize
        placingTower1 = false;
        newTower1 = null;
        
        // initialize
        placingTower2 = false;
        newTower2 = null;
        	
        // initialize
        gameIsOver = false;
    	gameIsWon = false;
        
        // Change the game state to start the game.
        state = GameState.UPDATE;  // You could also enter the 'DRAW' state.
    }
    

    private void doUpdateTasks()
    {	
    	if(gameIsOver)
    	{	state = GameState.DRAW;
    		return;
    	}
    	
    	if(gameIsWon)
    	{	state = GameState.DRAW;
    		return;
    	}
    	
    	// See how long it was since the last frame.
        long currentTime = System.currentTimeMillis();  // Integer number of milliseconds since 1/1/1970.
        elapsedTime = ((currentTime - lastTime) / 1000.0);  // Compute elapsed seconds
        lastTime = currentTime;  // Our current time is the next frame's last time
    	
        /* I think my elapsed time may be wrong */
        
    	// for each tower, interact in this game
    	for(Tower t: new LinkedList<Tower>(towers))
    	{	
    		t.interact(this, elapsedTime);
    		
    	}
        // for each effect, interact in this game      
    	for(Effect e: new LinkedList<Effect>(effects))
    	{	
    		e.interact(this, elapsedTime);
    		if(e.isDone())
    			effects.remove(e);	// add to list that has reached the end	
    	}
    	
    	// Advance each enemy on the path.
    	for(Enemy e: new LinkedList<Enemy>(enemies))
    	{	
    		e.advance();
     		if(e.getPosition().isAtTheEnd())
    		{
    			enemies.remove(e);	// add to list that has reached the end
    			livesCounter--;		// if they have reached the end, reduce lives
    		}

    	}
    	
        // Fill elements in an enemy list
        this.generateEnemies();
        
    	// increments frame counter 
    	frameCounter++;
    	
    	// Place towers if user chooses
    	this.placeTower1();
    	this.placeTower2();
    	
    	if(livesCounter <= 0)
    	{	gameIsOver = true;
    		livesCounter = 0;
    	}
    	
    	if(killsCounter >= 500)
    	{	gameIsWon = true;
    		killsCounter = 500;
    	}
    	
        // After we have updated the objects in the game, we need to
        //   redraw them.  Enter the 'DRAW' state.
        
        state = GameState.DRAW;
       
        // Careful!  At ANY time after we set this state, the draw method
        //   may execute.  Don't do any further updating.
    }
    

    public void draw(Graphics g)
    {
        // If we're not in the DRAW state, do not draw!
        
        if (state != GameState.DRAW)
            return;
        	
        // Draw the background image.
        g.drawImage(backdrop, 0, 0, null); 
     
        // Draw the path
        g.setColor(new Color (196, 190, 186));
        int[] xPos = new int[]{0, 64, 118, 251, 298, 344, 396, 416, 437, 459, 460, 498, 542, 600, 600, 568, 535, 509, 490, 481, 456, 414, 345, 287, 227, 98, 0};
        int[] yPos = new int[]{329, 316, 291, 189, 163, 154, 165, 186, 233, 344, 364, 415, 444, 461, 410, 396, 372, 331, 226, 195, 151, 117, 105, 117, 143, 244, 280};
        g.fillPolygon(xPos, yPos, 27);
        
        // Draw planet 
        g.setColor(new Color(65, 225, 204));
        g.fillArc(550, 385, 100, 100, 90, 180);
        g.setColor(Color.GREEN);
        int[] xCor = new int[]{600, 588, 574, 566, 557, 557, 563, 572, 576, 584, 600};
        int[] yCor = new int[]{459, 464, 462, 453, 454, 448, 438, 435, 422, 414, 415};
        g.fillPolygon(xCor, yCor, 11);
        
        // Draw the line along the path.
        line.drawLine(g);
        
        // draw all enemies before menu bar
    	for(Enemy e: new LinkedList<Enemy>(enemies))
    		e.draw(g);
    	
        // draw all towers in list
        for(Tower t: new LinkedList<Tower>(towers))
        	t.draw(g);
        	
    	// draw all towers in list
    	for(Effect s: new LinkedList<Effect>(effects))
    		s.draw(g);
    	
        // draw menu bar
        g.setColor(Color.WHITE);
        g.fillRect(600, 0, 200, 600);
        
        // draw score & life counters to menu bar
        g.setColor(Color.BLACK);
        g.setFont(new Font("Lucidia Sans", Font.BOLD, 16));
        g.drawString("Lives Remaining: " + livesCounter, 605, 100);	// lives counter
        g.drawString("Money Earned: " + scoreCounter, 605, 150);	// score counter
        g.drawString("Enemies Stopped: " + killsCounter, 605, 200);
        g.drawString("Tower1 Cost: 100", 610, 380);				// cost for black hole towers
        g.drawString("Tower2 Cost: 200", 640, 530);					// cost for sun towers
        g.setFont(new Font("Lucidia Sans", Font.ITALIC, 28));		
        g.drawString("Tower Defense demo", 600, 50);					// writes title
        g.drawLine(600, 50, 800, 50);								// underscore
        g.drawString("Towers", 640, 240);							// writes towers
        g.drawLine(620, 240, 780, 240);								// underscore	
        
        // draw box around T1 icon
        g.setColor(new Color(224, 153, 49));
        g.fillRect(650, 250, 100, 100);
        
        // draw tower in menu area
        tower1 T1 = new tower1(new Coordinate(700, 300));
        T1.draw(g);
        
        // draw box around T2 icon
        g.setColor(new Color(224, 93, 90));
        g.fillRect(650, 400, 100, 100);
        
        // draw tower in menu area
        tower2 T2 = new tower2(new Coordinate(700, 450));
        T2.draw(g);
        
        // draws T1 object with mouse movements
        if(newTower1 != null)
        	newTower1.draw(g);

        // draws T2 object with mouse movements
        if(newTower2 != null)
        	newTower2.draw(g);
        
        ImageLoader loader = ImageLoader.getLoader();	
		Image endGame = loader.getImage("resources/game_over.png"); // load game over image
    	
        if(livesCounter <= 0)										// if game is lost
        	g.drawImage(endGame, 0, 0, null);						// draw "game over"

		if(killsCounter >= 500)										// if game is lost
		{	g.setFont(new Font("Braggadocio", Font.ITALIC, 90));		
        	g.drawString("You Win!!!", 10, 250);					// draw "game over"
		}
		
        // Drawing is now complete.  Enter the WAIT state to create a small
        //   delay between frames.
        
        state = GameState.WAIT;
    }
    
    /**
     * Generates a stream of enemies
     * 
     */
    public void generateEnemies()
    {	
    	// adds enemies to list dependent on how many frames have passed
    	if(frameCounter % 30 == 0)								// slow 
    	{
    		enemies.add(new enemy2(line.getStart()));
    	}
 		else if(frameCounter % 25 == 0 && frameCounter >= 50)	// slow
 		{
 			enemies.add(new enemy2(line.getStart()));
 		}
	 	else if(frameCounter % 20 == 0 && frameCounter >= 100)	// medium
	 	{
	 		enemies.add(new enemy2(line.getStart()));
	 		enemies.add(new enemy1(line.getStart()));
	 	}	
 		else if(frameCounter % 15 == 0 && frameCounter >= 150)	// medium
 		{
 			enemies.add(new enemy2(line.getStart()));
 			enemies.add(new enemy1(line.getStart()));
 		}
	 	else if(frameCounter % 10 == 0 && frameCounter >= 200)	// fast
	 	{
	 		enemies.add(new enemy2(line.getStart()));
	 		enemies.add(new enemy1(line.getStart()));
	 		enemies.add(new enemy3(line.getStart()));
	 	}
	 	else if(frameCounter % 5 == 0 && frameCounter >= 250)	// fast
	 	{
	 		enemies.add(new enemy2(line.getStart()));
	 		enemies.add(new enemy1(line.getStart()));
	 		enemies.add(new enemy3(line.getStart()));
	 	}
    }
    

    public void placeTower1()
    {
    	/* I need to make it so you can't place towers on path or off the screen */
    	
    	 // variable to hold mouse location
    	Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);
    	
    	// moves the tower object as mouse moves
    	if(gamePanel.mouseX > 650 && gamePanel.mouseX < 750 && 
    		gamePanel.mouseY > 250 && gamePanel.mouseY < 350 && 
    		gamePanel.mouseIsPressed && scoreCounter >= 100)
    	{	// if mouse is pressed on tower icon, create a new object
	    		placingTower1 = true;
	    		newTower1 = new tower1(mouseLocation);
    	}    
    	else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 600 && 
        	gamePanel.mouseY > 0 && gamePanel.mouseY < 600 && 
        	gamePanel.mouseIsPressed && placingTower1
        	&& line.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 60)
    	{	// if mouse is pressed on game screen, place tower on game screen
	    		newTower1.setPosition(mouseLocation);
	    		towers.add(new tower1(mouseLocation));
	    		scoreCounter -= 100;
	    		newTower1 = null;
	    		placingTower1 = false;
    	}
    	
    	// moves tower object with mouse movements
    	if(newTower1 != null)
    	{
    		newTower1.setPosition(mouseLocation);
    	}	
    }
    

    public void placeTower2()
    {
    	/* I need to make it so you can't place towers on path or off the screen */
    	
    	 // variable to hold mouse location
    	Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);
    	
    	// moves the tower object as mouse moves
    	if(gamePanel.mouseX > 650 && gamePanel.mouseX < 750 && 
    		gamePanel.mouseY > 400 && gamePanel.mouseY < 500 && 
    		gamePanel.mouseIsPressed && scoreCounter >= 300)
    	{	// if mouse is pressed on tower icon, create a new object
	    		placingTower2 = true;
	    		newTower2 = new tower2(mouseLocation);
    	}    
    	else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 600 && 
        	gamePanel.mouseY > 0 && gamePanel.mouseY < 600 && 
        	gamePanel.mouseIsPressed && placingTower2
        	&& line.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 60)
    	{	// if mouse is pressed on game screen, place tower on game screen
	    		newTower2.setPosition(mouseLocation);
	    		towers.add(new tower2(mouseLocation));
	    		scoreCounter -= 300;
	    		newTower2 = null;
	    		placingTower2 = false;
    	}
    	
    	// moves tower object with mouse movements
    	if(newTower2 != null)
    	{
    		newTower2.setPosition(mouseLocation);
    	}	
    }
}	