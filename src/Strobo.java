import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class Strobo implements Effect{
	
	private Layer nozzleLayer;
	private PGraphics pg;
	private int brightness;
	private double timer;
	private PApplet p;
	private int speed;
	private int counter=0;
	private boolean dead = false;

	public Strobo(PApplet p, Layer nozzleLayer, int speed){
		this.p = p;
		this.nozzleLayer = nozzleLayer;
		this.speed = speed;
		pg = nozzleLayer.getLayer();
		timer = p.millis();
	}
	
	public void draw(){
		pg.beginDraw();
		pg.clear();pg.colorMode(PConstants.HSB, 360, 100, 100,255);
		pg.noStroke();
		pg.fill(0,0,100,brightness);
		pg.rect(0, 0, pg.width, pg.height);
		nozzleLayer.add();
		double current = p.millis();
		if(current-timer>speed){
			if(brightness!=0){
				brightness=0;
			}else{
				brightness=255-counter;
			}
			timer=current;
			//speed++;
			counter+=5;
		}
		
		if(counter>255){
			dead  = true;
		}
		
	}
	
	public boolean isDead(){
		return dead;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean fadeBack() {
		// TODO Auto-generated method stub
		return false;
	}

}
