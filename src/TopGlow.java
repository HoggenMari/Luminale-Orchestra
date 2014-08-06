import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import de.looksgood.ani.*;

public class TopGlow implements Effect{

	
	private PApplet p;
	private NozzleLayer nozzleLayer;
	private PGraphics pg;
	private int startGlow;
	float alpha;
	private AniSequence seq;
	private boolean dead;
	private ColorFade cf;
	private ColorFadeList cfList;
	private boolean cfStart;
	private ColorFade cf2;

	public TopGlow(PApplet p, NozzleLayer nozzleLayer, ColorFadeList cfList, int startGlow, int maxGlow){
	  System.out.println("KONSTRUKTOR");
	  this.p = p;
	  this.nozzleLayer = nozzleLayer;
	  this.alpha = startGlow;
	  this.cfList = cfList;
	  //this.pg = pg;
	  pg = nozzleLayer.getLayer();
	  
	  /*Ani.init(p);
	  
	  seq = new AniSequence(p);
	  seq.beginSequence();
		
	  seq.add(Ani.to(this, (float) 2.0, "alpha", 255, Ani.CIRC_IN));
	  seq.add(Ani.to(this, (float) 10.0, "alpha", 0, Ani.CIRC_OUT));

	  seq.endSequence();
	  
	  seq.start();*/
	  
	  cf = new ColorFade(p, 0, 0, 255, 0);
	  cf.alphaFade(255, (int)p.random(300,600), 1);
	  cfList.addColorFade(cf);
	  
	  //Ani.to(p, (float) 1.0, "alpha", 255, Ani.QUINT_IN);
		  
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		pg.beginDraw();
		pg.clear();
		pg.colorMode(PConstants.HSB, 360,255,255, 255);
		//System.out.println(alpha);
		pg.fill(cf.hue,cf.saturation,cf.brightness,cf.alpha);
		pg.noStroke();
		pg.rect(0, 4, pg.width, 1);
		pg.endDraw();
		//nozzleLayer.add();
		
		if(cf.isDead() && !cfStart){
			cf2 = new ColorFade(p, 0, 0, 255, 255);
			cf2.alphaFade(0, (int)p.random(2500,3000), 1);
			cfList.addColorFade(cf2);
			cfStart = true;
		} else if(cf.isDead()) {
			pg.beginDraw();
			pg.clear();
			pg.colorMode(PConstants.HSB, 360,255,255, 255);
			//System.out.println(alpha);
			pg.fill(cf2.hue,cf2.saturation,cf2.brightness,cf2.alpha);
			pg.noStroke();
			pg.rect(0, 4, pg.width, 1);
			pg.endDraw();
			//nozzleLayer.add();
			
			if(cf2.isDead()){
				dead = true;
			}
		}
		
		nozzleLayer.add();
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
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
