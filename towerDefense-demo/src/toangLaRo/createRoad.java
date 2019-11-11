package toangLaRo;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class createRoad
{
	// Each createRoad object will have a list of coordinates:
	private List <Coordinate> path;
	

	public createRoad(Scanner s)
	{	
		path = new ArrayList<Coordinate>();		// creates new ArrayList of Coordinates
		int counter = s.nextInt(); 				// reads number of coordinates c
		
		for(int n = 0; n < counter; n++)		// loops n times
		{	
			// builds coordinate object
			Coordinate c = new Coordinate(s.nextInt(), s.nextInt());	
			path.add(c);	// adds coordinate object to path list
		}	
	}
	
	/**
    * Draws the current path to the screen (to wherever the graphics object points)
    * using a highly-visible color.
    * 
    * @param g   a graphics object
    */ 
	public void drawLine(Graphics g)
	{	
		g.setColor(Color.WHITE);	// sets path color
		
		for(int n = 0; n < path.size()-1; n++)	
		{	// draws a segment then repeats
			g.drawLine(path.get(n).x, path.get(n).y, path.get(n+1).x, path.get(n+1).y);
		}	
	}
	
	 /** Returns a new path position object that represents
     * the first position along this path.
     * 
     * @return the first position along this path
     */
    public road getStart()
    {	
        return new road(new ArrayList<Coordinate>(path));
    }
    
    /**
     * Returns the distance (in pixels) from the
     * specified point to the nearest path point.
     */
    public double distanceToPath (double px, double py)
    {
        double minDistance = Double.MAX_VALUE;
        
        for (int i = 0; i < path.size()-1; i ++)
        {
            // Get start and end points for a segment.
            
            double sx = path.get(i).x;
            double sy = path.get(i).y;
            double ex = path.get(i+1).x;
            double ey = path.get(i+1).y;
            
            // Computed segment vector from start to end, and its length, then normalize it.
            
            double vx = ex - sx;
            double vy = ey - sy;
            double vl = Math.sqrt(vx * vx + vy * vy);
            vx /= vl;
            vy /= vl;
            
            // Compute vector from start to point
            
            double dx = px - sx;
            double dy = py - sy;
            double dl = Math.sqrt(dx * dx + dy * dy);
            
            // Compute the distance along the segment to the nearest point (dot product)
            
            double segDist = dx * vx + dy * vy;
            
            // If the nearest point is off one end of the segment or the other, 
            //   just compute the distance to that endpoint, otherwise use Pythagorean theorem.
            
            double distance;
            if (segDist < 0)
                distance = Math.sqrt((vx-sx)*(vx-sx) + (vy-sy)*(vy-sy));
            else if (segDist > vl)
                distance = Math.sqrt((vx-ex)*(vx-ex) + (vy-ey)*(vy-ey));
            else
                distance = Math.sqrt(dl*dl - segDist*segDist);
            
            // If the distance is smaller than existing distance, keep it.
            
            if (distance < minDistance)
                minDistance = distance;
        }
        
        return minDistance;
    }

}
