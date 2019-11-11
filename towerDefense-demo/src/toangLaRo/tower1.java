package toangLaRo;

import java.util.List;


public class tower1 extends Tower
{

	public tower1(Coordinate pos)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.tower = loader.getImage("resources/tower1.png");
		this.position = pos;
		this.anchorX = -40;
		this.anchorY = -40;
	}
	

	public void interact(towerDefenseGame towerDefenseGame, double deltaTime)
	{	// tracks time that effect has existed
		timeSinceLastFire += deltaTime;
		
		// if time less than 1.5 seconds, don't interact
		if(timeSinceLastFire < 2)
			return;
		
		List<Enemy> enemies = towerDefenseGame.enemies; // new list of enemies
		
		// Gives position of an enemy in enemy list
		for(Enemy e: enemies)
		{	
			
			// holds position of enemy
			Coordinate enemyPos = e.getPosition().getCoordinate();

			// Compute distance of enemy to tower
			double dx, dy, dist;	// change in x, y, and total distance
			
			// calculates change in x and y position 
			dx = enemyPos.x - position.x; // x position of enemy - tower
			dy = enemyPos.y - position.y; // y position of enemy - tower
		
			// use Pythagorean theorem to calculate distance
			dist = Math.sqrt((dx*dx) + (dy*dy));
			
			// holds position of effect
			Coordinate pos = new Coordinate(position.x, position.y);	
			
			// if enemy is in range, fire salt
			if(dist < 80)
			{	effectBullet1 stardust = new effectBullet1(pos, enemyPos);
				towerDefenseGame.effects.add(stardust);
				timeSinceLastFire = 0;
				return;
			}	
		} 
	}	
}
