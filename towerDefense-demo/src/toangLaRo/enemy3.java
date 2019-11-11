package toangLaRo;


public class enemy3 extends Enemy
{

	enemy3(road p)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.enemy = loader.getImage("resources/enemy3.png");
		this.position = p;
		this.anchorX = -25;
		this.anchorY = -25;
		this.velocity = 8;
	}
	
}
