import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PGraphics;


public class WarmCold {
	
	private PApplet p;
	private PGraphics pg;
	private Tween t;
	private int color;

	public WarmCold(PApplet p, PGraphics pg){
		this.p = p;
		this.pg = pg;
		
		p.colorMode(p.HSB, 360,100,100);
	    color = p.color(202,100,100,100);
		t = new Tween(300)
	    .addColor(this, "color", p.color(0, 0, 100,100))
	    .play(); 
	}
	
	public void draw(){
		pg.beginDraw();
		pg.fill(color);
		pg.rect(0,0,pg.width,pg.height);
		pg.endDraw();
	}
	
	
	

}
