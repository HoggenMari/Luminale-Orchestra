import processing.core.PApplet;
import processing.core.PGraphics;


public class Line {

	private PApplet p;
	private Layer nozzleLayer;
	private int speed;
	private PGraphics pg;
	private double timer;
	private int x;
	private int y;
	private boolean up;
	
	private boolean dead = false;
	
	public Line(PApplet p, Layer nozzleLayer, int speed){
		this.p = p;
		this.nozzleLayer = nozzleLayer;
		this.speed = speed;
		pg = nozzleLayer.getLayer();
		timer = p.millis();
		
		x = 0;
		y = 0;
		up = true;
	}
	
	public void draw(){
		System.out.println("GO");
		pg.beginDraw();
		pg.clear();
		pg.noStroke();
		pg.fill(255,255,255);
		pg.rect(x, y,1,1);
		pg.endDraw();
		nozzleLayer.add();
		
		double current = p.millis();
		if(current-timer>speed){
			
			if(up){
			if(y<=3){
				y++;
			}else {
				x++;
				up = !up;
			}
			}else{
			if(y>0){
				y--;
			}else {
				x++;
				up = !up;
			}	
			}
			timer=current;
		}
		
		/*if(y>pg.height && x>pg.width){
			dead = true;
		}*/
		
	}
	
	public boolean isDead(){
		return dead;
	}
}
