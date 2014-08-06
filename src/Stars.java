import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class Stars implements Effect {


	private PApplet p;
	private LinkedList<Nozzle> path;
	private boolean dead = false;
	private NozzleLayer nozzleLayer;
	private PGraphics pg;
	private ArrayList<GlitterParticle> glitterList;
	private int color;
	private ColorFadeList cfl;
	private ColorFade cf1;

	public Stars(PApplet p, NozzleLayer nozzleLayer, int color, ColorFadeList cfl) {
		this.p = p;
		this.nozzleLayer = nozzleLayer;
		this.color = color;
		this.cfl = cfl;
		pg = nozzleLayer.getLayer();
		glitterList = new ArrayList<GlitterParticle>();
		
		for(int i=0; i<30; i++){
			glitterList.add(new GlitterParticle(p,pg.width));
		}
		
		cf1 = new ColorFade(p, color, 0, 255, 255);
		cf1.alphaFade(255, 3000, 2);
		cf1.saturationFade(0, 3000, 1);
		cfl.addColorFade(cf1);

	}

	public void draw() {
		
		pg.beginDraw();
		pg.clear();
		pg.noStroke();
		pg.colorMode(PConstants.HSB, 360, 255, 255, 255);
		for(Iterator<GlitterParticle> glitterIterator = glitterList.iterator(); glitterIterator.hasNext();){
			GlitterParticle glitter = glitterIterator.next();
			pg.stroke(cf1.hue, cf1.saturation, cf1.brightness, glitter.lifetime);
			pg.strokeWeight(1);
			glitter.update();	  
			pg.point(p.random(glitter.x-20, glitter.x), p.random(0, pg.height));
			if(glitter.lifetime<0){
				//System.out.println("DEAD");
				glitterIterator.remove();
			}
		  }
		pg.endDraw();
		nozzleLayer.add();
		
		if(glitterList.isEmpty()){
			dead = true;
		}
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return dead;
	}
	
	class GlitterParticle{
		
		public float x;
		public int lifetime;

		public GlitterParticle(PApplet p, int x){
			x=(int)p.random(-x,0);
			lifetime=(int) p.random(100,255);
		}
		
		public void update(){
			lifetime --;
			x++;
		}
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
