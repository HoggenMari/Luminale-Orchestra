import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class HorizontalFineTube {

	private PApplet p;
	private NozzleLayer nozzleLayer;
	private ColorFade cf;
	private PGraphics pg;
	private int a1;
	private int m1;
	private int e1;
	private int y;
	private int current;
	private int timer;
	private int speed;
	private boolean dead;
	boolean middle;
	private int add = 1;

	public HorizontalFineTube(PApplet p, PGraphics pg, ColorFade cf, int a1, int m1, int e1, int speed){
		this.p = p;
		this.cf = cf;
		this.speed = speed;
		this.a1 = a1;
		this.m1 = m1;
		this.e1 = e1;

		y = -1;
		
		this.pg = pg;
	}
	
	public void draw(){
		pg.beginDraw();
		pg.colorMode(PConstants.HSB,360,255,255,255);
		pg.noStroke();
		pg.clear();
		pg.smooth();
	
		int amount = cf.alpha/a1;

		for(int i=0; i<a1; i++){
			pg.fill(cf.hue, cf.saturation, cf.brightness, i*amount);
			pg.rect(0, y-i, pg.width, 1);
		}
				
			pg.fill(cf.hue, cf.saturation, cf.brightness, a1*amount);
			pg.rect(0, y-a1+1, pg.width, -m1);
		
		
		amount = cf.alpha/e1;
		
		for(int i=0; i<e1; i++){
			pg.fill(cf.hue, cf.saturation, cf.brightness, cf.alpha-i*amount);
			pg.rect(0, y-i-a1-m1, pg.width, 1);
		}
		pg.endDraw();

		
		current = p.millis();
		if(current-timer>speed){
		y+=add ;
		timer=current;
		}

		if(y>a1+m1/2){
			add=-1;
			middle = true;
		}
	}
	
}
