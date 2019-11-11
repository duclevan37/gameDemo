package toangLaRo;


public class enemy2 extends Enemy
{

	enemy2(road p)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.enemy = loader.getImage("resources/enemy2.png");
		this.position = p;
		this.anchorX = -20;
		this.anchorY = -20;
		this.velocity = 2;
	}
	
}