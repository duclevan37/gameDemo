package toangLaRo;
import java.awt.*;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;

abstract public class Effect 
{

	protected int posX, posY;
	protected double velocityX, velocityY;
	protected double ageInSeconds;
	protected Image picture;
	
	public void interact(towerDefenseGame towerDefenseGame, double deltaTime)
	{
		// Increments time
		ageInSeconds = ageInSeconds + deltaTime ;
		
		// Sets new position of Effect
		posX += velocityX*deltaTime;
		posY += velocityY*deltaTime;
		
		// see if stardust hit enemy
		List<Enemy> enemies = towerDefenseGame.enemies;
		for(Enemy e: new LinkedList<Enemy>(enemies))
		{
			// Compute distance of enemy to effect
			double dx, dy, dist;	// change in x, y, and total distance
			
			// calculates change in x and y position 
			dx = e.position.getCoordinate().x - posX; // x position of enemy - effect 
			dy = e.position.getCoordinate().y - posY; // y position of enemy - effect 
			
			// use Pythagorean theorem to calculate distance
			dist = Math.sqrt((dx*dx) + (dy*dy));
			
			
			//if enemy is in range of effect, destroy enemy
			if(dist < 40)
			{	
				towerDefenseGame.enemies.remove(e);
				towerDefenseGame.scoreCounter += 3;
				towerDefenseGame.killsCounter += 1;
			}
		}
	}

	public void draw(Graphics g)
	{
		// Draws effect
		g.drawImage(picture, posX, posY, null);
		
		// Draws dot on Effect's (x, y) coordinates
		g.setColor(Color.WHITE);
		g.fillOval(posX, posY, 5, 5);
	}
	
	public boolean isDone()
	{
		return ageInSeconds >= 1;
	}
}
