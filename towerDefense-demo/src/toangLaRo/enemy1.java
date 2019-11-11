package toangLaRo;


public class enemy1 extends Enemy
{

	enemy1(road p)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.enemy = loader.getImage("resources/enemy1.png");
		this.position = p;
		this.anchorX = -20;
		this.anchorY = -20;
		this.velocity = 6;
	}

}
